import cn.j0n4than.epay.EPay;

import java.math.BigDecimal;
import java.util.HashMap;

public class Demo {
    /**
     * 下单接口
     *
     * @return 返回HTML代码
     */
    public String order() {
        EPay ePay = new EPay("id", "key", "https://test.test/submit.php");
        ePay.setOutTradeNo("tradeNO");
        ePay.setType("alipay");
        ePay.setMoney(new BigDecimal("8.88"));
        ePay.setNotifyUrl("https://test.test/api/pay/notify");
        ePay.setReturnUrl("https://test.test/api/pay/return");
        return ePay.order(true);
    }

    /**
     * 验证回调请求
     *
     * @return String
     */
    public String notifyHandle(HashMap<String, String[]> request) {
        EPay ePay = new EPay("id", "key", "https://test.test/submit.php");
        if (ePay.signVerify(request)) {  //回调请求[这里的参数可以直接用HttpServletRequest.getParameterMap()]
            //验证签名成功后的业务逻辑

            return "success";
        }

        return "failed";  //验证签名失败
    }

    public static void main(String[] args) {
        String order = new Demo().order();
        System.out.println("order = " + order);
    }
}
