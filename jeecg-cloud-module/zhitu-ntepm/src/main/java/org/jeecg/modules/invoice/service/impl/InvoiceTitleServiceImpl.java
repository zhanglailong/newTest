package org.jeecg.modules.invoice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.invoice.entity.InvoiceTitle;
import org.jeecg.modules.invoice.mapper.InvoiceTitleMapper;
import org.jeecg.modules.invoice.service.IInvoiceTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 发票抬头
 * @Author: jeecg-boot
 * @Date:   2021-04-22
 * @Version: V1.0
 */
@Service
public class InvoiceTitleServiceImpl extends ServiceImpl<InvoiceTitleMapper, InvoiceTitle> implements IInvoiceTitleService {

    @Autowired
    private InvoiceTitleMapper invoiceTitleMapper;

    @Override
    public List<InvoiceTitle> queryByUserId(String userId) {
        QueryWrapper<InvoiceTitle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("is_del",0);
        List<InvoiceTitle> invoiceTitles = invoiceTitleMapper.selectList(queryWrapper);
        return invoiceTitles;
    }
}
