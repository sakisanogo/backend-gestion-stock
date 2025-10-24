package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service de contexte pour le pattern Strategy - fait le pont entre les contrôleurs et les stratégies
// Implémente le pattern Context dans le pattern Strategy
@Service
public class StrategyPhotoContext {

    private BeanFactory beanFactory;  // Factory Spring pour récupérer les beans par nom
    private Strategy strategy;        // Stratégie courante déterminée dynamiquement
    @Setter
    private String context;           // Contexte courant (optionnel - peut être défini explicitement)

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    // Méthode principale pour sauvegarder une photo selon le contexte
    public Object savePhoto(String context, Integer id, InputStream photo, String title) throws FlickrException {
        determinContext(context);  // Détermine la stratégie à utiliser
        return strategy.savePhoto(id, photo, title);  // Délègue à la stratégie
    }

    // Détermine et initialise la stratégie appropriée selon le contexte
    private void determinContext(String context) {
        final String beanName = context + "Strategy";  // Construction du nom du bean Spring

        switch (context) {
            case "article":
                // Récupère la stratégie pour les articles
                strategy = beanFactory.getBean(beanName, SaveArticlePhoto.class);
                break;
            case "client":
                // Récupère la stratégie pour les clients
                strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
                break;
            case "fournisseur":
                // Récupère la stratégie pour les fournisseurs
                strategy = beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
                break;
            case "entreprise":
                // Récupère la stratégie pour les entreprises
                strategy = beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
                break;
            case "utilisateur":
                // Récupère la stratégie pour les utilisateurs
                strategy = beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
                break;
            default:
                // Gestion des contextes inconnus
                throw new InvalidOperationException("Contexte inconnue pour l'enregistrement de la photo", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }
}