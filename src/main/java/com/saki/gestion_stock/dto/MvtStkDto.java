// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importations des entités et enums
import com.saki.gestion_stock.model.Article;
import com.saki.gestion_stock.model.MvtStk;
import com.saki.gestion_stock.model.SourceMvtStk;
import com.saki.gestion_stock.model.TypeMvtStk;
// Importations des classes Java
import java.math.BigDecimal;
import java.time.Instant;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class MvtStkDto {

    // Identifiant unique du mouvement de stock
    private Integer id;

    // Date et heure du mouvement de stock
    private Instant dateMvt;

    // Quantité concernée par le mouvement (positive ou négative)
    private BigDecimal quantite;

    // Article concerné par le mouvement de stock
    private ArticleDto article;

    // Type de mouvement (ENTREE, SORTIE, CORRECTION_POS, CORRECTION_NEG)
    private TypeMvtStk typeMvt;

    // Source du mouvement (COMMANDE_CLIENT, COMMANDE_FOURNISSEUR, VENTE, etc.)
    private SourceMvtStk sourceMvt;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Méthode statique pour convertir une entité MvtStk en MvtStkDto
    public static MvtStkDto fromEntity(MvtStk mvtStk) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (mvtStk == null) {
            return null;
        }

        // Construction du MvtStkDto en utilisant le pattern Builder de Lombok
        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                // Conversion de l'article entité en ArticleDto
                .article(ArticleDto.fromEntity(mvtStk.getArticle()))
                .typeMvt(mvtStk.getTypeMvt())
                .sourceMvt(mvtStk.getSourceMvt())
                .idEntreprise(mvtStk.getIdEntreprise())
                .build();
    }

    // Méthode statique pour convertir un MvtStkDto en entité MvtStk
    public static MvtStk toEntity(MvtStkDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité MvtStk
        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(dto.getId());
        mvtStk.setDateMvt(dto.getDateMvt());
        mvtStk.setQuantite(dto.getQuantite());
        // Conversion de l'ArticleDto en entité Article
        mvtStk.setArticle(ArticleDto.toEntity(dto.getArticle()));
        mvtStk.setTypeMvt(dto.getTypeMvt());
        mvtStk.setSourceMvt(dto.getSourceMvt());
        mvtStk.setIdEntreprise(dto.getIdEntreprise());

        return mvtStk;
    }
}