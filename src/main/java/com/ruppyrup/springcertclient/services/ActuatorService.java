package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class ActuatorService {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PathUtil pathUtil;

    @Autowired
    RestTemplate restTemplate;

    private final String url = "http://localhost:8080/";
    private Logger LOG = LoggerFactory.getLogger(ActuatorService.class);

    public ResponseEntity<String> getHealth() {
        Object body = null;
        HttpEntity entity = getHttpEntity(body);

        UriComponents uriComponents = pathUtil.getUriComponents("actuator/health");

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }

}
