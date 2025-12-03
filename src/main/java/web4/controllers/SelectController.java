package web4.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import web4.model.Attempt;
import web4.model.AttemptDAO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class SelectController {
    @Autowired
    private AttemptDAO dao;
    private final ObjectMapper mapper = new ObjectMapper();

    public class Data {
        public List<Attempt> content;
        public long totalElements;
    }

    @PostMapping("/select")
    public String post(@RequestBody int[] data) throws JsonProcessingException {
        List<Attempt> list = dao.getSlice(data[0], data[1]);
        return mapper.writeValueAsString(list);
    }

    @GetMapping("/select")
    public String get(Pageable pageable) throws JsonProcessingException {
        List<Attempt> list = dao.getSlice(pageable.getPageNumber(), pageable.getPageSize());
        Data data = new Data();
        data.content = list;
        data.totalElements = dao.count();
        return mapper.writeValueAsString(data);
    }
}
