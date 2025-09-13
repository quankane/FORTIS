package vn.com.fortis.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.dto.request.category.CategoryRequestDto;
import vn.com.fortis.domain.dto.response.category.CategoryResponseDto;
import vn.com.fortis.domain.entity.product.Category;
import vn.com.fortis.domain.mapper.CategoryMapper;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.CategoryRepository;
import vn.com.fortis.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CATEGORY-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequest) {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ResourceNotFoundException(ErrorMessage.Category.ERR_CATEGORY_EXISTED);
        }
        Category category = categoryMapper.categoryRequestDtoToCategory(categoryRequest);
        category.setDeletedAt(null);
        return categoryMapper.categoryToCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessage.Category.ERR_CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategoryFromDto(categoryRequest, category);
        return categoryMapper.categoryToCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessage.Category.ERR_CATEGORY_NOT_EXISTED));
        return categoryMapper.categoryToCategoryResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();

        Map<Long, CategoryResponseDto> categoryMap = new HashMap<>();
        for (Category category : allCategories) {
            categoryMap.put(category.getId(), categoryMapper.categoryToCategoryResponseDto(category));
        }

        List<CategoryResponseDto> topLevelCategories = new ArrayList<>();

        for (Category category : allCategories) {
            CategoryResponseDto currentDto = categoryMap.get(category.getId());
            if (category.getParentCategory() != null) {
                CategoryResponseDto parentDto = categoryMap.get(category.getParentCategory().getId());
                if (parentDto != null) {
                    if (parentDto.getSubCategories() == null) {
                        parentDto.setSubCategories(new ArrayList<>());
                    }
                    parentDto.getSubCategories().add(currentDto);
                }
            } else {
                topLevelCategories.add(currentDto);
            }
        }
        return topLevelCategories;
    }

    @Override
    public List<CategoryResponseDto> getAllSubCategories() {
        List<Category> categories = categoryRepository.findByParentCategoryIsNotNull();
        return categories.stream().map(category -> categoryMapper.categoryToCategoryResponseDto(category)).toList();
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessage.Category.ERR_CATEGORY_NOT_EXISTED));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponseDto getCategoryByCategoryName(String categoryName) {
        Category category = categoryRepository.findByCategoryNameIgnoreCase(categoryName).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.Category.ERR_CATEGORY_NOT_EXISTED));
        return categoryMapper.categoryToCategoryResponseDto(category);
    }
}
