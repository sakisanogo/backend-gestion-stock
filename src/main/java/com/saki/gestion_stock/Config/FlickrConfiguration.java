// Déclaration du package pour la configuration
package com.saki.gestion_stock.Config;

// Importations des classes nécessaires pour Flickr
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Classe de configuration pour l'intégration Flickr (actuellement désactivée avec @Configuration en commentaire)
// @Configuration
public class FlickrConfiguration {

    // Injection de la clé API Flickr depuis les properties
    @Value("${flickr.apiKey}")
    private String apiKey;

    // Injection du secret API Flickr depuis les properties
    @Value("${flickr.apiSecret}")
    private String apiSecret;

    // Injection de la clé d'application Flickr depuis les properties
    @Value("${flickr.appKey}")
    private String appKey;

    // Injection du secret d'application Flickr depuis les properties
    @Value("${flickr.appSecret}")
    private String appSecret;

    // Première méthode de configuration Flickr (actuellement désactivée)
    // Cette méthode utilise le flux OAuth complet avec interaction utilisateur
    //@Bean
//  public Flickr getFlickr() throws InterruptedException, ExecutionException, IOException, FlickrException {
//    // Création de l'instance Flickr avec la clé API et le secret
//    Flickr flickr = new
//        Flickr(apiKey, apiSecret, new REST());
//
//    // Construction du service OAuth pour l'authentification
//    OAuth10aService service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(FlickrApi.instance(FlickrPerm.WRITE));
//
//    // Scanner pour lire l'entrée utilisateur
//    final Scanner scanner = new Scanner(System.in);
//
//    // Obtention du token de requête OAuth
//    final OAuth1RequestToken request = service.getRequestToken();
//
//    // Génération de l'URL d'autorisation
//    final String authUrl = service.getAuthorizationUrl(request);
//
//    // Affichage de l'URL pour que l'utilisateur s'authentifie
//    System.out.println(authUrl);
//    System.out.println("Paste it here >> ");
//
//    // Lecture du code de vérification fourni par l'utilisateur
//    final String authVerifier = scanner.nextLine();
//
//    // Échange du token de requête contre un token d'accès
//    OAuth1AccessToken accessToken = service.getAccessToken(request, authVerifier);
//
//    // Affichage des tokens obtenus
//    System.out.println(accessToken.getToken());
//    System.out.println(accessToken.getTokenSecret());
//
//    // Vérification du token avec l'API Flickr
//    Auth auth = flickr.getAuthInterface().checkToken(accessToken);
//
//    // Affichage des informations d'authentification finales
//    System.out.println("---------------------------");
//    System.out.println(auth.getToken());
//    System.out.println(auth.getTokenSecret());
//
//    // Retour de l'instance Flickr configurée
//    return flickr;
//  }

    // Deuxième méthode de configuration Flickr (méthode actuellement active)
    // Cette méthode utilise une configuration directe avec les tokens pré-obtenus
    @Bean
    public Flickr getFlickr2() {
        // Création de l'instance Flickr avec les clés API et le client REST
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());

        // Création de l'objet d'authentification
        Auth auth = new Auth();

        // Définition des permissions (ici READ = lecture seule)
        auth.setPermission(Permission.READ);

        // Configuration du token d'application
        auth.setToken(appKey);

        // Configuration du secret du token d'application
        auth.setTokenSecret(appSecret);

        // Obtention du contexte de requête courant
        RequestContext requestContext = RequestContext.getRequestContext();

        // Définition de l'authentification dans le contexte
        requestContext.setAuth(auth);

        // Définition de l'authentification dans l'instance Flickr
        flickr.setAuth(auth);

        // Retour de l'instance Flickr configurée
        return flickr;
    }

}