package com.example.llz.cloud1.jpa;

import com.example.llz.cloud1.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, String> {
}
