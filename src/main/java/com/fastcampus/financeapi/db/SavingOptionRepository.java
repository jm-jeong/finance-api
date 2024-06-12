package com.fastcampus.financeapi.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SavingOptionRepository extends JpaRepository<SavingOption, Long> {


	List<SavingOption> findBySaveTrmAndIntrRateType(String saveTrm, String intrRateType);

	@Query(value = "select t1.intr_rate, t1.intr_rate2, t1.saving_id, t1.intr_rate_type, t1.intr_rate_type_nm, t1.save_trm, t1.dcls_month, t1.fin_co_no, t1.fin_prdt_cd, t1.id, t1.rsrv_type, t1.rsrv_type_nm "
		+ "from saving_option as t1 "
		+ "left join saving_option as t2 on t1.saving_id = t2.saving_id and t1.intr_rate2 < t2.intr_rate2 "
		+ "where t2.intr_rate2 is null;", nativeQuery = true)
	List<SavingOption> findMaxIntrRate2List();

	@Query(value = "select distinct(t1.intr_rate_type), t1.intr_rate, t1.intr_rate2, t1.saving_id, t1.intr_rate_type_nm, t1.save_trm, t1.dcls_month, t1.fin_co_no, t1.fin_prdt_cd, t1.id, t1.rsrv_type_nm, t1.rsrv_type  "
		+ "from saving_option as t1 ", nativeQuery = true)
	List<SavingOption> findDistinctIntrRateTypeList();

	@Query(value = "select distinct(t1.rsrv_type), t1.intr_rate, t1.intr_rate2, t1.saving_id, t1.intr_rate_type_nm, t1.save_trm, t1.dcls_month, t1.fin_co_no, t1.fin_prdt_cd, t1.id, t1.rsrv_type_nm, t1.intr_rate_type  "
		+ "from saving_option as t1 ", nativeQuery = true)
	List<SavingOption> findDistinctRsrvTypeList();
}
