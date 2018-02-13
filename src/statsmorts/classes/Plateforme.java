/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Une classe pour représenter une plateforme de jeux.
 * @author Robin
 */
public class Plateforme extends ObjectDatabaseWithTitle implements FillDataset, Comparable<Plateforme> {
    
    //ATTRIBUTS
    /**
     * La collection des jeux sortis sur cette plateforme.
     */
    private final Map<Long,Jeu> jeux;
    
    
    //CONSTRUCTEURS
    /**
     * Crée une plateforme.
     * @param id l'identifiant de la plateforme
     * @param nom le nom de la plateforme
     */
    public Plateforme(long id, String nom) {
        super(id,nom);
        jeux = new HashMap();
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Ajoute un jeu à la collection des jeux de la plateforme.
     * @param jeu le jeu à ajouter
     */
    public void ajouterJeu(final Jeu jeu) {
        jeux.put(jeu.getID(),jeu);
    }
    
   /**
    * Supprime un jeu à la collection des jeux de la platforme dont l'identifiant
    * est 'idJeu'.
    * @param idJeu l'identifiant du jeu à supprimer
    */
    public void supprimerJeu(final long idJeu) {
        jeux.remove(idJeu);
    }
    
    /**
     * Supprime la plateforme dans les occurences des jeux auxquels la plateforme
     * est liée.
     */
    public void supprimerPlateforme() {
        Set<Entry<Long,Jeu>> setJeux = jeux.entrySet();
        for (Entry<Long,Jeu> entry : setJeux) {
            entry.getValue().supprimerPlateforme(super.getID());
        }
    }
    
    
    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitreDataset() {
        return super.getTitre();
    }
    
    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> lives = new ArrayList();
        Set<Entry<Long,Jeu>> jeuxSet = jeux.entrySet();
        for (Entry<Long,Jeu> jeuEntry : jeuxSet) {
            lives.addAll(jeuEntry.getValue().getLivesList());
        }
        return lives;
    }

    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        
    }
    
    
    //INTERFACE COMPARABLE
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Plateforme o) {
        return super.getTitre().compareTo(((Plateforme)o).getTitre());
    }
    
}
