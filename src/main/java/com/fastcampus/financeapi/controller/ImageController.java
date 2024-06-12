package com.fastcampus.financeapi.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fastcampus.financeapi.db.Bank;
import com.fastcampus.financeapi.dto.ProfileResponse;
import com.fastcampus.financeapi.service.BankService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {
	private final BankService bankService;

	@GetMapping("/bank")
	public ResponseEntity<byte[]> getBoard(@RequestParam String imageName) throws IOException {
		Resource imgFile = new ClassPathResource("static/images/" + imageName + ".png");
		byte[] bytes = Files.readAllBytes(imgFile.getFile().toPath());

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

	}

	@GetMapping("/profile/{username}")
	public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username) throws IOException {
		// 임의의 이메일 정보
		String email = username + "@example.com";

		// 이미지 파일 경로 설정
		String imagePath = "static/images/" + username + ".png";
		Resource imgFile = new ClassPathResource(imagePath);

		if (!imgFile.exists()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		byte[] bytes = Files.readAllBytes(imgFile.getFile().toPath());
		String imageBase64 = Base64.getEncoder().encodeToString(bytes);
		ProfileResponse response = new ProfileResponse(username, email, imageBase64);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
