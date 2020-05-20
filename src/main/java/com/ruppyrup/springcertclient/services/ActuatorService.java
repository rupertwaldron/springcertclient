package com.ruppyrup.springcertclient.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcertclient.config.EndpointConfiguration;
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

import java.util.Optional;

import static com.ruppyrup.springcertclient.util.HeaderUtil.getHttpEntity;

@Service
public class ActuatorService {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    EndpointConfiguration endpoint;

    @Autowired
    RestTemplate restTemplate;

    private final String url = "http://localhost:8080/";
    private Logger LOG = LoggerFactory.getLogger(ActuatorService.class);

    public ResponseEntity<String> getHealth() {
        Object body = null;
        HttpEntity entity = getHttpEntity(body);

        String port = Optional.ofNullable(endpoint.getPort()).orElse(null);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(endpoint.getScheme())
                .host(endpoint.getHost())
                .port(port)
                .path("actuator/health")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);
        LOG.info(String.valueOf(response));
        return response;
    }

}
