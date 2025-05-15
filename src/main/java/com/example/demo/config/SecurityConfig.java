package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.LoginCustom;

/*
 * ログイン成功・失敗とログアウト時の処理。 
 * 作成者 北川
 */
 

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private LoginCustom loginCustom;
	
	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.authenticationProvider(loginCustom)
		//HTTPリクエストに対するセキュリティ設定
		.authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
		//静的リソースへのアクセスは認証不要/*静的リソースをセキュリティ対象から除外する設定				
		//「/login」と「/register」へのアクセスは認証を必要としない(変更byナカムラ)
//		.requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**", "/webjars/**",
//                "/matching/form", "/matching/search","/matching/join").permitAll()	
		//その他のリクエストは認証が必要
//		.anyRequest().authenticated())
		//フォームベースのログイン設定
		.formLogin(form -> form
		//カスタムログインページのURLを指定
		.loginPage("/login")
		//ログイン処理のURLを指定 ↓難しいロジックのやつ
		.loginProcessingUrl("/authentication")
		//★アカウント名のname属性を指定
		.usernameParameter("user_name")
		//★パスワードのname属性を指定
		.passwordParameter("user_pass")
		//★ログイン成功時のリダイレクト先を指定 
		//.defaultSuccessUrl("/")
		.defaultSuccessUrl("/success",true)
		//★ログイン失敗時のリダイレクト先を指定
		.failureUrl("/login?error"))
		//★ログアウト設定
		.logout(logout -> logout
		//★ログアウトを処理するURLを指定
		.logoutUrl("/logout")
		//★ログアウト成功時のリダイレクト先を指定("/home")とか
		.logoutSuccessUrl("/login?logout")
		//ログアウト時にセッションを無効にする
		.invalidateHttpSession(true)
		//ログアウト時にCookieを削除する
		.deleteCookies("JSESSIONID")
		
		);
		return http.build();
	}
	
}
