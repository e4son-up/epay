package cn.j0n4than.epay.utils;

public class CryptoUtils {

    /**
     * MD5加密
     *
     * @param str 需要加密的字符串
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
