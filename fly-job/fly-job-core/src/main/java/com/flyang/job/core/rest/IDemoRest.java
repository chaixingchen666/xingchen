package com.flyang.job.core.rest;

import java.util.Map;

/**
 * @author meng.meng
 * @date 2018/4/9 上午11:38
 */
public interface IDemoRest {

    /**
     * 查询示例员工信息
     * @return Map
     */
    Map<String, Object> findEmployee();
}
