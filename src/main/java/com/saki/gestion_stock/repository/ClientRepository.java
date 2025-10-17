package com.saki.gestion_stock.repository;

import com.saki.gestion_stock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
