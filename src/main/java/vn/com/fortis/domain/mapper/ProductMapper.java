package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.product.CreateProductRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductResponseDto;
import org.mapstruct.*;
import vn.com.fortis.domain.entity.product.Product;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    @Mapping(target = "categoryId", expression = "java(product.getCategories() != null && !product.getCategories().isEmpty() ? product.getCategories().get(0).getId() : null)")
    ProductResponseDto toProductResponseDto(Product product);

    Product createProductRequestDtoToProduct(CreateProductRequestDto request);

    void updateProductFromDto(UpdateProductRequestDto request, @MappingTarget Product product);
}