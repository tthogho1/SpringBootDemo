package com.example.demo.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@Min(value = 1900, message="1900以上を入力してください")
	@Max(value = 2099, message="2099以下を入力してください")
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
	private String gender;

	/** 確認チェック */
	@NotNull(message="確認チェックをおこなってください")
	private String checked;


	/** 性別のMapオブジェクト */
	public Map<String, String> getGenderItems() {
		Map<String, String> genderMap = new LinkedHashMap<String, String>();
		genderMap.put("1", "男");
		genderMap.put("2", "女");
		return genderMap;
	}

}
