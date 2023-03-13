package cn.ouya.common.base.factory;

import cn.ouya.common.base.enums.BizExceptionEnum;

/**
 * @author LovelyLM
 * @date 2022-06-02 11:06
 */
public class Assert extends org.springframework.util.Assert {


    public static void isAssert(String message) {
        throw ExceptionFactory.logicException(message);

    }

    public static void isAssert() {
        throw ExceptionFactory.logicException();

    }


    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw ExceptionFactory.logicException();

        }
    }

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw ExceptionFactory.logicException();
        }
    }


    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw ExceptionFactory.logicException(message);
        }
    }

    public static void isFalse(boolean expression) {
        if (expression) {
            throw ExceptionFactory.logicException();
        }
    }

    public static void isFalse(boolean expression, BizExceptionEnum bizExceptionEnum) {
        if (expression) {
            throw ExceptionFactory.logicException(bizExceptionEnum);
        }
    }
}
