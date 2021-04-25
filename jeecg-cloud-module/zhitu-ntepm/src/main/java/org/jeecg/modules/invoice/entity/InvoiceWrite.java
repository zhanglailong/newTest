package org.jeecg.modules.invoice.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Transient;

/**
 * @Description: 开票
 * @Author: jeecg-boot
 * @Date:   2021-04-22
 * @Version: V1.0
 */
@Data
@TableName("ntepm_invoice_write")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ntepm_invoice_write对象", description="开票")
public class InvoiceWrite implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**发票抬头id*/
	@Excel(name = "发票抬头id", width = 15)
    @ApiModelProperty(value = "发票抬头id")
    private String titleId;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private String orderId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private String status;
    /**订单类型*/
    @Excel(name = "订单类型", width = 30)
    @ApiModelProperty(value = "订单类型")
    private OrderType orderType;
    /**pdfUrl*/
    @Excel(name = "pdf文件地址", width = 30)
    @ApiModelProperty(value = "pdf文件地址")
    private String pdfUrl;

    /**发票代码*/
    @Excel(name = "发票代码", width = 30)
    @ApiModelProperty(value = "发票代码")
    private String invoiceCode;

    /**发票号码*/
    @Excel(name = "发票号码", width = 30)
    @ApiModelProperty(value = "发票号码")
    private String invoiceNo;

    /** 票种
     PLAIN：增值税电子普通发票
     SPECIAL：增值税专用发票
     PLAIN_INVOICE:增值税普通发票
     PAPER_INVOICE:增值税普通发票（卷式）
     SALSE_INVOICE:机动车销售统一发票*/
    @Excel(name = "票种", width = 30)
    @ApiModelProperty(value = "票种")
    private String taxType;

    /**发票类型 blue－蓝票 red－红票*/
    @Excel(name = "发票类型", width = 30)
    @ApiModelProperty(value = "发票类型")
    private String invoiceType;

    @Transient
    private InvoiceTitle invoiceTitle;

    public enum OrderType{
        //在线测试
        ONLINE,

        //测试申请
        TEST_ONLINE,

        //资源
        RESOURCE
    }
}
