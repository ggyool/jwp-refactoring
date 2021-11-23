package kitchenpos.request;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;

import java.math.BigDecimal;
import java.util.List;

public class MenuRequest {

    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private List<MenuProductRequest> menuProducts;

    public MenuRequest() {

    }

    public MenuRequest(String name, BigDecimal price, Long menuGroupId, List<MenuProductRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public Menu toMenu() {
        return Menu.builder()
                .name(name)
                .price(price)
                .menuGroup(
                        MenuGroup.builder().id(menuGroupId).build()
                )
                .menuProducts(MenuProductRequest.toMenuProducts(menuProducts))
                .build();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }
}

