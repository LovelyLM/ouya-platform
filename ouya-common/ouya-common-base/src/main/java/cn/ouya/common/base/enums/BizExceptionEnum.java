package cn.ouya.common.base.enums;


/**
 * @author yuzhiheng
 * @date 2021-06-24 17:44
 */
public enum BizExceptionEnum {

    NEED_LOGIN_ERROR(10001, "暂无权限，请登录后重试。"),

    NOT_FOUND_DATA_ERROR(10002, "数据不存在，请重试。"),

    LOGIC_ERROR(10003, "业务逻辑异常。"),

    DUPLICATE_SUBMIT_ERROR(10004, "请勿重复提交。"),

    DUPLICATE_DATA_ERROR(10005, "数据已存在，请勿重复提交。"),

    REJECT_DELETE_ERROR(10006, "数据不允许删除。"),

    REJECT_SAVE_ERROR(1007, "数据不允许新增。"),

    REJECT_UPDATE_ERROR(10008, "数据不允许更新。"),

    VALIDATE_CODE_ERROR(10009, "验证码错误。"),
    RE_LOGIN_ERROR(10010, "重复登录。");


    private final int code;
    private final String message;

    BizExceptionEnum(int code, String message) {
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
