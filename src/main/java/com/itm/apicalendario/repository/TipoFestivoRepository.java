package com.itm.apicalendario.repository;

import com.itm.apicalendario.model.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoFestivoRepository extends JpaRepository<TipoFestivo, Long> {
}
