/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public class GenrePanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    public GenrePanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.GENRE);
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    @Override
    public void fillItem(long idItem) {
        controler.fillGenrePanel(idItem);
    }
    
}
