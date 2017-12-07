/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

/**
 *
 * @author robin
 */
public enum TypeServeurFichier {
    
    //ÉNUMÉRATIONS
    FICHIER("Fichier"),
    SERVEUR("Serveur");
    
    
    //ATTRIBUTS
    private final String nom;
    
    
    //CONSTRUCTEUR
    private TypeServeurFichier(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    
    
}
