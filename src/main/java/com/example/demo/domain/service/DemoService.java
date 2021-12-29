package com.example.demo.domain.service;

import com.example.demo.domain.repository.UserMapper;
import com.example.demo.infrastructure.entity.UserEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Controllerから呼び出されるサービスを定義します。
 *
 */
@Service
public class DemoService {
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * ユーザオブジェクトを作成する
	 *                     
	 * @param lastName 　姓
	 * @param firstName 名
	 */
	public void registUer(String lastName, String firstName) {

		userMapper.insert(lastName, firstName);
	}
	
	/**
	 * ユーザオブジェクトを作成する
	 *                     
	 * @return Userリスト
	 */
	public List<UserEntity> selectUers() {
		
		List<UserEntity> users;

		users = userMapper.selectAll();
		return users;
	}
	
}
