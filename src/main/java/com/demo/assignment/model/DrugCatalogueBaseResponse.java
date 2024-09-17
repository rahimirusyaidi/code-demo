package com.demo.assignment.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author rahimi.riduan
 */


@Builder
@Data
public class DrugCatalogueBaseResponse {

    ResultInfo pageable;

    List<Drug> drug;
}
