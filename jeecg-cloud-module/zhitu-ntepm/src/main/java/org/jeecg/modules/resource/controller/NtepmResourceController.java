package org.jeecg.modules.resource.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.resource.entity.NtepmResource;
import org.jeecg.modules.resource.service.INtepmResourceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 资源表
 * @Author: jeecg-boot
 * @Date:   2021-04-23
 * @Version: V1.0
 */
@Api(tags="资源表")
@RestController
@RequestMapping("/resource/ntepmResource")
@Slf4j
public class NtepmResourceController extends JeecgController<NtepmResource, INtepmResourceService> {
	@Autowired
	private INtepmResourceService ntepmResourceService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ntepmResource
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "资源表-分页列表查询")
	@ApiOperation(value="资源表-分页列表查询", notes="资源表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NtepmResource ntepmResource,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NtepmResource> queryWrapper = QueryGenerator.initQueryWrapper(ntepmResource, req.getParameterMap());
		Page<NtepmResource> page = new Page<NtepmResource>(pageNo, pageSize);
		IPage<NtepmResource> pageList = ntepmResourceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ntepmResource
	 * @return
	 */
	@AutoLog(value = "资源表-添加")
	@ApiOperation(value="资源表-添加", notes="资源表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NtepmResource ntepmResource) {
		ntepmResourceService.save(ntepmResource);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ntepmResource
	 * @return
	 */
	@AutoLog(value = "资源表-编辑")
	@ApiOperation(value="资源表-编辑", notes="资源表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NtepmResource ntepmResource) {
		ntepmResourceService.updateById(ntepmResource);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资源表-通过id删除")
	@ApiOperation(value="资源表-通过id删除", notes="资源表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ntepmResourceService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "资源表-批量删除")
	@ApiOperation(value="资源表-批量删除", notes="资源表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ntepmResourceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资源表-通过id查询")
	@ApiOperation(value="资源表-通过id查询", notes="资源表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		NtepmResource ntepmResource = ntepmResourceService.getById(id);
		if(ntepmResource==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ntepmResource);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ntepmResource
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NtepmResource ntepmResource) {
        return super.exportXls(request, ntepmResource, NtepmResource.class, "资源表");
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
        return super.importExcel(request, response, NtepmResource.class);
    }

}
