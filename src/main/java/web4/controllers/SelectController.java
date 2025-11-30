package web4.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web4.model.Attempt;
import web4.model.AttemptDAO;

import java.util.List;

@RestController
public class SelectController {
    @Autowired
    private AttemptDAO dao;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/select")
    public String select() throws JsonProcessingException {
        List<Attempt> list = dao.getAll();
        return mapper.writeValueAsString(list);
    }
}
