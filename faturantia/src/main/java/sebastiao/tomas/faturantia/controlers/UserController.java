package sebastiao.tomas.faturantia.controlers;


import sebastiao.tomas.faturantia.repositories.UserRepository;
import sebastiao.tomas.faturantia.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;



import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);




    @Autowired
    private UserRepository userRepository;

    // Método para obter todos os usuários
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> getUsers() {
        logger.info("Sending all Users");
        return userRepository.findAll();
    }

    // Método modificado para login usando JWT
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (!foundUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        logger.info("User logged in with email: " + user.getEmail());
        return ResponseEntity.ok(foundUser);
    }
   

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
    // Método para obter usuário por id
    
    
    @GetMapping(path = "/check-email")
    public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    // Método modificado para registro de usuário com hash de senha
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
    if (userRepository.existsByEmail(newUser.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
    }


    User savedUser = userRepository.save(newUser);
    logger.info("New user registered with email: " + newUser.getEmail());
    return ResponseEntity.ok(savedUser);
    }

    // Método para atualizar informações do usuário
    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
            .map(existingUser -> {
                // Atualiza os campos necessários
                if(updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
                if(updatedUser.getPassword() != null) existingUser.setPassword(updatedUser.getPassword());
                if(updatedUser.getName() != null) existingUser.setName(updatedUser.getName());
                if(updatedUser.getLocation() != null) existingUser.setLocation(updatedUser.getLocation());
                // Salva as alterações
                userRepository.save(existingUser);
                logger.info("User with id: " + id + " updated.");
                return ResponseEntity.ok(existingUser);
            })
            .orElse(ResponseEntity.notFound().build());
    }


}
