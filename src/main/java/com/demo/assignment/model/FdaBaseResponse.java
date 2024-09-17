package com.demo.assignment.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author rahimi.riduan
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FdaBaseResponse {

    Meta meta;

    List<FdaDrugResponse> results;


//    todo: please move this to its own class, this is not good practice
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Meta {
        ResultInfo results;
    }


    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ResultInfo {

        Integer skip;
        Integer limit;
        Integer total;

    }

}
