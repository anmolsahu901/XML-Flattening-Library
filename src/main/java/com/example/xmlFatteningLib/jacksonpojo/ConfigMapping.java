package com.example.xmlFatteningLib.jacksonpojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;


@Data
public class ConfigMapping {
    @JsonProperty("mappings")
    private List<FieldMapping> mappingList;

    public List<FieldMapping> getMappingList() {
        return mappingList;
    }
}
