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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long oAuthId;

    private String nickName;

    private String location;

    private String profileUrl;

    private double manner;

}
