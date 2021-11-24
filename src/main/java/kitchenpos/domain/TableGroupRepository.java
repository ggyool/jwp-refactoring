package kitchenpos.domain;

import kitchenpos.dao.TableGroupDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableGroupRepository extends JpaRepository<TableGroup, Long>, TableGroupDao {
}
