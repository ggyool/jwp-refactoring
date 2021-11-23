package kitchenpos.request;

import kitchenpos.domain.Product;

import java.math.BigDecimal;

public class ProductRequest {

    private Long id;
    private String name;
    private BigDecimal price;

    public ProductRequest() {
    }

    public ProductRequest(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
