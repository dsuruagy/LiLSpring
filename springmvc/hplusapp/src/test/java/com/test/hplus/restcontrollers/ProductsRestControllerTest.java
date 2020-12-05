package com.test.hplus.restcontrollers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getExistingProductTest() throws Exception {
        mockMvc.perform(get("/hplus/rest/products")
                    .param("name", "water"))
                .andExpect(status().isOk());
    }

    @Test
    public void getExistingProductPathVariableTest() throws Exception {
        mockMvc.perform(get("/hplus/rest/products/water"))
                .andExpect(status().isOk());
    }

    @Test
    public void getInvalidProductTest() throws Exception {
        mockMvc.perform(get("/hplus/rest/products")
                .param("name", "agua"))
                .andExpect(status().isNotFound());
    }
}