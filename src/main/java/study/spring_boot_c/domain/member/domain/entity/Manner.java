package study.spring_boot_c.domain.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import study.spring_boot_c.domain.model.entity.BaseEntity;
import study.spring_boot_c.domain.product.domain.entity.Product;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Manner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member evaluatee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member evaluator;

}
