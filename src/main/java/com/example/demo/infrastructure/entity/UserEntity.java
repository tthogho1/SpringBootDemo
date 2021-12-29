package com.example.demo.infrastructure.entity;

import lombok.Data;

/**
　*
 *  ユーザ情報を定義します。
 *
 */
@Data
public class UserEntity {
	private String id;
	private String lastName;
	private String firstName;
}
