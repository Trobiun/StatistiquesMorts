/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

import statsmorts.constantes.TexteConstantesSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.BDD;
import statsmorts.classes.Connexion;
import statsmorts.classes.Editeur;
import statsmorts.classes.FillDataset;
import statsmorts.classes.Genre;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Run;
import statsmorts.classes.Studio;
import statsmorts.classes.TypeDatabase;
import statsmorts.classes.TypeGroup;
import statsmorts.observer.Observable;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;
import statsmorts.classes.TypeBasicInputs;
import statsmorts.constantes.TexteConstantesConnexion;

/**
 * Une classe pour gérer la BDD et la vue.
 * @author Robin
 */
public class StatsMortsModele implements Observable {
    
    //ATTRIBUTS
    /**
     * Les préférences.
     */
    private final Preferences preferences;
    /**
     * La connexion pour les requêtes.
     */
    private final Connexion connexion;
    /**
     * La BDD à gérer.
     */
    private BDD bdd;
    /**
     * L'unité de temps dans laquelle calculer les données pour le dataset.
     */
    private TimeUnit timeUnit;
    /**
     * Le TreeSet des lives pour afficher dans le dataset. Utilisation d'un
     * TreeSet pour trier les lives en fonction de leur date.
     */
    private final TreeSet<Live> livesForDataset;
    /**
     * Le titre pour le dataset.
     */
    private String titreForDataset;
    /**
     * Le TypeGroup pour l'affichage du dataset?
     */
    private TypeGroup typeGroup;
    
    /**
     * L'observer à qui notifier les changements dans le modèle.
     */
    private Observer observer;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un modèle avec les préférences.
     * @param prefs les préférences
     */
    public StatsMortsModele(Preferences prefs) {
        connexion = new Connexion();
        preferences = prefs;
        timeUnit = preferences.getAffichageTemps().getTimeUnit();
        typeGroup = preferences.getAffichageGroup();
        livesForDataset = new TreeSet();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne un booléen pour savoir si le modèle à un Observer ou non.
     * @return un booléen à vrai si le modèle à un observer, faux sinon
     */
    public boolean hasObserver() {
        return null != observer;
    }
    
    
    //MUTATEURS
    /**
     * Crée une base de données fichier.
     * @param pathBDD le chemin de la base de données à créer
     */
    public void creerBDD(String pathBDD) {
        deconnecter();
        bdd = BDD.creerBDD(connexion,pathBDD);
        actualiser();
    }
    
    /**
     * Se connecte à une base de données fichier.
     * @param database la base de données
     */
    public void connecter(String database) {
        deconnecter();
        bdd = new BDD(connexion,database);
        actualiser();
    }
    
    /**
     * Se connecte à une base de données serveur.
     * @param type le type de la base de données
     * @param serveur le serveur auquel se connecter (+ le nom de la base de données)
     * @param user le no d'utilisateur à utiliser
     * @param password le mot de passe à utiliser
     */
    public void connecter(TypeDatabase type, String serveur, String user, char[] password) {
        deconnecter();
        bdd = new BDD(connexion, type, serveur, user, password);
        actualiser();
    }
    
    /**
     * Se déconnecte de la base de données.
     */
    public void deconnecter() {
        connexion.deconnecter();
    }
    
    
    //MÉTHODES DE GESTION DE LA BASE DE DONNÉES
    //GESTION BASIC INPUTS (PLATEFORMES, GENRES, STUDIOS)
    /**
     * Ajoute un objet "basique" (plateforme/genre/studio) dans la base de données.
     * @param typeBasicInputs le type d'obejt à ajouter
     * @param nom le nom de l'objet à ajouter
     */
    public void ajouterBasicInputs(TypeBasicInputs typeBasicInputs, String nom) {
        String table = null;
        String table_nom = null;
        switch (typeBasicInputs) {
            case PLATEFORMES:
                table = TexteConstantesSQL.TABLE_PLATEFORMES;
                table_nom = TexteConstantesSQL.TABLE_PLATEFORMES_NOM;
                break;
            case GENRES:
                table = TexteConstantesSQL.TABLE_GENRES;
                table_nom = TexteConstantesSQL.TABLE_GENRES_NOM;
                break;
            case STUDIOS:
                table = TexteConstantesSQL.TABLE_STUDIOS;
                table_nom = TexteConstantesSQL.TABLE_STUDIOS_NOM;
                break;
            default:
        }
        if (null != table && null != table_nom) {
            final String requete = TexteConstantesSQL.INSERT_INTO + " " + table
                    + "(" + table_nom + ") " + TexteConstantesSQL.VALUES + " (?)";
            int rows = connexion.executerPreparedUpdate(requete, nom);
            try {
                ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
                if (resultID.next()) {
                    switch (typeBasicInputs) {
                        case PLATEFORMES:
                            Plateforme plateforme = new Plateforme(resultID.getInt(1), nom);
                            bdd.ajouterPlateforme(plateforme);
                            notifyAddPlateforme(plateforme);
                            break;
                        case GENRES:
                            Genre genre = new Genre(resultID.getInt(1), nom);
                            bdd.ajouterGenre(genre);
                            notifyAddGenre(genre);
                            break;
                        case STUDIOS:
                            Studio studio = new Studio(resultID.getInt(1), nom);
                            bdd.ajouterStudio(studio);
                            notifyAddStudio(studio);
                            break;
                        default:
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Modifie un objet "basique" (plateforme/genre/studio) dans la base de données.
     * @param typeBasicInputs le type de l'objet à modifier
     * @param id l'identifiant de l'obejt à modifier
     * @param nom le nom de l'objet à modifier
     */
    public void modifierBasicInputs(TypeBasicInputs typeBasicInputs, long id, String nom) {
        String table = null;
        String table_id = null;
        String table_nom = null;
        switch (typeBasicInputs) {
            case PLATEFORMES:
                table = TexteConstantesSQL.TABLE_PLATEFORMES;
                table_id = TexteConstantesSQL.TABLE_PLATEFORMES_ID;
                table_nom = TexteConstantesSQL.TABLE_PLATEFORMES_NOM;
                break;
            case GENRES:
                table = TexteConstantesSQL.TABLE_GENRES;
                table_id = TexteConstantesSQL.TABLE_GENRES_ID;
                table_nom = TexteConstantesSQL.TABLE_GENRES_NOM;
                break;
            case STUDIOS:
                table = TexteConstantesSQL.TABLE_STUDIOS;
                table_id = TexteConstantesSQL.TABLE_STUDIOS_ID;
                table_nom = TexteConstantesSQL.TABLE_STUDIOS_NOM;
                break;
            default:
        }
        if (null != table && null != table_nom) {
            final String requete = TexteConstantesSQL.UPDATE + " "
                + table + " " + TexteConstantesSQL.SET + " " + table_nom
                + " = ? " + TexteConstantesSQL.WHERE + " " + table_id + " = ?";
            int rows = connexion.executerPreparedUpdate(requete, nom, TexteConstantesConnexion.LONG + id);
            if (rows > 0) {
                switch (typeBasicInputs) {
                    case PLATEFORMES:
                        bdd.getPlateforme(id).renommer(nom);
                        break;
                    case GENRES:
                        bdd.getGenre(id).renommer(nom);
                        break;
                    case STUDIOS:
                        bdd.getStudio(id).renommer(nom);
                        break;
                    default:
                }
            }
        }
    }
    
    /**
     * Supprime un objet "basique" (plateforme/genre) dans la base de données.
     * Excepté pour les studios : il faut supprimer les jeux liés au studio AVANT.
     * @param typeBasicInputs le type de l'objet à supprimer.
     * @param id l'identifiant de l'objet à modifier
     */
    public void supprimerBasicInputs(TypeBasicInputs typeBasicInputs, long id) {
//        if(typeBasicInputs.equals(TypeBasicInputs.STUDIOS)) {
//            supprimerStudio(id);
//            return;
//        }
        String table = null;
        String table_id = null;
        switch (typeBasicInputs) {
            case PLATEFORMES:
                table = TexteConstantesSQL.TABLE_PLATEFORMES;
                table_id = TexteConstantesSQL.TABLE_PLATEFORMES_ID;
                break;
            case GENRES:
                table = TexteConstantesSQL.TABLE_GENRES;
                table_id = TexteConstantesSQL.TABLE_GENRES_ID;
                break;
            case STUDIOS:
                table = TexteConstantesSQL.TABLE_STUDIOS;
                table_id = TexteConstantesSQL.TABLE_STUDIOS_ID;
                break;
            default:
        }
        if (null != table) {
            final String requete = TexteConstantesSQL.DELETE_FROM + " " + table
                + " " + TexteConstantesSQL.WHERE + " " + table_id + " = ?";
            final int rows = connexion.executerPreparedUpdate(requete, TexteConstantesConnexion.LONG + id);
            if (rows > 0) {
                switch (typeBasicInputs) {
                    case PLATEFORMES:
                        bdd.supprimerPlateforme(id);
                        notifyRemovePlateforme(id);
                        break;
                    case GENRES:
                        bdd.supprimerGenre(id);
                        notifyRemoveGenre(id);
                        break;
                    case STUDIOS:
                        long start = System.currentTimeMillis();
//                        String requeteSupprJeux = TexteConstantesSQL.DELETE_FROM + " "
//                                + TexteConstantesSQL.TABLE_JEUX + " " + TexteConstantesSQL.INNER_JOIN
//                                + " " + TexteConstantesSQL.TABLE_JEU_STUDIO + " "
//                                + TexteConstantesSQL.ON + " " + TexteConstantesSQL.TABLE_JEUX_ID
//                                + " = " + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU
//                                + " " + TexteConstantesSQL.WHERE + " "
//                                + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO + " = ?";
//                        System.out.println(requeteSupprJeux);
//                        connexion.executerPreparedUpdate(requeteSupprJeux, "(long)" + id);
                        Set<Entry<Long, Jeu>> setJeux = bdd.getStudio(id).getJeux().entrySet();
//                        System.out.println(bdd.getStudio(id));
//                        System.out.println(bdd.getStudio(id).getTitre());
//                        System.out.println(bdd.getStudio(id).getJeux().size());
//                        System.out.println(bdd.getStudio(id).getLivesList().size());
                        Iterator<Entry<Long, Jeu>> it = setJeux.iterator();
//                        SwingUtilities.invokeLater(new Runnable() {
//                            @Override
//                            public void run() {
                                while (it.hasNext()) {
                                    Entry<Long, Jeu> entry = it.next();
                                    it.remove();
                                    bdd.supprimerJeu(entry.getKey());
                                    notifyRemoveJeu(entry.getKey());
//                            supprimerJeu(entry.getKey());
                                }
                                bdd.supprimerStudio(id);
                                notifyRemoveStudio(id);
//                            }
//                        });
                        
                        
                        long end = System.currentTimeMillis();
                        System.out.println((end - start) + "ms");
                        break;
                    default:
                }
            }
        }
    }
    
    /**
     * Supprime un studio et tous les jeux liés au studio.
     * @param idStudio l'identifiant du studio
     */
    public void supprimerStudio(long idStudio) {
        long start = System.currentTimeMillis();
        String idStudioRequete = TexteConstantesConnexion.LONG + idStudio;
        //on supprime d'abord les jeux liés au studio
        String requeteSupprJeux = TexteConstantesSQL.DELETE_FROM + " "
                + TexteConstantesSQL.TABLE_JEUX + " " + TexteConstantesSQL.WHERE
                + " " + TexteConstantesSQL.TABLE_JEUX_ID_STUDIO + " = ?";
        connexion.executerPreparedUpdate(requeteSupprJeux, idStudioRequete);
        Set<Entry<Long, Jeu>> setJeux = bdd.getStudio(idStudio).getJeux().entrySet();
        Iterator<Entry<Long, Jeu>> it = setJeux.iterator();
        System.out.println(setJeux);
        while (it.hasNext()) {
            Entry<Long, Jeu> entry = it.next();
            it.remove();
            bdd.supprimerJeu(entry.getKey());
            notifyRemoveJeu(entry.getKey());
        }
        //on supprime le studio en lui-même
        String requeteSupprStudio = TexteConstantesSQL.DELETE_FROM + " "
                + TexteConstantesSQL.TABLE_STUDIOS + " " + TexteConstantesSQL.WHERE
                + " " + TexteConstantesSQL.TABLE_STUDIOS_ID + " = ?";
        connexion.executerPreparedUpdate(requeteSupprStudio, idStudioRequete);
        bdd.supprimerStudio(idStudio);
        notifyRemoveStudio(idStudio);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
    
    /**
     * Ajoute un jeu dans la base de données.
     * @param titre le titre du jeu
     * @param anneeSortie l'année de sortie du jeu
     * @param listPlateformes la liste des plateformes à lier au jeu
     * @param listGenres la liste des genres à lier au jeu
     * @param idStudio le studio à lier au jeu
     * @param idEditeur l'éditeur à lier au jeu
     */
    public void ajouterJeu(String titre, int anneeSortie, List<Long> listPlateformes, List<Long> listGenres, long idStudio, long idEditeur) {
        String requete = TexteConstantesSQL.INSERT_INTO + " " + TexteConstantesSQL.TABLE_JEUX
                + "(" + TexteConstantesSQL.TABLE_JEUX_TITRE + "," + TexteConstantesSQL.TABLE_JEUX_ANNEE_SORTIE
                + "," + TexteConstantesSQL.TABLE_JEUX_ID_STUDIO
                + "," + TexteConstantesSQL.TABLE_JEUX_ID_EDITEUR
                + ") " + TexteConstantesSQL.VALUES + "(?,?,?,?)";
        int rowsInserted = connexion.executerPreparedUpdate(requete, titre,
                TexteConstantesConnexion.INT + anneeSortie,
                TexteConstantesConnexion.LONG + idStudio,
                TexteConstantesConnexion.LONG + idEditeur);
        try {
            ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
            if (resultID.next()) {
                long idJeu = resultID.getInt(1);
                Studio studio = bdd.getStudio(idStudio);
                Editeur editeur = bdd.getEditeur(idEditeur);
                Jeu jeu = new Jeu(idJeu,titre,anneeSortie,studio,editeur);
                int rows;
                //ajout des plateformes au jeu et fait le lien dans la base de données
                String requetePlateformes = TexteConstantesSQL.INSERT_INTO + " "
                        + TexteConstantesSQL.TABLE_JEU_PLATEFORME
                        + "(" + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU
                        + "," + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME
                        + ") " + TexteConstantesSQL.VALUES + "(?,?)";
                for (Long idPlateforme : listPlateformes) {
                    rows = connexion.executerPreparedUpdate(requetePlateformes, TexteConstantesConnexion.LONG + idJeu, TexteConstantesConnexion.LONG + idPlateforme);
                    if (rows > 0) {
                        jeu.ajouterPlateforme(bdd.getPlateforme(idPlateforme));
                        bdd.getPlateforme(idPlateforme).ajouterJeu(jeu);
                    }
                }
                //ajout des genres au jeu et fait le lien dans la base de données
                String requeteGenres = TexteConstantesSQL.INSERT_INTO + " " + TexteConstantesSQL.TABLE_JEU_GENRE
                        + "(" + TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU + "," + TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE
                        + ") " + TexteConstantesSQL.VALUES + "(?,?)";
                for (Long idGenre : listGenres) {
                    rows = connexion.executerPreparedUpdate(requeteGenres, TexteConstantesConnexion.LONG + idJeu, TexteConstantesConnexion.LONG + idGenre);
                    if (rows > 0) {
                        jeu.ajouterGenre(bdd.getGenre(idGenre));
                        bdd.getGenre(idGenre).ajouterJeu(jeu);
                    }
                }
                if (rowsInserted > 0) {
                    jeu.setStudio(bdd.getStudio(idStudio));
                    bdd.getStudio(idStudio).ajouterJeu(jeu);
                    bdd.ajouterJeu(jeu);
                    notifyAddJeu(jeu);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retourne une requête pour supprimer des enregistrements dans une table.
     * @param table le nom de table dans laquelle supprimer
     * @param champID le nom du champ d'identifiant de la table
     * @return une requête pour supprimer des enregistrements
     */
    private String getClearRequete(String table, String champID) {
        return TexteConstantesSQL.DELETE_FROM + " " + table + " "
                + TexteConstantesSQL.WHERE + " " + champID + " = ?";
    }
    /**
     * Modifie un jeu dans la base de données.
     * @param idJeu l'identifiant du jeu à modifier
     * @param titre le nouveau titre du jeu à modifier
     * @param anneeSortie la nouvelle année de sortie du jeu
     * @param listPlateformes la nouvelle liste de plateformes à lier au jeu
     * @param listGenres la nouvelle liste des genres à lier au jeu
     * @param idStudio le nouveau studio à lier au jeu
     * @param idEditeur le nouvel éditeur à lier au jeu
     */
    public void modifierJeu(final long idJeu, final String titre, final int anneeSortie, final List<Long> listPlateformes,
            final List<Long> listGenres, final long idStudio, final long idEditeur) {
        Jeu jeu = bdd.getJeu(idJeu);
        String idStudioRequete = TexteConstantesConnexion.LONG + idStudio;
        String idEditeurRequete = TexteConstantesConnexion.LONG + idEditeur;
        String idJeuRequete = TexteConstantesConnexion.LONG + idJeu;
        //modifie les champs directs du jeu
        String requete = TexteConstantesSQL.UPDATE + " " + TexteConstantesSQL.TABLE_JEUX
                + " " + TexteConstantesSQL.SET + " "
                + TexteConstantesSQL.TABLE_JEUX_TITRE + " = ? , "
                + TexteConstantesSQL.TABLE_JEUX_ANNEE_SORTIE + " = ? , "
                + TexteConstantesSQL.TABLE_JEUX_ID_STUDIO + " = ? "
                + TexteConstantesSQL.TABLE_JEUX_ID_EDITEUR + " = ? "
                + TexteConstantesSQL.WHERE + " " + TexteConstantesSQL.TABLE_JEUX_ID + " = ?";
        int rows = connexion.executerPreparedUpdate(requete, titre, TexteConstantesConnexion.INT + anneeSortie, idStudioRequete, idEditeurRequete, idJeuRequete);
        if (rows > 0) {
            jeu.renommer(titre);
            jeu.setAnneeSortie(anneeSortie);
            jeu.setStudio(bdd.getStudio(idStudio));
            jeu.setEditeur(bdd.getEditeur(idEditeur));
            
            //suppression de toutes les occurences du jeu dans JeuPlateforme
            String requeteClearJeuPlateforme = getClearRequete(TexteConstantesSQL.TABLE_JEU_PLATEFORME, TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU);
            rows = connexion.executerPreparedUpdate(requeteClearJeuPlateforme, idJeuRequete);
            if (rows > 0) {
                jeu.clearPlateformes();
            }
            //rajoute toutes les nouvelles plateformes associées au jeu dans JeuPlateforme
            String requeteAddJeuPlateforme = TexteConstantesSQL.INSERT_INTO + " "
                    + TexteConstantesSQL.TABLE_JEU_PLATEFORME
                    + "(" + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU + ","
                    + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME
                    + ") " + TexteConstantesSQL.VALUES + "(?,?)";
            for (Long idPlateforme : listPlateformes) {
                rows = connexion.executerPreparedUpdate(requeteAddJeuPlateforme, idJeuRequete, TexteConstantesConnexion.LONG + idPlateforme);
                if (rows > 0) {
                    jeu.ajouterPlateforme(bdd.getPlateforme(idPlateforme));
                }
            }
            //suppression de toutes les occurences du jeu dans JeuGenre
            String requeteClearJeuGenre = getClearRequete(TexteConstantesSQL.TABLE_JEU_GENRE, TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU);
            rows = connexion.executerPreparedUpdate(requeteClearJeuGenre, idJeuRequete);
            if (rows > 0) {
                jeu.clearGenres();
            }
            //rajoute tous les nouveaux genres associés au jeu dans JeuGenre
            String requeteAddJeuGenre = TexteConstantesSQL.INSERT_INTO + " " + TexteConstantesSQL.TABLE_JEU_GENRE
                    + "(" + TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU + "," + TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE
                    + ") " + TexteConstantesSQL.VALUES + "(?,?)";
            for (Long idGenre : listGenres) {
                rows = connexion.executerPreparedUpdate(requeteAddJeuGenre, idJeuRequete, TexteConstantesConnexion.LONG + idGenre);
                if (rows > 0) {
                    jeu.ajouterGenre(bdd.getGenre(idGenre));
                }
            }
            
            notifyRemoveJeu(idJeu);
            notifyAddJeu(jeu);
        }
    }
    
    /**
     * Supprime un jeu dans la base de données.
     * @param idJeu l'identifiant du jeu à supprimer.
     */
    public void supprimerJeu(long idJeu) {
        String requete = TexteConstantesSQL.DELETE_FROM + " " + TexteConstantesSQL.TABLE_JEUX
                + " " + TexteConstantesSQL.WHERE + " " + TexteConstantesSQL.TABLE_JEUX_ID
                + " = ?";
        int rows = connexion.executerPreparedUpdate(requete,TexteConstantesConnexion.LONG + idJeu);
        if (rows > 0) {
            bdd.supprimerJeu(idJeu);
            notifyRemoveJeu(idJeu);
        }
    }
    
    /**
     * Ajoute une run dans la base de données.
     * @param titreRun le titre de la run
     * @param idJeu l'identifiant du jeu de la run
     */
    public void ajouterRun(String titreRun, long idJeu) {
        String requete = TexteConstantesSQL.INSERT_INTO + " " + TexteConstantesSQL.TABLE_RUNS
                + "(" + TexteConstantesSQL.TABLE_RUNS_ID_JEU + ","
                + TexteConstantesSQL.TABLE_RUNS_TITRE + ") " + TexteConstantesSQL.VALUES
                + "(?,?)";
        int rows = connexion.executerPreparedUpdate(requete, TexteConstantesConnexion.LONG + idJeu, titreRun);
        if (rows > 0) {
            try {
                ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
                if (resultID.next()) {
                    long idRun = resultID.getLong(1);
                    Run run = new Run(idRun,titreRun);
                    run.setJeu(bdd.getJeu(idJeu));
                    bdd.ajouterRun(run);
                    notifyAddRun(run);
                }
            } catch (SQLException ex) {
                Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Modifie une run dans la base de données.
     * @param idRun l'identifiant de la run à modifier
     * @param titreRun le nouveau titre de la run
     * @param idJeu l'identifiant du nouveau jeu à lier à la run
     */
    public void modifierRun(long idRun, String titreRun, long idJeu) {
        
    }
    
    /**
     * Supprime une run dans la base de données.
     * @param idRun l'identifiant de la run à supprimer
     */
    public void supprimerRun(long idRun) {
        String requete = TexteConstantesSQL.DELETE_FROM + " "+ TexteConstantesSQL.TABLE_RUNS
                + " " + TexteConstantesSQL.WHERE + " " + TexteConstantesSQL.TABLE_RUNS_ID
                + " = ?";
        int rows = connexion.executerPreparedUpdate(requete, "(long)" + idRun);
        if(rows > 0) {
            bdd.supprimerRun(idRun);
            notifyRemoveRun(idRun);
        }
    }
    
    /**
     * Ajoute un live dans la base de données.
     * @param dateDebut la date de début du live
     * @param dateFin la date de fin du live
     * @param morts le nombre de morts sur ce live
     * @param idRun l'identifiant de la run du live
     */
    public void ajouterLive(Date dateDebut, Date dateFin, int morts, long idRun) {
        
    }
    
    /**
     * Modifie un live dans la base de données.
     * @param idLive l'identifiant du lvie à modifier
     * @param dateDebut la nouvelle date de début du live
     * @param dateFin la nouvelle date de fin du live
     * @param morts le nouveau nombre de morts sur ce live
     * @param idRun le nouvel identifiant de la run du live
     */
    public void modifierLive(long idLive, Date dateDebut, Date dateFin, int morts, long idRun) {
        
    }
    
    /**
     * Supprime un live dans la base de données.
     * @param idLive l'identifiant du live à supprimer
     */
    public void supprimerLive(long idLive) {
        
    }
    
    
    /**
     * Change l'unité de temps dans laquelle afficher et calculer les durées
     * pour le graphique.
     * @param unit la nouvelle unité de temps
     */
    public void setTimeUnit(TimeUnit unit) {
        if (!this.timeUnit.equals(unit)) {
            this.timeUnit = unit;
            if (!livesForDataset.isEmpty()) {
                actualiserDataset();
            }
        }
    }
    
    /**
     * Actualise la base de données (objets) puis notifie l'observer des changements.
     */
    public void actualiser() {
        try {
            bdd.actualiserBDD(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    observer.clear(typeGroup);
                    Set<Entry<Long, Plateforme>> plateformes = bdd.getPlateformes().entrySet();
                    for (Entry<Long, Plateforme> entryPlateforme : plateformes) {
                        Plateforme plateforme = entryPlateforme.getValue();
                        notifyAddPlateforme(plateforme);
                    }
                    Set<Entry<Long, Genre>> genres = bdd.getGenres().entrySet();
                    for (Entry<Long, Genre> entryGenre : genres) {
                        Genre genre = entryGenre.getValue();
                        notifyAddGenre(genre);
                    }
                    Set<Entry<Long, Studio>> studios = bdd.getStudios().entrySet();
                    for (Entry<Long, Studio> entryStudio : studios) {
                        Studio studio = entryStudio.getValue();
                        notifyAddStudio(studio);
                    }
                    Set<Entry<Long, Jeu>> set = bdd.getJeux().entrySet();
                    for (Entry<Long, Jeu> entryJeu : set) {
                        Jeu jeu = entryJeu.getValue();
                        notifyAddJeu(jeu);
//                Map<Long, Run> runsMap = jeu.getRuns();
//                Set<Entry<Long, Run>> runsSet = runsMap.entrySet();
//                for (Entry<Long, Run> runEntry : runsSet) {
//                    Run run = runEntry.getValue();
//                    notifyAddRun(run);
//                    Map<Long, Live> livesMap = run.getLives();
//                    Set<Entry<Long, Live>> livesSet = livesMap.entrySet();
//                    for (Entry<Long, Live> liveEntry : livesSet) {
//                        Live live = liveEntry.getValue();
//                        notifyAddLive(live);
//                    }
//                }
                    }
                }
            });
//            observer.clear(this.typeGroup);
//            Set<Entry<Long, Plateforme>> plateformes = bdd.getPlateformes().entrySet();
//            for (Entry<Long, Plateforme> entryPlateforme : plateformes) {
//                Plateforme plateforme = entryPlateforme.getValue();
//                notifyAddPlateforme(plateforme);
//            }
//            Set<Entry<Long, Genre>> genres = bdd.getGenres().entrySet();
//            for (Entry<Long, Genre> entryGenre : genres) {
//                Genre genre = entryGenre.getValue();
//                notifyAddGenre(genre);
//            }
//            Set<Entry<Long, Studio>> studios = bdd.getStudios().entrySet();
//            for (Entry<Long, Studio> entryStudio : studios) {
//                Studio studio = entryStudio.getValue();
//                notifyAddStudio(studio);
//            }
//            Set<Entry<Long, Jeu>> set = bdd.getJeux().entrySet();
//            for (Entry<Long, Jeu> entryJeu : set) {
//                Jeu jeu = entryJeu.getValue();
//                notifyAddJeu(jeu);
////                Map<Long, Run> runsMap = jeu.getRuns();
////                Set<Entry<Long, Run>> runsSet = runsMap.entrySet();
////                for (Entry<Long, Run> runEntry : runsSet) {
////                    Run run = runEntry.getValue();
////                    notifyAddRun(run);
////                    Map<Long, Live> livesMap = run.getLives();
////                    Set<Entry<Long, Live>> livesSet = livesMap.entrySet();
////                    for (Entry<Long, Live> liveEntry : livesSet) {
////                        Live live = liveEntry.getValue();
////                        notifyAddLive(live);
////                    }
////                }
//            }
        }
    }
    
    public void actualiserAffichage() {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    observer.clear(typeGroup);
                    Set<Entry<Long, Plateforme>> plateformes = bdd.getPlateformes().entrySet();
                    for (Entry<Long, Plateforme> entryPlateforme : plateformes) {
                        Plateforme plateforme = entryPlateforme.getValue();
                        notifyAddPlateforme(plateforme);
                    }
                    Set<Entry<Long, Genre>> genres = bdd.getGenres().entrySet();
                    for (Entry<Long, Genre> entryGenre : genres) {
                        Genre genre = entryGenre.getValue();
                        notifyAddGenre(genre);
                    }
                    Set<Entry<Long, Studio>> studios = bdd.getStudios().entrySet();
                    for (Entry<Long, Studio> entryStudio : studios) {
                        Studio studio = entryStudio.getValue();
                        notifyAddStudio(studio);
                    }
                    Set<Entry<Long, Jeu>> set = bdd.getJeux().entrySet();
                    for (Entry<Long, Jeu> entryJeu : set) {
                        Jeu jeu = entryJeu.getValue();
                        notifyAddJeu(jeu);
//                Map<Long, Run> runsMap = jeu.getRuns();
//                Set<Entry<Long, Run>> runsSet = runsMap.entrySet();
//                for (Entry<Long, Run> runEntry : runsSet) {
//                    Run run = runEntry.getValue();
//                    notifyAddRun(run);
//                    Map<Long, Live> livesMap = run.getLives();
//                    Set<Entry<Long, Live>> livesSet = livesMap.entrySet();
//                    for (Entry<Long, Live> liveEntry : livesSet) {
//                        Live live = liveEntry.getValue();
//                        notifyAddLive(live);
//                    }
//                }
                    }
                }
            });
        }
    }
    
    /**
     * Remplit le Plate
     * @param idPlateforme 
     */
    public void fillPlateformePanel(long idPlateforme) {
        notifyFillPlateforme(idPlateforme);
    }
    
    /**
     * 
     * @param idGenre 
     */
    public void fillGenrePanel(long idGenre) {
        notifyFillGenre(idGenre);
    }
    
    /**
     * 
     * @param idStudio 
     */
    public void fillStudiPanel(long idStudio) {
        notifyFillStudio(idStudio);
    }
    
    /**
     * 
     * @param idJeu 
     */
    public void fillJeuPanel(long idJeu) {
        notifyFillJeu(idJeu);
    }
    
    /**
     * 
     * @param idRun 
     */
    public void fillRunPanel(long idRun) {
        notifyFillRun(idRun);
    }
    
    public void fillRunOnLivePanels(long idRun) {
        notifyFillRunOnLivePanels(idRun);
    }
    
    /**
     * 
     * @param idJeu 
     */
    public void selectJeuRunPanels(long idJeu) {
        notifySelectJeuRunPanels(idJeu);
    }
    
    public void selectJeuLivePanels(long idJeu) {
        notifySelectJeuLivePanels(idJeu);
    }
    
    public void selectRunLivePanels(long idRun) {
        notifySelectRunLivePanels(idRun);
    }
    
    /**
     * 
     * @param idLive 
     */
    public void fillLivePanel(long idLive) {
        notifyFillLive(idLive);
    }
    
    /**
     * 
     * @param idRun 
     */
    public void fillLivePanelRun(long idRun) {
        notifyFillRunOnLivePanels(idRun);
    }
    
    /**
     * 
     * @param idJeu 
     */
    public void setSelectionPossibleRun(final long idJeu) {
        HashMap<Long,Run> runsPossibles = bdd.getJeu(idJeu).getRuns();
        Set<Entry<Long,Run>>setEntries = runsPossibles.entrySet();
        for (Entry<Long,Run> runEntry : setEntries) {
            notifyAddRunPosssible(runEntry.getValue());
        }
    }
    
    /**
     * Crée le dataset pour le graphique.
     * @param titre le titre du dataset/graphique à créer
     * @param nodes la liste des objets avec lesquels créer le dataset
     */
    public void createDataset(final String titre,final  ArrayList<FillDataset> nodes) {
        livesForDataset.clear();
        for (FillDataset node : nodes) {
            livesForDataset.addAll(node.getLivesList());
        }
        this.titreForDataset = titre;
        actualiserDataset();
    }
    
    /**
     * Actualise le dataset avec les informations de l'arryalist "livesForDataset".
     */
    public void actualiserDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        float moyenne = 0, sommeDureeVie = 0, sommeMoyennes = 0, bossParLive = 0
                , tempsParBoss = 0;
        int count = 0, sommeMorts = 0, sommeBoss = 0;
        float sommeTime = 0;
        for (Live live : livesForDataset) {
            sommeTime += live.getDuration(timeUnit);
            sommeMorts += live.getMorts();
            sommeBoss += live.getBoss();
            sommeDureeVie += live.getDureeVieMoyenne(timeUnit);
            count++;
            moyenne = sommeDureeVie / (float)(count);
            bossParLive = sommeBoss / (float)(count);
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, timeUnit, false);
            dataset.addValue(moyenne, "Moyenne des durées de vie", live.getDateDebut());
            dataset.addValue(bossParLive, "Boss par live", live.getDateDebut());
        }
        if (livesForDataset.size() > 1) {
            float dureeVieMoyenneTotale = (float)(sommeTime) / (float)(sommeMorts + 1);
            float bossParLiveTotal = /*(sommeBoss == 0) ? 0 : */(float)(sommeBoss) / (float)(count);
            float mortsParBossTotal = (sommeBoss == 0) ? 0 :(float)(sommeMorts) / (float)(sommeBoss);
            tempsParBoss =  (sommeBoss == 0) ? 0 : sommeTime / (float)(sommeBoss);
            moyenne = sommeMoyennes / (float)(count);
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(sommeBoss, "Boss", "Total");
            dataset.addValue(mortsParBossTotal, "Morts par boss", "Total");
            dataset.addValue(tempsParBoss, "Temps par boss", "Total");
            dataset.addValue(bossParLiveTotal, "Boss par live","Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie", "Total");
        }
        notifyDataset(titreForDataset, dataset);
    }
    
    
    //OBSERVER
    /**
     * {@inheritDoc} 
     */
    @Override
    public void setObserver(final Observer observer) {
        this.observer = observer;
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void setGroup(final TypeGroup type) {
        this.typeGroup = type;
        this.actualiserAffichage();
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddPlateforme(final Plateforme plateforme) {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    observer.addPlateforme(plateforme);
                }
            });
            
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddGenre(final Genre genre) {
        if(hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    observer.addGenre(genre);
                }
            });
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddStudio(final Studio studio) {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    observer.addStudio(studio);
                }
            });
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddJeu(final Jeu jeu) {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    observer.addJeu(jeu);
                    Map<Long, Run> runsMap = jeu.getRuns();
                    Set<Entry<Long, Run>> runsSet = runsMap.entrySet();
                    for (Entry<Long, Run> runEntry : runsSet) {
                        Run run = runEntry.getValue();
                        notifyAddRun(run);
                        Map<Long, Live> livesMap = run.getLives();
                        Set<Entry<Long, Live>> livesSet = livesMap.entrySet();
                        for (Entry<Long, Live> liveEntry : livesSet) {
                            Live live = liveEntry.getValue();
                            notifyAddLive(live);
                        }
                    }
                }
            });
        }
    }
    
    public void notifyAddRunPosssible(final Run run) {
        if (hasObserver()) {
            observer.addPossibleRunOnRunPanels(run.getID());
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddRun(final Run run) {
        if (hasObserver()) {
            observer.addRun(run);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddLive(final Live live) {
        if (hasObserver()) {
            final Run run = live.getRun();
            observer.addLive(live);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemovePlateforme(final long idPlateforme) {
        if (hasObserver()) {
            observer.removePlateforme(idPlateforme);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveGenre(final long idGenre) {
        if (hasObserver()) {
            observer.removeGenre(idGenre);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveStudio(final long idStudio) {
        if (hasObserver()) {
            observer.removeStudio(idStudio);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveJeu(final long idJeu) {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    observer.removeJeu(idJeu);
                }
                
            });
            
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveRun(final long idRun) {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                     observer.removeRun(idRun);
                }
            });
           
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveLive(final long idLive) {
        if (hasObserver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                     observer.removeLive(idLive);
                }
            });
            
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyDataset(final String titre, final DefaultCategoryDataset dataset) {
        if (hasObserver() && null != dataset) {
            observer.updateDataset(titre,dataset);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillPlateforme(final long idPlateforme) {
        if (hasObserver()) {
            final Plateforme plateforme = bdd.getPlateformes().get(idPlateforme);
            if (null != plateforme) {
                observer.fillPlateforme(plateforme.getTitre());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillGenre(final long idGenre) {
        if (hasObserver()) {
            final Genre genre = bdd.getGenres().get(idGenre);
            if (null != genre) {
                observer.fillGenre(genre.getTitre());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillStudio(final long idStudio) {
        if (hasObserver()) {
            final Studio studio = bdd.getStudios().get(idStudio);
            if (null != studio) { 
                observer.fillStudio(studio.getTitre());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillJeu(final long idJeu) {
        if (hasObserver()) {
            final Jeu jeu = bdd.getJeux().get(idJeu);
            if (null != jeu && jeu.getStudio() != null) {
                observer.fillJeu(jeu.getTitre(),jeu.getAnneeSortie(),jeu.getPlateformesID(),jeu.getGenresID(),jeu.getStudio().getID());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillRun(final long idRun) {
        if (hasObserver()) {
            final Run run = bdd.getRun(idRun);
            if (null != run) {
                observer.fillRun(run.getTitre());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifySelectJeuRunPanels(final long idJeu) {
        if (hasObserver()) {
            final Jeu jeu = bdd.getJeu(idJeu);
            if (null != jeu) {
                observer.removeAllRunsOnRunPanels();
                observer.fillJeuOnRunPanels(jeu.getTitre());
                Set<Entry<Long,Run>> mapRuns = jeu.getRuns().entrySet();
                for(Entry<Long,Run> run : mapRuns) {
                    observer.addPossibleRunOnRunPanels(run.getValue().getID());
                }
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifySelectJeuLivePanels(long idJeu) {
        if (hasObserver()) {
            Jeu jeu = bdd.getJeu(idJeu);
            if (null != jeu) {
                observer.removeAllLivesOnLivePanels();
                observer.removeAllRunsOnLivePanels();
                observer.fillJeuOnLivePanels(jeu.getTitre());
                Set<Entry<Long,Run>> mapRuns = jeu.getRuns().entrySet();
                for(Entry<Long,Run> run : mapRuns) {
                    observer.addPossibleRunOnLivePanels(run.getValue().getID());
                }
            }
        }
    }
    
    @Override
    public void notifySelectRunLivePanels(long idRun) {
        if (hasObserver()) {
            Run run = bdd.getRun(idRun);
            if (null != run) {
                observer.removeAllLivesOnLivePanels();
                observer.fillRunOnLivePanels(run.getTitre());
                Set<Entry<Long,Live>> mapLives = run.getLives().entrySet();
                for (Entry<Long,Live> live : mapLives) {
                    observer.addPossibleLiveOnLivePanels(live.getValue().getID());
                }
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillLive(long idLive) {
        if (hasObserver()) {
            final Live live = bdd.getLive(idLive);
            if (null != live) {
                observer.fillLive(live.getRun().getID(), live.getDateDebut(), live.getDateFin(), live.getMorts(), live.getBoss());
            }
        }
    }
    
    @Override
    public void notifyFillRunOnLivePanels(long idRun) {
        if (hasObserver()) {
            Run run = bdd.getRun(idRun);
            if (null != run) {
                observer.fillRunOnLivePanels(run.getTitre());
            }
        }
    }
    
}
