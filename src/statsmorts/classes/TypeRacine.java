/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 *
 * @author Robin
 */
public enum TypeRacine {
    
    //ENUMERATION
    PLATEFORMES("Plateformes"),
    GENRES("Genres"),
    STUDIOS("Studios"),
    JEUX("Jeux");
    
    
    //ATTRIBUTS
    private final String nom;
    
    
    //CONSTRUCTEUR
    private TypeRacine(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    public String getNom() {
        return nom;
    }
    
}
