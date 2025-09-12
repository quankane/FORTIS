package vn.com.fortis.domain.mapper;

//import vn.com.fortis.domain.dto.request.category.CategoryRequestDto;
//import vn.com.fortis.domain.dto.response.category.CategoryResponseDto;
import vn.com.fortis.domain.entity.product.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CategoryMapper {

//    Category categoryRequestDtoToCategory(CategoryRequestDto requestDto);
//
//    void updateCategoryFromDto(CategoryRequestDto requestDto, @MappingTarget Category category);
//
//    CategoryResponseDto categoryToCategoryResponseDto(Category category);
}
