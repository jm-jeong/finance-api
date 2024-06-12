package com.fastcampus.financeapi.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositPreferenceRepository extends JpaRepository<DepositPreference, Long> {
	@Modifying
	@Query("UPDATE DepositPreference d SET d.depositId = :depositId WHERE d.finPrdtCd = :finPrdtCd")
	void updateDepositId(@Param("depositId") String depositId, @Param("finPrdtCd") String finPrdtCd);
}
