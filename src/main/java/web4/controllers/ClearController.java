package web4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import web4.model.AttemptDAO;

@RestController
public class ClearController {
    @Autowired
    private AttemptDAO dao;

    @DeleteMapping("/clear")
    public String clear() {
        try {
            dao.clear();
            return "{\"result\": true}";
        } catch (Exception e) {
            return "{\"result\": false}";
        }
    }
}
