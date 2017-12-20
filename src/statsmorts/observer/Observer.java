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
public interface Observer {
    
    //méthodes pour mettre à jour la vue en fonction du modèle
    void clear(TypeGroup type);
    void addPlateforme(Plateforme plateforme);
    void addGenre(Genre genre);
    void addStudio(Studio studio);
    void addJeu(Jeu jeu);
    void addRun(long idJeu, Run run);
    void addLive(long idRun, Live live);
    void removePlateforme(long idPlateforme);
    void removeGenre(long idGenre);
    void removeStudio(long idStudio);
    void removeJeu(long idJeu);
    void removeRun(long idRun);
    void removeLive(long idLive);
    void updateDataset(String titre, DefaultCategoryDataset dataset);
    
    //méthodes pour remplir les champs de saisie pour les entrées utilisateur
    void fillPlateforme(long idPlateforme, String nomPlateforme);
    void fillGenre(long idGenre, String nomGenre);
    void fillStudio(long idStudio, String nomStudio);
    void fillJeu(long idJeu, String titreJeu, int anneeSortie, Long[] listPlateformes, Long[] listGenres, long idStudio);
    void fillRun(long idRun, String titreRun, long idJeu, String titreJeu);
    void fillRunJeu(long idJeu, String titreJeu);
    void fillLive(long idLive);
    
}
