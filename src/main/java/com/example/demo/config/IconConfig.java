package com.example.demo.config;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class IconConfig {
	
	
	// MultipartFile → Base64（エンコード）
    public static String encodeImageFromFile(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }

    // Base64 文字列を受け取り、そのまま返す（デコードは不要）
    public static String decodeImage(String base64Image) {
        return base64Image; // `String` のまま管理
    }
}



