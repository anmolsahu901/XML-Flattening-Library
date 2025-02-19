package com.example.xmlFatteningPoc2.library.jacksonpojo;

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
