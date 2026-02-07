package com.example.lab04.controller;

import com.example.lab04.model.Category;
import com.example.lab04.model.Product;
import com.example.lab04.service.CategoryService;
import com.example.lab04.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping()
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        model.addAttribute("products", productService.getAll()); // Giữ lại để tương thích với template
        return "product/products";
    }
    
    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }
    
    @PostMapping("/create")
    public String Create(@Valid Product newProduct,
                        BindingResult result,
                        @RequestParam("category.id") int categoryId,
                        @RequestParam("imageProduct") MultipartFile imageProduct,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        
        // Validate image file
        if (imageProduct != null && !imageProduct.isEmpty()) {
            String filename = imageProduct.getOriginalFilename();
            if (filename != null && filename.length() > 200) {
                result.rejectValue("imageFile", "error.imageFile", "Tên file hình ảnh không được vượt quá 200 ký tự");
                model.addAttribute("product", newProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/create";
            }
            
            try {
                productService.updateImage(newProduct, imageProduct); // Xử lý ảnh
            } catch (IllegalArgumentException e) {
                result.rejectValue("imageFile", "error.imageFile", e.getMessage());
                model.addAttribute("product", newProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/create";
            }
        }
        
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);
        productService.add(newProduct);
        return "redirect:/products";
    }
    
    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "redirect:/products"; // Redirect thay vì error page
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }
    
    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct,
                      BindingResult result,
                      @RequestParam("category.id") int categoryId,
                      @RequestParam("imageProduct") MultipartFile imageProduct,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        
        // Lấy sản phẩm hiện tại để giữ lại ảnh cũ nếu không upload ảnh mới
        Product existingProduct = productService.get(editProduct.getId());
        if (existingProduct == null) {
            return "redirect:/products";
        }
        
        // Xử lý ảnh
        if (imageProduct != null && !imageProduct.isEmpty()) {
            String filename = imageProduct.getOriginalFilename();
            if (filename != null && filename.length() > 200) {
                result.rejectValue("imageFile", "error.imageFile", "Tên file hình ảnh không được vượt quá 200 ký tự");
                model.addAttribute("product", editProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/edit";
            }
            
            try {
                productService.updateImage(editProduct, imageProduct); // Cập nhật ảnh nếu có
            } catch (IllegalArgumentException e) {
                result.rejectValue("imageFile", "error.imageFile", e.getMessage());
                model.addAttribute("product", editProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/edit";
            }
        } else {
            // Giữ lại ảnh cũ nếu không upload ảnh mới
            editProduct.setImage(existingProduct.getImage());
        }
        
        // Set category
        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);
        
        productService.update(editProduct); // Cập nhật sản phẩm
        return "redirect:/products";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        boolean deleted = productService.delete(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm để xóa!");
        }
        return "redirect:/products";
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        model.addAttribute("listproduct", productService.search(keyword));
        model.addAttribute("products", productService.search(keyword)); // Giữ lại để tương thích
        model.addAttribute("keyword", keyword);
        return "product/products";
    }
}
