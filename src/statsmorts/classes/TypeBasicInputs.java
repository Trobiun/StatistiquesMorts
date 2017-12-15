/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 *
 * @author robin
 */
public enum TypeBasicInputs {
    
    //ÉNUMÉRATION
    PLATEFORMES("plateforme"),
    GENRES("genre"),
    STUDIOS("studio");
    
    //ATTRIBUTS
    private final String nom;
    
    
    //CONSTRUCTEUR
    private TypeBasicInputs(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    public String getNom() {
        return nom;
    }
    
}
