package com.cyanide9102.catalogservice.category.service;

import com.cyanide9102.catalogservice.category.dto.CategoryRequest;
import com.cyanide9102.catalogservice.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request, String userId);

    List<CategoryResponse> getCategories();

    CategoryResponse getCategoryById(String id);

    CategoryResponse updateCategory(String id, CategoryRequest request, String userId);

    void deleteCategory(String id);
}
