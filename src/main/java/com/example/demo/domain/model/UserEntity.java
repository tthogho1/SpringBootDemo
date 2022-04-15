package com.example.demo.domain.model;

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
