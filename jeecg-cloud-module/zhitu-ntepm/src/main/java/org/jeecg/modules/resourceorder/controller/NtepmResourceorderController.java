package org.jeecg.modules.resourceorder.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.resourceorder.entity.NtepmResourceorder;
import org.jeecg.modules.resourceorder.service.INtepmResourceorderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 资源订单
 * @Author: jeecg-boot
 * @Date:   2021-04-23
 * @Version: V1.0
 */
@Api(tags="资源订单")
@RestController
@RequestMapping("/resourceorder/ntepmResourceorder")
@Slf4j
public class NtepmResourceorderController extends JeecgController<NtepmResourceorder, INtepmResourceorderService> {
	@Autowired
	private INtepmResourceorderService ntepmResourceorderService;
	/**
	 * 分页列表查询
	 *
	 * @param ntepmResourceorder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "资源订单-分页列表查询")
	@ApiOperation(value="资源订单-分页列表查询", notes="资源订单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NtepmResourceorder ntepmResourceorder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NtepmResourceorder> queryWrapper = QueryGenerator.initQueryWrapper(ntepmResourceorder, req.getParameterMap());
		Page<NtepmResourceorder> page = new Page<NtepmResourceorder>(pageNo, pageSize);
		IPage<NtepmResourceorder> pageList = ntepmResourceorderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	/**
	 *   添加
	 *
	 * @param ntepmResourceorder
	 * @return
	 */
	@AutoLog(value = "资源订单-添加")
	@ApiOperation(value="资源订单-添加", notes="资源订单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NtepmResourceorder ntepmResourceorder) {
		ntepmResourceorder.setCreateTime(new Date());//设置创建时间
		String uuid= UUID.randomUUID().toString();
		ntepmResourceorder.setId(uuid);
		ntepmResourceorderService.save(ntepmResourceorder);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param ntepmResourceorder
	 * @return
	 */
	@AutoLog(value = "资源订单-编辑")
	@ApiOperation(value="资源订单-编辑", notes="资源订单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NtepmResourceorder ntepmResourceorder) {
		ntepmResourceorderService.updateById(ntepmResourceorder);
		return Result.OK("编辑成功!");
	}
	 /**
	  * @Description: 修改订单状态
	  * @Param:
	  * @return:
	  * @Author: Mr.Zhang
	  * @Date:
	  */
	 @AutoLog(value = "修改订单状态")
	 @ApiOperation(value="修改订单状态", notes="修改订单状态")
	 @PutMapping(value = "/updateorder_status")
	 public Result<?> updateorder_status(@RequestParam(name = "id")String id,@RequestParam(name = "id")int orderstatus) {
		 ntepmResourceorderService.updateorder_status(id,orderstatus);
		 return Result.OK("编辑成功!");
	 }
	 /**
	  * @Description: 修改资源支付状态
	  * @Param:
	  * @return:
	  * @Author: Mr.Zhang
	  * @Date:
	  */
	 @AutoLog(value = "修改资源支付状态")
	 @ApiOperation(value="修改资源支付状态", notes="修改资源支付状态")
	 @PutMapping(value = "/updatepay_status")
	 public Result<?> updatepay_status(@RequestParam(name = "id")String id,@RequestParam(name = "id")int paystatus) {
		 ntepmResourceorderService.updatepay_status(id,paystatus);
		 return Result.OK("编辑成功!");
	 }
	 /** 
	 * @Description:  资源订单详情
	 * @Param:  
	 * @return:  
	 * @Author: Mr.Zhang 
	 * @Date:  
	 */
	 @AutoLog(value = "资源订单详情")
	 @ApiOperation(value="资源订单详情", notes="资源订单详情")
	 @PutMapping(value = "/selectorder")
	 public Result<?> selectorder(@Param("resourceid") String resourceid){
		 NtepmResourceorder selectorder = ntepmResourceorderService.selectorder(resourceid);
		 if(selectorder==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(selectorder);
	 }
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资源订单-通过id删除")
	@ApiOperation(value="资源订单-通过id删除", notes="资源订单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ntepmResourceorderService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "资源订单-批量删除")
	@ApiOperation(value="资源订单-批量删除", notes="资源订单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ntepmResourceorderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资源订单-通过id查询")
	@ApiOperation(value="资源订单-通过id查询", notes="资源订单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		NtepmResourceorder ntepmResourceorder = ntepmResourceorderService.getById(id);
		if(ntepmResourceorder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ntepmResourceorder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ntepmResourceorder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NtepmResourceorder ntepmResourceorder) {
        return super.exportXls(request, ntepmResourceorder, NtepmResourceorder.class, "资源订单");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, NtepmResourceorder.class);
    }

}
