package web4.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web4.jwt.JwtUtil;
import web4.attempt.Attempt;
import web4.attempt.AttemptService;
import web4.attempt.Point;

@RestController
@RequestMapping("/api")
public class CheckController {
    @Autowired
    private AttemptService service;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestBody Point point, @RequestHeader("Authorization") String header) throws JsonProcessingException {
        String username = jwtUtil.getUsernameFromHeader(header);
        Attempt attempt = service.process(point, username);
        return ResponseEntity.ok(mapper.writeValueAsString(attempt));
    }
}
