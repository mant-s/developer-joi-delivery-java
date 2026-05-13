package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.seedData.SeedData;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    // For now, we fetch from SeedData. In the future, this would be a Repository.
    private final List<GroceryStore> stores = List.of(SeedData.store101, SeedData.store102);

    public GroceryStore fetchStoreById(String storeId) {
        return stores.stream()
            .filter(store -> storeId.equals(store.getOutletId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Store not found: " + storeId));
    }
}
