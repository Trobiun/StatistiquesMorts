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
 * Une interface pour l'architecture MVC.
 * @author Robin
 */
public interface Observer {
    
    //méthodes pour mettre à jour la vue en fonction du modèle
    /**
     * Vide tous les arbres et les panels de saisies d'utilisateur.
     * @param type le type par lequel grouper (pour reset le nœud root).
     */
    void clear(TypeGroup type);
    /**
     * Ajoute une plateforme à l'affichage.
     * @param plateforme la plateforme à ajouter à la vue
     */
    void addPlateforme(Plateforme plateforme);
    /**
     * Ajoute un genre à l'affichage.
     * @param genre le genre à ajouter à la vue
     */
    void addGenre(Genre genre);
    /**
     * Ajoute un studio à l'affichage.
     * @param studio le studio à ajouter à la vue
     */
    void addStudio(Studio studio);
    /**
     * Ajoute un jeu à l'affichage.
     * @param jeu le jeu à ajouter à la vue
     */
    void addJeu(Jeu jeu);
    /**
     * Ajoute une run à l'affichage. 
     * @param run la run à ajouter à la vue
     */
    void addRun(Run run);
    /**
     * Ajoute un live à l'affichage
     * @param live le live à ajouter à la vue
     */
    void addLive(Live live);
    /**
     * Supprime une plateforme de l'affichage.
     * @param idPlateforme l'identifiant de la plateforme à supprimer de la vue
     */
    void removePlateforme(long idPlateforme);
    /**
     * Supprime un genre de l'affichage.
     * @param idGenre l'identifiant du genre à supprimer de la vue
     */
    void removeGenre(long idGenre);
    /**
     * Supprime un studio de l'affichage.
     * @param idStudio l'identifiant du studio à supprimer de la vue
     */
    void removeStudio(long idStudio);
    /**
     * Supprime un jeu de l'affichage.
     * @param idJeu l'identifiant du jeu à supprimer de la vue
     */
    void removeJeu(long idJeu);
    /**
     * Supprime une run de l'affichage.
     * @param idRun l'identifiant de la run à supprimer de la vue
     */
    void removeRun(long idRun);
    /**
     * Supprime un live de l'affichage.
     * @param idLive l'identifiant du live à supprimer de la vue
     */
    void removeLive(long idLive);
    
    /**
     * Met à jour le dataset pour le graphique à afficher.
     * @param titre le titre du graphique
     * @param dataset les données du graphique à afficher
     */
    void updateDataset(String titre, DefaultCategoryDataset dataset);
    
    //méthodes pour remplir les champs de saisie pour les entrées utilisateur
    /**
     * Remplit les panels de saisies utilisateur pour les plateformes
     * @param nomPlateforme le nom de la plateforme à afficher dans les champs
     *                      de saisie utilisateur
     */
    void fillPlateforme(String nomPlateforme);
    /**
     * Remplit les panels de saisies utilisateur pour les genres.
     * @param nomGenre le nom du genre
     */
    void fillGenre(String nomGenre);
    /**
     * Remplit les panels de saisies utilisateur pour les studio.
     * @param nomStudio le nom du studio
     */
    void fillStudio(String nomStudio);
    /**
     * Remplit les panels de saisies utilisateur pour les jeux.
     * @param titreJeu le titre du jeu
     * @param anneeSortie l'année de sortie du jeu
     * @param listPlateformes la liste des identifiants des plateformes à présélectionner
     * @param listGenres la liste des identifiants des genres à présélectionner
     * @param idStudio l'identifiant du studio à présélectionner
     */
    void fillJeu(String titreJeu, int anneeSortie, Long[] listPlateformes, Long[] listGenres, long idStudio);
    /**
     * Remplit les panels de saisies utilisateur pour les runs.
     * Remplit les champs de saisies directement liés à la run.
     * @param titreRun le titre de la run
     * @param idJeu l'identifiant du jeu à présélectionner
     */
    void fillRun(String titreRun, long idJeu);
    /**
     * Remplit les panels de saisies utilisateur pour les runs.
     * Remplit les champs de saisies liés au jeu de la run.
     * @param titreJeu le titre du jeu
     */
    void fillRunJeu(String titreJeu);
    /**
     * Remplit les panels de saisies utilisateur pour les lives.
     * Remplit les champs de saisies directement liés au live.
     * @param idRun l'identifiant de la run
     * @param dateDebut la date de début
     * @param dateFin la date de fin
     * @param morts le nombre de morts
     */
    void fillLive(long idRun, Date dateDebut, Date dateFin, int morts);
    /**
     * Remplit les panels de saisies utilisateur pour les lives.
     * Remplit les champs de saisies liés à la run du live.
     * @param idRun l'identifiant de la run
     * @param titreRun le titre de la run
     * @param titreJeu le titre du jeu
     */
    void fillLiveRun(long idRun, String titreRun, String titreJeu);
    
}
