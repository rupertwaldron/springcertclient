package com.ruppyrup.springcertclient.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcertclient.dto.Credential;
import com.ruppyrup.springcertclient.dto.CredentialDTO;
import com.ruppyrup.springcertclient.dto.UserDTO;
import com.ruppyrup.springcertclient.services.CredentialService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerTest {

    @Autowired
    private CredentialService service;

    private String username = "client";
    private String password = "client";
    private UserDTO userDTO = new UserDTO(username, password);
    private String token;
    private CredentialDTO credentialDTO1 = new CredentialDTO("Charlies fakes", "www.cf.com", "rr", "pass");
    private CredentialDTO credentialDTO2 = new CredentialDTO("Bobs Barns", "www.bb.com", "terry", "football");
    private Credential createdCredential1;
    private Credential createdCredential2;

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
        service.deleteCredential(token, createdCredential1.getUuid());
        service.deleteCredential(token, createdCredential2.getUuid());
        service.deleteUser(token, userDTO);
    }

    @Test
    @Order(1)
    public void checkStatus() {
        //given server is running

        //when
        ResponseEntity<String> health = service.getHealth();

        //then
        Assertions.assertThat(health.getBody()).contains("UP");
    }

    @Test
    @Order(2)
    public void createCredentials() throws JsonProcessingException {
        //given server is running with authenticated user

        //when
        createdCredential1 = service.createCredential(token, credentialDTO1).getBody();
        createdCredential2 = service.createCredential(token, credentialDTO2).getBody();

        //then
        Assertions.assertThat(new CredentialDTO(createdCredential1)).isEqualTo(credentialDTO1);
        Assertions.assertThat(new CredentialDTO(createdCredential2)).isEqualTo(credentialDTO2);
    }

    @Test
    @Order(3)
    public void getCredential() {
        // given database has credentials and a user

        //when
        Credential credential1 = service.getCredential(token, createdCredential1.getUuid()).getBody();
        Credential credential2 = service.getCredential(token, createdCredential2.getUuid()).getBody();

        //then
        Assertions.assertThat(new CredentialDTO(credential1)).isEqualTo(credentialDTO1);
        Assertions.assertThat(new CredentialDTO(credential2)).isEqualTo(credentialDTO2);
    }

    @Test
    @Order(4)
    public void getCredentials() throws JsonProcessingException {
        // given database has credentials and a user

        //when
        List<Credential> credentials = service.getCredentials(token).getBody();

        //then
        List<CredentialDTO> credentialDTOList = credentials.stream().map(CredentialDTO::new).collect(Collectors.toList());
        Assertions.assertThat(credentialDTOList).containsExactlyInAnyOrder(credentialDTO1, credentialDTO2);
    }

    @Test
    @Order(5)
    public void updateCredential() throws JsonProcessingException {
        //given server is running with authenticated user

        //when
        createdCredential1 = service.updateCredential(token, credentialDTO1).getBody();
        createdCredential2 = service.createCredential(token, credentialDTO2).getBody();

        //then
        Assertions.assertThat(new CredentialDTO(createdCredential1)).isEqualTo(credentialDTO1);
        Assertions.assertThat(new CredentialDTO(createdCredential2)).isEqualTo(credentialDTO2);
    }

}
