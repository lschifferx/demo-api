package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnDefaultGreeting() throws Exception {
    mockMvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello, World!"));
  }

  @Test
  void shouldReturnCustomGreeting() throws Exception {
    mockMvc.perform(get("/hello").param("name", "Lucas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello, Lucas!"));
  }
}