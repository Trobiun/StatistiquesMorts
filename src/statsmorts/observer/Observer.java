/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.observer;

import java.util.Date;
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
    void fillPlateforme(String nomPlateforme);
    void fillGenre(String nomGenre);
    void fillStudio(String nomStudio);
    void fillJeu(String titreJeu, int anneeSortie, Long[] listPlateformes, Long[] listGenres, long idStudio);
    void fillRun(String titreRun, long idJeu);
    void fillRunJeu(String titreJeu);
    void fillLive(Date dateDebut, Date dateFin, int morts);
    void fillLiveRun(long idRun, String titreRun);
    
}
