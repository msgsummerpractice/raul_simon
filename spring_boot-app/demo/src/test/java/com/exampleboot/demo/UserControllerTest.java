package com.exampleboot.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
	private MockMvc mockMvc;
    
    @Test
    void testAddUser() throws Exception {
        mockMvc.perform(get("/users/add")
                .param("name", "John Doe")
                .param("age", "30")
                .param("email", "john.doe@example.com"))
                .andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
	void testListUsers() throws Exception {
		mockMvc.perform(get("/users/list"))
				.andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Alice\",\"age\":30,\"email\":\"alice@example.com\"}," +
				"{\"name\":\"Bob\",\"age\":25,\"email\":\"bob@example.com\"}," +
                "{\"name\":\"John Doe\",\"age\":30,\"email\":\"john.doe@example.com\"}]"))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"))
                .andExpect(jsonPath("$[2].name").value("John Doe"));
	}

    @Test
    public void testWhenValidParams_thenReturns200() throws Exception {
        mockMvc.perform(get("/users/add")
                .param("name", "Jane Doe")
                .param("age", "28")
                .param("email", "jane.doe@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testWhenInvalidEmail_thenReturns400() throws Exception {    
        mockMvc.perform(get("/users/add")
                .param("name", "Invalid Email User")
                .param("age", "30")
                .param("email", "invalid-email"))
                .andExpect(status().isBadRequest());
    }
}
