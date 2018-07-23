package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.User;

public interface UserMapper {

	User login(@Param("dzycode") String dzycode, @Param("password") String password);

	int insert(@Param("dzyid") String dzyid, @Param("dzycode") String dzycode);
}
