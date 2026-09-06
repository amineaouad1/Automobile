package com.atelier.repository;

import com.atelier.entity.Mecanicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MecanicienRepository extends JpaRepository<Mecanicien, Long> {
    List<Mecanicien> findByDisponibleTrue();
}