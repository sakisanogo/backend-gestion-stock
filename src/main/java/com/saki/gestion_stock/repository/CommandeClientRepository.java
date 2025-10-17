package com.saki.gestion_stock.repository;

import java.util.List;
import java.util.Optional;

import com.saki.gestion_stock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {

    Optional<CommandeClient> findCommandeClientByCode(String code);

    List<CommandeClient> findAllByClientId(Integer id);
}