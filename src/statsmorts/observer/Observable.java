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
    
    void setObserver(Observer observer);
    void setGroup(TypeGroup type);
    
    //notifications de la base de données
    void notifyAddPlateforme(Plateforme plateforme);
    void notifyAddGenre(Genre genre);
    void notifyAddStudio(Studio studio);
    void notifyAddJeu(Jeu jeu);
    void notifyAddRun(Run run);
    void notifyAddLive(Live live);
    void notifyRemovePlateforme(long idPlateforme);
    void notifyRemoveGenre(long idGenre);
    void notifyRemoveStudio(long idStudio);
    void notifyRemoveJeu(long idJeu);
    void notifyRemoveRun(long idRun);
    void notifyRemoveLive(long idLive);
    
    void notifyDataset(String titre, DefaultCategoryDataset dataset);
    
    //notifications pour remplir les panels d'entrées utilisateur
    void notifyFillPlateforme(long idPlateforme);
    void notifyFillGenre(long idGenre);
    void notifyFillStudio(long idStudio);
    void notifyFillJeu(long idJeu);
    void notifyFillRun(long idRun);
    void notifyFillRunJeu(long idJeu);
    void notifyFillLive(long idLive);
    
}
