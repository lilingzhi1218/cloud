package com.example.llz.cloudbiz1.dao;

import com.example.llz.cloudbiz1.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<City, String> {
}
