package com.example.demo.config;

import com.example.demo.domain.service.UserPassAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring-Securityの設定を行うクラス
 *
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
     * Spring-Security用のユーザーアカウント情報を
     * 取得・設定するサービスへのアクセス
     */
	@Autowired
    private UserPassAccountService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 主に全体に対するセキュリティ設定を行う
		// web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/sessionTimeout", "/css/**", "/js/**").permitAll() // 複数パス指定可
				.anyRequest()
				.authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(new DemoLoginUrlAuthenticationEntryPoint("/login"))
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/input")
				.permitAll()
				.and()
				.sessionManagement()
				.maximumSessions(1); // 1セッションしか動作しない。1セッションで複数ブラウザからのログイン操作ができなくなる。
		//		.expiredSessionStrategy(new DemoSessionInformationExpiredStrategy());
		// .expiredUrl("/sessionExpired.html"); ここでURLを指定しても、セッションタイムアウト時には、/loginに遷移
		// ログイン認証のほうが優先されている
	}

	/**
     * 認証するユーザー情報をデータベースからロードする処理
     * 
     * @param auth 認証マネージャ
     * @throws Exception Exceptionがあればスロー
     */
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//認証するユーザー情報をデータベースからロードする
		//その際、パスワードはEncoderで暗号化した値を利用する
		log.info("set authentication");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
 	
	/**
     * パスワードを暗号化するクラス 通常BCrypt 
     * @return パスワードを暗号化するクラスオブジェクト（ここでは、暗合化しないようにしている）
     */
	@Bean
    PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder();
		return new NoPasswordEncoder();
	}
}