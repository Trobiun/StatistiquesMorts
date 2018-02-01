/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe  gérer les entrées utilisateur pour gérer les genres de la base de
 * données.
 * @author Robin
 */
public class GenrePanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    /**
     * Crée un GenrePanels.
     * @param controler le controleur à utiliser pour remplir les champs de
     * saisie
     */
    public GenrePanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.GENRE);
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Demande au controleur de remplir les champs de saisie avec le genre qui a
     * pour identifiant idItem.
     * @param idItem l'identifiant du genre avec lequel remplir les champs de
     * saisie
     */
    @Override
    public void fillItem(long idItem) {
        controler.fillGenrePanel(idItem);
    }
    
}
