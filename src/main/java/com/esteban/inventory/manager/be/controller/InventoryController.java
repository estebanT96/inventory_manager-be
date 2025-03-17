package com.esteban.inventory.manager.be.controller;

import com.esteban.inventory.manager.be.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final ProductService productService;

    public InventoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Map<String, Object> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.getProducts(name, category, inStock, page, size);
    }
}
