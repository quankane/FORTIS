package vn.com.fortis.utils;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.entity.product.Product;
import vn.com.fortis.domain.entity.product.ProductVariation;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.ProductRepository;
import vn.com.fortis.repository.ProductVariationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateSoldQuantityUtil {

    ProductVariationRepository productVariationRepository;

    ProductRepository productRepository;

    public void updateProductTotalInventoryAndSoldQuantity(Long productId) {
        List<ProductVariation> activeVariations = productVariationRepository
                .findByProductId(productId);

        int totalQuantity = activeVariations.stream()
                .mapToInt(ProductVariation::getInventoryQuantity)
                .sum();

        int soldQuantity = activeVariations.stream()
                .collect(Collectors.summingInt(ProductVariation::getSoldQuantity));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Product.ERR_PRODUCT_NOT_EXISTED));

        product.setInventoryQuantity(totalQuantity);
        product.setSoldQuantity(soldQuantity);
        product.setUpdatedAt(new Date());
        productRepository.save(product);
    }
}
