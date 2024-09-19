package com.demo.assignment;

import com.demo.assignment.model.DrugCatalogueBaseResponse;
import com.demo.assignment.model.FdaBaseResponse;
import com.demo.assignment.model.FdaDrugResponse;
import com.demo.assignment.model.ResultInfo;
import com.demo.assignment.service.DrugCatalogueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author rahimi.riduan
 */
public class ServiceTests {

    @Spy
    @InjectMocks
    private DrugCatalogueServiceImpl drugCatalogueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @DisplayName(value = "Success scenario for getDrugData method ")
    @Test
    public void getDrugDataWithSuccessFdaApiCall() throws Exception {
        // Arrange
        ResultInfo resultInfo = new ResultInfo(1, 1);
        String manufacturerName = "Pfizer";

        FdaBaseResponse mockResponse = createSuccessCallMockFdaBaseResponse();
        when(drugCatalogueService
                .fetchDrugDataFromFda(resultInfo, manufacturerName))
                .thenReturn(mockResponse);

        // Act
        DrugCatalogueBaseResponse response = drugCatalogueService.getDrugData(resultInfo, manufacturerName);

        // Assert
        assertNotNull(response);
        assertFalse(response.getDrug().isEmpty());
        assertEquals("Pfizer", response.getDrug().get(0).getManufacturerName());
        assertEquals("1001", response.getDrug().get(0).getApplicationNumber());
        assertEquals("Aspirin", response.getDrug().get(0).getGenericName().get(0));
        assertEquals("Acetylsalicylic Acid", response.getDrug().get(0).getSubstanceName().get(0));
    }

    private FdaBaseResponse createSuccessCallMockFdaBaseResponse() {
        FdaBaseResponse response = new FdaBaseResponse();

        FdaBaseResponse.Meta meta = new FdaBaseResponse.Meta();
        FdaBaseResponse.ResultInfo resultInfo = new FdaBaseResponse.ResultInfo();
        resultInfo.setSkip(0);
        resultInfo.setTotal(1);
        meta.setResults(resultInfo);
        response.setMeta(meta);

        FdaDrugResponse.OpenFda openFda = new FdaDrugResponse.OpenFda();

        openFda.setApplicationNumber(List.of("1001"));
        openFda.setGenericName(List.of("Aspirin"));
        openFda.setManufacturingName(List.of("Pfizer"));
        openFda.setSubstanceName(List.of("Acetylsalicylic Acid"));
        FdaDrugResponse drugResponse = new FdaDrugResponse();
        drugResponse.setOpenfda(openFda);
        response.setResults(List.of(drugResponse));

        return response;
    }

}
