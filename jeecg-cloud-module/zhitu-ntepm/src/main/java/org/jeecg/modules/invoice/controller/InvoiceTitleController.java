package org.jeecg.modules.invoice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.InvoiceTitleModel;
import com.alipay.api.request.AlipayEbppInvoiceTitleListGetRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipayEbppInvoiceTitleListGetResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.alipay.AlipayConfig;
import org.jeecg.modules.invoice.entity.InvoiceTitle;
import org.jeecg.modules.invoice.service.IInvoiceTitleService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 发票抬头
 * @Author: jeecg-boot
 * @Date: 2021-04-22
 * @Version: V1.0
 */
@Api(tags = "发票抬头")
@RestController
@RequestMapping("/invoice/head")
@Slf4j
public class InvoiceTitleController extends JeecgController<InvoiceTitle, IInvoiceTitleService> {
    @Autowired
    private IInvoiceTitleService invoiceTitleService;

    /**
     * 分页列表查询
     *
     * @param invoiceTitle
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "发票抬头-分页列表查询")
    @ApiOperation(value = "发票抬头-分页列表查询", notes = "发票抬头-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(InvoiceTitle invoiceTitle,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<InvoiceTitle> queryWrapper = QueryGenerator.initQueryWrapper(invoiceTitle, req.getParameterMap());
        Page<InvoiceTitle> page = new Page<InvoiceTitle>(pageNo, pageSize);
        IPage<InvoiceTitle> pageList = invoiceTitleService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param invoiceTitle
     * @return
     */
    @AutoLog(value = "发票抬头-添加")
    @ApiOperation(value = "发票抬头-添加", notes = "发票抬头-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody InvoiceTitle invoiceTitle) {
        invoiceTitle.setIsDel(0);
        invoiceTitle.setIsDefault(0);
        invoiceTitle.setCreateBy(invoiceTitle.getUserId());
        invoiceTitleService.save(invoiceTitle);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param invoiceTitle
     * @return
     */
    @AutoLog(value = "发票抬头-编辑")
    @ApiOperation(value = "发票抬头-编辑", notes = "发票抬头-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody InvoiceTitle invoiceTitle) {
        if (StringUtils.isEmpty(invoiceTitle.getId())) {
            return Result.error("缺少id");
        }
        invoiceTitleService.updateById(invoiceTitle);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "发票抬头-通过id删除")
    @ApiOperation(value = "发票抬头-通过id删除", notes = "发票抬头-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        InvoiceTitle invoiceTitle = invoiceTitleService.getById(id);
        invoiceTitle.setIsDel(1);
        invoiceTitle.setUpdateTime(new Date());
        invoiceTitleService.updateById(invoiceTitle);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "发票抬头-批量删除")
    @ApiOperation(value = "发票抬头-批量删除", notes = "发票抬头-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Arrays.asList(ids.split(",")).forEach(id -> {
            InvoiceTitle invoiceTitle = invoiceTitleService.getById(id);
            invoiceTitle.setIsDel(1);
            invoiceTitleService.updateById(invoiceTitle);
        });
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "发票抬头-通过id查询")
    @ApiOperation(value = "发票抬头-通过id查询", notes = "发票抬头-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        InvoiceTitle invoiceTitle = invoiceTitleService.getById(id);
        if (invoiceTitle == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(invoiceTitle);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param invoiceTitle
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InvoiceTitle invoiceTitle) {
        return super.exportXls(request, invoiceTitle, InvoiceTitle.class, "发票抬头");
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
        return super.importExcel(request, response, InvoiceTitle.class);
    }

}
