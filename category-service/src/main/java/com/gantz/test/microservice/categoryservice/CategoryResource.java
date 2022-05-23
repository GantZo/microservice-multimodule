package com.gantz.test.microservice.categoryservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryResource {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("<div>Hello from category service</div><br><div></div>");
    }

}
