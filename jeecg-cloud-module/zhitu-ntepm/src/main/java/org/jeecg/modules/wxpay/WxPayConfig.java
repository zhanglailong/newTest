package org.jeecg.modules.wxpay;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @created by zyf 2021/4/25
 */
@Data
@Configuration
public class WxPayConfig {

    @Value("${wxpay.unifiedorder}")
    private String unifiedorder;

    @Value("${wxpay.appid}")
    private String appId;

    @Value("${wxpay.mchid}")
    private String mchId;

    @Value("${wxpay.notify}")
    private String notify;

    @Value("${wxpay.key}")
    private String key;
}
