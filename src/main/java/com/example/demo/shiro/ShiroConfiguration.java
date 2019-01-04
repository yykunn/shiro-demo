package com.example.demo.shiro;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.po.PermGrade;
import com.example.demo.pojo.SysPerm;
import com.example.demo.service.PermService;

@Configuration
public class ShiroConfiguration {
	@Autowired
	PermService permService;

	// 将自己的验证方式加入容器
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(credentialsMatcher());
		return myShiroRealm;
	}

	// 密码匹配器
	@Bean
	public CredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher match = new HashedCredentialsMatcher();
		match.setHashAlgorithmName("md5");// md5算法
		match.setHashIterations(2);// md5(md5(""))
		return match;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	// Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 登出
		// 放行静态资源
		map.put("/static/**", "anon");
		// 登录
		map.put("/login", "anon");
		// 放行swagger
		map.put("/swagger-ui.html", "anon");
		map.put("/swagger/**", "anon");
		map.put("/webjars/**", "anon");
		map.put("/swagger-resources/**", "anon");
		map.put("/v2/**", "anon");
		// map.put("/druid/**","anon");

		// 对permGrade为0的链接放行
		List<SysPerm> openPerms = permService.listPerms(PermGrade.OPEN);
		openPerms.stream().filter(perm -> perm.getUri() != null && !"".equals(perm.getUri().trim())).forEach(perm -> {
			map.put(perm.getUri(), "anon");
		});

		shiroFilterFactoryBean.setLoginUrl("/index");
		// 对permGrade=1 的进行拦截
		List<SysPerm> customPerms = permService.listPerms(PermGrade.LOGIN);
		customPerms.stream().filter(perm -> perm.getUri() != null && !"".equals(perm.getUri().trim()))
				.forEach(perm -> map.put(perm.getUri(), "user"));

		// 对permGrade=2 的进行拦截
		List<SysPerm> authPerms = permService.listPerms(PermGrade.AUTH);
		authPerms.stream().filter(perm -> perm.getUri() != null && !"".equals(perm.getUri().trim())).forEach(perm -> {
			map.put(perm.getUri(), "perms[" + perm.getUri() + "]");
		});

		// 对所有用户认证
		map.put("/**", "authc");
		// 首页
		shiroFilterFactoryBean.setSuccessUrl("/success");
		// 错误页面，认证不通过跳转
		shiroFilterFactoryBean.setUnauthorizedUrl("/refuse");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	// 开启aop 否则注解无效
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}
}