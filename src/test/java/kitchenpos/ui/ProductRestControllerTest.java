package kitchenpos.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kitchenpos.application.ProductService;
import kitchenpos.domain.Product;
import kitchenpos.request.ProductRequest;
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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @DisplayName("POST /api/products - (이름, 가격)으로 상품을 추가한다.")
    @Test
    void create() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest(
                null,
                "후라이드",
                BigDecimal.valueOf(16000)
        );
        Product newProduct = Product.builder()
                .name("후라이드")
                .price(BigDecimal.valueOf(16000))
                .build();
        Product expectedProduct = Product.builder()
                .id(1L)
                .name("후라이드")
                .price(BigDecimal.valueOf(16000))
                .build();

        given(productService.create(refEq(newProduct))).willReturn(expectedProduct);

        // when
        MockHttpServletResponse response = mockMvc.perform(postApiProducts(productRequest))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("location")).isEqualTo("/api/products/" + expectedProduct.getId());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

        then(productService).should(times(1)).create(refEq(newProduct));
    }

    private RequestBuilder postApiProducts(ProductRequest productRequest) throws JsonProcessingException {
        return post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest));
    }

    @DisplayName("GET /api/products - 상품의 리스트를 가져온다. (list)")
    @Test
    void list() throws Exception {
        // given
        List<Product> expectedProducts = Arrays.asList(
                Product.builder().id(1L).name("후라이드").price(BigDecimal.valueOf(16000)).build(),
                Product.builder().id(2L).name("양념치킨").price(BigDecimal.valueOf(16000)).build(),
                Product.builder().id(3L).name("반반치킨").price(BigDecimal.valueOf(16000)).build()
        );

        given(productService.list()).willReturn(expectedProducts);

        // when
        MockHttpServletResponse response = mockMvc.perform(getApiProducts())
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        then(productService).should(times(1)).list();
    }

    private RequestBuilder getApiProducts() {
        return get("/api/products");
    }
}
