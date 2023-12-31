package com.example.demo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/demo")
public class Demo {
    
    @GetMapping
    public ResponseEntity<String> hey(){
        return ResponseEntity.ok("Well done");
    }
}
