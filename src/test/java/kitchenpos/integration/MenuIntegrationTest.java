package kitchenpos.integration;

import kitchenpos.domain.Menu;
import kitchenpos.request.MenuProductRequest;
import kitchenpos.request.MenuRequest;
import kitchenpos.response.MenuProductResponse;
import kitchenpos.response.MenuResponse;
import kitchenpos.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuIntegrationTest extends AbstractIntegrationTest {

    @DisplayName("POST /api/menus - (이름, 가격, 메뉴 그룹 아이디, 복수의 메뉴 상품)으로 메뉴를 추가한다.")
    @Test
    void create() {
        // given
        String name = "후라이드+후라이드";
        BigDecimal price = BigDecimal.valueOf(19000);
        Long friedChickenProductId = 1L;
        Long doubleChickenMenuGroupId = 1L;

        MenuProductRequest menuProductRequest = new MenuProductRequest(
                friedChickenProductId,
                2L
        );
        MenuRequest menuRequest = new MenuRequest(
                name,
                price,
                doubleChickenMenuGroupId,
                Collections.singletonList(menuProductRequest)
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<MenuResponse> responseEntity = post(
                "/api/menus",
                httpHeaders,
                menuRequest,
                new ParameterizedTypeReference<MenuResponse>() {
                }
        );
        MenuResponse menuResponse = responseEntity.getBody();

        // then
        assertThat(menuResponse).isNotNull();
        assertThat(menuResponse.getId()).isNotNull();
        assertThat(menuResponse.getName()).isEqualTo(name);
        assertThat(menuResponse.getPrice()).isEqualByComparingTo(price);
        assertThat(menuResponse.getMenuGroupResponse().getId()).isEqualByComparingTo(doubleChickenMenuGroupId);
        assertThat(menuResponse.getMenuProducts())
                .extracting(MenuProductResponse::getSeq)
                .hasSize(1);
        assertThat(menuResponse.getMenuProducts())
                .extracting(MenuProductResponse::getProduct)
                .extracting(ProductResponse::getId)
                .containsExactly(friedChickenProductId);
        assertThat(menuResponse.getMenuProducts())
                .extracting(MenuProductResponse::getQuantity)
                .containsExactly(2L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(extractLocation(responseEntity)).isEqualTo("/api/menus/" + menuResponse.getId());
    }

    @DisplayName("GET /api/menus - 전체 메뉴의 리스트를 가져온다.")
    @Test
    void list() {
        // when
        ResponseEntity<List<MenuResponse>> responseEntity = get(
                "/api/menus",
                new ParameterizedTypeReference<List<MenuResponse>>() {
                }
        );
        List<MenuResponse> menuResponses = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(menuResponses).hasSize(6);
    }
}
