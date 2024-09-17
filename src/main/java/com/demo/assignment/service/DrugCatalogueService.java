package com.demo.assignment.service;

import com.demo.assignment.dao.CustomDrug;
import com.demo.assignment.model.Drug;
import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.ResultInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author rahimi.riduan
 */
public interface DrugCatalogueService {


    DrugCatalogueBaseResponse getDrugData(ResultInfo pageable, String manufacturerName) throws Exception;

    void addNewDrugCatalogue(Drug drug) throws Exception;

    Page<CustomDrug> fetchLocalDrugCatalogue(String manufacturerName, Pageable pageable) throws Exception;
}
