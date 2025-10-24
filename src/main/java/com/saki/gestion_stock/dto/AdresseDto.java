// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité Adresse
import com.saki.gestion_stock.model.Adresse;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class AdresseDto {

    // Champ pour la première ligne d'adresse
    private String adresse1;

    // Champ pour la deuxième ligne d'adresse (complément)
    private String adresse2;

    // Champ pour la ville
    private String ville;

    // Champ pour le code postal
    private String codePostale;

    // Champ pour le pays
    private String pays;

    // Méthode statique pour convertir une entité Adresse en AdresseDto
    public static AdresseDto fromEntity(Adresse adresse) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (adresse == null) {
            return null;
        }

        // Construction de l'AdresseDto en utilisant le pattern Builder de Lombok
        return AdresseDto.builder()
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .codePostale(adresse.getCodePostale())
                .ville(adresse.getVille())
                .pays(adresse.getPays())
                .build();
    }

    // Méthode statique pour convertir un AdresseDto en entité Adresse
    public static Adresse toEntity(AdresseDto adresseDto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (adresseDto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Adresse
        Adresse adresse = new Adresse();

        // Copie des données du DTO vers l'entité
        adresse.setAdresse1(adresseDto.getAdresse1());
        adresse.setAdresse2(adresseDto.getAdresse2());
        adresse.setCodePostale(adresseDto.getCodePostale());
        adresse.setVille(adresseDto.getVille());
        adresse.setPays(adresseDto.getPays());

        return adresse;
    }

}