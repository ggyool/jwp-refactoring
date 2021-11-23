package kitchenpos.response;

import kitchenpos.domain.Menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MenuResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private MenuGroupResponse menuGroupResponse;
    private List<MenuProductResponse> menuProducts;

    public MenuResponse() {
    }

    public MenuResponse(Long id, String name, BigDecimal price, MenuGroupResponse menuGroupResponse,
                        List<MenuProductResponse> menuProducts) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupResponse = menuGroupResponse;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse from(Menu menu) {
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.id = menu.getId();
        menuResponse.name = menu.getName();
        menuResponse.price = menu.getPrice();
        menuResponse.menuGroupResponse = MenuGroupResponse.from(menu.getMenuGroup());
        menuResponse.menuProducts = MenuProductResponse.toList(menu.getMenuProducts());
        return menuResponse;
    }

    public static List<MenuResponse> toList(List<Menu> menus) {
        return menus.stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
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

    public MenuGroupResponse getMenuGroupResponse() {
        return menuGroupResponse;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return menuProducts;
    }
}
