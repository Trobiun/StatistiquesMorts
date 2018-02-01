/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe  gérer les entrées utilisateur pour gérer les plateformes de la
 * base de données.
 * @author Robin
 */
public class PlateformePanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    /**
     * Crée un PlateformePanels.
     * @param controler le controleur à utiliser pour remplir les champs de
     * saisie
     */
    public PlateformePanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.PLATEFORME);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Demande au controleur de remplir les champs de saisie avec la plateforme
     * qui a pour identifiant idItem.
     * @param idItem l'identifiant de la plateforme avec laquelle remplir les
     * champs de saisie
     */
    @Override
    public void fillItem(long idItem) {
        controler.fillPlateformePanel(idItem);
    }
    
}
