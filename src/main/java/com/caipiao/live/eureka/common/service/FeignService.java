package com.caipiao.live.eureka.common.service;

import com.caipiao.live.common.model.common.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 阿布 微服務間接口互訪
 */
@FeignClient(name = "live-login")
public interface FeignService {

    /**
     * eurekaserver登录验证
     *
     * @param acclogin
     * @param password
     * @return
     */
    @RequestMapping(value = "/eserver/login", method = RequestMethod.POST)
    ResultInfo eurekaLogin(@RequestParam("acclogin") String acclogin, @RequestParam("password") String password);
}
