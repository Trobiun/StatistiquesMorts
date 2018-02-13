/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 * Une classe abstraite pour représenter un objet de la base de données.
 * @author Robin
 */
public abstract class ObjectDatabase {
    
    //ATTRIBUTS
    /**
     * L'identifiant de l'objet de la base de données.
     */
    private final long id;
    
    
    //CONSTRUCTEUR
    /**
     * Construit un ObjectDatabase.
     * @param id l'identifiant de l'objet dans la base de données
     */
    public ObjectDatabase(final long id) {
        this.id = id;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'identifiant de l'objet de la base de données.
     * @return l'identifiant de l'objet
     */
    public long getID() {
        return id;
    }
    
    
    //MUTATEURS
    
}
