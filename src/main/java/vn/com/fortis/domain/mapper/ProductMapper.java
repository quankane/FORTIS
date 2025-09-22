package vn.com.fortis.domain.mapper;

//import vn.com.fortis.domain.dto.response.product.ProductResponseDto;
import vn.com.fortis.domain.dto.response.product.ProductResponseDto;
import vn.com.fortis.domain.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProductMapper {

    ProductResponseDto productToProductResponseDto (Product product);
}
