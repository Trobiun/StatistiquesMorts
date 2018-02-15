/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe pour gérer les entrées utilisateur pour gérer les éditeurs de la
 * base de données.
 * @author Robin
 */
public class EditeurPanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEUR
    /**
     * Construit un EditeurPanels. 
     * @param controler le controleur à utiliser pour remplir les champs de
     * saisie
     */
    public EditeurPanels(StatsMortsControler controler) {
        super(controler, TexteConstantes.EDITEUR);
    }
    
    
    //ACCESSEUS
    
    
    //MUTATEURS
    /**
     * {@inheritDoc}
     */
    @Override
    public void fillItem(long idItem) {
        controler.fillEditeurPanel(idItem);
    }
    
}
