package com.example.demo;

import javax.servlet.Filter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.demo.servlet.MyServlet;

@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

	
	//自定义Servlet
	@Bean
	public ServletRegistrationBean myServlet() {
		ServletRegistrationBean myServlet = new ServletRegistrationBean();
		myServlet.addUrlMappings("/servlet");
		myServlet.setServlet(new MyServlet());
		return myServlet;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
