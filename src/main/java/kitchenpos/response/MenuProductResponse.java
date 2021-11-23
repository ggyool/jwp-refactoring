package kitchenpos.response;

import kitchenpos.domain.MenuProduct;

import java.util.List;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private Long seq;
    private ProductResponse product;
    private Long quantity;

    public MenuProductResponse() {
    }

    public MenuProductResponse(Long seq, ProductResponse product, Long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public static MenuProductResponse from(MenuProduct menuProduct) {
        MenuProductResponse menuProductResponse = new MenuProductResponse();
        menuProductResponse.seq = menuProduct.getSeq();
        menuProductResponse.product = ProductResponse.from(menuProduct.getProduct());
        menuProductResponse.quantity = menuProduct.getQuantity();
        return menuProductResponse;
    }

    public static List<MenuProductResponse> toList(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(MenuProductResponse::from)
                .collect(Collectors.toList());
    }

    public Long getSeq() {
        return seq;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }
}
