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
import statsmorts.classes.TypeRacine;


/**
 *
 * @author Robin
 */
public interface Observable {
    
    public void setObserver(Observer observer);
    public void setTypeRacine(TypeRacine type);
    public void notifyPlateforme(Plateforme plateforme);
    public void notifyGenre(Genre genre);
    public void notifyStudio(Studio studio);
    public void notifyJeu(Jeu jeu);
    public void notifyRun(Run run);
    public void notifyLive(Live live);
    public void notifyDataset(String titre, DefaultCategoryDataset dataset);
    public void notifyFillPlateforme(long idPlateforme);
    
}
