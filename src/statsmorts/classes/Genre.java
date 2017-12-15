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
 *
 * @author Robin
 */
public class Genre implements FillDataset, Comparable {
    
    //ATTRIBUTS
    private final long id;
    private String nom;
    private final Map<Long,Jeu> jeux;
    
    
    //CONSTRUCTEURS
    public Genre(long id, String nom) {
        this.id = id;
        this.nom = nom;
        this.jeux = new HashMap();
    }
    
    
    //ACCESSEURS
    public long getID() {
        return id;
    }
    
    @Override
    public String toString() {
        return nom;
    }
    
    
    //MUTATEURS
    public void putJeu(Jeu jeu) {
        jeux.put(jeu.getID(),jeu);
    }
    
    public void rename(String nouveauNom) {
        this.nom = nouveauNom;
    }
    
    
    //INTERFACE FILLDATASET
    @Override
    public String getTitre() {
        return nom;
    }
    
    @Override
    public String getTitreDataset() {
        return nom;
    }
    
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> livesList = new ArrayList();
        Set<Entry<Long,Jeu>> setJeux = jeux.entrySet();
        for (Entry<Long,Jeu> entryJeu : setJeux) {
            livesList.addAll(entryJeu.getValue().getLivesList());
        }
        return livesList;
    }
    
    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        
    }
    
    
    //INTERFACE COMPARABLE
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
