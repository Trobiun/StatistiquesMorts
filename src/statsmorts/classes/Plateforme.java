/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.Map.Entry;
import java.util.Set;

/**
 * Une classe pour représenter une plateforme de jeux.
 * @author Robin
 */
public class Plateforme extends ObjectDatabaseWithJeux {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    /**
     * Crée une plateforme.
     * @param id l'identifiant de la plateforme
     * @param nom le nom de la plateforme
     */
    public Plateforme(long id, String nom) {
        super(id,nom);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
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
    
}
