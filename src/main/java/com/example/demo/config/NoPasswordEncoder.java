package com.example.demo.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * パスワードの暗合化をさせないエンコーダーの設定
 *
 */
public class NoPasswordEncoder implements PasswordEncoder {
	@Override
    public String encode(CharSequence charSequence) {
		return charSequence.toString();
	}

	@Override
    public boolean matches(CharSequence charSequence, String s) {
		return charSequence.toString().equals(s);
	}
}