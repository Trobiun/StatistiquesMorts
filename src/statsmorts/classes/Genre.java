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
 * Une classe pour représenter un genre de jeux (action, RPG etc).
 * @author Robin
 */
public class Genre extends ObjectDatabaseWithTitle implements FillDataset, Comparable {
    
    //ATTRIBUTS
    /**
     * La collection des jeux qui ont pour genre(s) au moins l'objet courant.
     */
    private final Map<Long,Jeu> jeux;
    
    
    //CONSTRUCTEURS
    /**
     * Crée un genre sans jeu.
     * @param id l'identifiant du genre
     * @param nom le nom du genre
     */
    public Genre(final long id, final String nom) {
        super(id,nom);
        this.jeux = new HashMap();
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Ajoute un jeu à la collection du genre.
     * @param jeu le jeu à ajouter au genre
     */
    public void ajouterJeu(final Jeu jeu) {
        jeux.put(jeu.getID(),jeu);
    }
    
    /**
     * Supprime un jeu de la collecion du genre.
     * @param idJeu le jeu à supprimer du genre
     */
    public void supprimerJeu(final long idJeu) {
        jeux.remove(idJeu);
    }
    
    /**
     * Supprime le genre dans les occurences des jeux auxquels le genre est lié.
     */
    public void supprimerGenre() {
        Set<Entry<Long, Jeu>> setJeux = jeux.entrySet();
        for (Entry<Long, Jeu> entry : setJeux) {
            entry.getValue().supprimerGenre(super.getID());
        }
    }
    
    
    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitre() {
        return super.getTitre();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitreDataset() {
        return super.getTitre();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> livesList = new ArrayList();
        Set<Entry<Long,Jeu>> setJeux = jeux.entrySet();
        for (Entry<Long,Jeu> entryJeu : setJeux) {
            livesList.addAll(entryJeu.getValue().getLivesList());
        }
        return livesList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void fillDataset(final DefaultCategoryDataset dataset, final TimeUnit unit, final boolean total) {
        
    }
    
    
    //INTERFACE COMPARABLE
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Object o) {
         if (o instanceof Genre) {
            return super.getTitre().compareTo(((Genre)o).getTitre());
        }
        else {
            return super.getTitre().compareTo(o.toString());
        }
    }
    
}
