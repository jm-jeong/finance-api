package com.fastcampus.financeapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fastcampus.financeapi.db.Bank;
import com.fastcampus.financeapi.db.BankRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankService {
	private final BankRepository bankRepository;

	public Optional<Bank> findBank(String imageName) {
		return bankRepository.findByImageName(imageName);
	}
}
