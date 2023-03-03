package cn.ouya.common.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author leiming
 * @depiction 统一返回体
 * @date 2022/7/30 14:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<E> implements Serializable {

    public final static int COMMON_FAILED = 500;
    public final static int SUCCESS = 200;
    public final static int PERMISSION = 501;
    public final static int RE_LOGIN = 502;

    private int code;

    private String message;

    private E data;


    /**
     * 通用返回，参数自定义
     * @param code 状态码
     * @param message 消息提示标识
     * @param data 返回数据
     */
    public static <E> CommonResponse<E> response(int code, String message, E data) {
        return new CommonResponse<>(code, message, data);
    }

    /**
     * 默认状态码成功返回无数据
     */
    public static <E> CommonResponse<E> success() {
        return success("success", null);
    }

    /**
     * 默认状态码成功返回有数据
     */
    public static <E> CommonResponse<E> success(E data) {
        return new CommonResponse<>(SUCCESS,"success", data);
    }

    /**
     * 成功返回自定义消息和数据
     */
    public static <E> CommonResponse<E> success(String message, E data) {
        return new CommonResponse<>(SUCCESS, message, data);
    }

    /**
     * 默认失败返回 500 状态码 无数据
     */
    public static <E> CommonResponse<E> fail() {
        return fail("fail", null);
    }

    /**
     * 失败返回自定义数据
     */
    public static <E> CommonResponse<E> fail(E data) {
        return new CommonResponse<>(COMMON_FAILED, "fail", data);
    }

    /**
     * 失败返回自定义消息和数据
     */
    public static <E> CommonResponse<E> fail(String message, E data) {
        return new CommonResponse<>(COMMON_FAILED, message, data);
    }

    /**
     * 失败返回自定义数据
     */
    public static <E> CommonResponse<E> failMsg(String message) {
        return new CommonResponse<>(COMMON_FAILED, message, null);
    }

    /**
     * 失败返回自定义数据
     */
    public static <E> CommonResponse<E> failMsg(int code, String message) {
        return new CommonResponse<>(code, message, null);
    }

}
