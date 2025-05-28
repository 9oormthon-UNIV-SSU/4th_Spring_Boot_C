package study.spring_boot_c.domain.chat.exception;

import study.spring_boot_c.global.error.code.BaseErrorCode;
import study.spring_boot_c.global.error.exception.GeneralException;

public class ChatException extends GeneralException {

    public ChatException(BaseErrorCode code) {
        super(code);
    }
}
