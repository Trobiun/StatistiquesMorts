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
public interface Observable {
    
    public void setObserver(Observer observer);
    public void notifyJeu(Jeu jeu);
    public void notifyRun(Run run);
    public void notifyLive(Live live);
    public void notifyDataset(DefaultCategoryDataset dataset);
    
}
