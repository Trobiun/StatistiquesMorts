/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.classes.ObjectDatabaseWithTitle;

/**
 * Une classe pour un élément d'une JList.
 * @author Robin
 */
public class MyListElement {
    
    //ATTRIBUTS
    /**
     * L'identifiant de l'objet lié à ce MyListElement.
     */
    private final long id;
    /**
     * L'objet lié à ce MyListElement.
     */
    private final ObjectDatabaseWithTitle objet;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un MyListElement avec un identifiant et un objet FillDataset pour
     * avoir le titre.
     * @param id l'identifiant de l'objet
     * @param objet l'objet FillDataset
     */
    public MyListElement(long id, ObjectDatabaseWithTitle objet) {
        this.id = id;
        this.objet = objet;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne une chaîne de caractères qui représente le MyListElement
     * égale à l'identifiant puis le titre de l'objet.
     * @return une chaîne de caractères qui représente le MyListElement
     */
    @Override
    public String toString() {
        return objet.getTitre();
    }
    
    /**
     * Retourne l'identifiant de l'objet.
     * @return l'identifiant de l'objet
     */
    public long getID() {
        return id;
    }
    
    /**
     * Retourne le titre de l'objet.
     * @return le titre de l'objet
     */
    public String getTitre() {
        return objet.getTitre();
    }
    
    
    //MUTAUTEURS
    
    
    //EQUALS
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof MyListElement) {
            return this.id == ((MyListElement)other).id;
        }
        return false;
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
}

