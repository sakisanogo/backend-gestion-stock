package com.saki.gestion_stock.repository;

import java.util.Optional;

import com.saki.gestion_stock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentesRepository extends JpaRepository<Ventes, Integer> {

    Optional<Ventes> findVentesByCode(String code);
}