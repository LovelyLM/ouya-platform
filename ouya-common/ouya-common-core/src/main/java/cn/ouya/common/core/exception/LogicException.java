package cn.ouya.common.core.exception;

/**
 * @author LovelyLM
 * @date 2022-06-02 11:07
 */
public class LogicException extends RuntimeException{
    private final int code;

    public int getCode() {
        return code;
    }

    public LogicException(int code) {
        this.code = code;
    }

    public LogicException(int code, String s) {
        super(s);
        this.code = code;
    }

    public LogicException(String s, Throwable throwable, int code) {
        super(s, throwable);
        this.code = code;
    }

    public LogicException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public LogicException(String s, Throwable throwable, boolean b, boolean b1, int code) {
        super(s, throwable, b, b1);
        this.code = code;
    }
}
