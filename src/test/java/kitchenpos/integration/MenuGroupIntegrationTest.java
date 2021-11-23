package kitchenpos.integration;

import kitchenpos.request.MenuGroupRequest;
import kitchenpos.response.MenuGroupResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuGroupIntegrationTest extends AbstractIntegrationTest {

    @DisplayName("POST /api/menu-groups - (이름)으로 메뉴 그룹을 추가한다.")
    @Test
    void create() {
        // given
        String name = "두마리메뉴";
        MenuGroupRequest menuGroupRequest = new MenuGroupRequest(
                null,
                name
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<MenuGroupResponse> responseEntity = post(
                "/api/menu-groups",
                httpHeaders,
                menuGroupRequest,
                new ParameterizedTypeReference<MenuGroupResponse>() {
                }
        );
        MenuGroupResponse menuGroupResponse = responseEntity.getBody();

        // then
        assertThat(menuGroupResponse).isNotNull();
        assertThat(menuGroupResponse.getId()).isNotNull();
        assertThat(menuGroupResponse.getName()).isEqualTo(name);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(extractLocation(responseEntity)).isEqualTo("/api/menu-groups/" + menuGroupResponse.getId());
    }

    @DisplayName("GET /api/menu-groups - 전체 메뉴 그룹의 리스트를 가져온다.")
    @Test
    void list() {
        // when
        ResponseEntity<List<MenuGroupResponse>> responseEntity = get(
                "/api/menu-groups",
                new ParameterizedTypeReference<List<MenuGroupResponse>>() {
                }
        );
        List<MenuGroupResponse> menuGroupResponses = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(menuGroupResponses).hasSize(4);
    }
}
