package com.example.xmlFatteningPoc2.lib;


import com.example.xmlFatteningPoc2.jacksonpojo.ConfigMapping;
import com.example.xmlFatteningPoc2.jacksonpojo.FieldMapping;
import com.example.xmlFatteningPoc2.jacksonpojo.FieldValidationResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class XMLProcessor {

   private final List<FieldValidationResult> errorList = new ArrayList<>();

    public List<FieldValidationResult> getErrorList() {
        return errorList;
    }

    public String process(String xml, String jsonRules) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Parse JSON rules into mapping objects
        ConfigMapping configMapping = objectMapper.readValue(jsonRules, ConfigMapping.class);
//        System.out.println("Object mapper : "+objectMapper.writeValueAsString(configMapping));
//
//        System.out.println("List of mapping : "+configMapping.getMappingList().toString());


        Document xmlDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));
        XPath xPath = XPathFactory.newInstance().newXPath();

        Validator validator = new Validator();
        Map<String, Object> validData = new HashMap<>();

        for (FieldMapping mapping : configMapping.getMappingList()) {
            String extractedValue = xPath.evaluate(mapping.getxPath(), xmlDocument);

            // Check if the field requires JSON processing
            if (mapping.getJsonPath() != null) {
                if (extractedValue == null || extractedValue.isEmpty()) {
                    throw new Exception("JSON payload is missing at XPath: " + mapping.getxPath());
                }

                // Parse JSON and extract value using Jackson
                JsonNode jsonNode = objectMapper.readTree(extractedValue);
                JsonNode jsonValue = jsonNode.at(mapping.getJsonPath());
                if (jsonValue.isMissingNode()) {
                    throw new Exception("Field not found at JSONPath: " + mapping.getJsonPath());
                }

                // Validate and store the value
                validateAndStoreField(mapping, jsonValue.asText(), validator, validData);
            } else {
                if(!mapping.getIsRequired() && (extractedValue==null || extractedValue.trim().isEmpty())){
                    System.out.println("value is empty and not mandatory so skipping "+mapping.getDbField());
                }
                else{
                    // Handle standard XML field extraction
                    validateAndStoreField(mapping, extractedValue, validator, validData);
                }

            }
        }

        if (!validator.getErrorList().isEmpty()) {
            throw new Exception("Validation Failed: " + validator.getErrorList());
        }
        System.out.println("data validate and flattened : "+ validData);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(validData);
    }

    private void validateAndStoreField(FieldMapping mapping, String value, Validator validator, Map<String, Object> validData) {
        boolean isValid = validator.validateField(
                mapping.getDbField(),
                value,
                mapping.getIsRequired(),
                mapping.getMinLength(),
                mapping.getMaxLength(),
                mapping.getRegex()
        );

        if (isValid) {
            validData.put(mapping.getDbField(), value);
        }
    }

}
