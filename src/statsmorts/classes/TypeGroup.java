/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import statsmorts.constantes.TexteConstantes;

/**
 * Une énumération utilisée pour les différentes manières de regrouper les jeux
 * dans l'arbre dans l'interface graphique.
 * @author Robin
 */
public enum TypeGroup {
    
    //ENUMERATION
    /**
     * Pour grouper les jeux par plateformes.
     */
    PLATEFORMES(TexteConstantes.PLATEFORMES),
    /**
     * Pour grouper les jeux par genres.
     */
    GENRES(TexteConstantes.GENRES),
    /**
     * Pour grouper les jeux par studios.
     */
    STUDIOS(TexteConstantes.STUDIOS),
    /**
     * Pour grouper les jeux par jeux. (aucun regroupement)
     */
    JEUX(TexteConstantes.JEUX);
    
    
    //ATTRIBUTS
    private final String nom;
    
    
    //CONSTRUCTEUR
    /**
     * Créé un TypeGroup avec pour nom "nom".
     * @param nom 
     */
    private TypeGroup(String nom) {
        this.nom = nom;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le nom du TypeGroup pour l'afficher dans l'interface graphique.
     * @return le nom du TypeGroup.
     */
    public String getNom() {
        return nom;
    }
    
}
