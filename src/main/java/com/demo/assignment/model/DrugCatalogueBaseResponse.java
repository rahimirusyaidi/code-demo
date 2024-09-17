package com.demo.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author rahimi.riduan
 */


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugCatalogueBaseResponse {

    ResultInfo pageable;

    List<Drug> drug;
}
