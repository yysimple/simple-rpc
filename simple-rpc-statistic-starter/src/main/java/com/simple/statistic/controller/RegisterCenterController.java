package com.simple.statistic.controller;

import com.simple.statistic.constant.StatisticConstant;
import com.simple.statistic.entity.ApplicationEntity;
import com.simple.statistic.entity.SimpleResponse;
import com.simple.statistic.service.register.RedisRegisterCenterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-25 00:20
 **/
@RestController
@RequestMapping("/registerCenter")
@CrossOrigin
public class RegisterCenterController {

    @Resource
    private RedisRegisterCenterService service;

    @PostMapping("/listApp")
    public SimpleResponse<List<ApplicationEntity>> listApp(String appName) {
        return new SimpleResponse<>(service.listApp(appName));
    }
}
