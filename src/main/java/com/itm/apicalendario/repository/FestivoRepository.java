package com.itm.apicalendario.repository;

import com.itm.apicalendario.model.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {

    Optional<Festivo> findByFecha(LocalDate fecha);

    boolean existsByFechaAndPaisId(LocalDate fecha, Long paisId);
}
