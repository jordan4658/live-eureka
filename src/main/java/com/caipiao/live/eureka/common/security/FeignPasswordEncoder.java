package com.caipiao.live.eureka.common.security;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 阿布 eurekaserver权限远程数据库验证
 */
public class FeignPasswordEncoder implements PasswordEncoder {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Resource
//    private FeignService feignService;

    @Override
    public String encode(CharSequence acclogin) {
        return acclogin.toString();
    }

    @Override
    public boolean matches(CharSequence psd, String acclogin) {
        try {
            if (StringUtils.isEmpty(String.valueOf(psd).trim()) || StringUtils.isEmpty(acclogin.trim())) {
                return false;
            }
            return true;
//            ResultInfo jr = feignService.eurekaLogin(acclogin, MD5.md5(String.valueOf(psd)));
//            if (jr == null || !jr.isOk()) {
//                logger.error(new Date() + "登录失败，账号密码错误！");
//                return false;
//            } else {
//                return true;
//            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
