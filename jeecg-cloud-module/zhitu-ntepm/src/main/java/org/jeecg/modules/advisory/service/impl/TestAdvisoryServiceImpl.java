package org.jeecg.modules.demo.advisory.service.impl;

import org.jeecg.modules.advisory.entity.TestAdvisory;
import org.jeecg.modules.advisory.mapper.TestAdvisoryMapper;

import org.jeecg.modules.advisory.service.ITestAdvisoryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 咨询业务表
 * @Author: jeecg-boot
 * @Date:   2021-04-12
 * @Version: V1.0
 */
@Service
public class TestAdvisoryServiceImpl extends ServiceImpl<TestAdvisoryMapper, TestAdvisory> implements ITestAdvisoryService {

}
