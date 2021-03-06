package com.example.demo.domain.service;

import com.example.demo.domain.dto.UserPassAccount;
import com.example.demo.domain.model.UserPass;
import com.example.demo.domain.repository.UserPassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 *  ユーザのログイン時の情報を検索する
 */
@Service
public class UserPassAccountService implements UserDetailsService {
 
	/**
     * ユーザーパスワードデータテーブル(user_pass)へアクセスするマッパー
     */
	@Autowired
    private UserPassMapper mapper;
	
	/**
     * 指定したユーザー名をもつSpring-Security用のユーザーアカウント情報を取得する
     * @param username ユーザー名
     * @return 指定したユーザー名をもつSpring-Security用のユーザーアカウント情報
     * @throws UsernameNotFoundException 見つからない場合Throw
     */
	@Override
    public UserDetails loadUserByUsername(String username) 
                               throws UsernameNotFoundException {
		if (!StringUtils.hasLength(username)) {
			throw new UsernameNotFoundException("ユーザー名を入力してください");
		}
		//指定したユーザー名をもつUserPassオブジェクトを取得する
		UserPass userPass = mapper.findByName(username);
		if (userPass == null) {
			throw new UsernameNotFoundException("ユーザーが見つかりません");
		}
		//指定したユーザー名をもつSpring-Security用のユーザーアカウント情報を取得する
		return new UserPassAccount(userPass, AuthorityUtils.createAuthorityList("USER"));
	}
}
