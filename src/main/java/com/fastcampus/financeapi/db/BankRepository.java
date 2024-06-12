package com.fastcampus.financeapi.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, String> {

	Optional<Bank> findByImageName(String imageName);
}
