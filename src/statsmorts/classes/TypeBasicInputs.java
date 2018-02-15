/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 * Une énumération pour déterminer les types d'objets à remplir par des saisies
 * utilisateur très basiques (qui ne possèdent qu'un nom).
 * Valeurs possibles : PLATEFORMES, GENRES, STUDIOS
 * @author robin
 */
public enum TypeBasicInputs {
    
    //ÉNUMÉRATION
    /**
     * Type plateformes pour les saisies utilisateur sur les plateformes.
     */
    PLATEFORMES("plateforme"),
    /**
     * Type genres pour les saisies utilisateur sur les genres.
     */
    GENRES("genre"),
    /**
     * Type studios pour les saisies utilisateur sur les studios.
     */
    STUDIOS("studio"),
    /**
     * Type éditeurs pour les saisies utilisateur sur les éditeurs.
     */
    EDITEURS("éditeur");
    
    //ATTRIBUTS
    /**
     * Le nom du type, utile pour l'afficher
     */
    private final String nom;
    
    
    //CONSTRUCTEUR
    /**
     * Créé une valeur de cette énumération qui a pour nom "nom".
     * @param nom le nom a donné aux type, qui sera affiché pour les saisies
     *            utilisateur
     */
    private TypeBasicInputs(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le nom de l'objet de l'énumération.
     * @return le nom de l'objet de l'énumération.
     */
    public String getNom() {
        return nom;
    }
    
}
