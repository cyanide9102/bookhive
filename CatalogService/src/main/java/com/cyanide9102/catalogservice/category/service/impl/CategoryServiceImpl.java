package com.cyanide9102.catalogservice.category.service.impl;

import com.cyanide9102.catalogservice.annotation.RequiresAdmin;
import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.catalogservice.category.CategoryMapper;
import com.cyanide9102.catalogservice.category.CategoryRepository;
import com.cyanide9102.catalogservice.category.dto.CategoryRequest;
import com.cyanide9102.catalogservice.category.dto.CategoryResponse;
import com.cyanide9102.catalogservice.category.service.CategoryService;
import com.cyanide9102.catalogservice.common.exception.ResourceNotFoundException;
import com.cyanide9102.catalogservice.context.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final RequestContext requestContext;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @RequiresAdmin
    @Transactional
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        Category category = categoryMapper.toEntity(request);
        category = categoryRepository.save(category);

        return categoryMapper.fromEntity(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponse> getCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategoryById(UUID id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found!"));
        return categoryMapper.fromEntity(category);
    }

    @RequiresAdmin
    @Transactional
    @Override
    public CategoryResponse updateCategory(UUID id, CategoryRequest request) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found!"));

        categoryMapper.updateEntity(category, request);
        category = categoryRepository.save(category);

        return categoryMapper.fromEntity(category);
    }

    @RequiresAdmin
    @Transactional
    @Override
    public void deleteCategory(UUID id) {

        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(categoryRepository::delete);
    }
}
