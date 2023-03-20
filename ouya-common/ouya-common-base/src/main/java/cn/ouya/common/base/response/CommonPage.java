package cn.ouya.common.base.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author leiming
 * @depiction 分页通用返回类
 * @date 2021/7/30 21:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommonPage<T> {
    /**
     * 总数据量
     */
    private Integer total;
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 每页数量
     */
    private Integer limit;
    /**
     * 数据
     */
    private List<T> records;

    private static final CommonPage<?> EMPTY_INSTANCE = new CommonPage<>();


    @SuppressWarnings(value = "unchecked")
    public static <T> CommonPage<T> empty() {

        return (CommonPage<T>) EMPTY_INSTANCE;
    }


}
