package study.spring_boot_c.domain.product.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring_boot_c.domain.product.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
