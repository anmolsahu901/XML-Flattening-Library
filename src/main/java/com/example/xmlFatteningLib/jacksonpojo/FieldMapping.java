package com.example.xmlFatteningLib.jacksonpojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class FieldMapping {
    @JsonProperty("fieldName")
    private String dbField;
    @JsonProperty("xpath")
    private String xPath;
    @JsonProperty("isRequired")
    private boolean isRequired;
    @JsonProperty("minLength")
    private int minLength; // Changed to Integer
    @JsonProperty("maxLength")
    private int maxLength; // Changed to Integer
    @JsonProperty("regex")
    private String regex;
    @JsonProperty("jsonPath")
    private String jsonPath;

    public String getDbField() {
        return dbField;
    }

    public void setDbField(String dbField) {
        this.dbField = dbField;
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public boolean getIsRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }
}
