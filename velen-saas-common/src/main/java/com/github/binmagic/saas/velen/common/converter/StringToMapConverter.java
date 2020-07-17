package com.github.binmagic.saas.velen.common.converter;

import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StringToMapConverter implements Converter<String, Map<String, String>> {
    @Override
    public Map<String, String> convert(String source) {
        try {
            return new ObjectMapper().readValue(source, new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}