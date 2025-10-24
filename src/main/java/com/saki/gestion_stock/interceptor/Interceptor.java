package com.saki.gestion_stock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

// Intercepteur Hibernate pour ajouter un filtre de sécurité au niveau SQL
// Cet intercepteur ajoute automatiquement un filtre "idEntreprise" aux requêtes SELECT
public class Interceptor extends EmptyInterceptor {

    // Méthode appelée avant l'exécution de chaque requête SQL préparée par Hibernate
    @Override
    public String onPrepareStatement(String sql) {
        // Vérifie si la requête est une sélection (SELECT) et non vide
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // Extrait le nom de l'entité à partir de la requête SQL
            // Exemple: dans "select utilisateu0_.", on extrait "utilisateu0"
            final String entityName = sql.substring(7, sql.indexOf("."));

            // Récupère l'ID de l'entreprise depuis le contexte MDC (Logback)
            // MDC permet de stocker des informations dans le contexte du thread courant
            final String idEntreprise = MDC.get("idEntreprise");

            // Vérifie les conditions pour appliquer le filtre :
            // - Le nom de l'entité doit être valide
            // - L'entité ne doit pas être "entreprise" ou "roles" (pour éviter les boucles infinies)
            // - L'ID entreprise doit être présent dans le MDC
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("roles")
                    && StringUtils.hasLength(idEntreprise)) {

                // Ajoute la condition WHERE selon le format de la requête existante
                if (sql.contains("where")) {
                    // Si la requête a déjà une clause WHERE, on ajoute avec AND
                    sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
                } else {
                    // Si pas de clause WHERE, on crée une nouvelle clause WHERE
                    sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
                }
            }
        }
        // Retourne la requête SQL modifiée (ou originale si pas de modification)
        return super.onPrepareStatement(sql);
    }
}