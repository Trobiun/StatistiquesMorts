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
 * Une interface pour l'architecture MVC.
 * @author Robin
 */
public interface Observable {
    
    /**
     * Change l'observer par oobserver.
     * @param observer le nouvel observer.
     */
    void setObserver(Observer observer);
    /**
     * Change le regroupement des jeux par le type "type"
     * @param type le nouveau type par lequel regrouper les jeux
     */
    void setGroup(TypeGroup type);
    
    //notifications de la base de données
    /**
     * Notifie l'observer d'ajouter la plateforme "plateforme" à l'affichage.
     * @param plateforme la plateforme à ajouter.
     */
    void notifyAddPlateforme(Plateforme plateforme);
    /**
     * Notifie l'observer d'ajouter le genre "genre" à l'affichage.
     * @param genre le genre à ajouter.
     */
    void notifyAddGenre(Genre genre);
    /**
     * Notifie l'observer d'ajouter le studio "studio" à l'affichage.
     * @param studio le studio à ajouter
     */
    void notifyAddStudio(Studio studio);
    /**
     * Notifie l'observer d'ajouter le jeu "jeu" à l'affichage.
     * @param jeu le jeu à ajouter.
     */
    void notifyAddJeu(Jeu jeu);
    /**
     * Notifie l'observer d'ajouter la run "run" à l'affichage.
     * @param run la run à ajouter.
     */
    void notifyAddRun(Run run);
    /**
     * Notifie l'observer d'ajouter le live "live" à l'affichage.
     * @param live le live à ajouter.
     */
    void notifyAddLive(Live live);
    /**
     * Notifie l'observer de supprimer la plateforme "plateforme" à l'affichage.
     * @param idPlateforme l'identifiant de la plateforme à supprimer.
     */
    void notifyRemovePlateforme(long idPlateforme);
    /**
     * Notifie l'observer de supprimer le genre "genre" à l'affichage.
     * @param idGenre l'identifiant du genre à supprimer.
     */
    void notifyRemoveGenre(long idGenre);
    /**
     * Notifie l'observer de supprimer le studio "studio" à l'affichage.
     * @param idStudio l'identifiant du studio à supprimer.
     */
    void notifyRemoveStudio(long idStudio);
    /**
     * Notifie l'observer de supprimer le jeu "jeu" à l'affichage.
     * @param idJeu l'identifiant du jeu à supprimer.
     */
    void notifyRemoveJeu(long idJeu);
    /**
     * Notifie l'observer de supprimer la run "run" à l'affichage.
     * @param idRun l'identifiant de la run à supprimer.
     */
    void notifyRemoveRun(long idRun);
    /**
     * Notifie l'observer de supprimer le lvie "live" à l'affichage.
     * @param idLive l'identifiant du live à supprimer.
     */
    void notifyRemoveLive(long idLive);
    /**
     * Notifie l'observer que le dataset a changé et doit le mettre à jour.
     * @param titre le titre du graphique (chart) à afficher.
     * @param dataset les données du graphqiue à afficher.
     */
    void notifyDataset(String titre, DefaultCategoryDataset dataset);
    
    //notifications pour remplir les panels d'entrées utilisateur
    /**
     * Notifie l'observer de remplir les panels de saisie de plateformes.
     * @param idPlateforme l'identifiant de la plateforme à remplir.
     */
    void notifyFillPlateforme(long idPlateforme);
    /**
     * Notifie l'observer de remplir les panels de saisie de genres.
     * @param idGenre l'identifiant du genre à remplir.
     */
    void notifyFillGenre(long idGenre);
    /**
     * Notifie l'observer de remplir les panels de saisie de studios.
     * @param idStudio l'identifiant du studio à remplir.
     */
    void notifyFillStudio(long idStudio);
    /**
     * Notifie l'observer de remplir les panels de saisie de jeux.
     * @param idJeu l'identifiant du jeu à remplir.
     */
    void notifyFillJeu(long idJeu);
    /**
     * Notifie l'observer de remplir les panels de saisie de runs.
     * @param idRun l'identifiant de la run à remplir.
     */
    void notifyFillRun(long idRun);
    /**
     * Notifie l'observer de remplir les panels de jeux dans les panels de saisie de runs.
     * @param idJeu l'identifiant du jeu à remplir.
     */
    void notifyFillRunJeu(long idJeu);
    /**
     * Notifie l'observer de remplir les panels de saisie de lives.
     * @param idLive l'identifiant du live à remplir.
     */
    void notifyFillLive(long idLive);
    /**
     * Notifie l'observer de remplir les panels de runs dans les panels de saisie de lives.
     * @param idRun l'identifiant de la run à remplir.
     */
    void notifyFillLiveRun(long idRun);
    
}
