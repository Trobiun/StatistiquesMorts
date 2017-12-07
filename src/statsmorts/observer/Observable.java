/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.observer;

import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.Genre;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Run;
import statsmorts.classes.Studio;
import statsmorts.classes.TypeGroup;


/**
 *
 * @author Robin
 */
public interface Observable {
    
    public void setObserver(Observer observer);
    public void setGroup(TypeGroup type);
    public void notifyAddPlateforme(Plateforme plateforme);
    public void notifyAddGenre(Genre genre);
    public void notifyAddStudio(Studio studio);
    public void notifyAddJeu(Jeu jeu);
    public void notifyAddRun(Run run);
    public void notifyAddLive(Live live);
    public void notifyRemovePlateforme(long idPlateforme);
    public void notifyRemoveGenre(long idGenre);
    public void notifyRemoveStudio(long idStudio);
    public void notifyRemoveJeu(long idJeu);
    public void notifyRemoveRun(long idRun);
    public void notifyRemoveLive(long idLive);
    public void notifyDataset(String titre, DefaultCategoryDataset dataset);
    public void notifyFillPlateforme(long idPlateforme);
    public void notifyFillGenre(long idGenre);
    
}
