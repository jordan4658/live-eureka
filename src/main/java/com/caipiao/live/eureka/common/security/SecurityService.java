package com.caipiao.live.eureka.common.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 阿布 集成security權限管理
 *
 */
@Component
public class SecurityService implements UserDetailsService {

//	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String acclogin) throws UsernameNotFoundException {
		return new User(acclogin, acclogin, true, true, true, true, // 账户是否被锁定
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
