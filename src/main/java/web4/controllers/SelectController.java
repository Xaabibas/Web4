package web4.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web4.PageResponse;
import web4.attempt.GraphPointDTO;
import web4.jwt.JwtUtil;
import web4.attempt.Attempt;
import web4.attempt.AttemptService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SelectController {
    @Autowired
    private AttemptService service;
    @Autowired
    private JwtUtil jwtUtil;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/select")
    public ResponseEntity<?> get(Pageable pageable, @RequestHeader("Authorization") String header) throws JsonProcessingException {
        String username = jwtUtil.getUsernameFromHeader(header);
        PageResponse response = new PageResponse(
                service.getSlice(pageable.getPageNumber(), pageable.getPageSize(), username),
                service.count(username)
        );
        return ResponseEntity.ok(mapper.writeValueAsString(response));
    }

    @GetMapping("/select/graph")
    public ResponseEntity<?> graph(@RequestHeader("Authorization") String header) throws JsonProcessingException {
        String username = jwtUtil.getUsernameFromHeader(header);
        PageResponse response = new PageResponse(
                service.getPointsForGraph(username),
                service.count(username)
        );
        return ResponseEntity.ok(mapper.writeValueAsString(response));
    }
}
