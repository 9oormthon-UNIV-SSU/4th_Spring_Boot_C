package study.spring_boot_c.domain.member.exception;

import study.spring_boot_c.global.error.code.BaseErrorCode;
import study.spring_boot_c.global.error.exception.GeneralException;

public class MemberException extends GeneralException {

    public MemberException(BaseErrorCode code) {
        super(code);
    }
}

