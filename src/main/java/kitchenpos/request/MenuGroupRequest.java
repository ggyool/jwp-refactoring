package kitchenpos.request;

import kitchenpos.domain.MenuGroup;

public class MenuGroupRequest {

    private Long id;
    private String name;

    public MenuGroupRequest() {
    }

    public MenuGroupRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroup toMenuGroup() {
        return MenuGroup.builder()
                .id(id)
                .name(name)
                .build();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
