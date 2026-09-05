package com.atelier.repository;

import com.atelier.entity.HistoriqueIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueInterventionRepository extends JpaRepository<HistoriqueIntervention, Long> {
}