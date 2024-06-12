package com.fastcampus.financeapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.financeapi.db.DepositRepository;
import com.fastcampus.financeapi.service.DepositService;
import com.fastcampus.financeapi.service.SavingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class DataController {
	private final DepositService depositService;
	private final SavingService savingService;

	@GetMapping("/deposit/interate")
	public String depositInterate() {

		depositService.findMaxIntr2();
		depositService.find12Interate();

		return "deposit interate success";
	}

	@GetMapping("/saving/interate")
	public String savingInterate() {

		savingService.findMaxIntr2();
		savingService.find12Interate();

		return "deposit interate success";
	}

	@GetMapping("/deposit/interatetype")
	public String depositInteratetype() {
		depositService.findUpdateInterateType();
		return "deposit interatetype success";
	}

	@GetMapping("/saving/interatetype")
	public String savingInteratetype() {
		savingService.findUpdateInterateType();
		savingService.findUpdateRsrvType();
		return "deposit interatetype success";
	}

	@GetMapping("/deposit/update/depositId")
	public String depositUpdateDepositId() {
		depositService.updateDepositId();
		return "deposit update depositId success";
	}

	@GetMapping("/saving/update/savingId")
	public String depositUpdateSavingId() {
		savingService.updateSavingId();
		return "saving update savingId success";
	}
}
