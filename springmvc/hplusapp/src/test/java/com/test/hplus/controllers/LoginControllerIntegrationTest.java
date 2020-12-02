package com.test.hplus.controllers;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginControllerIntegrationTest {
    private static final String USERNAME = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginController loginController;

    @Before
    public void setUp() {
    }

    @Test
    public void successfulLogin() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", USERNAME)
                        .param("password", "admin"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("login", IsNull.notNullValue()))
                .andExpect(forwardedUrl("/userprofile"));
    }

    @Test
    public void wrongPasswordLogin() throws Exception {

        mockMvc.perform(post("/login")
                .param("username", USERNAME)
                .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(request().sessionAttribute("login", IsNull.nullValue()))
                .andExpect(forwardedUrlPattern("/**/error.jsp"));
    }

    @Test
    public void invalidUserLogin() throws Exception {

        mockMvc.perform(post("/login")
                .param("username", "invalid")
                .param("password", "admin"))
                .andExpect(status().isUnauthorized())
                .andExpect(request().sessionAttribute("login", IsNull.nullValue()))
                .andExpect(forwardedUrlPattern("/**/error.jsp"));
    }
}