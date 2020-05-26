package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcertclient.dto.UserDTO;
import com.ruppyrup.springcertclient.util.PathUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    PathUtil pathUtil;

    @InjectMocks
    UserService userService;

    private ObjectMapper mapper = new ObjectMapper();
    private UserDTO userDTO = new UserDTO("rup", "rubber-chicken");

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pathUtil, "portProfile", "8080");
        ReflectionTestUtils.setField(pathUtil, "scheme", "http");
        ReflectionTestUtils.setField(pathUtil, "host", "localhost");
    }

    @Test
    void registerUser() throws JsonProcessingException {
        //given
        String body = mapper.writeValueAsString(userDTO);
        ResponseEntity<String> entity = new ResponseEntity<>(body, HttpStatus.ACCEPTED);

        //when
        Mockito
                .when(restTemplate.exchange(
                        anyString(),
                        eq(HttpMethod.POST),
                        any(),
                        eq(String.class))).thenReturn(entity);

        ResponseEntity<String> response = userService.registerUser(userDTO);

        //then
        Assertions.assertThat(response.getBody()).isEqualTo(body);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void deleteUser() throws JsonProcessingException {
        //given method returns void

        //when
        userService.deleteUser("token", userDTO);

        //then
        then(restTemplate).should().exchange(
                anyString(),
                eq(HttpMethod.DELETE),
                any(),
                eq(String.class));
    }

    @Test
    void authenticateUser() throws JsonProcessingException {
        //given
        given(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(),
                eq(String.class))).willReturn(ResponseEntity.ok("Token"));

        //when
        ResponseEntity<String> responseEntity = userService.authenticateUser(userDTO);

        //then
        then(restTemplate).should().exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(),
                eq(String.class));

        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Token");

    }
}