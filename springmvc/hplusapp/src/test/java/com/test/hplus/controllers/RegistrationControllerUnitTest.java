package com.test.hplus.controllers;

import com.test.hplus.beans.Gender;
import com.test.hplus.beans.User;
import com.test.hplus.config.ApplicationConfig;
import com.test.hplus.repository.ProductRepository;
import com.test.hplus.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RegistrationController.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class RegistrationControllerUnitTest {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static final String USERNAME = "validtestuser";
    public static final String PASSWORD = "aA111111";
    public static final String FIRST_NAME = "Valid Test";
    public static final String LAST_NAME = "User";
    public static final String ACTIVITY = "Playing a sport";

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository; // Not used here...
    
    @InjectMocks
    private RegistrationController registrationController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userRegistration() throws Exception {
        User aUser = new User();
        aUser.setUsername(USERNAME);
        aUser.setPassword(PASSWORD);
        aUser.setFirstName(FIRST_NAME);
        aUser.setLastName(LAST_NAME);
        aUser.setActivity(ACTIVITY);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        aUser.setDateOfBirth(dateFormat.parse("1975-11-18"));
        aUser.setGender(Gender.MALE);

        when(userRepository.save(any(User.class))).thenReturn(aUser);

        // https://stackoverflow.com/questions/27483064/modelattribute-controller-spring-mvc-mocking
        ResultActions perform = mockMvc.perform(post("/registeruser")
                .param("username", USERNAME)
                .param("password", PASSWORD)
                .param("firstName", FIRST_NAME)
                .param("lastName", LAST_NAME)
                .param("activity", ACTIVITY)
                .param("dateOfBirth", "1975-11-18")
                .param("gender", "Male")
        );

        MvcResult mvcResult = perform
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BindingResult bindingResult = (BindingResult) mvcResult
                .getModelAndView().getModel().values().stream()
                .filter(res -> res instanceof BindingResult)
                .findFirst().orElse(null);

        verify(userRepository).save(any(User.class));
        assertNotNull(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

}