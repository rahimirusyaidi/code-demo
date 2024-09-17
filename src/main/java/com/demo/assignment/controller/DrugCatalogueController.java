package com.demo.assignment.controller;

import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.ResultInfo;
import com.demo.assignment.service.DrugCatalogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author rahimi.riduan
 */


@RestController(value = "/api/drug")
@RequiredArgsConstructor
public class DrugCatalogueController {

    private final DrugCatalogueService drugCatalogueService;

    @GetMapping(value = "/search")
    public DrugCatalogueBaseResponse getDrugByManufacturerName(@RequestParam(required = false) Optional<ResultInfo> pageable, @RequestParam String manufacturerName) throws Exception{
        return drugCatalogueService.getDrugData(pageable.orElseGet(ResultInfo::new), manufacturerName);
    }

}
