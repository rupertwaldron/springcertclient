package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcertclient.dto.Credential;
import com.ruppyrup.springcertclient.dto.CredentialDTO;
import com.ruppyrup.springcertclient.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CredentialService {

    private ObjectMapper mapper = new ObjectMapper();

    private int port = 8080;

    @Autowired
    RestTemplate restTemplate;

    private final String url = "http://localhost:8080/";
    private Logger LOG = LoggerFactory.getLogger(CredentialService.class);

    public ResponseEntity<String> getHealth() {
        Object body = null;
        HttpEntity entity = getHttpEntity(body);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("actuator/health")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }

    public ResponseEntity<String> registerUser(UserDTO user) throws JsonProcessingException {
        String body = mapper.writeValueAsString(user);

        HttpEntity<String> entity = getHttpEntity(body);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("register")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }

    public void deleteUser(String token, UserDTO userDTO) throws JsonProcessingException {
        String body = mapper.writeValueAsString(userDTO);

        HttpEntity<String> entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("users")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.DELETE, entity, String.class);
        LOG.info(String.valueOf(response));
    }

    public ResponseEntity<String> authenticateUser(UserDTO user) throws JsonProcessingException {
        String body = mapper.writeValueAsString(user);
        HttpEntity<String> entity = getHttpEntity(body);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("authenticate")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }




    public ResponseEntity<Credential> getCredential(String token, String credentialId) {
        Object body = null;
        HttpEntity entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("credentials/" + credentialId)
                .build();

        ResponseEntity<Credential> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, Credential.class);
        LOG.info(String.valueOf(response));
        return response;
    }

    public ResponseEntity<List<Credential>> getCredentials(String token) throws JsonProcessingException {
        Object body = null;
        HttpEntity entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("credentials")
                .build();

        ResponseEntity<Credential[]> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, Credential[].class);
        List<Credential> credentials = Arrays.asList(response.getBody());
        return new ResponseEntity<>(credentials, response.getStatusCode());
    }

    public ResponseEntity<Credential> createCredential(String token, CredentialDTO credentialDTO) throws JsonProcessingException {
        String credentialJson = mapper.writeValueAsString(credentialDTO);
        HttpEntity<String> entity = getHttpEntityWithToken(credentialJson, token);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("credentials")
                .build();

        ResponseEntity<Credential> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, Credential.class);
        return exchange;
    }

    private HttpEntity getHttpEntityWithToken(Object body, String token) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        headers.setBearerAuth(token);
        return new HttpEntity(body, headers);
    }

    private HttpEntity getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        return new HttpEntity(body, headers);
    }

    public ResponseEntity<Credential> updateCredential(String uuid, CredentialDTO credentialDTO) {
        return null;
    }

    public ResponseEntity<Credential> deleteCredential(String uuid) {
        return null;
    }

}
