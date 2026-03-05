package com.itm.apicalendario.repository;

import com.itm.apicalendario.model.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    List<Calendario> findByEsLaboral(Boolean esLaboral);

    List<Calendario> findByPaisIdAndFechaBetween(Long paisId, LocalDate inicio, LocalDate fin);
}
