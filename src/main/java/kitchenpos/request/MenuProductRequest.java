package kitchenpos.request;

import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class MenuProductRequest {

    private Long productId;
    private Long quantity;

    public MenuProductRequest() {
    }

    public MenuProductRequest(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct toMenuProduct() {
        return MenuProduct.builder()
                .product(Product.builder().id(productId).build())
                .quantity(quantity)
                .build();
    }

    public static List<MenuProduct> toMenuProducts(List<MenuProductRequest> menuProducts) {
        return menuProducts.stream()
                .map(MenuProductRequest::toMenuProduct)
                .collect(Collectors.toList());
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
