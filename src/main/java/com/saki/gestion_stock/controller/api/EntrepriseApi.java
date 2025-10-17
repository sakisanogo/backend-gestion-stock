package com.saki.gestion_stock.controller.api;

import com.saki.gestion_stock.dto.EntrepriseDto;

import io.swagger.annotations.Api;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import static com.saki.gestion_stock.utils.Constants.ENTREPRISE_ENDPOINT;

@CrossOrigin(origins = "http://localhost:4200")
@Api("entreprises")
public interface EntrepriseApi {

    @PostMapping(ENTREPRISE_ENDPOINT + "/create")
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(ENTREPRISE_ENDPOINT + "/{idEntreprise}")
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(ENTREPRISE_ENDPOINT + "/all")
    List<EntrepriseDto> findAll();

    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}")
    void delete(@PathVariable("idEntreprise") Integer id);

}