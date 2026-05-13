package com.tw.joi.delivery.dto.response;

import com.tw.joi.delivery.domain.enums.InventoryStatus;
import java.util.List;

public record InventoryHealthInfo(
    String storeId,
    String storeName,
    long totalProducts,
    long lowStockCount,
    long outOfStockCount,
    List<String> criticalProductIds,
    InventoryStatus overallStatus
) {}
