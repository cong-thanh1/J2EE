package com.example.lab04.service;

import com.example.lab04.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    List<Product> listProduct = new ArrayList<>();
    
    public List<Product> getAll() {
        return listProduct;
    }
    
    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }
    
    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            if (editProduct.getImage() != null && !editProduct.getImage().isEmpty()) {
                find.setImage(editProduct.getImage());
            }
            if (editProduct.getCategory() != null) {
                find.setCategory(editProduct.getCategory());
            }
        }
    }
    
    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        String contentType = imageProduct.getContentType();
        if (contentType == null || 
            (!contentType.equals("image/png") && 
             !contentType.equals("image/jpeg") && 
             !contentType.equals("image/jpg"))) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }
        
        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("src/main/resources/static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    
    public boolean delete(Integer id) {
        Product product = get(id);
        if (product != null) {
            return listProduct.remove(product);
        }
        return false;
    }
    
    public List<Product> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        
        String lowerKeyword = keyword.toLowerCase();
        return listProduct.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) ||
                           (p.getCategory() != null && p.getCategory().getName().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }
}
