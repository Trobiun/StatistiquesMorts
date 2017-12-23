/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

/**
 * Une énumération pour séparer les bases de données fichier / serveur.
 * @author robin
 */
public enum TypeServeurFichier {
    
    //ÉNUMÉRATIONS
    /**
     * Pour les bases de données fichier.
     */
    FICHIER("Fichier"),
    /**
     * Pour les bases de données serveur.
     */
    SERVEUR("Serveur");
    
    
    //ATTRIBUTS
    /**
     * Le nom du type.
     */
    private final String nom;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un objet de type TypeServeurFichier.
     * @param nom le nom du type
     */
    private TypeServeurFichier(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    
    
}
