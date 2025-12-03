package web4.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web4.model.Attempt;
import web4.model.AttemptDAO;
import web4.model.Checker;
import web4.model.Point;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class CheckController {
    @Autowired
    private Checker checker;
    @Autowired
    private AttemptDAO dao;
    private final ObjectMapper mapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @PostMapping("/check")
    public String check(@RequestBody Point point) throws JsonProcessingException {
        Attempt attempt = processRequest(point);
        System.out.println(mapper.writeValueAsString(attempt));
        return mapper.writeValueAsString(attempt);
    }

    private Attempt processRequest(Point point) {
        LocalDateTime date = LocalDateTime.now();
        long start = System.nanoTime();
        Attempt attempt = new Attempt();
        attempt.setPoint(point);
        attempt.setStart(date.format(formatter));
        attempt.setResult(checker.check(point));
        long end = System.nanoTime();
        attempt.setWorkTime((end - start) / 1_000);
        dao.save(attempt);
        return attempt;
    }
}
