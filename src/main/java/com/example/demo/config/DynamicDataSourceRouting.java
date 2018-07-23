package com.example.demo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSourceRouting extends AbstractRoutingDataSource {
	// 线程多数据源路由
	@Override
	protected Object determineCurrentLookupKey() {
		//继承spring AbstractRoutingDataSource
		//其根据本方法返回值 作为key 切换为其装载的数据源MAP
		return DataSourceContextHolder.getDbType();
	}

}
