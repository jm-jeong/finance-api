package com.fastcampus.financeapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.financeapi.db.Deposit;
import com.fastcampus.financeapi.db.DepositOption;
import com.fastcampus.financeapi.db.DepositOptionRepository;
import com.fastcampus.financeapi.db.DepositPreferenceRepository;
import com.fastcampus.financeapi.db.DepositRepository;
import com.fastcampus.financeapi.dto.DepositDto;
import com.fastcampus.financeapi.dto.DepositOptionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepositService {

	private final DepositRepository depositRepository;
	private final DepositOptionRepository depositOptionRepository;
	private final DepositPreferenceRepository depositPreferenceRepository;


	@Transactional
	public void saveDeposit(DepositDto depositDto) {
		depositRepository.save(depositDto.toEntity());
	}
	@Transactional
	public void saveDepositOption(DepositOptionDto depositOptionDto) {
		depositOptionRepository.save(depositOptionDto.toEntity());
	}

	@Transactional
	public void find12Interate() {
		List<DepositOption> s12List = depositOptionRepository.findBySaveTrmAndIntrRateType("12", "S");
		for (DepositOption depositOption : s12List) {
			depositRepository.updateDeposit12(depositOption.getDepositId(), depositOption.getIntrRate(), depositOption.getIntrRate2());
		}
		log.info("update 12inter_rate when find12Interate");
	}

	@Transactional
	public void findMaxIntr2() {
		List<DepositOption> maxIntrRate2List = depositOptionRepository.findMaxIntrRate2List();
		for (DepositOption depositOption : maxIntrRate2List) {
			depositRepository.updateDeposit12(depositOption.getDepositId(), depositOption.getIntrRate(), depositOption.getIntrRate2());
		}
		log.info("update 12inter_rate when findMaxIntr2");
	}

	@Transactional
	public void findUpdateInterateType() {
		List<DepositOption> distinctIntrRateTypeList = depositOptionRepository.findDistinctIntrRateTypeList();
		for (DepositOption depositOption : distinctIntrRateTypeList) {
			if (depositOption.getIntrRateType().equals("S")) {
				depositRepository.updateDepositInterateTypeS(depositOption.getDepositId(), depositOption.getIntrRateTypeNm());
			} else if (depositOption.getIntrRateType().equals("M")) {
				depositRepository.updateDepositInterateTypeM(depositOption.getDepositId(), depositOption.getIntrRateTypeNm());
			}

		}
	}

	@Transactional
	public void updateDepositId() {
		List<Deposit> allDepositList = depositRepository.findAll();
		for (Deposit deposit : allDepositList) {
			depositPreferenceRepository.updateDepositId(deposit.getDepositId(), deposit.getFinPrdtCd());
		}
	}
}
