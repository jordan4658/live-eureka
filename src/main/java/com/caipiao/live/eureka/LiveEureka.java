package com.caipiao.live.eureka;

import com.caipiao.live.common.util.redis.BasicRedisClient;
import com.caipiao.live.eureka.common.security.FeignPasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.TimeZone;


@EnableEurekaServer
@ComponentScan(value = "com.caipiao.live", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        BasicRedisClient.class
        }),
        @ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.caipiao.live.common.service.*")
    }
)
@EnableFeignClients("com.caipiao.live")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LiveEureka extends SpringBootServletInitializer {

    @Bean
    public FeignPasswordEncoder passwordEncoder() {
        return new FeignPasswordEncoder();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LiveEureka.class);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        // TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    /**
     * 文件上传临时路径
     */
    @Bean
    protected MultipartConfigElement multipartConfigElement() {
        String line = File.separator;
        String rootPath = null;
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        try {
            rootPath = java.net.URLDecoder.decode(jarF.getParentFile().toString(), "utf-8");
            if ("\\".equals(line)) {
                rootPath = rootPath.replace("/", "\\"); // 将/换成\\
            }
            // linux下
            if ("/".equals(line)) {
                rootPath = rootPath.replace("\\", "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(rootPath);
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(LiveEureka.class, args);
    }

}
