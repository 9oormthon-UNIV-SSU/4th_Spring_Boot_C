package study.spring_boot_c.domain.notification.exception;

import study.spring_boot_c.global.error.code.BaseErrorCode;
import study.spring_boot_c.global.error.exception.GeneralException;

public class NotificationException extends GeneralException {

    public NotificationException(BaseErrorCode code) {
        super(code);
    }
}
