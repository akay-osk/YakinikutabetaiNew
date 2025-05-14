package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/*
 * バリデーション 
 * 作成者 北川
 */

@Data
public class LoginForm {
	
	//パスワード
	@NotBlank(message = "パスワードは必須です")
	@Size(min = 6, message = "パスワードは6文字以上で入力してください")
	private String user_pass;
	
	//ユーザー名
	@NotBlank(message = "ユーザーネームは必須です")
	@Size(max = 20, message = "名前は20文字以内で入力してください")
	private String user_name;
	
}
