package web4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web4.model.Point;

@Controller
public class CheckController {
    @PostMapping("/check")
    public String check(@RequestBody Point point) {
        System.out.println(point.toString());
        return "";
    }
}
