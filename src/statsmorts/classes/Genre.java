/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.Map.Entry;
import java.util.Set;

/**
 * Une classe pour représenter un genre de jeux (action, RPG etc).
 * @author Robin
 */
public class Genre extends ObjectDatabaseWithJeux {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    /**
     * Crée un genre sans jeu.
     * @param id l'identifiant du genre
     * @param nom le nom du genre
     */
    public Genre(final long id, final String nom) {
        super(id,nom);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Supprime le genre dans les occurences des jeux auxquels le genre est lié.
     */
    public void supprimerGenre() {
        Set<Entry<Long, Jeu>> setJeux = jeux.entrySet();
        for (Entry<Long, Jeu> entry : setJeux) {
            entry.getValue().supprimerGenre(super.getID());
        }
    }
    
}
