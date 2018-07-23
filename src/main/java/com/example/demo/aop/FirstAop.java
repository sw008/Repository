package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FirstAop {

	//@Pointcut("execution(* com.example.demo.web.HomeController.dbChange(..)) ")
	public void a() {};
	
	@Pointcut("execution(* com.example.demo.web.HomeController.login(..)) ")
	public void b() {};
	
	
	@Before("b()")
	public void task(JoinPoint joinPoint) {
		Object[] objects= joinPoint.getArgs();
		//objects[0]="@Aspect change";
		System.out.println("@Aspect参数1捕获:"+objects[0]);
		
		
	};
	
	
	
}
