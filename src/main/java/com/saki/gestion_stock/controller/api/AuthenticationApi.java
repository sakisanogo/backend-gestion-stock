package com.saki.gestion_stock.controller.api;

import static com.saki.gestion_stock.utils.Constants.AUTHENTICATION_ENDPOINT;

import com.saki.gestion_stock.dto.auth.AuthenticationRequest;
import com.saki.gestion_stock.dto.auth.AuthenticationResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("authentication")
public interface AuthenticationApi {

    @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}