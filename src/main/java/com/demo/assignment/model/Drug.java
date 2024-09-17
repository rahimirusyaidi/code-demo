package com.demo.assignment.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author rahimi.riduan
 */

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Drug {

    String applicationNumber;

    @NotNull(message = "manufacturerName is mandatory field, cannot be null")
    @NotEmpty(message = "manufacturerName is mandatory field, cannot be empty")
    String manufacturerName;

    @NotNull(message = "atleast 1 genericName data required")
    List<String> genericName;

    @NotNull(message = "atleast 1 substanceName data required")
    List<String> substanceName;

}
