package com.example.demo;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> hello(@RequestParam(defaultValue = "World") String name) {
    return Map.of("message", "Hello, " + name + "!");
  }
}