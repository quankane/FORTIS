package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.product.ProductRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductResponseDto;
import vn.com.fortis.domain.entity.product.Product;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class, MediaMapper.class, ProductVariationMapper.class}
)
public interface ProductMapper {
    @Mapping(target = "categoriesName",
            expression = "java(product.getCategories().stream().map(vn.com.fortis.domain.entity.product.Category::getCategoryName).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "discountPercent",
            expression = "java(product.getCategories().stream()" +
                    "    .map(vn.com.fortis.domain.entity.product.Category::getPromotion)" +
                    "    .filter(java.util.Objects::nonNull)" +
                    "    .map(vn.com.fortis.domain.entity.product.Promotion::getDiscountPercent)" +
                    "    .filter(java.util.Objects::nonNull)" +
                    "    .max(java.util.Comparator.naturalOrder())" +
                    "    .orElse(0.0f))")
    @Mapping(target = "daysRemaining",
            expression = "java(product.getCategories().stream()" +
                    "    .map(vn.com.fortis.domain.entity.product.Category::getPromotion)" +
                    "    .filter(java.util.Objects::nonNull)" +
                    "    .filter(p -> p.getEndDate() != null && p.getEndDate().isAfter(java.time.LocalDate.now()))" +
                    "    .max(java.util.Comparator.comparing(vn.com.fortis.domain.entity.product.Promotion::getDiscountPercent))" +
                    "    .map(vn.com.fortis.domain.entity.product.Promotion::getEndDate)" +
                    "    .map(ld -> java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), ld))" +
                    "    .orElse(0L))")
    ProductResponseDto productToProductResponse(Product product);

    @Mapping(target = "categories", ignore = true)
    Product createProductRequestDtoToProduct(ProductRequestDto request);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    void updateProductFromUpdateDto(UpdateProductRequestDto request, @MappingTarget Product product);
}
