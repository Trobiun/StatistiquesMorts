/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import java.util.concurrent.TimeUnit;

/**
 * Une énumération pour sélectionner l'unité de temps à afficher.
 * @author Robin
 */
public enum Temps {
    
    /**
     * Pour les heures.
     */
    HEURES("heures",TimeUnit.HOURS),
    /**
     * Pour les minutes.
     */
    MINUTES("minutes",TimeUnit.MINUTES);
    
    //ATTRIBUTS
    /**
     * Le nom  à afficher.
     */
    private final String nom;
    /**
     * L'unité de temps à utiliser .
     */
    private final TimeUnit unit;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un objet Temps.
     * @param nom le nom à afficher
     * @param unit l'unité de temps à utiliser
     */
    private Temps(String nom, TimeUnit unit) {
        this.nom = nom;
        this.unit = unit;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le nom pour l'affichage
     * @return le nom à afficher
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Retourne l'unité de temps à utiliser.
     * @return l'unité de temps associé à l'objet Temps
     */
    public TimeUnit getTimeUnit() {
        return unit;
    }
}
