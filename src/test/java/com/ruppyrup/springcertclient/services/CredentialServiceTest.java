package com.ruppyrup.springcertclient.services;

import com.ruppyrup.springcertclient.dto.Credential;
import com.ruppyrup.springcertclient.util.PathUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class CredentialServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    PathUtil pathUtil;

    @InjectMocks
    CredentialService credentialService;

    private Credential credential = new Credential(1L, "a", "Boots", "www.boots.com", "RW", "password", "ruppyrup");

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pathUtil, "portProfile", "8080");
        ReflectionTestUtils.setField(pathUtil, "scheme", "http");
        ReflectionTestUtils.setField(pathUtil, "host", "localhost");
    }

    @Test
    public void whenGetCredentialIsCalled_shouldReturnMockObject() {
        ResponseEntity<Credential> entity = new ResponseEntity(credential, HttpStatus.OK);

        Mockito
                .when(restTemplate.exchange(
                        anyString(),
                        eq(HttpMethod.GET),
                        any(),
                        eq(Credential.class))).thenReturn(entity);

        ResponseEntity<Credential> response = credentialService.getCredential(null, "Boots");
        Assertions.assertThat(response.getBody()).isEqualTo(credential);
    }

    @Test
    public void whenGetCredentialsIsCalled_shouldReturnMockObject() {
        ResponseEntity<Credential> entity = new ResponseEntity(credential, HttpStatus.OK);
        Mockito
                .when(restTemplate.exchange(
                        anyString(),
                        eq(HttpMethod.GET),
                        any(),
                        eq(Credential.class))).thenReturn(entity);

        ResponseEntity<Credential> response = credentialService.getCredential(null, "Boots");
        Assertions.assertThat(response.getBody()).isEqualTo(credential);
    }

}