package com.flyang.job.client.controller;

import com.flyang.job.core.rest.IDemoRest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author meng.meng
 * @date 2018/4/9 上午11:26
 */
@RestController
@RequestMapping("/cDemo")
@EnableAutoConfiguration
public class DemoController {

    @Resource
    private IDemoRest demoRest;

    @RequestMapping(value = "/demo")
    public Map<String, Object> demo() {
        return demoRest.findEmployee();
    }
}

























