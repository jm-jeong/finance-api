package com.fastcampus.financeapi.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositOptionRepository extends JpaRepository<DepositOption, Long> {

	List<DepositOption> findBySaveTrmAndIntrRateType(String SaveTrm, String IntrRateType);

	// @Query("select t1.intr_rate, t1.intr_rate2, t1.deposit_id"
	// 	+ "from deposit_option as t1\n"
	// 	+ "         left join deposit_option as t2 on t1.deposit_id = t2.deposit_id and t1.intr_rate2 < t2.intr_rate2\n"
	// 	+ "where t2.intr_rate2 is null;")
	//
	//
	@Query(value = "select t1.intr_rate, t1.intr_rate2, t1.deposit_id, t1.intr_rate_type, t1.intr_rate_type_nm, t1.save_trm, t1.dcls_month, t1.fin_co_no, t1.fin_prdt_cd, t1.id  "
		+ "from deposit_option as t1 "
		+ "left join deposit_option as t2 on t1.deposit_id = t2.deposit_id and t1.intr_rate2 < t2.intr_rate2 "
		+ "where t2.intr_rate2 is null;", nativeQuery = true)
	List<DepositOption> findMaxIntrRate2List();

	@Query(value = "select distinct(t1.intr_rate_type), t1.intr_rate, t1.intr_rate2, t1.deposit_id, t1.intr_rate_type_nm, t1.save_trm, t1.dcls_month, t1.fin_co_no, t1.fin_prdt_cd, t1.id  "
		+ "from deposit_option as t1 ", nativeQuery = true)
	List<DepositOption> findDistinctIntrRateTypeList();
}

