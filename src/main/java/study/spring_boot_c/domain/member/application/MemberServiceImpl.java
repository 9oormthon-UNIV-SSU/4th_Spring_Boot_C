package study.spring_boot_c.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.spring_boot_c.domain.member.converter.MemberConverter;
import study.spring_boot_c.domain.member.domain.entity.Member;
import study.spring_boot_c.domain.member.domain.repository.MemberRepository;
import study.spring_boot_c.domain.member.dto.MemberResponseDTO;
import study.spring_boot_c.domain.member.exception.MemberException;
import study.spring_boot_c.global.error.code.status.ErrorStatus;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO.Example exampleMethod(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.NO_SUCH_MEMBER));

        return MemberConverter.toExample(member);
    }
}
