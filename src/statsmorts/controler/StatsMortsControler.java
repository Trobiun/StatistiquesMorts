/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.controler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import statsmorts.classes.FillDataset;
import statsmorts.classes.TypeDatabase;
import statsmorts.classes.TypeGroup;
import statsmorts.modele.StatsMortsModele;
import statsmorts.classes.TypeBasicInputs;

/**
 * Une classe pour contrôler les saisies utilisateur et contrôler le modèle.
 * @author robin
 */
public class StatsMortsControler {
    
    //ATTRIBUTS
    /**
     * Le modèle à contrôler.
     */
    private final StatsMortsModele modele;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un contrôler avec le modèle à contrôler.
     * @param modele le modèle à contrôler
     */
    public StatsMortsControler(final StatsMortsModele modele) {
        this.modele = modele;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    //MÉTHODES AYANT UN IMPACT SEULEMENT SUR LA VUE
    /**
     * Demande au modèle de s'actualiser.
     */
    public void actualiser() {
        modele.actualiser();
    }
    
    /**
     * Change l'unité de temps du modèle.
     * @param unit l'unité de temps
     */
    public void setTimeUnit(final TimeUnit unit) {
        modele.setTimeUnit(unit);
    }
    
    /**
     * Change le regroupement des jeux (par plateformes, genres, studios, jeux).
     * @param type le type de groupement
     */
    public void setGroup(final TypeGroup type) {
        modele.setGroup(type);
    }
    
    /**
     * Demande au modèle de créer le dataset et de le remplir.
     * @param titre le titre du dataset à afficher
     * @param objets la liste d'objets qui serviront à remplir le dataset
     */
    public void createDataset(final String titre, final ArrayList<FillDataset> objets) {
        modele.createDataset(titre, objets);
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour les plateformes.
     * @param idPlateforme l'identifiant de la plateforme à laquelle il faut
     * chercher les informations
     */
    public void fillPlateformePanel(final long idPlateforme) {
        if (idPlateforme > 0) {
            modele.fillPlateformePanel(idPlateforme);
        }
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour les genres.
     * @param idGenre l'identifiant du genre auquel il faut chercher les
     * informations
     */
    public void fillGenrePanel(final long idGenre) {
        if (idGenre > 0) {
            modele.fillGenrePanel(idGenre);
        }
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour les studios.
     * @param idStudio l'identifiant du studio auquel il faut chercher les
     * informations
     */
    public void fillStudioPanel(final long idStudio) {
        if (idStudio > 0) {
            modele.fillStudiPanel(idStudio);
        }
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour les jeux.
     * @param idJeu l'identifiant du jeu auquel il faut chercher les
     * informations
     */
    public void fillJeuPanel(final long idJeu) {
        if (idJeu > 0) {
            modele.fillJeuPanel(idJeu);
        }
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour les runs.
     * @param idRun l'identifiant du jeu auquel il faut chercher les
     * informations
     */
    public void fillRunPanel(final long idRun) {
        if (idRun > 0) {
            modele.fillRunPanel(idRun);
        }
    }
    /**
     * Demande au modèle de remplir les champs de saisie pour la partie "jeux"
     * des champs de saisie pour les runs.
     * @param idJeu l'identifiant du jeu auquel il faut chercher les
     * informations
     */
    public void selectJeuRunPanels(final long idJeu) {
        if (idJeu > 0) {
            modele.selectJeuRunPanels(idJeu);
        }
    }
    
    public void selectJeuLivePanels(final long idJeu) {
        if (idJeu > 0){
            modele.selectJeuLivePanels(idJeu);
        }
    }
    
    public void selectRunLivePanels(final long idRun) {
        if (idRun > 0) {
            modele.selectRunLivePanels(idRun);
        }
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour les lives.
     * @param idLive l'identifiant du live auquel il faut chercher les
     * informations
     */
    public void fillLivePanel(final long idLive) {
        if (idLive > 0) {
            modele.fillLivePanel(idLive);
        }
    }
    
    /**
     * Demande au modèle de remplir les champs de saisie pour la partie "run"
     * dans les champs de saisie pour les lives.
     * @param idRun l'identifiant de la run à laquelle il faut chercher les
     * informations
     */
    public void fillLivePanelRun(final long idRun) {
        if (idRun > 0) {
            modele.fillLivePanelRun(idRun);
        }
    }
    
    public void fillRunOnPanelLive(final long idRun) {
        if (idRun > 0) {
            modele.fillRunOnLivePanels(idRun);
        }
    }
    
    //MÉTHODES AYANT UN IMPACT SUR LE MODÈLE
    /**
     * Demande au modèle de créer une base de données fichier.
     * @param pathBDD le chemin de la base de données à créer
     */
    public void creerBDD(final String pathBDD) {
        String lowerCase = pathBDD.toLowerCase();
        if (lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
         || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
         || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
         || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
         || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
         || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
         || lowerCase.endsWith(".sl3")) {
            modele.creerBDD(pathBDD);
        }
    }
    
    /**
     * Demande au modèle d'ouvrir une base de données fichier.
     * @param pathBDD le chemin de la base de données à ouvrir
     */
    public void ouvrirBDD(final String pathBDD) {
        String lowerCase = pathBDD.toLowerCase();
        if (lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
         || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
         || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
         || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
         || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
         || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
         || lowerCase.endsWith(".sl3") || lowerCase.endsWith(".kexi")) {
            modele.connecter(pathBDD);
        }
    }
    
    /**
     * Demande au modèle de se connecter à une base de donnée serveur.
     * @param typeBDD le type du serveur de base de données
     * @param adresseServeur l'adresse du serveur
     * @param port le port sur lequel écoute le serveur
     * @param nomBDD le nom de la base de données du serveur à laquelle se connecter
     * @param utilisateur l'utilisateur avec lequel se connecter
     * @param password le mot de passe
     */
    public void connecterServeur(final TypeDatabase typeBDD, final String adresseServeur,
            final int port, final String nomBDD, final String utilisateur, final char[] password) {
        if(null != typeBDD && null != adresseServeur && !adresseServeur.isEmpty() && port > 0 
                && null != nomBDD && !nomBDD.isEmpty() && null != utilisateur && !utilisateur.isEmpty()) {
            modele.connecter(typeBDD, adresseServeur + ":" + port + "/" + nomBDD, utilisateur, password);
        }
    }
    
    /**
     * Demande au modèle d'ajouter un(e) pateforme/genre/studio à la base de données.
     * @param typeInputs le type de l'objet à ajouter
     * @param nom le nom de l'objet à ajouter
     */
    public void ajouterBasicInputs(final TypeBasicInputs typeInputs, final String nom ) {
        if (null != typeInputs && null != nom && !nom.isEmpty()) {
            modele.ajouterBasicInputs(typeInputs, nom);
        }
    }
    
    /**
     * Demande au modèle de modifier un(e) pateforme/genre/studio à la base de données.
     * @param typeInputs le type de l'objet à modifier
     * @param id l'identifiant de l'objet à modifier
     * @param nom le (nouveau) nom de l'objet
     */
    public void modifierBasicInputs(final TypeBasicInputs typeInputs, final long id, final String nom) {
        if (null != typeInputs && id > 0 && null != nom && !nom.isEmpty()) {
            modele.modifierBasicInputs(typeInputs, id, nom);
        }
    }
    
    /**
     * Demande au modèle de supprimer un(e) pateforme/genre/studio à la base de données.
     * @param typeInputs le type de l'objet à supprimer
     * @param id l'identifiant de l'objet à supprimer
     */
    public void supprimerBasicInputs(final TypeBasicInputs typeInputs, final long id) {
        if (null != typeInputs & id > 0) {
            modele.supprimerBasicInputs(typeInputs, id);
        }
    }
    
    /**
     * Demande au modèle d'ajouter un jeu à la base de données.
     * @param titreJeu le titre du jeu à ajouter
     * @param anneeSortie l'année de sortie du jeu à ajouter
     * @param listPlateformes la liste des plateformes à lier au jeu
     * @param listGenres la liste des genres à lier au jeu
     * @param idStudio l'identifiant du studio à lier au jeu
     */
    public void ajouterJeu(final String titreJeu, final int anneeSortie,
            final List<Long> listPlateformes, final List<Long> listGenres,
            final long idStudio, final long idEditeur) {
        if (null != titreJeu && !titreJeu.isEmpty() && anneeSortie > 0 && listPlateformes.size() > 0 && listGenres.size() > 0 && idStudio > 0) {
            modele.ajouterJeu(titreJeu, anneeSortie, listPlateformes, listGenres, idStudio, idEditeur);
        }
    }
    
    /**
     * Demande au modèle de modifier un jeu dans la base de données.
     * @param idJeu l'identifiant du jeu à modifier
     * @param nouveauTitre le nouveau titre du jeu
     * @param anneeSortie la nouvelle année de sortie du jeu
     * @param listPlateformes la liste des nouvelels plateformes à lier au jeu
     * @param listGenres la liste des nouveaux genres à lier au jeu
     * @param idStudio l'identifiant du studio à lier au jeu
     */
    public void modifierJeu(final long idJeu, final String nouveauTitre, final int anneeSortie,
            final List<Long> listPlateformes, final List<Long> listGenres, final long idStudio, final long idEditeur) {
        if (idJeu > 0 && null != nouveauTitre && !nouveauTitre.isEmpty() && anneeSortie > 0 && listPlateformes.size() > 0 && listGenres.size() > 0 && idStudio > 0) {
            modele.modifierJeu(idJeu, nouveauTitre, anneeSortie, listPlateformes, listGenres, idStudio, idEditeur);
        }
    }
    
    /**
     * Demande au modèle de supprimer un jeu.
     * @param idJeu l'identifiant du jeu à supprimer
     */
    public void supprimerJeu(final long idJeu) {
        if (idJeu > 0) {
            modele.supprimerJeu(idJeu);
        }
    }
    
    /**
     * Demande au modèle d'ajouter une run à la base de données.
     * @param titreRun le titre de la run à ajouter
     * @param idJeu l'identifiant du jeu sur lequel est la run
     */
    public void ajouterRun(final String titreRun, final long idJeu) {
        if (null != titreRun && !titreRun.isEmpty() && idJeu > 0) {
            modele.ajouterRun(titreRun, idJeu);
        }
    }
    
    /**
     * Demande au modèle de modifier une run dans la base de données.
     * @param idRun l'identifiant de la run à modifier
     * @param titreRun le nouveau titre de la run
     * @param idJeu le nouvel identifiant du jeu de la run
     */
    public void modifierRun(final long idRun, final String titreRun, final long idJeu) {
        if (idRun > 0 && null != titreRun && !titreRun.isEmpty() && idJeu > 0) {
            modele.modifierRun(idRun, titreRun, idJeu);
        }
    }
    
    /**
     * Demande au modèle de supprimer une run dans la base de données.
     * @param idRun l'identifian de la run à supprimer
     */
    public void supprimerRun(final long idRun) {
        if (idRun > 0) {
            modele.supprimerRun(idRun);
        }
    }
    
    /**
     * Demande au modèle d'ajouter un live dans la base de données.
     * @param dateDebut la date de début du live à ajouter
     * @param dateFin la date de fin du live à ajouter
     * @param morts le nombre de morts du live à ajouter
     * @param idRun l'identifiant de la run à lier au live
     */
    public void ajouterLive(final Date dateDebut, final Date dateFin, final int morts, final long idRun) {
        if (null != dateDebut && null != dateFin && idRun > 0) {
            
        }
    }
    
    /**
     * Demande au modèle de modifier un live dans la base de données.
     * @param idLive l'identifian du live à modifier
     * @param dateDebut la nouvelle date de début du live à modifier
     * @param dateFin la nouvelle date de fin du live à modifier
     * @param morts le nouveau nombre de morts du live à modifier
     * @param idRun le nouvel identifiant de la run du live à modifier
     */
    public void modifierLive(final long idLive, final Date dateDebut, final Date dateFin,
            final int morts, final long idRun) {
        if (idLive > 0 && null != dateDebut && null != dateFin && idRun > 0) {
            
        }
    }
    
    /**
     * Demande au live de supprimer un live dans la base de données.
     * @param idLive l'identifiant du live à supprimer
     */
    public void supprimerLive(final long idLive) {
        if (idLive > 0) {
            
        }
    }
    
    /**
     * Demande au modèle de se déconnecter de la base de données.
     */
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
