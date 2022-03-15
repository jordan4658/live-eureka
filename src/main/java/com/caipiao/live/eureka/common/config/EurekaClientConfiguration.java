//package com.caipiao.live.eureka.common.config;
//
//import com.caipiao.live.common.util.http.HttpRespons;
//import com.caipiao.live.common.util.http.HttpUtils;
//import com.netflix.appinfo.EurekaInstanceConfig;
//import com.netflix.discovery.EurekaClientConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.condition.SearchStrategy;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.client.CommonsClientAutoConfiguration;
//import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
//import org.springframework.cloud.client.discovery.noop.NoopDiscoveryClientAutoConfiguration;
//import org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration;
//import org.springframework.cloud.commons.util.InetUtils;
//import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
//import org.springframework.cloud.netflix.eureka.config.DiscoveryClientOptionalArgsConfiguration;
//import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadata;
//import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadataProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Profile;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.util.Map;
//
//import static org.springframework.cloud.commons.util.IdUtils.getDefaultInstanceId;
//
///**
// *
// * @author 阿布 20200307
// *
// *         初始化Eurekaclient參數設置,解決官方默認配置無法獲取服務器外網ip問題
// *
// *         默認取yml設置ip-address值
// *
// *
// */
//@SuppressWarnings("deprecation")
////@Profile("dev")
//@Configuration
//@EnableConfigurationProperties
//@ConditionalOnClass(EurekaClientConfig.class)
//@Import(DiscoveryClientOptionalArgsConfiguration.class)
//@ConditionalOnProperty(value = "eureka.client.enabled", matchIfMissing = true)
//@ConditionalOnDiscoveryEnabled
//@AutoConfigureBefore({ NoopDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class, ServiceRegistryAutoConfiguration.class })
//@AutoConfigureAfter(name = { "org.springframework.cloud.autoconfigure.RefreshAutoConfiguration", "org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration",
//		"org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration" })
//public class EurekaClientConfiguration {
//
//	private Logger log = LoggerFactory.getLogger(this.getClass());
//
//	private ConfigurableEnvironment env;
//
//	public EurekaClientConfiguration(ConfigurableEnvironment env) {
//		this.env = env;
//	}
//
//	private String getProperty(String property) {
//		return this.env.containsProperty(property) ? this.env.getProperty(property) : "";
//	}
//
//	@Bean
//	@ConditionalOnMissingBean(value = EurekaInstanceConfig.class, search = SearchStrategy.CURRENT)
//	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils, ManagementMetadataProvider managementMetadataProvider) {
//		String hostname = getProperty("eureka.instance.hostname");
//		boolean preferIpAddress = Boolean.parseBoolean(getProperty("eureka.instance.prefer-ip-address"));
//		String ipAddress = getProperty("eureka.instance.ip-address");
//		if (StringUtils.isEmpty(ipAddress)) {
//			try {
//				HttpRespons hr = HttpUtils.sendGet("http://ifconfig.me");
//				ipAddress = hr.content.trim();
//				log.error("初始化Eurekaclient ip-address成功,註冊ipAddress:" + ipAddress);
//			} catch (IOException e) {
//				log.error("初始化Eurekaclient ip-address失敗," + e.getMessage());
//			}
//		}
//		boolean isSecurePortEnabled = Boolean.parseBoolean(getProperty("eureka.instance.secure-port-enabled"));
//		String serverContextPath = env.getProperty("server.servlet.context-path", "/");
//		int serverPort = Integer.valueOf(env.getProperty("server.port", env.getProperty("port", "8080")));
//		Integer managementPort = env.getProperty("management.server.port", Integer.class); // nullable.
//		String managementContextPath = env.getProperty("management.server.servlet.context-path"); // nullable.
//		Integer jmxPort = env.getProperty("com.sun.management.jmxremote.port", Integer.class); // nullable
//		EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils);
//		instance.setNonSecurePort(serverPort);
//		instance.setInstanceId(getDefaultInstanceId(env));
//		instance.setPreferIpAddress(preferIpAddress);
//		instance.setSecurePortEnabled(isSecurePortEnabled);
//		if (StringUtils.hasText(ipAddress))
//			instance.setIpAddress(ipAddress);
//		if (isSecurePortEnabled)
//			instance.setSecurePort(serverPort);
//		if (StringUtils.hasText(hostname))
//			instance.setHostname(hostname);
//		String statusPageUrlPath = getProperty("eureka.instance.status-page-url-path");
//		String healthCheckUrlPath = getProperty("eureka.instance.health-check-url-path");
//		if (StringUtils.hasText(statusPageUrlPath))
//			instance.setStatusPageUrlPath(statusPageUrlPath);
//		if (StringUtils.hasText(healthCheckUrlPath))
//			instance.setHealthCheckUrlPath(healthCheckUrlPath);
//		ManagementMetadata metadata = managementMetadataProvider.get(instance, serverPort, serverContextPath, managementContextPath, managementPort);
//
//		if (metadata != null) {
//			instance.setStatusPageUrl(metadata.getStatusPageUrl());
//			instance.setHealthCheckUrl(metadata.getHealthCheckUrl());
//			if (instance.isSecurePortEnabled()) {
//				instance.setSecureHealthCheckUrl(metadata.getSecureHealthCheckUrl());
//			}
//			Map<String, String> metadataMap = instance.getMetadataMap();
//			metadataMap.computeIfAbsent("management.port", k -> String.valueOf(metadata.getManagementPort()));
//		} else {
//			if (StringUtils.hasText(managementContextPath)) {
//				instance.setHealthCheckUrlPath(managementContextPath + instance.getHealthCheckUrlPath());
//				instance.setStatusPageUrlPath(managementContextPath + instance.getStatusPageUrlPath());
//			}
//		}
//		setupJmxPort(instance, jmxPort);
//		return instance;
//	}
//
//	private void setupJmxPort(EurekaInstanceConfigBean instance, Integer jmxPort) {
//		Map<String, String> metadataMap = instance.getMetadataMap();
//		if (metadataMap.get("jmx.port") == null && jmxPort != null)
//			metadataMap.put("jmx.port", String.valueOf(jmxPort));
//	}
//
//}
