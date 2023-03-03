package cn.ouya.common.core.handler;


import cn.dev33.satoken.exception.*;
import cn.ouya.common.core.exception.LogicException;
import cn.ouya.common.core.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * @author LovelyLM
 * @depiction 全局异常拦截处理
 * @date 2021/8/4 004 21:21
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = LogicException.class)
    public CommonResponse<String> bizExceptionHandler(IllegalArgumentException e) {
        log.error("自定义错误信息：", e);
        return CommonResponse.failMsg(e.getMessage());
    }

    /**
     * 处理权限验证的异常
     */
    @ExceptionHandler(value = SaTokenException.class)
    public CommonResponse<String> saTokenExceptionHandler(SaTokenException saTokenException) {
        log.error("权限错误信息：", saTokenException);
        String msg = "服务器繁忙，请联系管理员~";
        int code = CommonResponse.COMMON_FAILED;
        if (saTokenException instanceof NotLoginException) {
            String type = ((NotLoginException) saTokenException).getType();
            switch (type) {
                case NotLoginException.KICK_OUT:
                    msg = "已被踢下线";
                    break;
                case NotLoginException.BE_REPLACED:
                    msg = "已被顶下线";
                    break;
                case NotLoginException.TOKEN_TIMEOUT:
                    msg = "登录已过期，请重新登录";
                    break;
                case NotLoginException.INVALID_TOKEN:
                    msg = "登录已失效，请重新登录";
                    break;
                default:
                    msg = "未登录，请登录";
            }
            code = CommonResponse.RE_LOGIN;
        } else if (saTokenException instanceof NotRoleException) {
            msg = "无此角色~~";
        } else if (saTokenException instanceof NotPermissionException) {
            msg = "权限不足~~";
        } else if (saTokenException instanceof DisableServiceException) {
            msg = "账号被封禁！";
        }
        return CommonResponse.failMsg(code, msg);
    }

    /**
     * 处理参数异常
     */
    @ExceptionHandler(value = BindException.class)
    public CommonResponse<String> param1ExceptionHandler(BindException e) {
        log.error("参数错误信息：", e);
        return CommonResponse.failMsg("参数错误！");
    }

    /**
     * 处理参数异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public CommonResponse<String> httpMethodExceptionHandler(ValidationException e) {
        log.error("参数错误信息：", e);
        return CommonResponse.failMsg("参数错误");
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public CommonResponse<String> httpMethodExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("参数错误信息：", e);
        return CommonResponse.failMsg("不支持" + e.getMethod() + "请求！");
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> exceptionHandler(Exception e) {
        log.error("错误信息：", e);
        return CommonResponse.failMsg("服务器异常！");
    }
}
