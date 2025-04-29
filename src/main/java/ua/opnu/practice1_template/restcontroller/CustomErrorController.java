package ua.opnu.practice1_template.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        Map<String, Object> response = new HashMap<>();
        response.put("status", statusCode);
        response.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        response.put("message", exception != null ? exception.getMessage() : "Unexpected error occurred");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }
}
