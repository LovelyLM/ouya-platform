package cn.ouya.product.api;


import cn.ouya.common.core.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author LeiMing
 * @date 2022-01-06 17:35
 */
@FeignClient(value = "ouya-product", path = "leiming/get")
public interface RemoteProductService {



    /**
     * 根据id获取商品信息
     *
     */
    @GetMapping
    CommonResponse<String> getProductById(@RequestParam String key);

}
