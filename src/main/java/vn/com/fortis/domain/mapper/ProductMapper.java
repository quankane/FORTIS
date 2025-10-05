package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.entity.product.Product;
import vn.com.fortis.domain.dto.request.product.ProductRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductResponseDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class, MediaMapper.class}
)
public interface ProductMapper {
    @Mapping(target = "categoriesName",
            expression = "java(product.getCategories().stream().map(vn.com.fortis.domain.entity.product.Category::getCategoryName).collect(java.util.stream.Collectors.toList()))")
    ProductResponseDto productToProductResponse(Product product);

    @Mapping(target = "categories", ignore = true)
    Product createProductRequestDtoToProduct(ProductRequestDto request);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    void updateProductFromUpdateDto(UpdateProductRequestDto request, @MappingTarget Product product);
}
