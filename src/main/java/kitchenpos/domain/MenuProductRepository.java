package kitchenpos.domain;

import kitchenpos.dao.MenuProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuProductRepository extends JpaRepository<MenuProduct, Long>, MenuProductDao {
}
