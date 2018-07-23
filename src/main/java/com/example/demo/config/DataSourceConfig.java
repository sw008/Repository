package com.example.demo.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.bean.DataSourceKeyMap;
import com.example.demo.servlet.MyServlet;
import com.github.pagehelper.PageInterceptor;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Autowired
	@Qualifier("DataSourceKeyMap")
	DataSourceKeyMap dataSourceKeyMap; // 100个非Bean测试数据源列表

	@Bean("testDS")
	@ConfigurationProperties(prefix = "spring.datasource.test") // application.yml中对应属性的前缀
	public DruidDataSource dataSource1() {
		return new DruidDataSource();
	}

	@Bean("testDS-1")
	@ConfigurationProperties(prefix = "spring.datasource.test1") // application.yml中对应属性的前缀
	public DruidDataSource dataSource2() {
		return new DruidDataSource();
	}

	@Bean("dynamicDataSource")
	public DataSource dynamicDataSource() {
		// 动态数据源
		DynamicDataSourceRouting dynamicRoutingDataSource = new DynamicDataSourceRouting();
		// 指定默认数据源
		dynamicRoutingDataSource.setDefaultTargetDataSource(dataSource1());
		// 指定的数据源列表（数据源会等待第一次请求连接数据库时打开）
		Map<Object,Object> dataSourceMap = new HashMap<>();
		dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
		dataSourceMap.put("testDS", dataSource1());
		dataSourceMap.put("testDS-1", dataSource2());
		// 装载非Bean数据源
		dataSourceMap.putAll(dataSourceKeyMap.getDataSourceKeyMap());
		// 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
		DataSourceContextHolder.setKeySet(dataSourceMap.keySet());

		return dynamicRoutingDataSource;
	}

	/**
	 * 配置 SqlSessionFactoryBean
	 * 
	 * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到 Bean
	 *                          的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond
	 *                          statement 异常
	 * 
	 * @return the sql session factory bean
	 * @throws IOException
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 配置动态数据源，如果没有将 dynamicDataSource 作为数据源则不能实现切换
		sqlSessionFactoryBean.setDataSource(dynamicDataSource());
		sqlSessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapperxml/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("com.example.demo.pojo");

		// pagehelper 插件
		PageInterceptor pInterceptor = new PageInterceptor();
		Properties properties = new Properties();
		properties.setProperty("helperDialect", "mysql");
		properties.setProperty("reasonable", "true");
		pInterceptor.setProperties(properties);

		// 添加插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { pInterceptor });
		return sqlSessionFactoryBean;
	}

	/**
	 * 注入 DataSourceTransactionManager 用于事务管理
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		// 配置动态数据源
		return new DataSourceTransactionManager(dynamicDataSource());
	}

}
