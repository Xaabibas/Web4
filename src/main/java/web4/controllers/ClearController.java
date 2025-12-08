package web4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web4.jwt.JwtUtil;
import web4.attempt.AttemptService;

@RestController
@RequestMapping("/api")
public class ClearController {
    @Autowired
    private AttemptService service;
    @Autowired
    private JwtUtil jwtUtil;

    @DeleteMapping("/clear")
    public ResponseEntity<String> clear(@RequestHeader("Authorization") String header) {
        String username = jwtUtil.getUsernameFromHeader(header);
        service.clear(username);
        return ResponseEntity.ok("{\"message\": \"Данные очищены\"}");
    }
}
