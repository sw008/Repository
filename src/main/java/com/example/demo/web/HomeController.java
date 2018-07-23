package com.example.demo.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.config.DataSourceContextHolder;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

@RestController
public class HomeController {
	@Autowired
	UserService userService;

	@GetMapping("/")
	public ModelAndView home(ModelAndView mv) {

		System.out.println("home");
		mv.setViewName("homePage");
		return mv;
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam("dzycode") String dzycode, @RequestParam("password") String password,
			ModelAndView mv) throws Exception {

		System.out.println(dzycode + " " + password);
		User user = userService.sqlSelectlogin(dzycode, password);
		System.out.println(user);
		mv.setViewName("main");
		return mv;
	}

	@GetMapping("/login") // http://localhost:8080/login?dzycode=72-092&password=123
	public User loginGet(@RequestParam("dzycode") String dzycode, @RequestParam("password") String password)
			throws Exception {
		return userService.sqlSelectlogin(dzycode, password);
	}

	@GetMapping("/dbchange/{dbkey}/{dzycode}")
	public User dbChange(@PathVariable("dbkey") String dbkey, @PathVariable("dzycode") String dzycode)
			throws Exception {
		// 测试切换数据库的查询，http://localhost:8080/dbchange/testDS/72-092
		DataSourceContextHolder.setDbType(dbkey); // 可以手动切换/AOP切换
		//Thread.sleep(5000);// 让当前线程睡眠，可测试让另一个请求切换数据源，验证两次请求结果的正确性
		return userService.sqlSelectlogin(dzycode, "123");
	}

	@GetMapping("/insert/{dbkey}/{dzycode}/{dzyid}")
	public int insert(@PathVariable("dbkey") String dbkey, @PathVariable("dzycode") String dzycode,
			@PathVariable("dzyid") String dzyid) throws Exception {
		// 测试切换数据库的事务，http://localhost:8080/insert/testDS11/72-099/DZY00000005
		DataSourceContextHolder.setDbType(dbkey); // 可以手动切换/AOP切换
		int i = userService.insert(dzyid, dzycode);
		// dzycode > 80-000 UserServiceImpl抛出异常 测试动态数据源 事务
		return i;
	}

}
