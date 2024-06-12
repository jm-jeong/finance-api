package com.fastcampus.financeapi.db;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepositTest {

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private DepositOptionRepository depositOptionRepository;

	@Test
	@Rollback(false)
	public void testFindById() {
		// Given
		String deposit_id = "202405_0010001_WR0001B";

		// When
		List<DepositOption> distinctByIntrRateType = depositOptionRepository.findDistinctByIntrRateType();
		System.out.println("distinctByIntrRateType = " + distinctByIntrRateType);
		// Then

	}
}