package tiket.isep.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiket.isep.users.models.dtos.UserD;
import tiket.isep.users.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Récupérer tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<UserD>> getAllUsers() {
        List<UserD> users = userService.getAllUsers().stream()
                .map(user -> new UserD(user.getId(), user.getNomComplete(), user.getTelephone(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserD> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(user -> new UserD(user.getId(), user.getNomComplete(), user.getTelephone(), user.getEmail(), user.getRole()))
                .map(userD -> new ResponseEntity<>(userD, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUserById(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
