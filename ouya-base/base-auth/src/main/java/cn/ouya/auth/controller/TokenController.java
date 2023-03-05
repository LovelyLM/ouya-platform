//package cn.ouya.auth.controller;
//
//import cn.ouya.auth.form.LoginBody;
//import cn.ouya.auth.form.RegisterBody;
//import cn.ouya.auth.form.SmsLoginBody;
//import cn.ouya.auth.service.SysLoginService;
//import cn.ouya.common.core.response.CommonResponse;
//import com.alibaba.nacos.api.common.Constants;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.constraints.NotBlank;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * token 控制
// *
// * @author Lion Li
// */
//@Validated
//@RequiredArgsConstructor
//@RestController
//public class TokenController {
//
//    private final SysLoginService sysLoginService;
//
//    /**
//     * 登录方法
//     */
//    @PostMapping("login")
//    public CommonResponse<Map<String, Object>> login(@Validated @RequestBody LoginBody form) {
//        // 用户登录
//        String accessToken = sysLoginService.login(form.getUsername(), form.getPassword());
//
//        // 接口返回信息
//        Map<String, Object> rspMap = new HashMap<String, Object>();
//        rspMap.put("access_token", accessToken);
//        return CommonResponse.success(rspMap);
//    }
//
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
//
//}
