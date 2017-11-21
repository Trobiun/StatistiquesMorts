/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.observer;

import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Run;

/**
 *
 * @author Robin
 */
public interface Observer {
    
    public void updateJeu(Jeu jeu);
    public void updateRun(long idJeu, Run run);
    public void updateLive(long idJeu, long idRun, Live live);
    public void updateDataset(DefaultCategoryDataset dataset);
    
}
