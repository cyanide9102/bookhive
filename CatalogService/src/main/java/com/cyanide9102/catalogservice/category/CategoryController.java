package com.cyanide9102.catalogservice.category;

import com.cyanide9102.catalogservice.category.dto.CategoryRequest;
import com.cyanide9102.catalogservice.category.dto.CategoryResponse;
import com.cyanide9102.catalogservice.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request, @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Roles") List<String> userRoles) {

        return categoryService.createCategory(request, userId, userRoles);
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {

        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable UUID id) {

        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request, @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Roles") List<String> userRoles) {

        return categoryService.updateCategory(id, request, userId, userRoles);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id, @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Roles") List<String> userRoles) {

        categoryService.deleteCategory(id, userId, userRoles);
    }
}
