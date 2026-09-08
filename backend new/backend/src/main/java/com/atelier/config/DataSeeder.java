package com.atelier.config;

import com.atelier.entity.Intervention;
import com.atelier.entity.Mecanicien;
import com.atelier.entity.Vehicule;
import com.atelier.entity.enums.Priorite;
import com.atelier.entity.enums.StatutIntervention;
import com.atelier.entity.enums.TypeIntervention;
import com.atelier.repository.InterventionRepository;
import com.atelier.repository.MecanicienRepository;
import com.atelier.repository.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MecanicienRepository mecanicienRepository;
    private final VehiculeRepository vehiculeRepository;
    private final InterventionRepository interventionRepository;

    public DataSeeder(MecanicienRepository mecanicienRepository,
                      VehiculeRepository vehiculeRepository,
                      InterventionRepository interventionRepository) {
        this.mecanicienRepository = mecanicienRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.interventionRepository = interventionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mecanicienRepository.count() == 0) {

            System.out.println("⏳ Remplissage de la base de données en cours...");

            Mecanicien m1 = new Mecanicien();
            m1.setNom("Hassan"); m1.setSpecialite("Moteur"); m1.setDisponible(true);

            Mecanicien m2 = new Mecanicien();
            m2.setNom("Karim"); m2.setSpecialite("Électricité"); m2.setDisponible(false);

            mecanicienRepository.saveAll(List.of(m1, m2));

            Vehicule v1 = new Vehicule();
            // Mola7ada: Ila bqaa k-y-3tik erreur hna, bdelha l smiya dyal l-setter li 3ndk f entité Vehicule (matalan: setImmatriculation)
            v1.setImmatriculation("1234-A-50");
            v1.setMarque("Renault");
            v1.setModele("Clio 4");
            v1.setAnnee(2018);
            v1.setKilometrage(85000);
            v1.setClientFictif("Ahmed");

            Vehicule v2 = new Vehicule();
            v2.setImmatriculation("9876-B-40");
            v2.setMarque("Peugeot");
            v2.setModele("208");
            v2.setAnnee(2020);
            v2.setKilometrage(45000);
            v2.setClientFictif("Fatima");

            vehiculeRepository.saveAll(List.of(v1, v2));

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime hier = now.minusDays(1);
            LocalDateTime demain = now.plusDays(1);
            LocalDateTime enRetard = now.minusDays(2);

            Intervention i1 = new Intervention();
            i1.setVehicule(v1);
            i1.setType(TypeIntervention.REVISION);
            i1.setDescriptionClient("Vidange et filtres");
            i1.setStatut(StatutIntervention.RECUE);
            i1.setPriorite(Priorite.NORMALE);
            i1.setDateDepot(now);
            i1.setDateRestitutionPrevue(demain);

            Intervention i2 = new Intervention();
            i2.setVehicule(v2);
            i2.setType(TypeIntervention.REPARATION);
            i2.setDescriptionClient("Problème de démarrage");
            i2.setDiagnostic("Batterie morte, à remplacer");
            i2.setCoutEstime(BigDecimal.valueOf(850.0));
            i2.setStatut(StatutIntervention.EN_REPARATION);
            i2.setPriorite(Priorite.HAUTE);
            i2.setDateDepot(hier);
            i2.setDateRestitutionPrevue(demain);
            i2.setMecanicien(m2);

            Intervention i3 = new Intervention();
            i3.setVehicule(v1);
            i3.setType(TypeIntervention.DIAGNOSTIC);
            i3.setDescriptionClient("Bruit bizarre moteur");
            i3.setStatut(StatutIntervention.DIAGNOSTIC_EN_COURS);
            i3.setPriorite(Priorite.HAUTE); // Bdelnaha mn CRITIQUE l HAUTE
            i3.setDateDepot(enRetard);
            i3.setDateRestitutionPrevue(hier);

            Intervention i4 = new Intervention();
            i4.setVehicule(v2);
            i4.setType(TypeIntervention.PNEUMATIQUES);
            i4.setDescriptionClient("Changement de pneus");
            i4.setStatut(StatutIntervention.TERMINEE);
            i4.setPriorite(Priorite.BASSE);
            i4.setCoutEstime(BigDecimal.valueOf(1200.0));
            i4.setDateDepot(hier);
            i4.setDateRestitutionPrevue(now);
            i4.setMecanicien(m1);

            interventionRepository.saveAll(List.of(i1, i2, i3, i4));

            System.out.println("✅ Base de données remplie avec succès !");
        }
    }
}