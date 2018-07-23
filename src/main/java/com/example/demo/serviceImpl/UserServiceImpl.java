package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;


@Service("UserService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper userMapper;


	public User sqlSelectlogin(String dzycode, String password) throws Exception {

		User user=null;
		user=userMapper.login(dzycode, password);
		//System.out.println(user);
	
		return user;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int insert(String dzyid,String dzycode) throws Exception {
		 int i= userMapper.insert(dzyid,dzycode);
		 if (dzycode.compareTo("80-000")>0) {
			//测试切换数据库的事务
			 throw new RuntimeException("dzycode过大");
		 }
				
		 
		 return i;
	}
}
