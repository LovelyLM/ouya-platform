package cn.ouya.common.base.factory;


import cn.ouya.common.base.enums.BizExceptionEnum;
import cn.ouya.common.base.exception.InnerApiException;
import cn.ouya.common.base.exception.LogicException;
import cn.ouya.common.base.exception.SystemException;
import cn.ouya.common.base.response.CommonResponse;

/**
 * 异常工厂
 *
 * @author yuzhiheng
 * @date 2021-06-24 17:35
 */
public class ExceptionFactory {

    public static LogicException logicException() {
        return new LogicException(BizExceptionEnum.LOGIC_ERROR);
    }

    public static LogicException logicException(String message) {
        return new LogicException(message, BizExceptionEnum.LOGIC_ERROR.getCode());
    }

    public static LogicException logicException(BizExceptionEnum bizExceptionEnum) {
        return new LogicException(bizExceptionEnum.getMessage(), bizExceptionEnum.getCode());
    }

    public static LogicException logicException(BizExceptionEnum bizExceptionEnum, String message) {
        return new LogicException(message, bizExceptionEnum.getCode());
    }

    public static LogicException logicException(BizExceptionEnum bizExceptionEnum, Exception e) {
        return new LogicException(e, bizExceptionEnum.getMessage(), bizExceptionEnum.getCode());
    }

    public static SystemException systemException(BizExceptionEnum bizExceptionEnum) {
        return new SystemException(bizExceptionEnum.getMessage(), bizExceptionEnum.getCode());
    }

    public static SystemException systemException(BizExceptionEnum bizExceptionEnum, String message) {
        return new SystemException(message, bizExceptionEnum.getCode());
    }

    public static SystemException systemException(BizExceptionEnum bizExceptionEnum, Exception message) {
        return new SystemException(message, bizExceptionEnum.getMessage(), bizExceptionEnum.getCode());
    }

    public static <T> InnerApiException innerApiException(CommonResponse<T> resp) {
        return new InnerApiException(resp.getMessage(), resp.getCode());
    }

    public static <T> InnerApiException innerApiException(BizExceptionEnum bizExceptionEnum) {
        return new InnerApiException(bizExceptionEnum.getMessage(), bizExceptionEnum.getCode());
    }

    public static <T> InnerApiException innerApiException(BizExceptionEnum bizExceptionEnum, String message) {
        return new InnerApiException(message, bizExceptionEnum.getCode());
    }

}

