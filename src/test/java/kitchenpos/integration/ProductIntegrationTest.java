package kitchenpos.integration;

import kitchenpos.request.ProductRequest;
import kitchenpos.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductIntegrationTest extends AbstractIntegrationTest {

    @DisplayName("POST /api/products - (이름, 가격)으로 상품을 추가한다.")
    @Test
    void create() {
        // given
        String name = "깐부치킨";
        BigDecimal price = BigDecimal.valueOf(20000);
        ProductRequest productRequest = new ProductRequest(
                null,
                name,
                price
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<ProductResponse> responseEntity = post(
                "/api/products",
                httpHeaders,
                productRequest,
                new ParameterizedTypeReference<ProductResponse>() {
                }
        );
        ProductResponse productResponse = responseEntity.getBody();

        // then
        assertThat(productResponse).isNotNull();
        assertThat(productResponse.getId()).isNotNull();
        assertThat(productResponse.getName()).isEqualTo(name);
        assertThat(productResponse.getPrice()).isEqualByComparingTo(price);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(extractLocation(responseEntity)).isEqualTo("/api/products/" + productResponse.getId());
    }

    @DisplayName("GET /api/products - 상품의 리스트를 가져온다. (list)")
    @Test
    void list() {
        // when
        ResponseEntity<List<ProductResponse>> responseEntity = get(
                "/api/products",
                new ParameterizedTypeReference<List<ProductResponse>>() {
                }
        );
        List<ProductResponse> products = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(products).hasSize(6);
    }
}
