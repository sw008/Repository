package com.example.demo.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * 项目启动时，初始化此Bean 此Bean负责装载非Spring Bean的DataSource
 * 
 * 项目关闭时，销毁此bean 此Bean负责关闭非Spring Bean的DataSource
 * 
 * Spring Bean的DataSource会在项目关闭时，自己执行close
 *
 */
@Component
@Qualifier("DataSourceKeyMap")
public class DataSourceKeyMap implements InitializingBean, DisposableBean {
	// 非Bean数据源列表
	private final HashMap<String, DruidDataSource> dataSourceKeyMap = new HashMap<>();

	public HashMap<String, DruidDataSource> getDataSourceKeyMap() {
		return dataSourceKeyMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 装载非Bean数据源（数据源会等待第一次请求连接数据库时打开）
		DruidDataSource druidDataSource;
		for (int i = 0; i < 100; i++) {
			druidDataSource = new DruidDataSource();
			druidDataSource.setUrl(
					"jdbc:mysql://localhost:3306/taotoa?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=true");
			druidDataSource.setUsername("root");
			druidDataSource.setPassword("sw008218");
			druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSourceKeyMap.put("testDS" + i, druidDataSource);
		}

	}

	@Override
	public void destroy() throws Exception {
		// 关闭非Bean数据源
		Iterator<Entry<String, DruidDataSource>> dataSources = dataSourceKeyMap.entrySet().iterator();
		DruidDataSource DataSource;
		while (dataSources.hasNext()) {
			DataSource = dataSources.next().getValue();
			if (!DataSource.isClosed()) {
				DataSource.close();
			}
		}

	}

}
