/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.Map;
import java.util.Set;

/**
 * Une classe pour représenter un studio de développement.
 * @author Robin
 */
public class Studio extends ObjectDatabaseWithJeux {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    /**
     * Crée un studio.
     * @param id l'identifiant du studio
     * @param nom le nom du studio
     */
    public Studio(final long id, final String nom) {
        super(id, nom);
    }
    
    
    //ACCESSEURS    
    /**
     * Retourne une chaîne de caractères représentant le studio, avec ses attributs
     * et des variables calculées (les durées, durées de vie moyennes, morts etc).
     * Utilisée par la méthode getInformations de l'interface Informations.
     * @return une chaîne de caractères représentant le studio
     * @see statsmorts.vue.Informations#getInformations()
     */
    @Override
    public String toString() {
        return "Studio : " + super.getTitre();
    }
    
    
    //MUTATEURS
    /**
     * Supprime tous les jeux de ce studio.
     */
    public void supprimerStudio() {
        jeux.clear();
        Set<Map.Entry<Long,Jeu>> setJeux = jeux.entrySet();
        for (Map.Entry<Long,Jeu> entry : setJeux) {
            entry.getValue().setStudio(null);
        }
//        Set<Entry<Long, Jeu>> setJeux = jeux.entrySet();
//        for (Entry<Long, Jeu> entry : setJeux) {
//            entry.getValue().supprimerJeu();
//        }
    }
    
}
