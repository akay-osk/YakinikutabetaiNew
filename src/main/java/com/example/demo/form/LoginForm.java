package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginForm {
//	アカウント名
	@NotEmpty(message = "アカウント名を入力してください")
	private String accountnameInput;
//	パスワード
	@NotEmpty(message = "パスワードを入力してください")
	private String passwordInput;
}
