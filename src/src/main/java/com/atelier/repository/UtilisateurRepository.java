package com.atelier.repository;

import com.atelier.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    // Hada howa s-ster s-s7ri li k-y-khli Spring Security y-lqa l-Client b l-Email dyalo
    Optional<Utilisateur> findByEmail(String email);

}