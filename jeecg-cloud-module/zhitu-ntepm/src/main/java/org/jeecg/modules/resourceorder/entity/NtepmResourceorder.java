package org.jeecg.modules.resourceorder.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 资源订单
 * @Author: jeecg-boot
 * @Date:   2021-04-23
 * @Version: V1.0
 */
@Data
@TableName("ntepm_resourceorder")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ntepm_resourceorder对象", description="资源订单")
public class NtepmResourceorder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**支付状态（1：未支付，2：已支付）*/
	@Excel(name = "支付状态（1：未支付，2：已支付）", width = 15)
    @ApiModelProperty(value = "支付状态（1：未支付，2：已支付）")
    private java.lang.Integer payStatus;
	/**订单状态（1：未下单，2：已下单，3：未到期，4：已到期，5：已删除）*/
	@Excel(name = "订单状态（1：未下单，2：已下单，3：未到期，4：已到期，5：已删除）", width = 15)
    @ApiModelProperty(value = "订单状态（1：未下单，2：已下单，3：未到期，4：已到期，5：已删除）")
    private java.lang.Integer orderStatus;
	/**租借天数*/
	@Excel(name = "租借天数", width = 15)
    @ApiModelProperty(value = "租借天数")
    private java.lang.Integer day;
	/**支付方式（1：支付宝，2：微信）*/
	@Excel(name = "支付方式（1：支付宝，2：微信）", width = 15)
    @ApiModelProperty(value = "支付方式（1：支付宝，2：微信）")
    private java.lang.Integer orderWay;
}
