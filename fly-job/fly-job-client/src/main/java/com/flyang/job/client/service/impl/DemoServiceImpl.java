package com.flyang.job.client.service.impl;

import com.flyang.job.client.mapper.EmployeMapper;
import com.flyang.job.core.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author meng.meng
 * @date 2018/4/9 上午11:38
 */
@Service("demoService")
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private EmployeMapper employeMapper;

    @Override
    public Map<String, Object> findEmployee() {
        return employeMapper.selectOnceEmp();
    }
}
