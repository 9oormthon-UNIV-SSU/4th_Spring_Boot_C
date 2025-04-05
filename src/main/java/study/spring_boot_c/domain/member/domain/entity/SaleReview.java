package study.spring_boot_c.domain.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import study.spring_boot_c.domain.member.domain.enums.SaleReviewType;
import study.spring_boot_c.domain.model.entity.BaseEntity;
import study.spring_boot_c.domain.product.domain.entity.Product;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SaleReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private SaleReviewType saleReviewType;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
