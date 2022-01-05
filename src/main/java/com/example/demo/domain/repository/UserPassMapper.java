package com.example.demo.domain.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.infrastructure.entity.UserPass;

@Mapper
public interface UserPassMapper {
 
    /**
     * ユーザーパスワードデータテーブル(user_pass)からユーザー名をキーにデータを取得する
     * @param name ユーザー名
     * @return ユーザー名をもつデータ
     */
    UserPass findByName(String name);
 

}
