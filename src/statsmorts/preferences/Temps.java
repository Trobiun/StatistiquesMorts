/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

/**
 *
 * @author Robin
 */
public enum Temps {
    
    HEURES("heures"),
    MINUTES("minutes");
    
    //ATTRIBUTS
    private final String nom;
    
    
    //CONSTRUCTEUR
    private Temps(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    public String getNom() {
        return nom;
    }
}
