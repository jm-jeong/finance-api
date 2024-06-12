package com.fastcampus.financeapi.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SavingPreferenceRepository extends JpaRepository<SavingPreference, Long> {

	@Modifying
	@Query("UPDATE SavingPreference d SET d.savingId = :savingId WHERE d.finPrdtCd = :finPrdtCd")
	void updateSavingId(@Param("savingId") String savingId, @Param("finPrdtCd") String finPrdtCd);

}
