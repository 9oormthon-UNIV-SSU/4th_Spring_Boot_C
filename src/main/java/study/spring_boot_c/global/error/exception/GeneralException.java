package study.spring_boot_c.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.spring_boot_c.global.error.code.BaseErrorCode;
import study.spring_boot_c.global.error.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}

