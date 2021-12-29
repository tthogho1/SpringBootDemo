package com.example.demo.domain.repository;

import com.example.demo.infrastructure.entity.UserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 *  ユーザ情報をDBに登録、DBから抽出
 */
@Mapper
public interface UserMapper {
	void insert(String firstName, String lastName);

	List<UserEntity> selectAll();
}
