package kitchenpos.domain;

import kitchenpos.dao.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderDao {
}
