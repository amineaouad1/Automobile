package com.atelier.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "immatriculation_fictive", nullable = false, unique = true, length = 20)
    private String immatriculationFictive;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;

    @Column(nullable = false)
    private int annee;

    @Column(nullable = false)
    private int kilometrage;

    @Column(name = "client_fictif", nullable = false, length = 100)
    private String clientFictif;
}