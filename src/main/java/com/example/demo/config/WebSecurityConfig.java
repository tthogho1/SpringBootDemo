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
import org.springframework.security.web.AuthenticationEntryPoint;

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
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/actuator/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/sessionTimeout", "/css/**", "/js/**").permitAll() 
				.anyRequest()
				.authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/input", true)
				//.defaultSuccessUrl("/input", false) // trueの場合必ず設定された遷移先、falseの場合は、ログイン画面繊維前に指定したURL
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login")
				.and()
				.sessionManagement()
				.maximumSessions(1); // 1セッションしか動作しない。1セッションで複数ブラウザからのログイン操作ができなくなる。
		//		.expiredSessionStrategy(new DemoSessionInformationExpiredStrategy());
		// .expiredUrl("/sessionExpired.html"); ここでURLを指定しても、セッションタイムアウト時には、/loginに遷移
		// ログイン認証のほうが優先されているため
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
 	
	
	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new DemoLoginUrlAuthenticationEntryPoint("/login");
		//return new SessionExpiredAuthenticationEntryPoint("/login");		
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