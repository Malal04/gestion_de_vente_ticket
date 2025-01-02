package tiket.isep.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tiket.isep.auth.models.AuthRequest;
import tiket.isep.auth.models.AuthResponse;
import tiket.isep.auth.models.RegisterRequest;
import tiket.isep.auth.services.AuthService;
import tiket.isep.tokens.models.Token;
import tiket.isep.tokens.repositories.TokenRepository;
import tiket.isep.tokens.services.JwtService;
import tiket.isep.users.models.entities.User;
import tiket.isep.users.services.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final JwtService jwtService;

    private final UserService userService;

    private final TokenRepository tokenRepo;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String refreshToken) {
        Token token = tokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token invalid"));

        if (token.isExpired() || token.isRevoked()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired or revoked");
        }

        User user = token.getUser();
        String newJwt = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        token.setToken(newRefreshToken);
        tokenRepo.save(token);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(newJwt)
                .refreshToken(newRefreshToken)
                .nomComplete(user.getNomComplete())
                .telephone(user.getTelephone())
                .email(user.getEmail())
                .role(user.getRole())
                .build());
    }

    @GetMapping("/check/session")
    public ResponseEntity<?> checkSession() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            return ResponseEntity.ok(userService.findByEmail(username));
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" User est n'est pas authenticated ");

        }
    }


}
