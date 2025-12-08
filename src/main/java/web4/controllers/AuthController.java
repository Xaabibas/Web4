package web4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web4.jwt.JwtResponse;
import web4.jwt.JwtUtil;
import web4.user.UserRequest;
import web4.user.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody UserRequest request) {
        UserDetails userDetails = userService.authUser(request);
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
