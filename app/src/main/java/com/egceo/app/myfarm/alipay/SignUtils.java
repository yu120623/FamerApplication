package com.egceo.app.myfarm.alipay;

import com.egceo.app.myfarm.http.API;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtils {

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    // 商户PID
    public static final String PARTNER = "2088021836494555";
    // 商户收款账号
    public static final String SELLER = "my@egceo.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKtUy6OQ420WGrY9" +
            "a7MHtBGkkdzuGJ6uscutCKBcGUWPQUGc/k+f9PxoY7GKeNOf3oBj/B/OdaFlOBFL" +
            "QLMsqS9JX+zfIENDDgi6LaMFy54IVVtfkryn8xYaAV4xnbjhymT3xAErzrv0IStN" +
            "0Enp2dvFS+zXKyd4Sy67njNY5lRfAgMBAAECgYEApW5K8rSD5GDjifPkegmGJULy" +
            "VJDR1OZ5ZmC9Y5mKDVA1glVEeT3KH63sFRHPeQd0oCPq3gtMKXrCmHBBGMCW2iwG" +
            "XOBZB/1XSCTghUKPqF1o6GGUs7K6xYaeI0fO5WbbapUF3ODJmQGraMEhACxu60HM" +
            "I7zOH3vGPtvXE/UogoECQQDjNgK0sdWFkyZPMu+fNDxMa4b5fLLG/PYl0L/Snnd6" +
            "/WXOUwDcAVBdo1hmhBMLwUyXInQg9Ot7ZS/rERX1H7MbAkEAwQo6A2BZUzMDkwLQ" +
            "xl6Bl5iB9RUcbPmFouBhX4GQinZzBgiFsIt8wo20bjduaivhxUW8QHa395FJsl0m" +
            "64Z0DQJAGfJFBNV6CLLxIQI0Ev4oFKIC2+K9l7OzeEYKYcbu5gCh65nW80jDIK5S" +
            "ngJfGmyMjoXEtS7C5Wh8vF9LHp7sGQJAS+llnuZIujsZcLput1N6ys6ibGp83y3Q" +
            "q5hpMlGVMZ4a5Yh8akywJyHqjl0mAoJ3KkieGwuUlLqBrK6NG5moEQJALrG3SrsL" +
            "nP8CmX4Ze9XtIE8DUfDipvn8jDnmQ4fmc8PXB6UGqHgNo5LcEc7NfT8qGq+2Vcu0" +
            "oBWyNtXAv4Gqog==";

    public static String getOrderInfo(String subject, String body, String price,String no) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + API.URL+"payAli"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    public static String getPayInfo(String orderInfo) {
        try {
            String sign = URLEncoder.encode(sign(orderInfo, RSA_PRIVATE), "UTF-8");
            String payInfo = orderInfo + "&sign=\"" + sign + "\"&"+ getSignType();
            return payInfo;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        };
        return "";
    }

    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM,"BC");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
