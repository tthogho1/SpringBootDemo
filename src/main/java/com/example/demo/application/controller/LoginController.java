package com.example.demo.application.controller;

import com.example.demo.application.resource.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @ModelAttribute
    LoginForm loginForm(){
    	return new LoginForm();
    }
  
    /**
     * ログイン画面表示
     * @return getメソッドの時はログイン画面を表示する
     */
    @GetMapping("/login")
    public String loginFrom(){
    	return "login";
    }

    /**
     * 入力チェック
     *
     * @param form
     * @param br
     * @return
     */
    @PostMapping("/login")
    public String index(@Validated @ModelAttribute LoginForm form, BindingResult br) {
        // 入力チェックエラーがある場合は、元の画面にもどる
        if (br.hasErrors()) {
            return "login";
        }
        //20190309入力チェックが通った場合は、SecurityConfigで設定した認証処理にフォワードする
        //20190309Postメソッドでなければいけないのでforwardを使う必要がある
        return "forward:" + "/";
    }


}

