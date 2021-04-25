package org.jeecg.modules.invoice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.invoice.entity.InvoiceTitle;

import java.util.List;

/**
 * @Description: 发票抬头
 * @Author: jeecg-boot
 * @Date:   2021-04-22
 * @Version: V1.0
 */
public interface IInvoiceTitleService extends IService<InvoiceTitle> {

    List<InvoiceTitle> queryByUserId(String userId);
}
