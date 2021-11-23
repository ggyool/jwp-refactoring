package kitchenpos.dao;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@Import({JdbcTemplateMenuDao.class, JdbcTemplateMenuGroupDao.class})
class JdbcTemplateMenuDaoTest extends AbstractJdbcTemplateDaoTest {

    private static final Long NON_EXISTENT_ID = 987654321L;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuGroupDao menuGroupDao;

    private Menu friedChicken, seasoningChicken, halfAndHalfChicken;

    private MenuGroup singleQuantityMenuGroup;

    @BeforeEach
    void setUp() {
        singleQuantityMenuGroup = menuGroupDao.save(
                MenuGroup.builder().name("한마리메뉴").build()
        );
        friedChicken = menuDao.save(
                Menu.builder()
                        .name("후라이드치킨")
                        .price(BigDecimal.valueOf(16000))
                        .menuGroup(
                                MenuGroup.builder().id(singleQuantityMenuGroup.getId()).build()
                        )
                        .build()
        );
        seasoningChicken = menuDao.save(
                Menu.builder()
                        .name("양념치킨")
                        .price(BigDecimal.valueOf(16000))
                        .menuGroup(
                                MenuGroup.builder().id(singleQuantityMenuGroup.getId()).build()
                        )
                        .build()
        );
        halfAndHalfChicken = menuDao.save(
                Menu.builder()
                        .name("반반치킨")
                        .price(BigDecimal.valueOf(16000))
                        .menuGroup(
                                MenuGroup.builder().id(singleQuantityMenuGroup.getId()).build()
                        )
                        .build()
        );
    }

    @DisplayName("메뉴를 저장한다.")
    @Test
    void save() {
        // given
        String name = "반반치킨";
        BigDecimal price = BigDecimal.valueOf(16000);
        Long menuGroupId = singleQuantityMenuGroup.getId();

        Menu newMenu = Menu.builder()
                .name(name)
                .price(price)
                .menuGroup(
                        MenuGroup.builder().id(menuGroupId).build()
                )
                .build();

        // when
        Menu menu = menuDao.save(newMenu);

        // then
        assertThat(menu.getId()).isNotNull();
        assertThat(menu.getName()).isEqualTo(name);
        assertThat(menu.getPrice()).isEqualByComparingTo(price);
        assertThat(menu.getMenuGroup().getId()).isEqualTo(menuGroupId);
    }

    @DisplayName("존재하는 메뉴를 조회한다.")
    @Test
    void findByIdWhenExistent() {
        // given
        Long id = friedChicken.getId();

        // when
        Optional<Menu> optionalMenu = menuDao.findById(id);

        // then
        assertThat(optionalMenu).get()
                .usingRecursiveComparison()
                .isEqualTo(friedChicken);
    }

    @DisplayName("존재하지 않는 메뉴를 조회한다.")
    @Test
    void findByIdWhenNonexistent() {
        // when
        Optional<Menu> optionalMenu = menuDao.findById(NON_EXISTENT_ID);

        // then
        assertThat(optionalMenu).isEmpty();
    }

    @DisplayName("전체 메뉴를 가져온다.")
    @Test
    void findAll() {
        // when
        List<Menu> menus = menuDao.findAll();

        // then
        assertThat(menus)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(friedChicken, seasoningChicken, halfAndHalfChicken);
    }

    @DisplayName("메뉴에 존재하는 ID의 수를 센다.")
    @Test
    void countByIdIn() {
        // given
        List<Long> ids = Arrays.asList(
                friedChicken.getId(), halfAndHalfChicken.getId(), NON_EXISTENT_ID
        );

        // when, then
        assertThat(menuDao.countByIdIn(ids)).isEqualTo(2);
    }
}
