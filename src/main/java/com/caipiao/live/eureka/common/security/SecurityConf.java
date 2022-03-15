package com.caipiao.live.eureka.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author 阿布 重写安全过滤器 只允许注册服务免登陆连接
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// springcloud 2.0默認棄用csrf校驗
//		// Configure HttpSecurity as needed (e.g. enable http basic).
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
//		http.csrf().disable();
//		// 注意：为了可以使用 http://${user}:${password}@${host}:${port}/eureka/ 这种方式登录,所以必须是httpBasic,
//		// 如果是form方式,不能使用url格式登录
//		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//		super.configure(http);
//	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		// 只允许注册发现服务免登陆连接
		http.authorizeRequests() // 认证请求
				.antMatchers("/eureka/**").permitAll();
		// .anyRequest() //对任何请求
		// .authenticated() //都需要认证
		// .and()
		// .formLogin();
		// .httpBasic(); //使用Spring Security提供的登录界面
		//super.configure(http);
	}
}
