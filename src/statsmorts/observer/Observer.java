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
public interface Observer {
    
    public void clear(TypeRacine type);
    public void addPlateforme(Plateforme plateforme);
    public void addGenre(Genre genre);
    public void addStudio(Studio studio);
    public void addJeu(Jeu jeu);
    public void addRun(long idJeu, Run run);
    public void addLive(long idRun, Live live);
    public void updateDataset(String titre, DefaultCategoryDataset dataset);
    public void fillPlateforme(long idPlateforme, String nomPlateforme);
    
}
