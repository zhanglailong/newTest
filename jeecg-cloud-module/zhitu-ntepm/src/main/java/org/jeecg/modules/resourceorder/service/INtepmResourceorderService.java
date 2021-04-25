package org.jeecg.modules.resourceorder.service;

import org.jeecg.modules.resourceorder.entity.NtepmResourceorder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 资源订单
 * @Author: jeecg-boot
 * @Date:   2021-04-23
 * @Version: V1.0
 */
public interface INtepmResourceorderService extends IService<NtepmResourceorder> {
    /**
     * @Description: 修改订单状态
     * @Param:
     * @return:
     * @Author: Mr.Zhang
     * @Date:
     */
    public NtepmResourceorder updateorder_status(String id, int orderstatus);
    /**
     * @Description: 修改资源订单支付状态
     * @Param:
     * @return:
     * @Author: Mr.Zhang
     * @Date:
     */
    public NtepmResourceorder updatepay_status(String id, int paystatus);
    /**
     * @Description: 根据resourceid查询订单详情
     * @Param:
     * @return:
     * @Author: Mr.Zhang
     * @Date:
     */
    public NtepmResourceorder selectorder(@Param("resourceid") String resourceid);
}
