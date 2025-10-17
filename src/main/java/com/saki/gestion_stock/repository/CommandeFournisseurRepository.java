package com.saki.gestion_stock.repository;

import java.util.List;
import java.util.Optional;

import com.saki.gestion_stock.model.CommandeClient;
import com.saki.gestion_stock.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    List<CommandeClient> findAllByFournisseurId(Integer id);

    boolean existsByFournisseurId(Integer id);
}
