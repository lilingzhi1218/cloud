package com.example.llz.cloud1.jpa;

import org.springframework.stereotype.Service;

@Service
class AmericaDao implements CountryDao {

	@Override
	public void findCountry() {
		System.out.println("America");
	}
}