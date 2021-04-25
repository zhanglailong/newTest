package org.jeecg.modules.resourceorder.service.impl;

import org.jeecg.modules.resource.entity.NtepmResource;
import org.jeecg.modules.resource.mapper.NtepmResourceMapper;
import org.jeecg.modules.resourceorder.entity.NtepmResourceorder;
import org.jeecg.modules.resourceorder.mapper.NtepmResourceorderMapper;
import org.jeecg.modules.resourceorder.service.INtepmResourceorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 资源订单
 * @Author: jeecg-boot
 * @Date:   2021-04-23
 * @Version: V1.0
 */
@Service
public class NtepmResourceorderServiceImpl extends ServiceImpl<NtepmResourceorderMapper, NtepmResourceorder> implements INtepmResourceorderService {
    @Resource
    NtepmResourceorderMapper ntepmResourceMapper;

    /**
     * @Description: 修改资源订单状态
     * @Param:
     * @return:
     * @Author: Mr.Zhang
     * @Date:
     */
    public NtepmResourceorder updateorder_status(String id, int orderstatus) {
        return ntepmResourceMapper.updateorder_status(id,orderstatus);
    }
    /**
     * @Description: 修改资源订单支付状态
     * @Param:
     * @return:
     * @Author: Mr.Zhang
     * @Date:
     */
    public NtepmResourceorder updatepay_status(String id, int orderstatus) {
        return ntepmResourceMapper.updatepay_status(id,orderstatus);
    }
    /**
     * @Description: 根据resourceid查询订单详情
     * @Param:
     * @return:
     * @Author: Mr.Zhang
     * @Date:
     */
    public NtepmResourceorder selectorder(@Param("resourceid") String resourceid){
        return ntepmResourceMapper.selectorder(resourceid);
    }
}
