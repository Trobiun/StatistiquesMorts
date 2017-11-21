/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import javax.swing.tree.DefaultMutableTreeNode;
import statsmorts.classes.Live;

/**
 *
 * @author Robin
 */
public class LiveTreeNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final Live live;
    
    
    //CONSTRUCTEUR
    public LiveTreeNode(Live live) {
        super(live);
        this.live = live;
    }
    
    public Live getLive() {
        return live;
    }
    
    //ACCESSEURS
    @Override
    public String toString() {
        return live.getDateDebut().toString();
    }
    
    
    //MUTATEURS
    
    
    //INFORMATIONS
    @Override
    public String getInformations() {
        return live.toString();
    }
    
}
