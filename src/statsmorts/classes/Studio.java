/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Robin
 */
public class Studio {
    
    //ATTRIBUTS
    private final long id;
    private final String nom;
    private final Map<Long,Jeu> jeux;
    
    //CONSTRUCTEURS
    public Studio(long id, String nom) {
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
        return "Studio : " + nom;
    }
    
    
    //MUTATEURS
    public void putJeu(Jeu jeu) {
        jeux.putIfAbsent(jeu.getID(),jeu);
    }
    
}
