package kitchenpos.domain;

import kitchenpos.dao.MenuDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuDao {
}
