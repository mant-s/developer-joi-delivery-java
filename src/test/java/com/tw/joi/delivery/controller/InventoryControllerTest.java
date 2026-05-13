package com.tw.joi.delivery.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tw.joi.delivery.domain.enums.InventoryStatus;
import com.tw.joi.delivery.dto.response.InventoryHealthInfo;
import com.tw.joi.delivery.service.InventoryService;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Test
    void shouldReturnTheHealthOfTheStore() throws Exception {
        String url = "/inventory/health";
        String storeId = "store101";

        InventoryHealthInfo inventoryHealthInfo = new InventoryHealthInfo(
            storeId,
            "Test Store",
            100,
            10,
            5,
            List.of("product101", "product102"),
            InventoryStatus.WARNING
        );

        when(inventoryService.fetchStoreInventoryHealth(storeId)).thenReturn(inventoryHealthInfo);
        
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .param("storeId", storeId)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.storeId").value(storeId))
            .andExpect(jsonPath("$.storeName").value("Test Store"))
            .andExpect(jsonPath("$.totalProducts").value(100))
            .andExpect(jsonPath("$.lowStockCount").value(10))
            .andExpect(jsonPath("$.outOfStockCount").value(5))
            .andExpect(jsonPath("$.criticalProductIds", containsInAnyOrder("product101", "product102")))
            .andExpect(jsonPath("$.overallStatus").value(InventoryStatus.WARNING.toString()));
    }
}
