package com.cyanide9102.catalogservice.category.service;

import com.cyanide9102.catalogservice.category.dto.CategoryRequest;
import com.cyanide9102.catalogservice.category.dto.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request, String userId, List<String> userRoles);

    List<CategoryResponse> getCategories();

    CategoryResponse getCategoryById(UUID id);

    CategoryResponse updateCategory(UUID id, CategoryRequest request, String userId, List<String> userRoles);

    void deleteCategory(UUID id, String userId, List<String> userRoles);
}
