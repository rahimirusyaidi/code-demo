package com.demo.assignment.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author rahimi.riduan
 */

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Drug {

    String applicationNumber;

    String manufacturerName;

    List<String> genericName;

}
