package com.fastcampus.financeapi.dto;


import com.fastcampus.financeapi.db.SavingOption;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SavingOptionDto {

	private String id;
	private Double intr_rate2;
	private Double intr_rate;
	private String save_trm;
	private String intr_rate_type_nm;
	private String intr_rate_type;
	private String fin_prdt_cd;
	private String fin_co_no;
	private String dcls_month;
	private String rsrv_type;
	private String rsrv_type_nm;


	public SavingOption toEntity(){
		return SavingOption.builder()
			.savingId(id)
			.rsrvType(rsrv_type)
			.rsrvTypeNm(rsrv_type_nm)
			.intrRateTypeNm(intr_rate_type_nm)
			.intrRateType(intr_rate_type)
			.intrRate(intr_rate)
			.intrRate2(intr_rate2)
			.saveTrm(save_trm)
			.dclsMonth(dcls_month)
			.finPrdtCd(fin_prdt_cd)
			.finCoNo(fin_co_no)
			.build();
	}
}
