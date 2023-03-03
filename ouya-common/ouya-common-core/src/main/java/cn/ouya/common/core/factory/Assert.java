package cn.ouya.common.core.factory;

import cn.ouya.common.core.exception.LogicException;

/**
 * @author LovelyLM
 * @date 2022-06-02 11:06
 */
public class Assert extends org.springframework.util.Assert {

    public static void isAssert(int code, String message) {
        throw new LogicException(code, message);
    }

    public static void isTrue(int code, boolean expression, String message) {
        if (!expression) {
            throw new LogicException(code, message);
        }
    }

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new LogicException(500, "系统业务异常");
        }
    }


    public static void isFalse(int code, boolean expression, String message) {
        if (expression) {
            throw new LogicException(code, message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new LogicException(500, message);
        }
    }

    public static void isFalse(boolean expression) {
        if (expression) {
            throw new LogicException(500, "系统业务异常");
        }
    }
}
