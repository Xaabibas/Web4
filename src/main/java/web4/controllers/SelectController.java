package web4.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public class Data {
        public List<Attempt> content;
        public long totalElements;
    }

    @GetMapping("/select")
    public ResponseEntity<?> get(Pageable pageable, @RequestHeader("Authorization") String header) throws JsonProcessingException {
        String username = jwtUtil.getUsernameFromHeader(header);
        List<Attempt> list = service.getSlice(pageable.getPageNumber(), pageable.getPageSize(), username);
        Data data = new Data();
        data.content = list;
        data.totalElements = service.count(username);
        return ResponseEntity.ok(mapper.writeValueAsString(data));
    }
}
