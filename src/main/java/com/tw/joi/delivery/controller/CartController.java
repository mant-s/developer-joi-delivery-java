package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.service.CartService;
import com.tw.joi.delivery.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<CartProductInfo> addProductToCart(@RequestBody AddProductRequest addProductRequest) {
        return ResponseEntity.ok(cartService.addProductToCartForUser(addProductRequest));
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam(name = "userId") String userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }

    @GetMapping("/get-product")
    public ResponseEntity<GroceryProduct> getProduct(@RequestParam(name = "productId") String productId, @RequestParam(name = "outletId") String outletId) {
        return ResponseEntity.ok(productService.getProduct(productId, outletId));
    }
}
