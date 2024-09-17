package com.demo.assignment.service;

import com.demo.assignment.model.Drug;
import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.FdaBaseResponse;
import com.demo.assignment.model.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author rahimi.riduan
 */

@Service
@Slf4j
public class DrugCatalogueServiceImpl implements DrugCatalogueService {

    @Override
    public DrugCatalogueBaseResponse getDrugData(ResultInfo pageable, String manufacturerName) throws Exception {

        // fetch data from FDA website
        FdaBaseResponse response = this.fetchDrugDataFromFda(pageable, manufacturerName);

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drug not found for manufacturer: " + manufacturerName);
        }

        return DrugCatalogueBaseResponse.builder()
                .pageable(new ResultInfo(response.getMeta()))
                .drug(response.getResults()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(data -> Drug.builder()
                                .applicationNumber(data.getOpenfda().getApplicationNumber().stream().findAny().orElse("N/A"))
                                .genericName(data.getOpenfda().getGenericName())
                                .manufacturerName(data.getOpenfda().getManufacturingName().stream().filter(manufacture -> manufacture.contains(manufacturerName)).findFirst().orElse("N/A"))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private FdaBaseResponse fetchDrugDataFromFda(ResultInfo pageable, String manufacturerName) throws Exception {
        //TODO: add formatted logging for this rest template
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        // sample url for reference:
        // https://api.fda.gov/drug/drugsfda.json?search=openfda.manufacturer_name:"Pfizer"

        String searchQuery = "openfda.manufacturer_name:" +
                manufacturerName;

        String url = UriComponentsBuilder
                .fromUriString("https://api.fda.gov/drug/drugsfda.json")
                .queryParam("search", searchQuery)
                .queryParam("limit", pageable.getLimit())
                .queryParam("skip", pageable.getSkip())
                .build().toUriString();

        try {
            return restTemplate.getForObject(url, FdaBaseResponse.class);
        } catch (HttpClientErrorException e) {
            throw new Exception("client side exception error :" + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new Exception("server side exception error :" + e.getMessage());
        }
    }
}
