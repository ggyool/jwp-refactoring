package kitchenpos.domain;

import kitchenpos.dao.OrderTableDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTableRepository extends JpaRepository<OrderTable, Long>, OrderTableDao {
}
