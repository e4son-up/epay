# epay

更简单方便的接入易支付

# 使用:
```java
import cn.j0n4than.epay.EPay;

import java.util.HashMap;

class Demo {
    /**
     * 下单接口
     *
     * @return 返回HTML代码
     */
    public String order() {
        EPay ePay = new EPay("id", "key", "https://test.test/submit.php");
        ePay.setOutTradeNo("tradeNO");
        ePay.setType("alipay");
        ePay.setMoney("8.88");
        ePay.setNotifyUrl("https://test.test/api/pay/notify");
        ePay.setReturnUrl("https://test.test/api/pay/return");
        return ePay.order(true);
    }

    /**
     * 验证回调请求
     *
     * @return String
     */
    public String notify() {
        EPay ePay = new EPay("id", "key", "https://test.test/submit.php");
        if (ePay.signVerify(new HashMap<String, String[]>())) {  //回调请求[HttpServletRequest.getParameterMap()]
            //验证签名成功后的业务逻辑

            return "success";
        }

        return "failed";  //验证签名失败
    }
}
```
