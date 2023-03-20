package cn.ouya.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.ouya.auth.form.LoginBody;
import cn.ouya.common.base.response.CommonResponse;
import cn.ouya.common.satoken.config.StpUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * token 控制
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class TokenController {

//    private final SysLoginService sysLoginService;

    /**
     * C端登录方法
     */
    @PostMapping("/user/login")
    public CommonResponse<String> userLogin(@Validated @RequestBody LoginBody form) {
        StpUserUtil.login(1);
        return CommonResponse.success(StpUserUtil.getTokenValue());
    }

    /**
     * C端登录方法
     */
    @PostMapping("/admin/login")
    public CommonResponse<String> adminLogin(@Validated @RequestBody LoginBody form) {
        StpUtil.login(1);
        return CommonResponse.success(StpUtil.getTokenValue());
    }



//    /**
//     * 短信登录
//     *
//     * @param smsLoginBody 登录信息
//     * @return 结果
//     */
//    @PostMapping("/smsLogin")
//    public CommonResponse<Map<String, Object>> smsLogin(@Validated @RequestBody SmsLoginBody smsLoginBody) {
//        Map<String, Object> ajax = new HashMap<>();
//        // 生成令牌
//        String token = sysLoginService.smsLogin(smsLoginBody.getPhonenumber(), smsLoginBody.getSmsCode());
//        ajax.put(Constants.TOKEN, token);
//        return CommonResponse.success(ajax);
//    }
//
//    /**
//     * 小程序登录(示例)
//     *
//     * @param xcxCode 小程序code
//     * @return 结果
//     */
//    @PostMapping("/xcxLogin")
//    public CommonResponse<Map<String, Object>> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
//        Map<String, Object> ajax = new HashMap<>();
//        // 生成令牌
//        String token = sysLoginService.xcxLogin(xcxCode);
//        ajax.put(Constants.TOKEN, token);
//        return CommonResponse.success(ajax);
//    }
//
//    /**
//     * 登出方法
//     */
//    @DeleteMapping("logout")
//    public CommonResponse<Void> logout() {
//        sysLoginService.logout();
//        return CommonResponse.success();
//    }
//
//    /**
//     * 用户注册
//     */
//    @PostMapping("register")
//    public CommonResponse<Void> register(@RequestBody RegisterBody registerBody) {
//        // 用户注册
//        sysLoginService.register(registerBody);
//        return CommonResponse.success();
//    }

}
