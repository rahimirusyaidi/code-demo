package com.demo.assignment.controller;

import com.demo.assignment.dao.CustomDrug;
import com.demo.assignment.model.Drug;
import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.ResultInfo;
import com.demo.assignment.service.DrugCatalogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author rahimi.riduan
 */


@RestController
@RequestMapping(value = "/api/drug")
@RequiredArgsConstructor
@Validated
public class DrugCatalogueController {

    private final DrugCatalogueService drugCatalogueService;

    @GetMapping(value = "/search")
    public DrugCatalogueBaseResponse getDrugByManufacturerName(@RequestParam String manufacturerName, @RequestParam(required = false) Integer skip,  @RequestParam(required = false) Integer limit) throws Exception{
        return drugCatalogueService.getDrugData(new ResultInfo(skip, limit), manufacturerName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomDrugCatalogue(@Valid @RequestBody Drug customDrugEntry) throws Exception{
        drugCatalogueService.addNewDrugCatalogue(customDrugEntry);
    }

    @GetMapping
    public Page<CustomDrug> fetchLocalDrug(@RequestParam String manufacturerName,
                                           @PageableDefault(size = 10) Pageable pageable) throws Exception{
        return drugCatalogueService.fetchLocalDrugCatalogue(manufacturerName, pageable);
    }

}
