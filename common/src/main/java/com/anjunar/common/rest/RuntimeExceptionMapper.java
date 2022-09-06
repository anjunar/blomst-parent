package com.anjunar.common.rest;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.HashMap;
import java.util.Map;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger log = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException exception) {
        switch (exception) {
            case WebApplicationException webApplicationException -> {
                Map<String, String> map = new HashMap<>();
                map.put("exception", exception.getClass().getName());
                map.put("message", exception.getMessage());
                int status = webApplicationException.getResponse().getStatus();
                return Response.status(status).entity(map).type(MediaType.APPLICATION_JSON_TYPE).build();
            }
            default -> {
                String confidentialMarkerText = "CONFIDENTIAL";
                Marker confidentialMarker = MarkerFactory.getMarker(confidentialMarkerText);
                log.error(confidentialMarker, exception.getMessage(), exception);
                Map<String, String> map = new HashMap<>();
                map.put("exception", exception.getClass().getName());
                map.put("message", exception.getMessage());
                return Response.status(500).entity(map).type(MediaType.APPLICATION_JSON_TYPE).build();
            }
        }
    }

}