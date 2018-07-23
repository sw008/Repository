package com.example.demo.config;

import java.util.Set;

public class DataSourceContextHolder {
	// 线程数据源上下文选择器
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	private static Set keySet;

	public static void setDbType(String dbType) {
		// 线程可以通过此方法 在执行sql前 设置其要使用的数据源KEY，DynamicDataSourceRouting切换数据源
		// 数据源KEY不在列表中则不切换，使用线程上次的数据源KEY，若线程从未成功设置key则使用DynamicDataSourceRouting默认数据源
		if (keySet.contains(dbType)) {
			contextHolder.set(dbType);
		}

	}

	public static String getDbType() {
		return ((String) contextHolder.get());
	}

	public static void clearDbType() {
		contextHolder.remove();
	}

	public static void setKeySet(Set keySet) {
		DataSourceContextHolder.keySet = keySet;
	}

}
