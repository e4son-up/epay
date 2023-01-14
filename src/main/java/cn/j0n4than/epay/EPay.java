package cn.j0n4than.epay;

import cn.hutool.http.HttpUtil;
import cn.j0n4than.epay.base.BasePay;
import cn.j0n4than.epay.utils.CryptoUtils;

import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Life was like a box of chocolates
 * you never know what you're going to get.
 * <p>
 * 易支付操作类
 *
 * @author jonathan [admin@56fkj.cn]
 */
public class EPay extends BasePay {

    /**
     * 商户ID
     */
    private final String id;

    /**
     * 商户KEY
     */
    private final String key;

    /**
     * 网关地址
     */
    private final String url;

    /**
     * 支付类型，具体看文档
     */
    private String type;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 同步通知地址(跳转通知)
     */
    private String returnUrl;

    /**
     * 订单名称
     */
    private String name = "product";

    /**
     * 订单金额
     */
    private BigDecimal money;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 加密算法(一般情况不用改)
     */
    private String signType = "MD5";

    /**
     * Constructor
     *
     * @param id  商户id
     * @param key 商户key
     * @param url 网关地址
     */
    public EPay(String id, String key, String url) {
        this.id = id;
        this.key = key;
        this.url = url;
    }

    /**
     * 下单操作
     *
     * @return 返回url或html
     */
    public String order(boolean getHtml) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("pid", this.id);
        requestBody.put("type", this.type);
        requestBody.put("out_trade_no", this.outTradeNo);
        requestBody.put("notify_url", this.notifyUrl);
        requestBody.put("return_url", this.returnUrl);
        requestBody.put("name", this.name);
        requestBody.put("money", this.money);

        //排序
        requestBody = this.sort(requestBody);
        //获取签名
        requestBody.put("sign", this.getSign(requestBody));
        StringBuilder result = new StringBuilder();
        if (getHtml) {
            //构建html
            result.append("<form id='epay' name='epay' action='")
                    .append(this.url)
                    .append("submit.php")
                    .append("' method='post'>");

            for (Map.Entry<String, Object> m : requestBody.entrySet()) {
                result.append("<input hidden type='text' name='")
                        .append(m.getKey())
                        .append("' value='")
                        .append(m.getValue()).append("' />");
            }
            result.append("<input type='submit' value='正在跳转支付...'></form><script>document.forms['epay'].submit();</script>");
            return result.toString();
        }

        //构建url
        result.append(this.url).append("?");
        for (Map.Entry<String, Object> m : requestBody.entrySet()) {
            result.append(m.getKey())
                    .append("=")
                    .append(m.getValue())
                    .append("&");
        }

        return result.substring(0, result.length() - 1);
    }

    public String mApi() {
        Map<String, Object> params = new HashMap<>();
        params.put("pid", this.id);
        params.put("type", this.type);
        params.put("out_trade_no", this.outTradeNo);
        params.put("notify_url", this.notifyUrl);
        params.put("name", this.name);
        params.put("money", this.money.toString());
        params.put("clientip", this.clientIp);

        params = this.sort(params);

        params.put("sign", this.getSign(params));
        params.put("sign_type", "MD5");
        return HttpUtil.post(this.url + "/mapi.php", params);
    }

    /**
     * 获取签名
     *
     * @param data 排序后的数据
     *
     * @return 签名
     */
    public String getSign(Map<String, Object> data) {
        StringBuilder signStr = new StringBuilder();

        //拼接签名明文
        for (Map.Entry<String, Object> m : data.entrySet()) {
            //跳过不需要加入签名的参数
            if (m.getKey().equals("sign") || m.getKey().equals("sign_type") || m.getKey().isEmpty()) {
                continue;
            }
            signStr.append(m.getKey()).append("=").append(m.getValue()).append("&");
        }

        //System.out.println(signStr.substring(0, signStr.length() - 1) + this.key);

        //明文拼接key, md5加密后返回
        return CryptoUtils.md5(signStr.substring(0, signStr.length() - 1) + this.key);
    }

    /**
     * 签名验证
     *
     * @param data 请求的数据
     *
     * @return true 验证成功, false 验证失败
     */
    @Override
    public boolean signVerify(Map<String, String[]> data) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, String[]> m : data.entrySet()) {
            map.put(m.getKey(), m.getValue()[0]);
        }

        map = this.sort(map);

        //对比签名
        return this.getSign(map).equals(map.get("sign"));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
