package cn.ouya.common.base.enums;

import lombok.Getter;


/**
 * 登录用户的jwt中role枚举值。
 *
 * @author YongNing
 * @date 2021-08-11 16:56
 */
@Getter
public enum LoginRoleEnum {

    /**
     * 用户正常登录
     */
    MALL_USER("user", "用户正常登陆"),
    /**
     * 服务人员登录代下单
     */
    MALL_AGENT("emp", "服务人员登陆代下单"),
    /**
     * 游客访问
     */
    MALL_VISITOR("visitor", "游客访问"),
    /**
     * 注册人，门店审核中
     */
    MALL_REGISTRANT("registrant", "注册人，门店审核中");

    private final String code;
    private final String desc;

    LoginRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
