package com.example.xmlFatteningPoc2.flow;

import com.example.xmlFatteningPoc2.library.flow.RulesCache;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

@Component
public class AppStartup {


    private static final Logger log = LoggerFactory.getLogger(AppStartup.class);

    @PostConstruct
    public void init() {
        // Load and cache rules at startup
//        RulesCache.loadRules("RULE_SET_1", readJsonFromFile("mapping.json"));
        RulesCache.loadRules("RULE_SET_1", readJsonFromFile("src/main/resources/mapping.json"));
//        RulesCache.loadRules("RULE_SET_2", readJsonFromFile("rules2.json"));
        log.info(">>>>>>>>>>>>>Pre loading the Json File ");

    }

    private String readJsonFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON config: " + filePath, e);
        }
    }
}
