package com.esteban.inventory.manager.be.service;

import com.esteban.inventory.manager.be.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final List<Product> productList = new ArrayList<>();

    public ProductService() {
        // Preload some dummy data
        productList.add(new Product(1L, "Sandwich", "Food", 999.99, LocalDate.of(2025, 4, 30), 10, LocalDate.now(), LocalDate.now()));
        productList.add(new Product(2L, "Mouse", "Electronics", 25.99, null, 5, LocalDate.now(), LocalDate.now()));
        productList.add(new Product(3L, "Notebook", "Clothing", 3.50, null, 20, LocalDate.now(), LocalDate.now()));
        productList.add(new Product(4L, "Keyboard", "Electronics", 49.99, null, 15, LocalDate.now(), LocalDate.now()));
        productList.add(new Product(14L, "Bread", "Food", 5.99, LocalDate.of(2025, 8, 20), 30, LocalDate.now(), LocalDate.now()));
    }

    // Fetch all products with optional filtering and pagination
    public Map<String, Object> getProducts(String name, String category, Boolean inStock, int page, int size) {
        List<Product> filteredProducts = productList.stream()
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(product -> (category == null || product.getCategory().equalsIgnoreCase(category)))
                .filter(product -> (inStock == null || (inStock ? product.getQuantityInStock() > 0 : product.getQuantityInStock() == 0)))
                .collect(Collectors.toList());

        // Get the total number of products after filtering
        int totalProducts = filteredProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / size);

        // Apply pagination
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalProducts);

        List<Product> paginatedProducts = fromIndex >= totalProducts ? new ArrayList<>() : filteredProducts.subList(fromIndex, toIndex);

        // Create a response map
        Map<String, Object> response = new HashMap<>();
        response.put("products", paginatedProducts);
        response.put("totalProducts", totalProducts);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }
}
