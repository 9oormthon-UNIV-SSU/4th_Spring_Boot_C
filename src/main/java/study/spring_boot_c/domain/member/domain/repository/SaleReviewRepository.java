package study.spring_boot_c.domain.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring_boot_c.domain.member.domain.entity.SaleReview;

public interface SaleReviewRepository extends JpaRepository<SaleReview,Long> {
}
