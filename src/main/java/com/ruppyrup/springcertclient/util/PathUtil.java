package com.ruppyrup.springcertclient.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PathUtil {

    @Value("${endpoint.port}")
    String portProfile;

    @Value("${endpoint.scheme}")
    String scheme;

    @Value("${endpoint.host}")
    String host;


    public final UriComponents getUriComponents(String path) {
        String port = portProfile.isBlank() ? null : portProfile;

        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .build();
    }
}
