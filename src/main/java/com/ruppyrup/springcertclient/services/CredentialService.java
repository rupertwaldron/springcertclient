package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcertclient.dto.Credential;
import com.ruppyrup.springcertclient.dto.CredentialDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static com.ruppyrup.springcertclient.util.HeaderUtil.getHttpEntityWithToken;

@Service
public class CredentialService {

    private ObjectMapper mapper = new ObjectMapper();

    private int port = 8080;

    @Autowired
    RestTemplate restTemplate;

    private final String url = "http://localhost:8080/";
    private Logger LOG = LoggerFactory.getLogger(CredentialService.class);

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
        LOG.info(credentials.toString());
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
        LOG.info(exchange.toString());
        return exchange;
    }

    public ResponseEntity<Credential> updateCredential(String token, String uuid, CredentialDTO credentialDTO) throws JsonProcessingException {
        String credentialJson = mapper.writeValueAsString(credentialDTO);
        HttpEntity<String> entity = getHttpEntityWithToken(credentialJson, token);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("credentials/" + uuid)
                .build();

        ResponseEntity<Credential> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, entity, Credential.class);
        return exchange;
    }

    public ResponseEntity<Credential> deleteCredential(String token, String uuid) {
        Object body = null;
        HttpEntity entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("credentials/" + uuid)
                .build();

        ResponseEntity<Credential> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.DELETE, entity, Credential.class);
        LOG.info(exchange.toString());
        return exchange;
    }

}
