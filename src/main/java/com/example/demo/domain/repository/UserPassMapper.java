package com.example.demo.domain.repository;

import com.example.demo.infrastructure.entity.UserPass;
import org.apache.ibatis.annotations.Mapper;

/**
 *  ユーザのログイン情報を取得する
 */
@Mapper
public interface UserPassMapper {
 
	/**
     * ユーザーパスワードデータテーブル(user_pass)からユーザー名をキーにデータを取得する
     * @param name ユーザー名
     * @return ユーザー名をもつデータ
     */
	UserPass findByName(String name);

}
