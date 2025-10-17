package com.saki.gestion_stock.services.auth;

import java.util.ArrayList;
import java.util.List;

import com.saki.gestion_stock.dto.UtilisateurDto;
import com.saki.gestion_stock.model.auth.ExtendedUser;
import com.saki.gestion_stock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Récupération de l'utilisateur depuis la base
        UtilisateurDto utilisateur = service.findByEmail(email);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + email);
        }

        // Construction des rôles avec le préfixe ROLE_
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (utilisateur.getRoles() != null) {
            utilisateur.getRoles().forEach(role -> {
                String roleName = role.getRoleName();
                if (!roleName.startsWith("ROLE_")) {
                    roleName = "ROLE_" + roleName.toUpperCase();
                }
                authorities.add(new SimpleGrantedAuthority(roleName));
            });
        }

        // Retourne l'utilisateur étendu pour Spring Security
        return new ExtendedUser(
                utilisateur.getEmail(),
                utilisateur.getMoteDePasse(),
                utilisateur.getEntreprise() != null ? utilisateur.getEntreprise().getId() : null,
                authorities
        );
    }
}
