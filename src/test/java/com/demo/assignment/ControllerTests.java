package com.demo.assignment;

import com.demo.assignment.dao.CustomDrug;
import com.demo.assignment.model.Drug;
import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.ResultInfo;
import com.demo.assignment.service.DrugCatalogueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author rahimi.riduan
 */
@DisplayName("Drug API test")
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @MockBean
    DrugCatalogueService drugCatalogueService;

    @DisplayName(value = "GET Method Success Call - /api/drug/search")
    @Test
    void successScenarioCallToFetchData(@Autowired MockMvc mockMvc) throws Exception {

        // Mock the service method to return the expected result
        Drug mockDrugData = Drug.builder().applicationNumber("ANDA084949").manufacturerName("Pfizer Laboratories Div Pfizer Inc").genericName(List.of("ESTERIFIED ESTROGENS")).substanceName(List.of("ESTROGENS, ESTERIFIED")).build();
        DrugCatalogueBaseResponse mockedData = new DrugCatalogueBaseResponse(new ResultInfo(0, 1, 124), List.of(mockDrugData));

        when(drugCatalogueService.getDrugData(isA(ResultInfo.class), isA(String.class)))
                .thenReturn(mockedData);

        MvcResult result = mockMvc.perform(get("/api/drug/search?skip=0&limit=1&manufacturerName=Pfizer"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // Compare the full response with the expected one
        String response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        String expectedResponse = """
                {
                    "pageable": {
                        "skip": 0,
                        "limit": 1,
                        "total": 124
                    },
                    "drug": [
                        {
                            "applicationNumber": "ANDA084949",
                            "manufacturerName": "Pfizer Laboratories Div Pfizer Inc",
                            "genericName": [
                                "ESTERIFIED ESTROGENS"
                            ],
                            "substanceName": [
                                "ESTROGENS, ESTERIFIED"
                            ]
                        }
                    ]
                }""";

        // Compare entire response instead of extracting parts
        assertEquals(objectMapper.readTree(expectedResponse), objectMapper.readTree(response));
    }

    @DisplayName(value = "POST Method Success Call - /api/drug")
    @Test
    void successScenarioCallToAddData(@Autowired MockMvc mockMvc) throws Exception {

        Drug requestData = Drug.builder()
                .applicationNumber("ANDA084949")
                .manufacturerName("Pfizer Laboratories Div Pfizer Inc")
                .genericName(List.of("ESTERIFIED ESTROGENS"))
                .substanceName(List.of("ESTROGENS, ESTERIFIED"))
                .build();

        MvcResult result = mockMvc.perform(post("/api/drug")
                        .content(new ObjectMapper().writeValueAsString(requestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.CREATED.value());
    }

    @DisplayName(value = "GET Method Success Call - /api/drug")
    @Test
    public void successScenarioCallToFetchLocalData(@Autowired MockMvc mockMvc) throws Exception {
        CustomDrug mockDrug = new CustomDrug();
        mockDrug.setApplicationId(UUID.randomUUID());
        mockDrug.setManufacturerName("Homeopathy");
        mockDrug.setSubstanceName(Arrays.asList("Camphor", "Ethyl"));

        Pageable pageable = PageRequest.of(0, 2);
        Page<CustomDrug> mockPage = new PageImpl<>(Collections.singletonList(mockDrug), pageable, 1);


        when(drugCatalogueService.fetchLocalDrugCatalogue(isA(String.class), any(Pageable.class)))
                .thenReturn(mockPage);

        mockMvc.perform(get("/api/drug")
                        .param("manufacturerName", "Homeopathy")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].applicationId").exists())
                .andExpect(jsonPath("$.content[0].manufacturerName").value("Homeopathy"))
                .andExpect(jsonPath("$.content[0].substanceName[0]").value("Camphor"))
                .andExpect(jsonPath("$.content[0].substanceName[1]").value("Ethyl"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }

    }
