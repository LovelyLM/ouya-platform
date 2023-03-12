package cn.ouya.common.base.enums;


/**
 * @author yuzhiheng
 * @date 2021-06-24 17:44
 */
public enum SystemExceptionEnum {

    NOT_BY_GATEWAY_ERROR(20001, "请从网关访问。"),
    NOT_FOUND_ERROR(20002, "接口不存在。"),
    INTERNAL_SERVER_ERROR(20003, "当前服务繁忙，请稍候再试。"),
    SYSTEM_UPDATING_ERROR(20004, "系统升级中，请稍后再试。"),
    SERVICE_NOT_FOUND_ERROR(20005, "该服务未找到。");


    private final int code;
    private final String message;

    SystemExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
