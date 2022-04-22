package com.example.demo.domain.repository;

import com.example.demo.domain.model.UserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 *  ユーザ情報をDBに登録、DBから抽出
 */
@Mapper
public interface UserMapper {
	void insert(UserEntity user);

	List<UserEntity> selectAll();
}
