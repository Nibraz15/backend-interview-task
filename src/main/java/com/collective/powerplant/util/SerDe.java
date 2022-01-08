package com.collective.powerplant.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InvalidObjectException;

@Slf4j
public class SerDe {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @SneakyThrows
    public static <T> String serialize(T object){
        if (object == null) {
            throw new InvalidObjectException("Failed to serialize object: input is null");
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String message = String.format("Failed to serialize object [%s]", object);
            throw new InvalidObjectException(message);
        }
    }

    @SneakyThrows
    public static <T> T deserialize(String input, Class<T> clazz) {
        try {
            return mapper.readValue(input, clazz);
        } catch (IOException e) {
            // Sometimes input is too big and the error is not logged properly
            log.error("An error occurred to deserialize the input string", e);
            String message = String.format("Failed to deserialize string [%s]", input);
            throw new InvalidObjectException(message);
        }
    }


}
