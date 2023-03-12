//package cn.ouya.gateway.utils;
//
//
//import cn.ouya.common.base.enums.ExceptionEnum;
//import cn.ouya.common.base.exception.InnerApiException;
//import cn.ouya.common.base.exception.LogicException;
//import cn.ouya.common.base.exception.SystemException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.WebProperties;
//import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.reactive.function.server.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 自定义异常处理
// *
// * <p>异常时用JSON代替HTML异常信息<p>
// *
// * @author ：yuzhiheng
// * @date ：2021-06-28 14:51
// */
//@Slf4j
//public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {
//
//    public JsonExceptionHandler(ErrorAttributes errorAttributes,
//                                WebProperties webProperties,
//                                ErrorProperties errorProperties,
//                                ApplicationContext applicationContext) {
//
//        super(errorAttributes, webProperties.getResources(), errorProperties, applicationContext);
//    }
//
//    /**
//     * 获取异常属性
//     */
//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
//
//        Throwable error = super.getError(request);
//
//        log.error("HttpMethod:[{}] Uri:[{}] Exception:[{}] Message:[{}]", request.method(), request.uri(), error, error.getMessage());
//
//        if (error instanceof NotFoundException) {
//            return response(ExceptionEnum.SYSTEM_UPDATING_ERROR.getCode(), ExceptionEnum.SYSTEM_UPDATING_ERROR.getMessage());
//        }
//
//        if (error instanceof LogicException) {
//            LogicException logicException = (LogicException) error;
//            return response(logicException.getCode(), logicException.getMessage());
//        }
//
//        if (error instanceof SystemException) {
//            SystemException systemException = (SystemException) error;
//            return response(systemException.getCode(), systemException.getMessage());
//        }
//
//        if (error instanceof InnerApiException) {
//            InnerApiException innerApiException = (InnerApiException) error;
//            return response(innerApiException.getCode(), innerApiException.getMessage());
//        }
//        if (error instanceof ResponseStatusException) {
//            ResponseStatusException responseStatusException = (ResponseStatusException) error;
//            return response(responseStatusException.getStatus().value(), responseStatusException.getStatus().getReasonPhrase());
//        }
//
//
//        return response(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode(), ExceptionEnum.INTERNAL_SERVER_ERROR.getMessage());
//    }
//
//    /**
//     * 构建返回的JSON数据格式
//     */
//    public static Map<String, Object> response(int code, String msg) {
//
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("code", code);
//        map.put("message", msg);
//        map.put("data", null);
//
//        return map;
//    }
//
//    /**
//     * 指定响应处理方法为JSON处理的方法
//     */
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
//    }
//
//    /**
//     * 根据code获取对应的HttpStatus
//     */
//    @Override
//    protected int getHttpStatus(Map<String, Object> errorAttributes) {
//
//        return 200;
//    }
//}