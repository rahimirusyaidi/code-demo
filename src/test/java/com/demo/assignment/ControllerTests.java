package com.demo.assignment;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        String expectedResponse = "{\n" +
                "    \"pageable\": {\n" +
                "        \"skip\": 0,\n" +
                "        \"limit\": 1,\n" +
                "        \"total\": 124\n" +
                "    },\n" +
                "    \"drug\": [\n" +
                "        {\n" +
                "            \"applicationNumber\": \"ANDA084949\",\n" +
                "            \"manufacturerName\": \"Pfizer Laboratories Div Pfizer Inc\",\n" +
                "            \"genericName\": [\n" +
                "                \"ESTERIFIED ESTROGENS\"\n" +
                "            ],\n" +
                "            \"substanceName\": [\n" +
                "                \"ESTROGENS, ESTERIFIED\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

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
}
