package kitchenpos.domain;

import kitchenpos.dao.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductDao {
}
