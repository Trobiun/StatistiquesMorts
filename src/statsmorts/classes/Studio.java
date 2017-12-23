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
 * Une classe pour représenter un studio de développement.
 * @author Robin
 */
public class Studio implements FillDataset, Comparable<Studio> {
    
    //ATTRIBUTS
    /**
     * L'identifiant unique du studio dans la base de données, utilisé pour les  maps.
     */
    private final long id;
    /**
     * Le nom du studio.
     */
    private String nom;
    /**
     * La collection des jeux développés par ce studio.
     */
    private final Map<Long,Jeu> jeux;
    
    //CONSTRUCTEURS
    /**
     * Crée un studio.
     * @param id l'identifiant du studio
     * @param nom le nom du studio
     */
    public Studio(final long id, final String nom) {
        this.id = id;
        this.nom = nom;
        this.jeux = new HashMap();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'identifiant du studio.
     * @return l'identifiant du studio
     */
    public long getID() {
        return id;
    }
    
    /**
     * Retourne la collection des jeux développés par ce studio.
     * @return la collection des jeux développés par ce studio
     */
    public Map<Long,Jeu> getJeux() {
        return jeux;
    }
    
    /**
     * Retourne une chaîne de caractères représentant le studio, avec ses attributs
     * et des variables calculées (les durées, durées de vie moyennes, morts etc).
     * Utilisée par la méthode getInformations de l'interface Informations.
     * @return une chaîne de caractères représentant le studio
     * @see statsmorts.vue.Informations#getInformations()
     */
    @Override
    public String toString() {
        return "Studio : " + nom;
    }
    
    
    //MUTATEURS
    /**
     * Ajoute un jeu à la collection des jeux de ce studio.
     * @param jeu le jeu à ajouter
     */
    public void ajouterJeu(final Jeu jeu) {
        jeux.putIfAbsent(jeu.getID(),jeu);
    }
    
    /**
     * Supprime le jeu dont l'identifiant est 'idJeu' dans la collection des jeux
     * de ce studio.
     * @param idJeu l'identifiant du jeu à supprimer
     */
    public void supprimerJeu(final long idJeu) {
        jeux.remove(idJeu);
    }
    
    /**
     * Renomme le studio.
     * @param nouveauNom le nouveau nom du studio
     */
    public void renommer(final String nouveauNom) {
        this.nom = nouveauNom;
    }
    
    /**
     * Supprime tous les jeux de ce studio.
     */
    public void supprimerStudio() {
        jeux.clear();
//        Set<Entry<Long, Jeu>> setJeux = jeux.entrySet();
//        for (Entry<Long, Jeu> entry : setJeux) {
//            entry.getValue().supprimerJeu();
//        }
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
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Studio o) {
        return this.nom.compareTo(((Studio)o).nom);
    }
    
}
