package ua.opnu.practice1_template.restcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Course Management System API is running. Use /api/* endpoints to access resources.";
    }

    // Додаємо обробник помилки 404
    @GetMapping("/error")
    @ResponseBody
    public String handleError() {
        return "Error occurred. Please check the URL and try again.";
    }
}
