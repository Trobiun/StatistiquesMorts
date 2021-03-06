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
        if(typeBasicInputs.equals(TypeBasicInputs.STUDIOS)) {
            supprimerStudio(id);
            return;
        }
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
//            case STUDIOS:
//                table = TexteConstantesSQL.TABLE_STUDIOS;
//                table_id = TexteConstantesSQL.TABLE_STUDIOS_ID;
//                break;
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
//                    case STUDIOS:
//                        long start = System.currentTimeMillis();
//                        String requeteSupprJeux = TexteConstantesSQL.DELETE_FROM + " "
//                                + TexteConstantesSQL.TABLE_JEUX + " " + TexteConstantesSQL.INNER_JOIN
//                                + " " + TexteConstantesSQL.TABLE_JEU_STUDIO + " "
//                                + TexteConstantesSQL.ON + " " + TexteConstantesSQL.TABLE_JEUX_ID
//                                + " = " + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU
//                                + " " + TexteConstantesSQL.WHERE + " "
//                                + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO + " = ?";
//                        System.out.println(requeteSupprJeux);
//                        connexion.executerPreparedUpdate(requeteSupprJeux, "(long)" + id);
//                        Set<Entry<Long,Jeu>> setJeux = bdd.getStudio(id).getJeux().entrySet();
//                        Iterator<Entry<Long,Jeu>> it = setJeux.iterator();
//                        while (it.hasNext()) {
//                            Entry<Long,Jeu> entry = it.next();
//                            it.remove();
////                            supprimerJeu(entry.getKey());
//                        }
//                        bdd.supprimerStudio(id);
//                        notifyRemoveStudio(id);
//                        long end = System.currentTimeMillis();
//                        System.out.println((end - start) + "ms");
//                        break;
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
        String requeteSupprJeux = TexteConstantesSQL.DELETE_FROM + " "
                + TexteConstantesSQL.TABLE_JEUX + " " + TexteConstantesSQL.WHERE
                + " " + TexteConstantesSQL.TABLE_JEUX_ID + " " + TexteConstantesSQL.IN
                + " (" + TexteConstantesSQL.SELECT + " " + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU
                + " " + TexteConstantesSQL.FROM + " " + TexteConstantesSQL.TABLE_JEU_STUDIO
                + " " + TexteConstantesSQL.WHERE + " " + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO
                + " = ?)";
        connexion.executerPreparedUpdate(requeteSupprJeux, idStudioRequete);
        Set<Entry<Long, Jeu>> setJeux = bdd.getStudio(idStudio).getJeux().entrySet();
        Iterator<Entry<Long, Jeu>> it = setJeux.iterator();
        while (it.hasNext()) {
            Entry<Long, Jeu> entry = it.next();
            it.remove();
            bdd.supprimerJeu(entry.getKey());
            notifyRemoveJeu(entry.getKey());
        }
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
     */
    public void ajouterJeu(String titre, int anneeSortie, List<Long> listPlateformes, List<Long> listGenres, long idStudio) {
        String requete = TexteConstantesSQL.INSERT_INTO + " " + TexteConstantesSQL.TABLE_JEUX
                + "(" + TexteConstantesSQL.TABLE_JEUX_TITRE + "," + TexteConstantesSQL.TABLE_JEUX_ANNEE_SORTIE
                + ") " + TexteConstantesSQL.VALUES + "(?,?,?)";
        int rowsInserted = connexion.executerPreparedUpdate(requete, titre,
                TexteConstantesConnexion.INT + anneeSortie,
                TexteConstantesConnexion.LONG + idStudio);
        try {
            ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
            if (resultID.next()) {
                long idJeu = resultID.getInt(1);
                Studio studio = bdd.getStudio(idStudio);
                Jeu jeu = new Jeu(idJeu,titre,anneeSortie,studio);
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
                //ajoute le studio au jeu et fait el lien dans la base de données
                String requeteStudio = TexteConstantesSQL.INSERT_INTO + " " + TexteConstantesSQL.TABLE_JEU_STUDIO
                        + "(" + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU + "," + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO
                        + ")" + TexteConstantesSQL.VALUES + "(?,?)";
                rows = connexion.executerPreparedUpdate(requeteStudio, TexteConstantesConnexion.LONG + idJeu, TexteConstantesConnexion.LONG + idStudio);
                if (rows > 0) {
//                    jeu.setStudio(bdd.getStudio(idStudio));
                    bdd.getStudio(idStudio).ajouterJeu(jeu);
                }
                if (rowsInserted > 0) {
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
     * @param idStudio le nouveau studio à lier au eju
     */
    public void modifierJeu(long idJeu, String titre, int anneeSortie, List<Long> listPlateformes, List<Long> listGenres, long idStudio) {
        Jeu jeu = bdd.getJeu(idJeu);
        String idJeuRequete = TexteConstantesConnexion.LONG + idJeu;
        //modifie les champs directs du jeu
        String requete = TexteConstantesSQL.UPDATE + " " + TexteConstantesSQL.TABLE_JEUX
                + " " + TexteConstantesSQL.SET + " " + TexteConstantesSQL.TABLE_JEUX_TITRE
                + " = ? , " + TexteConstantesSQL.TABLE_JEUX_ANNEE_SORTIE + " = ? "
                + TexteConstantesSQL.WHERE + " " + TexteConstantesSQL.TABLE_JEUX_ID + " = ?";
        int rows = connexion.executerPreparedUpdate(requete, titre, TexteConstantesConnexion.INT + anneeSortie, idJeuRequete);
        if (rows > 0) {
            jeu.renommer(titre);
            jeu.setAnneeSortie(anneeSortie);
            
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
            //changement du studio dans la table JeuStudio
            String requeteChangeStudio = TexteConstantesSQL.UPDATE + " " + TexteConstantesSQL.TABLE_JEU_STUDIO
                    + " " + TexteConstantesSQL.SET + " " + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO
                    + " = ? " + TexteConstantesSQL.WHERE + " " + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU
                    + " = ?";
            rows = connexion.executerPreparedUpdate(requeteChangeStudio, TexteConstantesConnexion.LONG + idStudio, idJeuRequete);
            if (rows > 0) {
                jeu.setStudio(bdd.getStudio(idStudio));
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
    
    /**
     * 
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
    
    /**
     * 
     * @param idJeu 
     */
    public void fillRunPanelJeu(long idJeu) {
        notifyFillRunJeu(idJeu);
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
        notifyFillLiveRun(idRun);
    }
    
    
    /**
     * 
     * @param titre
     * @param nodes 
     */
    public void createDataset(String titre, ArrayList<FillDataset> nodes) {
        livesForDataset.clear();
        for (FillDataset node : nodes) {
            livesForDataset.addAll(node.getLivesList());
        }
        this.titreForDataset = titre;
        actualiserDataset();
    }
    
    /**
     * 
     */
    public void actualiserDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        float moyenne = 0, sommeDureeVie = 0, sommeMoyennes = 0;
        int count = 0, sommeMorts = 0;
        float sommeTime = 0;
        for (Live live : livesForDataset) {
            sommeTime += live.getDuration(timeUnit);
            sommeMorts += live.getMorts();
            sommeDureeVie += live.getDureeVieMoyenne(timeUnit);
            count++;
            moyenne = sommeDureeVie / (float)count;
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, timeUnit, false);
            dataset.addValue(moyenne,"Moyenne des durées de vie",live.getDateDebut());
        }
        if (livesForDataset.size() > 1) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
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
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void setGroup(TypeGroup type) {
        this.typeGroup = type;
        this.actualiser();
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddPlateforme(Plateforme plateforme) {
        if (hasObserver()) {
            observer.addPlateforme(plateforme);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddGenre(Genre genre) {
        if(hasObserver()) {
            observer.addGenre(genre);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddStudio(Studio studio) {
        if (hasObserver()) {
            observer.addStudio(studio);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddJeu(Jeu jeu) {
        if (hasObserver()) {
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
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddRun(Run run) {
        if (hasObserver()) {
            observer.addRun(run);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyAddLive(Live live) {
        if (hasObserver()) {
            final Run run = live.getRun();
            observer.addLive(live);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemovePlateforme(long idPlateforme) {
        if (hasObserver()) {
            observer.removePlateforme(idPlateforme);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveGenre(long idGenre) {
        if (hasObserver()) {
            observer.removeGenre(idGenre);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveStudio(long idStudio) {
        if (hasObserver()) {
            observer.removeStudio(idStudio);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveJeu(long idJeu) {
        if (hasObserver()) {
            observer.removeJeu(idJeu);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveRun(long idRun) {
        if (hasObserver()) {
            observer.removeRun(idRun);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyRemoveLive(long idLive) {
        if (hasObserver()) {
            observer.removeLive(idLive);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyDataset(String titre, DefaultCategoryDataset dataset) {
        if (hasObserver() && null != dataset) {
            observer.updateDataset(titre,dataset);
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillPlateforme(long idPlateforme) {
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
    public void notifyFillGenre(long idGenre) {
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
    public void notifyFillStudio(long idStudio) {
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
    public void notifyFillJeu(long idJeu) {
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
    public void notifyFillRun(long idRun) {
        if (hasObserver()) {
            final Run run = bdd.getRun(idRun);
            if (null != run) {
                final Jeu jeu = run.getJeu();
                observer.fillRun(run.getTitre(),jeu.getID());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillRunJeu(long idJeu) {
        if (hasObserver()) {
            final Jeu jeu = bdd.getJeu(idJeu);
            if (null != jeu) {
                observer.fillRunJeu(jeu.getTitre());
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
                observer.fillLive(live.getRun().getID(), live.getDateDebut(), live.getDateFin(), live.getMorts());
            }
        }
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void notifyFillLiveRun(long idRun) {
        if (hasObserver()) {
            final Run run = bdd.getRun(idRun);
            if (null != run) {
                final Jeu jeu = run.getJeu();
                if (null != jeu) {
                    observer.fillLiveRun(run.getID(), run.getTitre(), jeu.getTitre());
                }
            }
        }
    }
    
}
