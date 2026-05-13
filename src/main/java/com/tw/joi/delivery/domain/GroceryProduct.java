package com.tw.joi.delivery.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryProduct extends Product {

    private BigDecimal sellingPrice;
    private BigDecimal weight;

    private LocalDate expiryDate;

    private int threshold;

    private int availableStock;

    private BigDecimal discount;

    private GroceryStore store;

    @Builder
    public GroceryProduct(String productId, String productName, BigDecimal mrp, Cart cart,
                          BigDecimal sellingPrice, BigDecimal weight, LocalDate expiryDate, int threshold,
                          int availableStock, GroceryStore store, BigDecimal discount) {
        super(productId, productName,  mrp);
        this.sellingPrice = sellingPrice;
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.threshold = threshold;
        this.availableStock = availableStock;
        this.store = store;
        this.discount = discount;
    }

    public boolean isLowStock() {
        return availableStock <= threshold && availableStock > 0;
    }

    public boolean isOutOfStock() {
        return availableStock == 0;
    }

    public void reduceStock(int quantity) {
        if (quantity > availableStock) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        this.availableStock -= quantity;
    }

    public void updateStock() {
        if (expiryDate != null && LocalDate.now().isAfter(expiryDate)) availableStock = 0;
    }
}