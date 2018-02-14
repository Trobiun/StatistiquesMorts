/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Une classe abstraite pour représenter un objet (d'une table SQL) avec id,
 * nom/titre et des jeux.
 * @author Robin
 */
public abstract class ObjectDatabaseWithJeux extends ObjectDatabaseWithTitle implements FillDataset, Comparable<ObjectDatabaseWithJeux> {
    
    //ATTRIBUTS
    /**
     * La HashMap contenant les jeux, utilise les identifiants des jeux pour la clé.
     */
    protected final HashMap<Long,Jeu> jeux;
    
    
    //CONSTRUCTEUR
    public ObjectDatabaseWithJeux(final long id, final String nom) {
        super(id,nom);
        jeux = new HashMap();
    }
    
    
    //ACCESSEURS
    public HashMap<Long,Jeu> getJeux() {
        return jeux;
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
        Set<Map.Entry<Long,Jeu>> setJeux = jeux.entrySet();
        for (Map.Entry<Long,Jeu> entryJeu : setJeux) {
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
    public int compareTo(ObjectDatabaseWithJeux other) {
        return super.getTitre().compareTo(other.getTitre());
    }
    
}
