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
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    private String title;

    private String description;

    private String location;

    private ProductState productState;

    private String thumbNailImage;

    private int viewCount;

    private int refreshCount;

    private LocalDateTime refreshedAt;

    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member buyer;

}
