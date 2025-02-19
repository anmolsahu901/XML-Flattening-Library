package com.example.xmlFatteningPoc2.library.flow;

import com.example.xmlFatteningPoc2.library.jacksonpojo.ConfigMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class RulesCache {

    private static final Map<String, ConfigMapping> rulesCache = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(RulesCache.class);

    // Load and cache JSON rules
    public static void loadRules(String key, String jsonConfig) {
        try {

            ConfigMapping mapping = objectMapper.readValue(jsonConfig, ConfigMapping.class);
            rulesCache.put(key, mapping);
            log.info("Rules are loaded from the Json");

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON rules: " + key, e);
        }
    }

    // Get cached rules
    public static ConfigMapping getRules(String key) {
        return rulesCache.get(key);
    }
}

