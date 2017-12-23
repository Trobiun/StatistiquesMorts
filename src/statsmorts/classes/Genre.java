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
public class Genre implements FillDataset, Comparable {
    
    //ATTRIBUTS
    /**
     * L'identifiant unique du genre dans la base de données, utilisé pour les maps.
     */
    private final long id;
    /**
     * Le nom du genre dans la base de données, utilisé pour l'affichage.
     */
    private String nom;
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
        this.id = id;
        this.nom = nom;
        this.jeux = new HashMap();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'identifiant du genre dans la base de données.
     * @return l'identifiant du genre dans la base de données
     */
    public long getID() {
        return id;
    }
    
    /**
     * Retourne une chaîne de caractères représentant l'objet (le nom du genre
     * pour l'affichage)
     * @return le nom du genre
     */
    @Override
    public String toString() {
        return nom;
    }
    
    
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
     * Renomme le genre.
     * @param nouveauNom le nouveau nom du genre 
     */
    public void renommer(final String nouveauNom) {
        this.nom = nouveauNom;
    }
    
    /**
     * Supprime le genre dans les occurences des jeux auxquels le genre est lié.
     */
    public void supprimerGenre() {
        Set<Entry<Long, Jeu>> setJeux = jeux.entrySet();
        for (Entry<Long, Jeu> entry : setJeux) {
            entry.getValue().supprimerGenre(id);
        }
    }
    
    
    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitre() {
        return nom;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitreDataset() {
        return nom;
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
            return this.nom.compareTo(((Genre)o).nom);
        }
        else {
            return this.nom.compareTo(o.toString());
        }
    }
    
}
