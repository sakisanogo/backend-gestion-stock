package com.saki.gestion_stock.model.auth;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

// Classe étendue de User de Spring Security pour ajouter des informations métier spécifiques
// Cette classe permet d'enrichir l'utilisateur Spring Security avec l'ID de l'entreprise
public class ExtendedUser extends User {

    // ID de l'entreprise associée à l'utilisateur
    // Lombok génère automatiquement les getters et setters
    @Getter
    @Setter
    private Integer idEntreprise;

    // Constructeur sans ID entreprise (pour rétrocompatibilité)
    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        // Appel du constructeur parent de Spring Security
        super(username, password, authorities);
    }

    // Constructeur complet avec ID entreprise
    public ExtendedUser(String username, String password, Integer idEntreprise,
                        Collection<? extends GrantedAuthority> authorities) {
        // Appel du constructeur parent de Spring Security
        super(username, password, authorities);
        // Initialisation de l'ID entreprise spécifique à notre application
        this.idEntreprise = idEntreprise;
    }
}