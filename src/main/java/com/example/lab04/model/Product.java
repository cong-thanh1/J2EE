package com.example.lab04.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm phải từ 1 đến 9,999,999")
    @Max(value = 9999999, message = "Giá sản phẩm phải từ 1 đến 9,999,999")
    private Double price;
    
    private String image;
    
    @NotNull(message = "Danh mục không được để trống")
    private Category category;
    
    // Transient field for file upload
    private transient MultipartFile imageFile;
}
