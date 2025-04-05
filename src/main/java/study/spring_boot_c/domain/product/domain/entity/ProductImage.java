package study.spring_boot_c.domain.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import study.spring_boot_c.domain.member.domain.entity.Member;
import study.spring_boot_c.domain.model.entity.BaseEntity;
import study.spring_boot_c.domain.product.domain.enums.Category;
import study.spring_boot_c.domain.product.domain.enums.ProductState;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}

