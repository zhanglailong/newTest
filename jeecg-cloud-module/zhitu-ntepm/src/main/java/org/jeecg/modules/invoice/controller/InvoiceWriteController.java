package org.jeecg.modules.invoice.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.invoice.entity.InvoiceTitle;
import org.jeecg.modules.invoice.entity.InvoiceWrite;
import org.jeecg.modules.invoice.service.IInvoiceTitleService;
import org.jeecg.modules.invoice.service.IInvoiceWriteService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.order.entity.NtepmOrder;
import org.jeecg.modules.order.service.INtepmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 开票
 * @Author: jeecg-boot
 * @Date: 2021-04-22
 * @Version: V1.0
 */
@Api(tags = "开票")
@RestController
@RequestMapping("/invoice/write")
@Slf4j
public class InvoiceWriteController extends JeecgController<InvoiceWrite, IInvoiceWriteService> {
    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private IInvoiceWriteService invoiceWriteService;
    @Autowired
    private INtepmOrderService iNtepmOrderService;
    @Autowired
    private IInvoiceTitleService iInvoiceTitleService;

    /**
     * 分页列表查询
     *
     * @param invoiceWrite
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "开票-分页列表查询")
    @ApiOperation(value = "开票-分页列表查询", notes = "开票-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(InvoiceWrite invoiceWrite,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<InvoiceWrite> queryWrapper = QueryGenerator.initQueryWrapper(invoiceWrite, req.getParameterMap());
        Page<InvoiceWrite> page = new Page<InvoiceWrite>(pageNo, pageSize);
        IPage<InvoiceWrite> pageList = invoiceWriteService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "开票-添加")
    @ApiOperation(value = "开票-添加", notes = "开票-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody InvoiceWrite invoiceWrite) {
        if (StringUtils.isEmpty(invoiceWrite.getOrderId()) || StringUtils.isEmpty(invoiceWrite.getTitleId()) || null == invoiceWrite.getOrderType()){
            return Result.error("缺少参数");
        }
        NtepmOrder order = iNtepmOrderService.getById(invoiceWrite.getOrderId());
        invoiceWrite.setCreateTime(new Date());
        invoiceWrite.setStatus("0");
        invoiceWrite.setUserId(order.getUserId());
        invoiceWrite.setCreateBy(order.getUserId());
        invoiceWriteService.save(invoiceWrite);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param invoiceWrite
     * @return
     */
    @AutoLog(value = "开票-编辑")
    @ApiOperation(value = "开票-编辑", notes = "开票-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody InvoiceWrite invoiceWrite) {
        if (StringUtils.isEmpty(invoiceWrite.getId())) {
            return Result.error("缺少id");
        }
        invoiceWriteService.updateById(invoiceWrite);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "开票-通过id删除")
    @ApiOperation(value = "开票-通过id删除", notes = "开票-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        InvoiceWrite invoiceWrite = invoiceWriteService.getById(id);
        invoiceWrite.setStatus("1");
        invoiceWriteService.updateById(invoiceWrite);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "开票-批量删除")
    @ApiOperation(value = "开票-批量删除", notes = "开票-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.invoiceWriteService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "开票-通过id查询")
    @ApiOperation(value = "开票-通过id查询", notes = "开票-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        InvoiceWrite invoiceWrite = invoiceWriteService.getById(id);
        InvoiceTitle invoiceTitle = iInvoiceTitleService.getById(invoiceWrite.getTitleId());
        invoiceWrite.setInvoiceTitle(invoiceTitle);
        if (invoiceWrite == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(invoiceWrite);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param invoiceWrite
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InvoiceWrite invoiceWrite) {
        return super.exportXls(request, invoiceWrite, InvoiceWrite.class, "开票");
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
        return super.importExcel(request, response, InvoiceWrite.class);
    }


    /*pdf文件上传*/
    @AutoLog(value = "测试项目订单-文件上传")
    @ApiOperation(value = "测试项目订单-文件上传", notes = "测试项目订单-文件上传")
    @PostMapping(value = "/upload")
    public Result<?> upload(HttpServletRequest request) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> file = multipartRequest.getFiles("file");// 获取上传文件对象

        for (MultipartFile multipartFile : file) {
            if (file == null)
                return Result.error("上传的文件不存在");

            String oName = multipartFile.getOriginalFilename();
            //获取扩展名
            String suffixName = oName.substring(oName.lastIndexOf("."));

            //随机生成文件的名称
            String fileName = System.currentTimeMillis() + (int) (Math.random() * 10) + "_" + oName.substring(0, oName.lastIndexOf(".")) + suffixName;

            //生成目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String format = dateFormat.format(new Date());

            String filePath = applicationName + "/file/invoice/pdf";
            String OSName = System.getProperties().getProperty("os.name").toLowerCase();
            if (OSName.contains("windows")) {
                filePath = "d:/" + filePath;
            } else {
                filePath = "/home/" + filePath;
            }

            // 判断路径是否存在 如果不存在则需要创建
            File subPath = new File(filePath + "/" + format);
            if (!subPath.exists()) {
                subPath.mkdirs();
            }
//            System.out.println(uploadPath+"-----------------------");
            try {
                multipartFile.transferTo(new File(filePath + "/" + format + "/" + fileName));
                return Result.OK("/" + applicationName + "/file/" + format + "/" + fileName);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                System.err.println(e.getStackTrace());
                return Result.error("文件上传错误");
            }
            //return Result.OK(format + "/" + fileName);
        }

        return Result.error("上传文件失败");
    }


    /*pdf文件下载*/
    @AutoLog(value = "测试项目订单-文件下载")
    @ApiOperation(value = "测试项目订单-文件下载", notes = "测试项目订单-文件下载")
    @GetMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        response.setContentType("text/html;charset=UTF-8");
        try {
            String downloadFilePath;
            String OSName = System.getProperties().getProperty("os.name").toLowerCase();
            if (OSName.contains("windows")) {
                downloadFilePath = "d:" + filePath;
            } else {
                downloadFilePath = "/home" + filePath;
            }
            File file = new File(downloadFilePath);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开            
                //下载文件名称删除前15位时间戳加随机数
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"), "iso-8859-1").substring(14));
                inputStream = new BufferedInputStream(new FileInputStream(file));
                outputStream = response.getOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                response.flushBuffer();
            } else {
                response.getWriter().write(JSON.toJSONString(Result.error("文件不存在")));
            }

        } catch (Exception e) {
            log.info("文件下载失败" + e.getMessage());
            // e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
