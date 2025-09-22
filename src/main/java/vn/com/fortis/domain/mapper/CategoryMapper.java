package vn.com.fortis.domain.mapper;

import org.mapstruct.*;
import vn.com.fortis.domain.dto.request.category.CategoryRequestDto;
import vn.com.fortis.domain.dto.response.category.CategoryResponseDto;
import vn.com.fortis.domain.entity.product.Category;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CategoryMapper {

    @Mapping(target = "parentCategory", ignore = true)
    Category categoryRequestDtoToCategory(CategoryRequestDto requestDto);

    void updateCategoryFromDto(CategoryRequestDto requestDto, @MappingTarget Category category);

    @Mapping(target = "parentId", source = "parentCategory.id")
    CategoryResponseDto categoryToCategoryResponseDto(Category category);
}

