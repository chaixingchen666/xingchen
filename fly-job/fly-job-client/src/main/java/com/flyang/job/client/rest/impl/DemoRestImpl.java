package com.flyang.job.client.rest.impl;

import com.flyang.job.core.rest.IDemoRest;
import com.flyang.job.core.service.IDemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author meng.meng
 * @date 2018/4/9 上午11:40
 */
@Service("demoRest")
public class DemoRestImpl implements IDemoRest {

    @Resource
    private IDemoService demoService;

    @Override
    public Map<String, Object> findEmployee() {
        return demoService.findEmployee();
    }

}
