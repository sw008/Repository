package com.example.demo.service;

import com.example.demo.pojo.User;

public interface UserService {

	 User sqlSelectlogin(String dzycode,String password)  throws Exception  ;
	 
	 int insert(String dzyid,String dzycode)  throws Exception  ;
}
