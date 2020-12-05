package com.test.hplus.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.test.hplus.beans.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String getRequestJson(String username, String password) throws JsonProcessingException {
        // https://stackoverflow.com/questions/20504399/testing-springs-requestbody-using-spring-mockmvc
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(login);
    }

    @Test
    public void successfulLoginTest() throws Exception {
        mockMvc.perform(post("/hplus/rest/loginuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestJson("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    public void invalidLoginTest() throws Exception {
        mockMvc.perform(post("/hplus/rest/loginuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestJson("nouser", "no")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failureLoginTest() throws Exception {
        mockMvc.perform(post("/hplus/rest/loginuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestJson("admin", "no")))
                .andExpect(status().isUnauthorized());
    }
}