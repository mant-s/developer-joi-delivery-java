package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.ProductLookupKey;
import com.tw.joi.delivery.seedData.SeedData;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final Map<ProductLookupKey, GroceryProduct> products = SeedData.groceryProducts;

    public GroceryProduct getProduct(String productId, String outletId) {
        return products.get(new ProductLookupKey(productId, outletId));
    }

}
