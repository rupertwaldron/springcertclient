package com.ruppyrup.springcertclient.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

public final class HeaderUtil {
    public static final HttpEntity getHttpEntityWithToken(Object body, String token) {
        HttpHeaders headers = getHttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity(body, headers);
    }

    public static final HttpEntity getHttpEntity(Object body) {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity(body, headers);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        return headers;
    }
}
