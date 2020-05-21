package com.ruppyrup.springcertclient.util;

import com.ruppyrup.springcertclient.config.EndpointConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PathUtil {

    @Autowired
    EndpointConfiguration endpoint;

    public final UriComponents getUriComponents(String path) {
        String port = endpoint.getPort().isBlank() ? null : endpoint.getPort();

        return UriComponentsBuilder.newInstance()
                .scheme(endpoint.getScheme())
                .host(endpoint.getHost())
                .port(port)
                .path(path)
                .build();
    }
}
