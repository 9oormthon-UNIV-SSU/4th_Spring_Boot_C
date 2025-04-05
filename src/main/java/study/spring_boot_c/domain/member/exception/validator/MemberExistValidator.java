package study.spring_boot_c.domain.member.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.spring_boot_c.domain.member.domain.repository.MemberRepository;
import study.spring_boot_c.domain.member.exception.annotation.MemberExist;
import study.spring_boot_c.global.error.code.status.ErrorStatus;

@Component
@RequiredArgsConstructor
public class MemberExistValidator implements ConstraintValidator<MemberExist, Long> {

    private final MemberRepository memberRepository;

    @Override
    public void initialize(MemberExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long memberId, ConstraintValidatorContext context) {
        boolean isValid = memberRepository.existsById(memberId);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.NO_SUCH_MEMBER.toString()).addConstraintViolation();
        }

        return isValid;

    }
}

