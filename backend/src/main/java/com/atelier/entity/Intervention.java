package com.atelier.entity;

import com.atelier.entity.enums.Priorite;
import com.atelier.entity.enums.StatutIntervention;
import com.atelier.entity.enums.TypeIntervention;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "intervention")
@Getter
@Setter
@NoArgsConstructor
public class Intervention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mecanicien_id")
    private Mecanicien mecanicien;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "createur_id", nullable = false)
    private Utilisateur createur;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "type_intervention")
    private TypeIntervention type;

    @Column(name = "description_client", columnDefinition = "TEXT")
    private String descriptionClient;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "statut_intervention")
    private StatutIntervention statut = StatutIntervention.RECUE;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "priorite")
    private Priorite priorite = Priorite.NORMALE;

    @Column(name = "cout_estime", precision = 10, scale = 2)
    private BigDecimal coutEstime;

    @Column(name = "date_depot", nullable = false)
    private LocalDateTime dateDepot = LocalDateTime.now();

    @Column(name = "date_restitution_prevue")
    private LocalDateTime dateRestitutionPrevue;

    @Column(name = "date_cloture")
    private LocalDateTime dateCloture;
}