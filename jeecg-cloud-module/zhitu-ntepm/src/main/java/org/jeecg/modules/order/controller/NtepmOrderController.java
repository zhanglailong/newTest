package org.jeecg.modules.order.controller;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.alipay.AliPay;
import org.jeecg.modules.order.entity.NtepmOrder;
import org.jeecg.modules.order.service.INtepmOrderService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.wxpay.WXPayUtil;
import org.jeecg.modules.wxpay.WxPay;
import org.jeecg.modules.wxpay.WxPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 测试项目订单
 * @Author: jeecg-boot
 * @Date: 2021-04-14
 * @Version: V1.0
 */
@Api(tags = "测试项目订单")
@RestController
@RequestMapping("/online/ntepmOrder")
@Slf4j
public class NtepmOrderController extends JeecgController<NtepmOrder, INtepmOrderService> {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private INtepmOrderService ntepmOrderService;
    @Autowired
    private AliPay aliPay;
    @Autowired
    private WxPay wxPay;
    @Autowired
    private WxPayConfig wxPayConfig;

    /**
     * 分页列表查询
     *
     * @param ntepmOrder
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "测试项目订单-分页列表查询")
    @ApiOperation(value = "测试项目订单-分页列表查询", notes = "测试项目订单-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(NtepmOrder ntepmOrder,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<NtepmOrder> queryWrapper = QueryGenerator.initQueryWrapper(ntepmOrder, req.getParameterMap());
        Page<NtepmOrder> page = new Page<NtepmOrder>(pageNo, pageSize);
        IPage<NtepmOrder> pageList = ntepmOrderService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    /**
     * 分页列表查询
     *
     * @param ntepmOrder
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "测试项目订单-分页列表查询")
    @ApiOperation(value = "测试项目订单-分页列表查询", notes = "测试项目订单-分页列表查询")
    @GetMapping(value = "/onlineList")
    public Result<?> onlineQueryPageListByUserId(NtepmOrder ntepmOrder,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 HttpServletRequest req) {
        Wrapper<NtepmOrder> queryWrapper = new QueryWrapper<>(ntepmOrder).eq("user_id", ntepmOrder.getUserId());
        Page<NtepmOrder> page = new Page<NtepmOrder>(pageNo, pageSize);
        IPage<NtepmOrder> pageList = ntepmOrderService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param ntepmOrder
     * @return
     */
    @AutoLog(value = "测试项目订单-添加")
    @ApiOperation(value = "测试项目订单-添加", notes = "测试项目订单-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody NtepmOrder ntepmOrder) {
        ntepmOrderService.save(ntepmOrder);
        return Result.OK("添加成功！");
    }


    /**
     * 在线下单添加
     *
     * @param ntepmOrder
     * @return
     */
    @AutoLog(value = "测试项目订单-在线下单添加")
    @ApiOperation(value = "测试项目订单-在线下单添加", notes = "测试项目订单-在线下单添加")
    @PostMapping(value = "/onlineAdd")
    public Result<?> onlineAdd(@RequestBody NtepmOrder ntepmOrder) {

        /*订单状态
         * 0 已提交
         * 1 已定价
         * 2 已下单
         * 3 已完成*/

        ntepmOrder.setPayState("0");
        ntepmOrder.setOrderState("0");
        ntepmOrder.setProcessState("0");
        ntepmOrder.setIdel("0");
        ntepmOrderService.save(ntepmOrder);
        return Result.OK("在线下单添加成功！");
    }

    /**
     * 管理员定价
     *
     * @param ntepmOrder
     * @return
     */
    @AutoLog(value = "测试项目订单-管理员定价")
    @ApiOperation(value = "测试项目订单-管理员定价", notes = "测试项目订单-管理员定价")
    @PutMapping(value = "/pricing")
    public Result<?> pricing(@RequestBody NtepmOrder ntepmOrder) {

        boolean isPircing = ntepmOrderService.updateById(ntepmOrder);
        if (isPircing) {
            ntepmOrder.setProcessState("1");
            ntepmOrder.setOrderState("1");
            ntepmOrder.setPayState("0");
            ntepmOrderService.updateById(ntepmOrder);
        }
        return Result.OK("管理员定价成功!");
    }

    /**
     * 用户支付
     *
     * @param ntepmOrder
     * @return
     */
    @AutoLog(value = "测试项目订单-用户支付")
    @ApiOperation(value = "测试项目订单-用户支付", notes = "测试项目订单-用户支付")
    @PutMapping(value = "/payOrder")
    public Result<?> payOrder(@RequestBody NtepmOrder ntepmOrder) {

        ntepmOrder.setProcessState("1");
        //已下单
        ntepmOrder.setOrderState("2");
        ntepmOrderService.updateById(ntepmOrder);
        return Result.OK("用户付款成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "测试项目订单-通过id删除")
    @ApiOperation(value = "测试项目订单-通过id删除", notes = "测试项目订单-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        NtepmOrder ntepmOrder = ntepmOrderService.getById(id);
        ntepmOrder.setIdel("1");
        ntepmOrderService.updateById(ntepmOrder);
        return Result.OK("删除成功!");
    }

    /**
     * 取消订单
     *
     * @param id
     * @return
     */
    @AutoLog(value = "测试项目订单-根据id取消订单")
    @ApiOperation(value = "测试项目订单-根据id取消订单", notes = "测试项目订单-根据id取消订单")
    @GetMapping(value = "/cancelById")
    public Result<?> cancelOrder(@RequestParam(name = "id", required = true) String id) {
        NtepmOrder ntepmOrder = ntepmOrderService.getById(id);
        ntepmOrder.setOrderState("-1");
        ntepmOrderService.updateById(ntepmOrder);
        return Result.OK("取消成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "测试项目订单-批量删除")
    @ApiOperation(value = "测试项目订单-批量删除", notes = "测试项目订单-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ntepmOrderService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "测试项目订单-通过id查询")
    @ApiOperation(value = "测试项目订单-通过id查询", notes = "测试项目订单-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        NtepmOrder ntepmOrder = ntepmOrderService.getById(id);
        if (ntepmOrder == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ntepmOrder);
    }

    /*文件上传*/
    @AutoLog(value = "测试项目订单-文件上传")
    @ApiOperation(value = "测试项目订单-文件上传", notes = "测试项目订单-文件上传")
    @PostMapping(value = "/upload")
    public Result<?> upload(HttpServletRequest request) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> file = multipartRequest.getFiles("file");// 获取上传文件对象
        List<String> paths = new ArrayList<>();

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

            String filePath = applicationName + "/file/ntepmOrder";
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
                paths.add("\"/\" + applicationName + \"/file/ntepmOrder\" + format + \"/\" + fileName");
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                System.err.println(e.getStackTrace());
                return Result.error("文件上传错误");
            }
            //return Result.OK(format + "/" + fileName);
        }

        return Result.OK(paths);
    }


    /*文件下载*/
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


    /**
     * 导出excel
     *
     * @param request
     * @param ntepmOrder
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NtepmOrder ntepmOrder) {
        return super.exportXls(request, ntepmOrder, NtepmOrder.class, "测试项目订单");
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
        return super.importExcel(request, response, NtepmOrder.class);
    }

    /**
     * 获取支付链接
     *
     * @param id
     * @param payType
     * @return
     */
    @GetMapping(value = "/pay")
    public Result<?> getPayLink(String id, Integer payType) {
        NtepmOrder ntepmOrder = ntepmOrderService.getById(id);
        String payUrl = null;
        switch (payType) {
            //支付宝支付
            case 0:
                payUrl = aliPay.getTradePrecreateUrl(ntepmOrder.getId(), ntepmOrder.getOrderPrice().toString(), ntepmOrder.getProDesc(), "/online/ntepmOrder/aliPayNotify");
                break;
            //微信支付
            case 1:
                payUrl = wxPay.getNativeUrl(ntepmOrder.getId(), BigDecimal.valueOf(ntepmOrder.getOrderPrice()).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "", ntepmOrder.getProDesc(), "/online/ntepmOrder/wxPayNotify");
                break;
        }
        if (StringUtils.isEmpty(payUrl)) {
            return Result.error("二维码链接生成失败");
        } else {
            return Result.OK(payUrl);
        }

    }

    //支付宝回调函数
    @PostMapping(value = "/aliPayNotify")
    public String aliPayNotify(HttpServletRequest request) {
        Map<String, String> params = aliPay.solveRequest(request);

        boolean signVerified = false; //调用SDK验证签名
        signVerified = aliPay.checkSign(params);
        if (signVerified) {
            String tradeStatus = params.get("trade_status");
            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                String id = params.get("out_trade_no");
                NtepmOrder ntepmOrder = ntepmOrderService.getById(id);
                ntepmOrder.setPayState("2");
                ntepmOrder.setOrderState("3");
                ntepmOrder.setPayType(0);
                ntepmOrderService.updateById(ntepmOrder);
                return "success";
            }
        }
        return "fail";
    }

    //微信支付回调函数
    @PostMapping(value = "/wxPayNotify")
    public String wxPayNotify(HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = wxPay.getResultMap(request);
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("return_code", "FAIL");
        returnMap.put("return_msg", "error");
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            //签名校验 返回的sign 用返回的map和自己的key生成新的sign,对比值是否一样
            if (wxPay.checkSign(resultMap, wxPayConfig.getKey())) {
                //修改订单状态
                String id = resultMap.get("out_trade_no");
                NtepmOrder ntepmOrder = ntepmOrderService.getById(id);
                ntepmOrder.setPayState("2");
                ntepmOrder.setOrderState("3");
                ntepmOrder.setPayType(1);
                ntepmOrderService.updateById(ntepmOrder);
            }
            returnMap.put("return_code", "SUCCESS");
            returnMap.put("return_msg", "OK");
        }
        return WXPayUtil.mapToXml(returnMap);
    }

}
