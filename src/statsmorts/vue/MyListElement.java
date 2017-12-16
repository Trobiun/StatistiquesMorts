/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.classes.FillDataset;

/**
 *
 * @author Robin
 */
public class MyListElement {
    
    //ATTRIBUTS
    private final long id;
    private final FillDataset objet;
    
    
    //CONSTRUCTEUR
    public MyListElement(long id, FillDataset objet) {
        this.id = id;
        this.objet = objet;
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        return "" + id + " : " + objet.getTitre();
    }
    
    public String getTitre() {
        return objet.getTitre();
    }
    
    
    //MUTAUTEURS
    
    
    //EQUALS
    @Override
    public boolean equals(Object other) {
        if (other instanceof MyListElement) {
            return this.id == ((MyListElement)other).id;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
}

