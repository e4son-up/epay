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

    /**
     * MD5加密
     *
     * @param str 需要加密的字符串
     *
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        // 生成一个MD5加密计算摘要
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            byte[] b = md.digest();
            // 16是表示转换为16进制数
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
