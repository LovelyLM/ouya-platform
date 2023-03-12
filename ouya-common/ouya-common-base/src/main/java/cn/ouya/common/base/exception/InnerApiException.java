package cn.ouya.common.base.exception;

import cn.ouya.common.base.enums.BizExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内部接口异常
 *
 * @author yuzhiheng
 * @date 2021-06-24 17:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerApiException extends RuntimeException {

    protected int code;
    protected String message;

    public InnerApiException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getMessage());
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }

    public InnerApiException(Throwable cause, String message, int code) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public InnerApiException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
