/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Robin
 */
public enum Temps {
    
    HEURES("heures",TimeUnit.HOURS),
    MINUTES("minutes",TimeUnit.MINUTES);
    
    //ATTRIBUTS
    private final String nom;
    private final TimeUnit unit;
    
    
    //CONSTRUCTEUR
    private Temps(String nom, TimeUnit unit) {
        this.nom = nom;
        this.unit = unit;
    }
    
    
    //ACCESSEURS
    public String getNom() {
        return nom;
    }
    
    public TimeUnit getTimeUnit() {
        return unit;
    }
}
