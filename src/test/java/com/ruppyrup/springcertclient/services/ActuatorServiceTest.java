package com.ruppyrup.springcertclient.services;

import com.ruppyrup.springcertclient.util.PathUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)

class ActuatorServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    PathUtil pathUtil;

    @InjectMocks
    ActuatorService service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pathUtil, "portProfile", "8080");
        ReflectionTestUtils.setField(pathUtil, "scheme", "http");
        ReflectionTestUtils.setField(pathUtil, "host", "localhost");
    }

    @Test
    void getHealth() {
        service.getHealth();
    }
}