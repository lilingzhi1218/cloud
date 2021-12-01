package com.example.llz.cloud1.dao;

import com.example.llz.cloud1.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<City, String> {
}
