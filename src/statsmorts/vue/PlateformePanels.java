/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public class PlateformePanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    
    
    //CONSTRUCTEURS
    public PlateformePanels(StatsMortsControler controler) {
        super(controler);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    @Override
    public void fillItem(long idItem) {
        controler.fillPlateformePanel(idItem);
    }
    
}