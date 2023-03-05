package cn.ouya.common.core.log.filter;



import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.ouya.common.core.log.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yuzhiheng
 * @date 2021-06-29 13:42
 */
@Component
@Slf4j
public class ResourceLogFilter implements Filter {

    @Value("${spring.application.name}")
    private String projectName;

    private static String PROJECT_NAME;
    private static String SERVER_REQUEST;
    private static String SERVER_RESPONSE;
    private static final String NOTIFICATION_PREFIX = "* ";
    private static final AtomicLong LOG_SEQUENCE = new AtomicLong(0);
    private static final String FILTERED_URI = "/actuator";

    @PostConstruct
    public void init() {

        PROJECT_NAME = this.projectName;
        SERVER_REQUEST = PROJECT_NAME + " > ";
        SERVER_RESPONSE = PROJECT_NAME + " < ";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Long start = System.currentTimeMillis();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (StrUtil.containsAnyIgnoreCase(httpServletRequest.getRequestURI(), FILTERED_URI)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        long id = LOG_SEQUENCE.incrementAndGet();

        StringBuilder b = new StringBuilder();
        printRequestLine(SERVER_REQUEST, b, id, httpServletRequest.getMethod(), httpServletRequest.getRequestURI());

        printRequestHeaders(SERVER_REQUEST, b, id, httpServletRequest);

        printRequestParams(SERVER_REQUEST, b, id, httpServletRequest);

        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        printRequestBody(SERVER_REQUEST, b, id, requestWrapper);

        log.info(b.toString());

        chain.doFilter(requestWrapper, response);

        StringBuilder b2 = new StringBuilder();
        printResponseLine(SERVER_RESPONSE, b2, id, httpServletResponse.getStatus());
        printResponseHeaders(SERVER_RESPONSE, b2, id, httpServletResponse);

        Long end = System.currentTimeMillis();

        printResponseTime(SERVER_RESPONSE, b2, id, end - start);

        log.info(b2.toString());
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private void printRequestLine(final String prefix, StringBuilder b, long id, String method, String uri) {

        prefixId(b, id)
                .append(NOTIFICATION_PREFIX)
                .append("ResourceLogFilter - Request received on thread ")
                .append(Thread.currentThread().getName()).append("\n");

        prefixId(b, id)
                .append(prefix)
                .append(method)
                .append(" ")
                .append(uri)
                .append("\n");
    }

    private void printRequestHeaders(final String prefix, StringBuilder b, long id, HttpServletRequest request) {

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            String value = request.getHeader(headerName);

            prefixId(b, id).append(prefix).append(headerName).append(": ").append(value).append("\n");
        }
    }

    private void printRequestBody(final String prefix, StringBuilder b,
                                  long id, RequestWrapper request) {
        prefixId(b, id)
                .append(prefix)
                .append("requestBody：")
                .append(JSONUtil.formatJsonStr(LogUtils.requestToRequestBodyJson(request).toString()));
    }

    private void printRequestParams(final String prefix, StringBuilder b, long id, HttpServletRequest request) {
        prefixId(b, id)
                .append(prefix)
                .append("parameters: ")
                .append(JSONUtil.formatJsonStr(LogUtils.requestToJson(request).toString()))
                .append("\n");
    }

    private void printResponseLine(final String prefix, StringBuilder b, long id, int status) {
        prefixId(b, id)
                .append(NOTIFICATION_PREFIX)
                .append("ResourceLogFilter - Response received on thread ")
                .append(Thread.currentThread().getName()).append("\n");

        prefixId(b, id).
                append(prefix)
                .append(status)
                .append("\n");
    }

    private void printResponseHeaders(final String prefix, StringBuilder b, long id, HttpServletResponse response) {

        Collection<String> headerNames = response.getHeaderNames();

        for (String headerName : headerNames) {
            String value = response.getHeader(headerName);

            prefixId(b, id)
                    .append(prefix)
                    .append(headerName)
                    .append(": ")
                    .append(value)
                    .append("\n");
        }
    }

    private void printResponseTime(final String prefix, StringBuilder b, long id, long time) {
        prefixId(b, id)
                .append(prefix)
                .append("cost: ")
                .append(time)
                .append(" ms");
    }

    private StringBuilder prefixId(StringBuilder b, long id) {
        b.append(id).append(" ");
        return b;
    }

}