package study.spring_boot_c.domain.member.converter;

import study.spring_boot_c.domain.member.domain.entity.Member;
import study.spring_boot_c.domain.member.dto.MemberResponseDTO;

public class MemberConverter {

    public static MemberResponseDTO.Example toExample(Member member){
        return MemberResponseDTO.Example.builder()
                .name(member.getNickName())
                .build();
    }
}
