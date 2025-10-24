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

// Service Spring Security responsable de l'authentification des utilisateurs
// Implémente UserDetailsService pour intégrer le système d'authentification personnalisé avec Spring Security
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    // Injection du service utilisateur pour récupérer les informations des utilisateurs
    @Autowired
    private UtilisateurService service;

    // Méthode principale de Spring Security pour charger un utilisateur par son identifiant (email)
    // Cette méthode est appelée automatiquement par Spring Security lors de l'authentification
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Récupération de l'utilisateur depuis la base de données via le service
        UtilisateurDto utilisateur = service.findByEmail(email);

        // Vérification si l'utilisateur existe
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + email);
        }

        // Construction des autorisations (rôles) pour Spring Security
        // Spring Security nécessite que les rôles aient le préfixe "ROLE_"
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (utilisateur.getRoles() != null) {
            utilisateur.getRoles().forEach(role -> {
                String roleName = role.getRoleName();
                // Ajout du préfixe "ROLE_" si absent et conversion en majuscules
                if (!roleName.startsWith("ROLE_")) {
                    roleName = "ROLE_" + roleName.toUpperCase();
                }
                authorities.add(new SimpleGrantedAuthority(roleName));
            });
        }

        // Retourne l'utilisateur étendu avec les informations supplémentaires (idEntreprise)
        // ExtendedUser étend la classe User de Spring Security pour ajouter l'ID d'entreprise
        return new ExtendedUser(
                utilisateur.getEmail(),           // Identifiant (email)
                utilisateur.getMoteDePasse(),     // Mot de passe (sera vérifié par Spring Security)
                utilisateur.getEntreprise() != null ? utilisateur.getEntreprise().getId() : null, // ID entreprise pour le multi-locataire
                authorities                       // Liste des rôles/authorities
        );
    }
}