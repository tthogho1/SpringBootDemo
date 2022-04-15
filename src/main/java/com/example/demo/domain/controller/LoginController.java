package com.example.demo.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Login用のコントローラ
 * 
 */
@Controller
public class LoginController {

	/**
     * ログイン画面表示
     * @return getメソッドの時はログイン画面を表示する
     */
	@GetMapping("/login")
	public String loginFom() {
		return "login";
	}

}

