package kitchenpos.domain;

import kitchenpos.dao.OrderLineItemDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long>, OrderLineItemDao {
}
