package com.fastcampus.financeapi.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SavingRepository extends JpaRepository<Saving, String> {

	@Modifying
	@Query("UPDATE Saving d SET d.intrRateShow = :intrRateShow, d.intrRate2Show = :intrRate2Show WHERE d.savingId = :savingId")
	void updateDeposit12(@Param("savingId") String savingId, @Param("intrRateShow") Double intrRateShow, @Param("intrRate2Show") Double intrRate2Show);

	@Modifying
	@Query("UPDATE Saving d SET d.intrRateTypeNmM = :intrRateTypeNmM WHERE d.savingId = :savingId")
	void updateSavingInterateTypeM(@Param("savingId") String savingId, @Param("intrRateTypeNmM") String intrRateTypeNmM);

	@Modifying
	@Query("UPDATE Saving d SET d.intrRateTypeNmS = :intrRateTypeNmS WHERE d.savingId = :savingId")
	void updateSavingInterateTypeS(@Param("savingId") String savingId, @Param("intrRateTypeNmS") String intrRateTypeNmS);

	@Modifying
	@Query("UPDATE Saving d SET d.rsrvTypeNmF = :rsrvTypeNmF WHERE d.savingId = :savingId")
	void updateSavingRsrvTypeNmF(@Param("savingId") String savingId, @Param("rsrvTypeNmF") String rsrvTypeNmF);

	@Modifying
	@Query("UPDATE Saving d SET d.rsrvTypeNmS = :rsrvTypeNmS WHERE d.savingId = :savingId")
	void updateSavingRsrvTypeNmS(@Param("savingId") String savingId, @Param("rsrvTypeNmS") String rsrvTypeNmS);
}
