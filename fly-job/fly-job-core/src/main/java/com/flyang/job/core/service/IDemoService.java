package com.flyang.job.core.service;

import java.util.Map;

/**
 * @author meng.meng
 * @date 2018/4/9 上午11:37
 */
public interface IDemoService {

    /**
     * 查询示例员工信息
     * @return Map
     */
    Map<String, Object> findEmployee();
}
