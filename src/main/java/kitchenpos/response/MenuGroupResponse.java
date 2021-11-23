package kitchenpos.response;

import kitchenpos.domain.MenuGroup;

import java.util.List;
import java.util.stream.Collectors;

public class MenuGroupResponse {

    private Long id;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse from(MenuGroup menuGroup) {
        MenuGroupResponse menuGroupResponse = new MenuGroupResponse();
        menuGroupResponse.id = menuGroup.getId();
        menuGroupResponse.name = menuGroup.getName();
        return menuGroupResponse;
    }

    public static List<MenuGroupResponse> toList(List<MenuGroup> menuGroups) {
        return menuGroups.stream()
                .map(MenuGroupResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
