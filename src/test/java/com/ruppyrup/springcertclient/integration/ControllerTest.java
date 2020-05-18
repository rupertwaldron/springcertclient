package com.ruppyrup.springcertclient.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcertclient.dto.Credential;
import com.ruppyrup.springcertclient.dto.CredentialDTO;
import com.ruppyrup.springcertclient.dto.UserDTO;
import com.ruppyrup.springcertclient.services.CredentialService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class ControllerTest {

    @Autowired
    private CredentialService service;

    private String username = "client";
    private String password = "client";

    private UserDTO userDTO = new UserDTO(username, password);

    private String token;

    private CredentialDTO credentialDTO1 = new CredentialDTO("Charlies fakes", "www.cf.com", "rr", "pass");

    //todo need to get proper token object
    @BeforeAll
    public void setup() throws JsonProcessingException {
        try {
            service.registerUser(userDTO);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
        ResponseEntity<String> authenticateResponse = service.authenticateUser(userDTO);
        token = JsonPath.parse(authenticateResponse.getBody()).read("$.token");
        System.out.println(token);
    }

    @AfterAll
    public void cleanUp() throws JsonProcessingException {
        service.deleteUser(token, userDTO);
        //service.deleteCredential()
    }

    @Test
    public void checkStatus() {
        //given server is running

        //when
        ResponseEntity<String> health = service.getHealth();

        //then
        Assertions.assertThat(health.getBody()).contains("UP");
    }

    @Test
    public void createCredential() throws JsonProcessingException {
        //given server is running with authenticated user

        //when
        ResponseEntity<Credential> credential = service.createCredential(token, credentialDTO1);

        //then
        Assertions.assertThat(new CredentialDTO(credential.getBody())).isEqualTo(credentialDTO1);
    }

}
