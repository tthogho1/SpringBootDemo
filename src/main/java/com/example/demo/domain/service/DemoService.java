package com.example.demo.domain.service;

import com.example.demo.domain.dto.DemoForm;
import com.example.demo.domain.model.UserEntity;
import com.example.demo.domain.repository.UserMapper;
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
	 * @param demoForm フォームデータ
	 */
	public void registUer(DemoForm demoForm) {
		
		UserEntity user = new UserEntity();		
		user.setName(demoForm.getLastName() + " " + demoForm.getFirstName());
		user.setBirthday(demoForm.getBirthYear() + demoForm.getBirthMonth() + demoForm.getBirthDay());
		user.setGender(Integer.parseInt(demoForm.getGender()));

		userMapper.insert(user);
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
