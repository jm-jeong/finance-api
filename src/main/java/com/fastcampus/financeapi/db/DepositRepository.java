package com.fastcampus.financeapi.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DepositRepository extends JpaRepository<Deposit, String> {

	@Modifying
	@Query("UPDATE Deposit d SET d.intrRateShow = :intrRateShow, d.intrRate2Show = :intrRate2Show WHERE d.depositId = :depositId")
	void updateDeposit12(@Param("depositId") String depositId, @Param("intrRateShow") Double intrRateShow, @Param("intrRate2Show") Double intrRate2Show);

	@Modifying
	@Query("UPDATE Deposit d SET d.intrRateTypeNmM = :intrRateTypeNmM WHERE d.depositId = :depositId")
	void updateDepositInterateTypeM(@Param("depositId") String depositId, @Param("intrRateTypeNmM") String intrRateTypeNmM);

	@Modifying
	@Query("UPDATE Deposit d SET d.intrRateTypeNmS = :intrRateTypeNmS WHERE d.depositId = :depositId")
	void updateDepositInterateTypeS(@Param("depositId") String depositId, @Param("intrRateTypeNmS") String intrRateTypeNmS);


}
