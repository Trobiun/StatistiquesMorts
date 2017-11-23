/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import javax.swing.tree.DefaultMutableTreeNode;
import statsmorts.classes.FillDataset;
import statsmorts.classes.Run;

/**
 *
 * @author Robin
 */
public class RunTreeNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final Run run;
    
    
    //CONSTRUCTEUR
    public RunTreeNode(Run run) {
        super(run);
        this.run = run;
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        return run.getTitre();
    }
    
    public Run getRun() {
        return run;
    }
    
    
    //MUTATEURS
    
    
    //INFORMATIONS
    @Override
    public String getInformations() {
        return run.toString();
    }

    @Override
    public FillDataset getObjectFillDataset() {
        return run;
    }
    
}
