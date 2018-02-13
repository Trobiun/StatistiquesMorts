/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 * Une classe abstraite pour représenter un objet (d'une table SQL) avec id et
 * nom/titre.
 * @author Robin
 */
public abstract class ObjectDatabaseWithTitle extends ObjectDatabase {
    
    //ATTRIBUTS
    /**
     * Le titre/nom de l'objet.
     */
    private String titre;
    
    
    //CONSTRUCTEUR
    /**
     * Construit un ObjectDatabaseWithTitle.
     * @param id l'identifiant de l'objet
     * @param titre le titre de l'objet
     */
    public ObjectDatabaseWithTitle(final long id, final String titre) {
        super(id);
        this.titre = titre;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le titre de l'objet.
     * @return le titre
     */
    public String getTitre() {
        return titre;
    }
    
    /**
     * Retourne une chaîne de caractères qui représente l'objet (ici le titre).
     * @return le titre
     */
    @Override
    public String toString() {
        return titre;
    }
    
    
    //MUTATEURS
    /**
     * Change le titre de l'objet.
     * @param nouveauTitre le nouveau titre de l'objet
     */
    public void renommer(final String nouveauTitre) {
        this.titre = nouveauTitre;
    }
    
    
}
