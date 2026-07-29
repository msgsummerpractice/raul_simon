package com.exampleboot.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.exampleboot.demo.model.User;
import com.exampleboot.demo.repository.UserRepo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    private void setup(){
        userRepo.getUsers().clear();
        userRepo.addUser(new User("Alice", 30, "alice@example.com"));
        userRepo.addUser(new User("Bob", 25, "bob@example.com"));
    }

    @Test
    void testAddUser() throws Exception {
        String userJson = "{\"name\":\"John Doe\",\"age\":30,\"email\":\"john.doe@example.com\"}";
        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk()).andExpect(content().json("{\"name\":\"John Doe\",\"age\":30,\"email\":\"john.doe@example.com\"}"));
    }

    @Test
	void testListUsers() throws Exception {
		mockMvc.perform(get("/users/list"))
				.andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Alice\",\"age\":30,\"email\":\"alice@example.com\"}," +
				"{\"name\":\"Bob\",\"age\":25,\"email\":\"bob@example.com\"}]"))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
	}

    @Test
    public void testWhenValidParams_thenReturns200() throws Exception {
        String userJson = "{\"name\":\"Jane Doe\",\"age\":28,\"email\":\"jane.doe@example.com\"}";
        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testWhenInvalidEmail_thenReturns400() throws Exception {    
        String userJson = "{\"name\":\"Invalid Email User\",\"age\":30,\"email\":\"invalid-email\"}";
        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest());
    }
}
