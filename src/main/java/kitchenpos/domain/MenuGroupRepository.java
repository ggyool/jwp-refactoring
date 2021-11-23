package kitchenpos.domain;

import kitchenpos.dao.MenuGroupDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long>, MenuGroupDao {
}
