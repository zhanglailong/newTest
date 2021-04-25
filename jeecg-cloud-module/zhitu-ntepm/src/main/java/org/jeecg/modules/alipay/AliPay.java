package org.jeecg.modules.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @created by zyf 2021/4/23
 */
@Component
public class AliPay {

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private AlipayClient alipayClient;

    public  String getTradePrecreateUrl(String orderNo,String price,String describe,String notifyUrl){
        try {
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("total_amount", price);
            map.put("subject", describe);
            map.put("timeout_express", "30m");
            request.setBizContent(JSON.toJSONString(map));
            request.setNotifyUrl(alipayConfig.getNotify() + notifyUrl);
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) {
                return response.getQrCode();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,String> solveRequest(HttpServletRequest request){
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    public boolean checkSign(Map<String, String> params){
        try {
            return AlipaySignature.rsaCheckV1(params, alipayConfig.getPk(), alipayConfig.getCharset(), alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }
}
