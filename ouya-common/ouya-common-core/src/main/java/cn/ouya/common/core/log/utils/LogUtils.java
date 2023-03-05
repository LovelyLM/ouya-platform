package cn.ouya.common.core.log.utils;



import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ouya.common.core.log.filter.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuzhiheng
 * @date 2021-06-29 14:07
 */
public class LogUtils {

    private static final String XML_PREFIX_SIGN_1 = "<xml>";
    private static final String XML_PREFIX_SIGN_2 = "<soapenv:Envelope";
    private static final String XML_PREFIX_SIGN_3 = "<?xml";

    public static JSON requestToRequestBodyJson(RequestWrapper request) {

        BufferedReader reader;
        StringBuilder wholeStr = new StringBuilder();
        try {
            reader = request.getReader();
            String str;

            // 一行一行的读取body体里面的内容；
            while ((str = reader.readLine()) != null) {
                wholeStr.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSON json;

        // 如果是数组
        if (wholeStr.toString().startsWith("[")) {
            json = JSONUtil.parseArray(wholeStr.toString());
        }
        // 如果是xml
        else if (wholeStr.toString().startsWith(XML_PREFIX_SIGN_1) || wholeStr.toString().startsWith(XML_PREFIX_SIGN_2) || wholeStr.toString().startsWith(XML_PREFIX_SIGN_3)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("xml", wholeStr.toString());
            json = jsonObject;
        } else {
            if (StrUtil.isBlank(wholeStr.toString())) {
                json = JSONUtil.createObj();
            } else {
                json = JSONUtil.parseObj(wholeStr.toString());
            }
        }

        return json;
    }

    public static JSONObject requestToJson(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
        Map<?, ?> parameterMap = request.getParameterMap();

        for (Object o : parameterMap.keySet()) {
            String key = o.toString();
            String[] values = (String[]) parameterMap.get(key);
            if (values.length == 1) {
                jsonObject.put(key, values[0]);
            } else {
                jsonObject.put(key, values);
            }

        }
        return jsonObject;
    }

}
