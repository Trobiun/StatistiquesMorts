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
public class Plateforme implements FillDataset, Comparable {
    
    //ATTRIBUTS
    private final long id;
    private String nom;
    private final Map<Long,Jeu> jeux;
    
    
    //CONSTRUCTEURS  
    public Plateforme(long id, String nom) {
        this.id = id;
        this.nom = nom;
        jeux = new HashMap();
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
        ArrayList<Live> lives = new ArrayList();
        Set<Entry<Long,Jeu>> jeuxSet = jeux.entrySet();
        for (Entry<Long,Jeu> jeuEntry : jeuxSet) {
            lives.addAll(jeuEntry.getValue().getLivesList());
        }
        return lives;
    }

    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        
    }
    
    
    //INTERFACE COMPARABLE
    @Override
    public int compareTo(Object o) {
        if (o instanceof Plateforme) {
            return this.nom.compareTo(((Plateforme)o).nom);
        }
        else {
            return this.nom.compareTo(o.toString());
        }
    }
    
}
