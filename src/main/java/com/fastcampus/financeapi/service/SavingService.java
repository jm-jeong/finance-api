package com.fastcampus.financeapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.financeapi.db.Deposit;
import com.fastcampus.financeapi.db.DepositOption;
import com.fastcampus.financeapi.db.DepositOptionRepository;
import com.fastcampus.financeapi.db.DepositRepository;
import com.fastcampus.financeapi.db.Saving;
import com.fastcampus.financeapi.db.SavingOption;
import com.fastcampus.financeapi.db.SavingOptionRepository;
import com.fastcampus.financeapi.db.SavingPreferenceRepository;
import com.fastcampus.financeapi.db.SavingRepository;
import com.fastcampus.financeapi.dto.DepositDto;
import com.fastcampus.financeapi.dto.DepositOptionDto;
import com.fastcampus.financeapi.dto.SavingDto;
import com.fastcampus.financeapi.dto.SavingOptionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SavingService {

	private final SavingRepository savingRepository;
	private final SavingOptionRepository savingOptionRepository;
	private final SavingPreferenceRepository savingPreferenceRepository;

	@Transactional
	public void saveSaving(SavingDto savingDto) {
		savingRepository.save(savingDto.toEntity());
	}

	@Transactional
	public void saveSavingOption(SavingOptionDto savingOptionDto) {
		savingOptionRepository.save(savingOptionDto.toEntity());
	}

	@Transactional
	public void find12Interate() {
		List<SavingOption> s12List = savingOptionRepository.findBySaveTrmAndIntrRateType("12", "S");
		for (SavingOption savingOption : s12List) {
			savingRepository.updateDeposit12(savingOption.getSavingId(), savingOption.getIntrRate(), savingOption.getIntrRate2());
		}
		log.info("update 12inter_rate when find12Interate");
	}

	@Transactional
	public void findMaxIntr2() {
		List<SavingOption> maxIntrRate2List = savingOptionRepository.findMaxIntrRate2List();
		for (SavingOption savingOption : maxIntrRate2List) {
			savingRepository.updateDeposit12(savingOption.getSavingId(), savingOption.getIntrRate(), savingOption.getIntrRate2());
		}
		log.info("update 12inter_rate when findMaxIntr2");
	}

	@Transactional
	public void findUpdateInterateType() {
		List<SavingOption> distinctIntrRateTypeList = savingOptionRepository.findDistinctIntrRateTypeList();
		for (SavingOption savingOption : distinctIntrRateTypeList) {
			if (savingOption.getIntrRateType().equals("S")) {
				savingRepository.updateSavingInterateTypeS(savingOption.getSavingId(), savingOption.getIntrRateTypeNm());
			} else if (savingOption.getIntrRateType().equals("M")) {
				savingRepository.updateSavingInterateTypeM(savingOption.getSavingId(),
					savingOption.getIntrRateTypeNm());
			}
		}
	}

	@Transactional
	public void findUpdateRsrvType() {
		List<SavingOption> distinctIntrRateTypeList = savingOptionRepository.findDistinctRsrvTypeList();
		for (SavingOption savingOption : distinctIntrRateTypeList) {
			 if (savingOption.getRsrvType().equals("F")) {
				savingRepository.updateSavingRsrvTypeNmF(savingOption.getSavingId(), savingOption.getRsrvTypeNm());
			} else if (savingOption.getRsrvType().equals("S")) {
				savingRepository.updateSavingRsrvTypeNmS(savingOption.getSavingId(), savingOption.getRsrvTypeNm());
			}
		}
	}

	@Transactional
	public void updateSavingId() {
		List<Saving> allSavingList = savingRepository.findAll();
		for (Saving saving : allSavingList) {
			log.info("fin_prdt_cd {}", saving.getFinPrdtCd());
			if (saving.getFinPrdtCd().equals("202405_0010017_01020400490002")) {
				log.info("update saving_id {}", saving.getSavingId());
			}
			savingPreferenceRepository.updateSavingId(saving.getSavingId(), saving.getFinPrdtCd());
		}
	}

}
