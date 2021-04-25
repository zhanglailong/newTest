package org.jeecg.modules.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: Mr.Zhang
 * @Date:
 */

@Data
@Configuration
public class AlipayConfig {

    //支付宝下单统一请求路径
    @Value("${alipay.domain}")
    private String domain;

    //APPID
    @Value("${alipay.appid}")
    private String appId;

    //商户私钥
    @Value("${alipay.mk}")
    private String mk;

    //支付宝公钥
    @Value("${alipay.pk}")
    private String pk;

    //数据格式
    @Value("${alipay.format}")
    private String format;

    //编码
    @Value("${alipay.charset}")
    private String charset;

    //加密方式
    @Value("${alipay.signType}")
    private String signType;

    //回调统一地址
    @Value("${alipay.notify}")
    private String notify;

    @Value("${alipay.authUrl}")
    private String authUrl;


    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient =
                new DefaultAlipayClient(domain, appId, mk, format, charset, pk, signType);
        return alipayClient;
    }
}

