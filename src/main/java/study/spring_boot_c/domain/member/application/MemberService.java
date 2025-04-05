package study.spring_boot_c.domain.member.application;

import study.spring_boot_c.domain.member.dto.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO.Example exampleMethod(Long memberId);

}
