package kitchenpos.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kitchenpos.application.MenuService;
import kitchenpos.domain.Menu;
import kitchenpos.request.MenuProductRequest;
import kitchenpos.request.MenuRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MenuRestController.class)
class MenuRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuService menuService;

    @DisplayName("POST /api/menus - (이름, 가격, 메뉴 그룹 아이디, 복수의 메뉴 상품)으로 메뉴를 추가한다.")
    @Test
    void create() throws Exception {
        // given
        String name = "후라이드+후라이드";
        BigDecimal price = BigDecimal.valueOf(19000);
        Long menuGroupId = 1L;

        List<MenuProductRequest> menuProducts = Collections.singletonList(
                new MenuProductRequest(1L, 2L)
        );
        MenuRequest menuRequest = new MenuRequest(
                name,
                price,
                menuGroupId,
                menuProducts
        );
        Menu menu = menuRequest.toMenu();
        Menu expectedMenu = Menu.builder()
                .id(1L)
                .name(menu.getName())
                .price(menu.getPrice())
                .menuGroup(menu.getMenuGroup())
                .menuProducts(menu.getMenuProducts())
                .build();

        given(menuService.create(any(Menu.class))).willReturn(expectedMenu);

        // when
        MockHttpServletResponse response = mockMvc.perform(postApiMenus(menuRequest))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("location")).isEqualTo("/api/menus/" + expectedMenu.getId());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

        then(menuService).should(times(1)).create(any(Menu.class));
    }

    private RequestBuilder postApiMenus(MenuRequest menuRequest) throws JsonProcessingException {
        return post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuRequest));
    }

    @DisplayName("GET /api/menus - 전체 메뉴의 리스트를 가져온다.")
    @Test
    void list() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(getApiMenus())
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(menuService).should(times(1)).list();
    }

    private RequestBuilder getApiMenus() {
        return get("/api/menus");
    }
}
