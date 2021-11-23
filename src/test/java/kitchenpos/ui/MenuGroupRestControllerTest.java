package kitchenpos.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kitchenpos.application.MenuGroupService;
import kitchenpos.domain.MenuGroup;
import kitchenpos.request.MenuGroupRequest;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MenuGroupRestController.class)
class MenuGroupRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuGroupService menuGroupService;

    @DisplayName("POST /api/menu-groups - (이름)으로 메뉴 그룹을 추가한다.")
    @Test
    void create() throws Exception {
        // given
        MenuGroupRequest menuGroupRequest = new MenuGroupRequest(
                null,
                "두마리메뉴"
        );
        MenuGroup menuGroup = MenuGroup.builder()
                .name("두마리메뉴")
                .build();
        MenuGroup expectedMenuGroup = MenuGroup.builder()
                .name("두마리메뉴")
                .id(1L)
                .build();

        given(menuGroupService.create(refEq(menuGroup))).willReturn(expectedMenuGroup);

        // when
        MockHttpServletResponse response = mockMvc.perform(postApiMenuGroups(menuGroupRequest))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("location")).isEqualTo("/api/menu-groups/" + expectedMenuGroup.getId());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

        then(menuGroupService).should(times(1)).create(refEq(menuGroup));
    }

    private RequestBuilder postApiMenuGroups(MenuGroupRequest menuGroupRequest) throws JsonProcessingException {
        return post("/api/menu-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuGroupRequest));
    }

    @DisplayName("GET /api/menu-groups - 전체 메뉴 그룹의 리스트를 가져온다.")
    @Test
    void list() throws Exception {
        // given
        List<MenuGroup> expectedMenuGroups = Arrays.asList(
                MenuGroup.builder().id(1L).name("두마리메뉴").build(),
                MenuGroup.builder().id(2L).name("한마리메뉴").build(),
                MenuGroup.builder().id(3L).name("순살파닭두마리메뉴").build()
        );

        given(menuGroupService.list()).willReturn(expectedMenuGroups);

        // when
        MockHttpServletResponse response = mockMvc.perform(getApiMenuGroups())
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        then(menuGroupService).should(times(1)).list();
    }

    private RequestBuilder getApiMenuGroups() {
        return get("/api/menu-groups");
    }
}
