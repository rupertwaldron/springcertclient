package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcertclient.dto.UserDTO;
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

import static com.ruppyrup.springcertclient.util.HeaderUtil.getHttpEntity;
import static com.ruppyrup.springcertclient.util.HeaderUtil.getHttpEntityWithToken;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PathUtil pathUtil;

    private ObjectMapper mapper = new ObjectMapper();
    private Logger LOG = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<String> registerUser(UserDTO user) throws JsonProcessingException {
        String body = mapper.writeValueAsString(user);

        HttpEntity<String> entity = getHttpEntity(body);

        UriComponents uriComponents = pathUtil.getUriComponents("register");

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }

    public void deleteUser(String token, UserDTO userDTO) throws JsonProcessingException {
        String body = mapper.writeValueAsString(userDTO);

        HttpEntity<String> entity = getHttpEntityWithToken(body, token);

        UriComponents uriComponents = pathUtil.getUriComponents("users");

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.DELETE, entity, String.class);
        LOG.info(String.valueOf(response));
    }

    public ResponseEntity<String> authenticateUser(UserDTO user) throws JsonProcessingException {
        String body = mapper.writeValueAsString(user);
        HttpEntity<String> entity = getHttpEntity(body);

        UriComponents uriComponents = pathUtil.getUriComponents("authenticate");

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }
}
