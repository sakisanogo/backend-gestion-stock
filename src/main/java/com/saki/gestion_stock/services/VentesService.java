package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.VentesDto;
import java.util.List;

public interface VentesService {
    VentesDto save(VentesDto dto);
    VentesDto findById(Integer id);
    VentesDto findByCode(String code);
    List<VentesDto> findAll();
    void delete(Integer id);
}