package cn.j0n4than.epay.base;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Life was like a box of chocolates
 * you never know what you're going to get.
 * <p>
 * 支付基础类
 *
 * @author jonathan [admin@56fkj.cn]
 */
public abstract class BasePay {

    /**
     * 异步通知验证
     *
     * @param data 请求的数据
     *
     * @return true 验证成功, false 验证失败
     */
    public abstract boolean signVerify(Map<String, String[]> data);

    /**
     * 升序排序
     *
     * @param map 需要排序的Map
     * @param <K> key
     * @param <V> value
     *
     * @return 排序后的Map
     */
    protected <K extends Comparable<? super K>, V> Map<K, V> sort(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
