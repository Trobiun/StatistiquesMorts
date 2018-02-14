/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Robin
 */
public class Editeur extends ObjectDatabaseWithJeux {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEUR
    /**
     * Crée un éditeur sans jeu.
     * @param id
     * @param title 
     */
    public Editeur(final long id, final String title) {
        super(id, title);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void supprimerEditeur() {
        Set<Map.Entry<Long, Jeu>> setJeux = jeux.entrySet();
        for (Map.Entry<Long, Jeu> entry : setJeux) {
            entry.getValue().setEditeur(null);
        }
    }
    
}
