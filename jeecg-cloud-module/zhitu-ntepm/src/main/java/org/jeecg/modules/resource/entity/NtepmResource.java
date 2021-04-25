package org.jeecg.modules.resource.entity;

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
 * @Description: 资源表
 * @Author: jeecg-boot
 * @Date:   2021-04-23
 * @Version: V1.0
 */
@Data
@TableName("ntepm_resource")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ntepm_resource对象", description="资源表")
public class NtepmResource implements Serializable {
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
	/**机器名称*/
	@Excel(name = "机器名称", width = 15)
    @ApiModelProperty(value = "机器名称")
    private java.lang.String name;
	/**cpu数量*/
	@Excel(name = "cpu数量", width = 15)
    @ApiModelProperty(value = "cpu数量")
    private java.lang.Integer cpuNumber;
	/**内存容量*/
	@Excel(name = "内存容量", width = 15)
    @ApiModelProperty(value = "内存容量")
    private java.lang.Integer memoryCapacity;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
    private java.lang.Integer price;
	/**工具*/
	@Excel(name = "工具", width = 15)
    @ApiModelProperty(value = "工具")
    private java.lang.String untis;
}
