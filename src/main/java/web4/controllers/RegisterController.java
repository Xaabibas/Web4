package web4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web4.jwt.JwtResponse;
import web4.jwt.JwtUtil;
import web4.user.UserRequest;
import web4.user.User;
import web4.user.UserRepository;

@RestController
public class RegisterController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateKeyException("Пользователь с таким именем уже зарегистрирован");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));

        repository.save(user);
        String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
