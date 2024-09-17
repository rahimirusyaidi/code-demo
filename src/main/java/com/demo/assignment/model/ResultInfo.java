package com.demo.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author rahimi.riduan
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultInfo {

    Integer skip;
    Integer limit;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer total;

    public ResultInfo(Integer skip, Integer limit) {
        this.skip = skip;
        this.limit = limit;
    }

    public ResultInfo(FdaBaseResponse.Meta meta) {
        this.skip = meta.getResults().getSkip();
        this.limit = meta.getResults().getLimit();
        this.total = meta.getResults().getTotal();
    }
}
