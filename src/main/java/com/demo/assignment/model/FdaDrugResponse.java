package com.demo.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author rahimi.riduan
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FdaDrugResponse {

    OpenFda openfda;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OpenFda {

        @JsonProperty("application_number")
        List<String> applicationNumber;

        @JsonProperty("manufacturer_name")
        List<String> manufacturingName;

        @JsonProperty("substance_name")
        List<String> substanceName;

        @JsonProperty("generic_name")
        List<String> genericName;
    }


}
