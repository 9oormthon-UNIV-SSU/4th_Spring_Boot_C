package study.spring_boot_c.domain.product.exception;

import study.spring_boot_c.global.error.code.BaseErrorCode;
import study.spring_boot_c.global.error.exception.GeneralException;

public class ProductException extends GeneralException {

    public ProductException(BaseErrorCode code) {
        super(code);
    }
}
