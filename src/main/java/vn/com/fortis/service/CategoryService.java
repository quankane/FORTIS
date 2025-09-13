package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.category.CategoryRequestDto;
import vn.com.fortis.domain.dto.response.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto addCategory(CategoryRequestDto categoryRequest);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequest);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    List<CategoryResponseDto> getAllSubCategories();

    void deleteCategory(Long id);

    CategoryResponseDto getCategoryByCategoryName(String categoryName);
}
