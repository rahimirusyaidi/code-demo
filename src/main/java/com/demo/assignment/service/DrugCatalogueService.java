package com.demo.assignment.service;

import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.ResultInfo;

/**
 * @author rahimi.riduan
 */
public interface DrugCatalogueService {


    DrugCatalogueBaseResponse getDrugData(ResultInfo pageable, String manufacturerName) throws Exception;


}
