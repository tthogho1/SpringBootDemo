package com.example.demo.application.resource;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * DemoコントローラのFormオブジェクトのクラス
 */
@Data
public class DemoForm {

	/* 
	 *  バリデーションのエラーメッセージはメッセージファイルを使用して定義することも可能
	 *  
	 */
	
	/** 名前 */
	@NotNull(message="名前を入力してください")
	@NotEmpty(message="名前を入力してください")
	private String firstName;
	
	/** 姓 */
	@NotNull(message="姓名を入力してください")
	@NotEmpty(message="姓名を入力してください")
	private String lastName;

	/** 生年月日_年  */
	@NotNull(message="年を入力してください")
	@NotEmpty(message="年を入力してください")
	private String birthYear;

	/** 生年月日_月 */
	@NotNull(message="月を入力してください")
	@NotEmpty(message="月を入力してください")
	private String birthMonth;

	/** 生年月日_日 */
	@NotNull(message="日を入力してください")
	@NotEmpty(message="日を入力してください")
	private String birthDay;

	/** 性別 */
	@NotNull
	private String sex;

	/** 確認チェック */
	@NotNull(message="確認チェックをおこなってください")
	private String checked;

	/** 生年月日_月のMapオブジェクト */
	public Map<String, String> getMonthItems() {
		Map<String, String> monthMap = new LinkedHashMap<String, String>();
		for (int i = 1; i <= 12; i++) {
			monthMap.put(String.valueOf(i), String.valueOf(i));
		}
		return monthMap;
	}

	/** 生年月日_日のMapオブジェクト */
	public Map<String, String> getDayItems() {
		Map<String, String> dayMap = new LinkedHashMap<String, String>();
		for (int i = 1; i <= 31; i++) {
			dayMap.put(String.valueOf(i), String.valueOf(i));
		}
		return dayMap;
	}

	/** 性別のMapオブジェクト */
	public Map<String, String> getSexItems() {
		Map<String, String> sexMap = new LinkedHashMap<String, String>();
		sexMap.put("1", "男");
		sexMap.put("2", "女");
		return sexMap;
	}

}
