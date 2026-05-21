package com.cyanide9102.catalogservice.category;

import com.cyanide9102.catalogservice.category.dto.CategoryRequest;
import com.cyanide9102.catalogservice.category.dto.CategoryResponse;
import com.cyanide9102.catalogservice.category.service.CategoryService;
import com.cyanide9102.common.annotation.RequiresAdmin;
import com.cyanide9102.common.context.RequestContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final RequestContext requestContext;
    private final CategoryService categoryService;

    @RequiresAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {

        return categoryService.createCategory(request, requestContext.userId());
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {

        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable String id) {

        return categoryService.getCategoryById(id);
    }

    @RequiresAdmin
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable String id, @Valid @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(id, request, requestContext.userId());
    }

    @RequiresAdmin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String id) {

        categoryService.deleteCategory(id);
    }
}
