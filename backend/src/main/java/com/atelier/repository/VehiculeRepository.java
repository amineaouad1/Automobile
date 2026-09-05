package com.atelier.repository;

import com.atelier.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    boolean existsByImmatriculationFictive(String immatriculationFictive);
}