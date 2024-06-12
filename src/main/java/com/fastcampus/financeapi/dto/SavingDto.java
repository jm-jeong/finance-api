package com.fastcampus.financeapi.dto;

import com.fastcampus.financeapi.db.Saving;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SavingDto {

	private String id;
	private String fin_co_subm_day;
	private String dcls_strt_day;
	private String dcls_end_day;
	private Long max_limit;
	private String etc_note;
	private String join_member;
	private String join_deny;
	private String spcl_cnd;
	private String mtrt_int;
	private String join_way;
	private String fin_prdt_nm;
	private String kor_co_nm;
	private String fin_prdt_cd;
	private String fin_co_no;
	private String dcls_month;
	private String top_fin_grp_no;

	public Saving toEntity() {
		return Saving.builder()
			.savingId(id)
			.finCoNo(fin_co_no)
			.finCoSubmDay(fin_co_subm_day)
			.finPrdtCd(fin_prdt_cd)
			.dclsStrtDay(dcls_strt_day)
			.dclsMonth(dcls_month)
			.dclsEndDay(dcls_end_day)
			.finPrdtNm(fin_prdt_nm)
			.etcNote(etc_note)
			.joinMember(join_member)
			.joinDeny(join_deny)
			.joinWay(join_way)
			.korCoNm(kor_co_nm)
			.maxLimit(max_limit)
			.mtrtInt(mtrt_int)
			.spclCnd(spcl_cnd)
			.topFinGrpNo(top_fin_grp_no)
			.build();
	}
}
