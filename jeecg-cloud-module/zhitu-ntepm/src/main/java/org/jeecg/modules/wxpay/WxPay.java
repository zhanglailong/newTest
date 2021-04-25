package org.jeecg.modules.wxpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.StringUtils;
import org.jeecg.modules.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @created by zyf 2021/4/25
 */
@Component
public class WxPay {

    @Autowired
    private WxPayConfig wxPayConfig;

    public String getNativeUrl(String orderNo, String price, String describe, String notifyUrl) {
        try {
            // 生成微信「统一下单」请求数据 afdsf
            Map<String, String> dataMap = new HashMap<>(16);
            //公证号id  微信支付分配的公众号id
            dataMap.put("appid", wxPayConfig.getAppId());
            //商户号   微信支付分配的商户号
            dataMap.put("mch_id", wxPayConfig.getMchId());
            //随机字符串   调用方法
            dataMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品描述
            dataMap.put("body", describe);
            //商品订单号
            dataMap.put("out_trade_no", orderNo);
            //标价总额
            dataMap.put("total_fee", price);
            //终端ip
            dataMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
            //通知地址
            dataMap.put("notify_url", wxPayConfig.getNotify() + notifyUrl);
            //交易类型
            dataMap.put("trade_type", "NATIVE ");
            //签名
            dataMap.put("sign", WXPayUtil.generateSignature(dataMap,wxPayConfig.getKey()));

            // 签名,请求「统一下单」接口，并解析返回结果
            String payXml = WXPayUtil.mapToXml(dataMap);
            String result = HttpClientUtil.requestWithoutCert(wxPayConfig.getUnifiedorder(), payXml, 5000,10000);
            if (!StringUtils.isEmpty(result)){
                Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
                if (checkResultMap(resultMap)){
                    return resultMap.get("code_url");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getResultMap(HttpServletRequest request) throws Exception {
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        Map<String, String> resultMap = WXPayUtil.xmlToMap(stringBuffer.toString());
        return resultMap;
    }

    private boolean checkResultMap(Map<String,String> map){
        return  ("SUCCESS".equals(map.get("return_code"))&& "SUCCESS".equals(map.get("result_code")));
    }

    public boolean checkSign(final Map<String,String> map,String key) throws Exception {
        String resultSign = map.get("sign");
        String newSign = WXPayUtil.generateSignature(map,key);
        return resultSign.equals(newSign);
    }


}
