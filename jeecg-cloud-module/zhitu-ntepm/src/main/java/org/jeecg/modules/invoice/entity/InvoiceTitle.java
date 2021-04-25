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

/**
 * @Description: 发票抬头
 * @Author: jeecg-boot
 * @Date:   2021-04-22
 * @Version: V1.0
 */
@Data
@TableName("ntepm_invoice_title")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ntepm_invoice_title对象", description="发票抬头")
public class InvoiceTitle implements Serializable {
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
	/**公司名称*/
	@Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private String name;
	/**公司税号*/
	@Excel(name = "公司税号", width = 15)
    @ApiModelProperty(value = "公司税号")
    private String taxNo;
	/**注册地址*/
	@Excel(name = "注册地址", width = 15)
    @ApiModelProperty(value = "注册地址")
    private String registerAddress;
	/**注册电话*/
	@Excel(name = "注册电话", width = 15)
    @ApiModelProperty(value = "注册电话")
    private String registerPhone;
	/**开户银行*/
	@Excel(name = "开户银行", width = 15)
    @ApiModelProperty(value = "开户银行")
    private String openBankName;
	/**银行账号*/
	@Excel(name = "银行账号", width = 15)
    @ApiModelProperty(value = "银行账号")
    private String openBankAccount;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String email;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**删除字段 0 未删 1已删*/
    @Excel(name = "删除字段 0 未删 1已删", width = 4)
    @ApiModelProperty(value = "删除字段 0 未删 1已删")
    private Integer isDel;
    /**是否默认 0 非默认 1默认*/
    @Excel(name = "是否默认 0 非默认 1默认", width = 4)
    @ApiModelProperty(value = "是否默认 0 非默认 1默认")
    private Integer isDefault;
    @Excel(name = "抬头类型 PERSONAL（个人）CORPORATION（单位）", width = 30)
    @ApiModelProperty(value = "抬头类型 抬头类型 PERSONAL（个人）CORPORATION（单位）")
    private String titleType;

}
