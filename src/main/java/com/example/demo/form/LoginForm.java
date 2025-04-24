package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

//  バリデーション設定フォーム

@Data
public class LoginForm {
//	アカウント名
	@NotEmpty(message = "登録名を入力してください")
	private String user_name;
//	パスワード
	@NotEmpty(message = "パスワードを入力してください")
	private String user_pass;
}
