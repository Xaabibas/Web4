package web4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web4.jwt.JwtResponse;
import web4.jwt.JwtUtil;
import web4.user.User;
import web4.user.UserRequest;
import web4.user.UserService;

@RestController
@RequestMapping("/api")
public class RegisterController {
    @Autowired
    private UserService service;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        User user = service.registerUser(request);
        String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
