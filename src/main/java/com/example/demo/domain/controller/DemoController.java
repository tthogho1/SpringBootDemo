package com.example.demo.domain.controller;

import com.example.demo.domain.dto.DemoForm;
import com.example.demo.domain.model.UserEntity;
import com.example.demo.domain.service.DemoService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * コントローラクラス 「@SessionAttributes(types = DemoForm.class)」により、
 * 同一コントローラ内で、生成したFormオブジェクトをセッションにもたせている 
 */
@Controller
@Slf4j
@SessionAttributes(types = DemoForm.class)
public class DemoController {

	@Autowired
	private DemoService demoService;

	/**
	 * Formオブジェクトを初期化して返却する ModelAttributeアノテーションが
	 * ついている場合、コントローラ呼び出し時に呼び出され
	 * 戻り値をModelにセットする。
	 * 
	 * @request HttpServletRequest セッション情報を出力するただけ
	 * @return Formオブジェクト
	 */
	@ModelAttribute("demoForm")
	public DemoForm createDemoForm(HttpServletRequest request) {

		log.info(request.getSession(false).getId() + " initialize DemoForm");
		DemoForm demoForm = new DemoForm();
		//
		// 名前・性別の初期値を設定する
		//
		demoForm.setLastName("");
		demoForm.setFirstName("");
		demoForm.setGender("1");

		return demoForm;
	}

	/**
	 * 入力画面に遷移する
	 *
	 * @param demoForm Formオブジェクト
	 * @return 入力画面へのパス
	 */
	@GetMapping("/input")
	public String index(DemoForm demoForm) {
		log.info("call index");

		return "input";
	}

	/**
	 * 確認画面に遷移する フォーム入力チェックをバリデータで実行している
	 * 入力データはFormオブジェクトで取得している。
	 * 
	 * @param demoForm Formオブジェクト
	 * @return 確認画面へのパス
	 */
	@PostMapping("/confirm")
	public String confirm(@Valid DemoForm demoForm, BindingResult bindingResult) {

		log.info("call confirm");

		if (bindingResult.hasErrors()) {
			return "input";
		}

		return "confirm";
	}

	/**
	 * 完了画面へのリダイレクトパスに遷移する
	 * 
	 * @param demoForm フォームオブジェクト(セッションから？)
	 * @return 完了画面へのリダイレクトパス
	 */
	@RequestMapping(value="send", params = "send", method = RequestMethod.POST)
	public String send(DemoForm demoForm) {
		log.info("call send");

		demoService.registUer(demoForm);
		return "redirect:/complete";
	}
	
	/**
	 * 初期画面へリダイレクトで戻る
	 *  
	 * @return 初期画面へのリダイレクトパス
	 */
	@RequestMapping(value="send", params = "back", method = RequestMethod.POST)
	public String back() {
		log.info("call back");
		
		return "redirect:/input";
	}
	
	/**
	 * 完了画面に遷移する
	 * 
	 * @param model         Modelクラス
	 * @param sessionStatus セッションステータス
	 * @return 完了画面
	 */
	@GetMapping("/complete")
	public String complete(SessionStatus sessionStatus, Model model) {
		log.info("call complete");

		List<UserEntity> users = demoService.selectUers();

		model.addAttribute("users", users);

		// セッションオブジェクトを破棄
		sessionStatus.setComplete();
		return "complete";
	}
}