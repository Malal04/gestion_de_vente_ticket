package tiket.isep.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tiket.isep.auth.models.AuthRequest;
import tiket.isep.auth.models.AuthResponse;
import tiket.isep.auth.models.RegisterRequest;
import tiket.isep.tokens.models.Token;
import tiket.isep.tokens.models.enums.TypeToken;
import tiket.isep.tokens.repositories.TokenRepository;
import tiket.isep.tokens.services.JwtService;
import tiket.isep.users.models.entities.User;
import tiket.isep.users.models.enums.Role;
import tiket.isep.users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getNomComplete() == null || request.getTelephone() == null || request.getPassword() == null
                || request.getEmail() == null) {
            return AuthResponse.builder().message("Merci de renseigner tous les champs").build();
        }

        if (request.getNomComplete() == "" || request.getTelephone() == "" || request.getPassword() == ""
                || request.getEmail() == "") {
            return AuthResponse.builder().message("Merci de renseigner tous les champs").build();
        }

        Optional<User> checkIfExists = repository.findByEmail(request.getEmail());

        if (checkIfExists.isPresent()) {
            return AuthResponse.builder().message("Un compte est déjà associé à l'adresse email renseigné")
                    .build();
        }

        User user = User.builder()
                .nomComplete(request.getNomComplete())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = repository.save(user);
        String jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthResponse.builder()
                .nomComplete(request.getNomComplete())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .accessToken(jwtToken)
                .build();

    }

    public AuthResponse authenticate(AuthRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());
        AuthResponse response;
        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = repository.findByEmail(request.getEmail())
                    .orElseThrow();


            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            saveUserRefreshToken(user, refreshToken);

            response = AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .nomComplete(user.getNomComplete())
                    .telephone(user.getTelephone())
                    .email(user.getEmail())
//                    .role(user.getRole())
                    .build();

        }catch (Exception ex) {
            response = AuthResponse.builder()
                    .message("Nom d'utilisateur ou mot de passe invalide")
                    .build();
        }
        return response;
    }

    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .typeToken(TypeToken.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void saveUserRefreshToken(User user, String refreshToken) {
        Token token = Token.builder()
                .user(user)
                .token(refreshToken)
                .typeToken(TypeToken.REFRESH) // Définir le type de token comme REFRESH
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


}
