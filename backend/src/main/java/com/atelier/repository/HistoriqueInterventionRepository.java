package com.atelier.repository;

import com.atelier.entity.HistoriqueIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueInterventionRepository extends JpaRepository<HistoriqueIntervention, Long> {
    List<HistoriqueIntervention> findByInterventionIdOrderByDateDesc(Long interventionId);
}
