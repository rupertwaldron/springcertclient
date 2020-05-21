package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcertclient.dto.Credential;
import com.ruppyrup.springcertclient.dto.CredentialDTO;
import com.ruppyrup.springcertclient.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.util.Arrays;
import java.util.List;

import static com.ruppyrup.springcertclient.util.HeaderUtil.getHttpEntityWithToken;

@Service
public class CredentialService {

    @Autowired
    PathUtil pathUtil;

    @Autowired
    RestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();
    private Logger LOG = LoggerFactory.getLogger(CredentialService.class);

    public ResponseEntity<Credential> getCredential(String token, String credentialId) {
        Object body = null;
        HttpEntity entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = pathUtil.getUriComponents("credentials/" + credentialId);

        ResponseEntity<Credential> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, Credential.class);
        LOG.info(String.valueOf(response));
        return response;
    }

    public ResponseEntity<List<Credential>> getCredentials(String token) throws JsonProcessingException {
        Object body = null;
        HttpEntity entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = pathUtil.getUriComponents("credentials");

        ResponseEntity<Credential[]> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, Credential[].class);
        List<Credential> credentials = Arrays.asList(response.getBody());
        LOG.info(credentials.toString());
        return new ResponseEntity<>(credentials, response.getStatusCode());
    }

    public ResponseEntity<Credential> createCredential(String token, CredentialDTO credentialDTO) throws JsonProcessingException {
        String credentialJson = mapper.writeValueAsString(credentialDTO);
        HttpEntity<String> entity = getHttpEntityWithToken(credentialJson, token);

        UriComponents uriComponents = pathUtil.getUriComponents("credentials");

        ResponseEntity<Credential> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, Credential.class);
        LOG.info(exchange.toString());
        return exchange;
    }

    public ResponseEntity<Credential> updateCredential(String token, String uuid, CredentialDTO credentialDTO) throws JsonProcessingException {
        String credentialJson = mapper.writeValueAsString(credentialDTO);
        HttpEntity<String> entity = getHttpEntityWithToken(credentialJson, token);

        UriComponents uriComponents = pathUtil.getUriComponents("credentials/" + uuid);

        ResponseEntity<Credential> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, entity, Credential.class);
        return exchange;
    }

    public ResponseEntity<Credential> deleteCredential(String token, String uuid) {
        Object body = null;
        HttpEntity entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = pathUtil.getUriComponents("credentials/" + uuid);

        ResponseEntity<Credential> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.DELETE, entity, Credential.class);
        LOG.info(exchange.toString());
        return exchange;
    }

}
