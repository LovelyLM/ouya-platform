package cn.ouya.common.core.handler;


import cn.dev33.satoken.exception.*;

import cn.ouya.common.base.enums.SystemExceptionEnum;
import cn.ouya.common.base.exception.LogicException;
import cn.ouya.common.base.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

import static cn.ouya.common.base.enums.BizExceptionEnum.RE_LOGIN_ERROR;

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
    public CommonResponse<String> bizExceptionHandler(LogicException e) {
        log.error("自定义错误信息：", e);
        return CommonResponse.failMsg(e.getCode(), e.getMessage());
    }

    /**
     * 处理权限验证的异常
     */
    @ExceptionHandler(value = SaTokenException.class)
    public CommonResponse<String> saTokenExceptionHandler(SaTokenException saTokenException) {
        log.error("权限错误信息：", saTokenException);
        String msg = "服务器繁忙，请联系管理员~";
        int code = CommonResponse.COMMON_FAILED_CODE;
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
            code = RE_LOGIN_ERROR.getCode();
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
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getField() + " : " + e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("错误信息：{}", message);
        return CommonResponse.failMsg("参数错误！，" + message);
    }

    /**
     * 处理参数异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResponse<String> httpMethodExceptionHandler(ConstraintViolationException e) {
        String message = e.getMessage();

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> item : violations) {

            String[] propertyPath = item.getPropertyPath().toString().split("\\.");
            String methodName = propertyPath[0];
            String parameterName = propertyPath[1].replace("arg", "").replace(methodName, "");
            message = parameterName + " : " + item.getMessage();
        }
        log.error("错误信息：{}", message);
        return CommonResponse.failMsg("参数错误！，" + message);

    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public <T> CommonResponse<T> httpMethodExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("参数错误信息：", e);
        return CommonResponse.failMsg("不支持" + e.getMethod().toUpperCase() + "请求！");
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> CommonResponse<T> exceptionHandler(Exception e) {
        log.error("错误信息：", e);
        return CommonResponse.fail(SystemExceptionEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 接口不存在
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T> CommonResponse<T> handle(NoHandlerFoundException exception) {
        log.error(exception.getMessage());
        return CommonResponse.fail(SystemExceptionEnum.NOT_FOUND_ERROR);
    }

}
