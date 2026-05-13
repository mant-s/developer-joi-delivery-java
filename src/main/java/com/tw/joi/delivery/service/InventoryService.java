package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.enums.InventoryStatus;
import com.tw.joi.delivery.dto.response.InventoryHealthInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StoreService storeService;

    public InventoryHealthInfo fetchStoreInventoryHealth(String storeId) {
        GroceryStore store = storeService.fetchStoreById(storeId);
        
        // Ensure product expiry is updated before calculating health
        store.getInventory().forEach(GroceryProduct::updateStock);
        
        long totalProducts = store.getInventory().size();
        
        List<GroceryProduct> lowStockProducts = store.getInventory().stream()
            .filter(GroceryProduct::isLowStock)
            .toList();
            
        List<GroceryProduct> outOfStockProducts = store.getInventory().stream()
            .filter(GroceryProduct::isOutOfStock)
            .toList();

        List<String> criticalIds = outOfStockProducts.stream()
            .map(GroceryProduct::getProductId)
            .toList();

        InventoryStatus status = calculateStatus(lowStockProducts.size(), outOfStockProducts.size());

        return new InventoryHealthInfo(
            store.getOutletId(),
            store.getName(),
            totalProducts,
            lowStockProducts.size(),
            outOfStockProducts.size(),
            criticalIds,
            status
        );
    }

    private InventoryStatus calculateStatus(int lowStock, int outOfStock) {
        if (outOfStock > 0) return InventoryStatus.CRITICAL;
        if (lowStock > 0) return InventoryStatus.WARNING;
        return InventoryStatus.HEALTHY;
    }
}
