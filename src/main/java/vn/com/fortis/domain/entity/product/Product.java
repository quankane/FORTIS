package vn.com.fortis.domain.entity.product;

import vn.com.fortis.constant.CommonConstant;
import vn.com.fortis.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String productCode;

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    Double price;

    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    String detailDescription;

    @Column(nullable = false)
    Integer inventoryQuantity;

    @Column()
    Boolean isDeleted = CommonConstant.FALSE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<ProductVariation> productVariations = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Media> medias = new HashSet<>();

    // ---------------- Helper methods ----------------
    //Category
    public void addCategory(Category category) {
        if (categories == null) {
            categories = new HashSet<>();
        }
        if (!categories.contains(category)) {
            categories.add(category);
            category.getProducts().add(this);
        }
    }

    public void removeCategory(Category category) {
        if (categories.contains(category)) {
            categories.remove(category);
            category.getProducts().remove(this);
        }
    }

    //Review
    public void addReview(Review review) {
        if (!reviews.contains(review)) {
            reviews.add(review);
            review.setProduct(this);
        }
    }

    public void removeReview(Review review) {
        if (reviews.contains(review)) {
            reviews.remove(review);
            review.setProduct(null);
        }
    }

    //Cart Item
    public void addCartItem(CartItem cartItem) {
        if (!cartItems.contains(cartItem)) {
            cartItems.add(cartItem);
            cartItem.setProduct(this);
        }
    }

    public void removeCartItem(CartItem cartItem) {
        if (cartItems.contains(cartItem)) {
            cartItems.remove(cartItem);
            cartItem.setProduct(null);
        }
    }

    //Order Item
    public void addOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            orderItems.add(orderItem);
            orderItem.setProduct(this);
        }
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (orderItems.contains(orderItem)) {
            orderItems.remove(orderItem);
            orderItem.setProduct(null);
        }
    }
}
