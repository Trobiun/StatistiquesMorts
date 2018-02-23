/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import statsmorts.constantes.TexteConstantesSQL;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Une classe qui représente en objets Java, la base de données à laquelle
 * l'application est connectée.
 * @author Robin
 */
public class BDD {
    
    //ATTRIBUTS STATIC
    /**
     * Une chaîne de caractères pour mettre un champ en AUTO_INCREMENT et clé
     * primaire pour les bases de données Access.
     */
    private static final String AUTOINCREMENT_ACCESS = TexteConstantesSQL.AUTOINCREMENT
            + " " + TexteConstantesSQL.PRIMARY_KEY;
    
    /**
     * Une chaîne de caractères pour mettre un champ en AUTO_INCREMENT et clé
     * primaire pour les bases de données sauf Access.
     */
    private static final String AUTOINCREMENT = TexteConstantesSQL.SQL_INTEGER + " "
            + TexteConstantesSQL.PRIMARY_KEY + " " + TexteConstantesSQL.AUTOINCREMENT;
    
    /**
     * Une chaîne de caractère pour mettre les contraintes des clés étrangères 
     * (ON UPDATE CASCADE ON DELETE CASCADE et NOT NULL)
     */
    private static final String CLES_ENTRAGERES_REACTIONS = TexteConstantesSQL.ON
            + " " + TexteConstantesSQL.UPDATE + " " + TexteConstantesSQL.CASCADE + " "
            + TexteConstantesSQL.ON + " " + TexteConstantesSQL.DELETE + " "
            + TexteConstantesSQL.CASCADE + " " + TexteConstantesSQL.NOT_NULL;
    
    //ATTRIBUTS
    /**
     * La collection de toutes les plateformes de la base de données.
     */
    private Map<Long,Plateforme> plateformes;
    /**
     * La collection de tous les genres de jeux de la base de données.
     */
    private Map<Long,Genre> genres;
    /**
     * La collection de tous les studios de la base de données.
     */
    private Map<Long,Studio> studios;
    /**
     * La collection des tous les éditeurs de la base de données.
     */
    private Map<Long,Editeur> editeurs;
    /**
     * La collection de tous les jeux de la base de données.
     */
    private Map<Long,Jeu> jeux;
    /**
     * La collection de toutes les runs de la base de données.
     */
    private Map<Long,Run> runs;
    /**
     * La collection de tous les lives de la base de données.
     */
    private Map<Long,Live> lives;
    
    
    //CONSTRUCTEURS
    /**
     * Crée une BDD en se connectant à la base de données donné en paramètres.
     * @param connexion la connexion pour pouvoir se connecter à la base de données
     * @param path le chemin de la base de données (fichier)
     */
    public BDD(Connexion connexion, String path) {
        this.connecter(connexion,path);
    }
    /**
     * Crée une BDD en se connectant à la base de données donné en paramètres.
     * @param connexion la connexion pour pouvoir se connecter à la base de données
     * @param type le type de base de données (MySQL / PostgreSQL)
     * @param serveur l'adresse du serveur (+ de la base de données)
     * @param user l'utlisateur à utiliser pour se connecter
     * @param password le mot de passe pour se conneter
     */
    public BDD(Connexion connexion, TypeDatabase type, String serveur, String user, char[] password) {
        this.connecter(connexion, type, serveur, user, password);
    }
    
    
    //ACCESSEURS
    /**
     * Retourne la collection des plateformes.
     * @return la collection des plateformes
     */
    public Map<Long,Plateforme> getPlateformes() {
        return plateformes;
    }
    
    /**
     * Retourne la plateforme dont l'identifiant est 'idPlateforme'
     * @param idPlateforme l'identifiant de la plateforme à retourner
     * @return la plateforme présente dont l'identifiant est 'idPlateforme'
     * ou null si elle n'est pas présente
     */
    public Plateforme getPlateforme(long idPlateforme) {
        return plateformes.get(idPlateforme);
    }
    
    /**
     * Retourne la collection des genres.
     * @return la collection des genres
     */
    public Map<Long,Genre> getGenres() {
        return genres;
    }
    
    /**
     * Retourne le genre dont l'identifiant est 'idGenre'
     * @param idGenre l'identifiant du genre à retourner
     * @return le genre présent dont l'identifiant est 'idGenre'
     * ou null s'il n'est pas présent
     */
    public Genre getGenre(long idGenre) {
        return genres.get(idGenre);
    }
    
    /**
     * Retourne la collection des studios.
     * @return la collection des studios
     */
    public Map<Long,Studio> getStudios() {
        return studios;
    }
    
    /**
     * Retourne le studio dont l'identifiant est 'idStudio'.
     * @param idStudio l'identifiant du studio à retourner
     * @return le studio dont l'identifiant est 'idStudio'
     * ou null s'il n'est pas présent
     */
    public Studio getStudio(final long idStudio) {
        return studios.get(idStudio);
    }
    
    /**
     * Retourne la collection des éditeurs.
     * @return la collection des éditeurs
     */
    public Map<Long,Editeur> getEditeurs() {
        return editeurs;
    }
    
    /**
     * Retourne l'éditeru dont l'identifiant est 'idEditeur'.
     * @param idEditeur l'identifiant de l'éditeur à retourner
     * @return le studio dont l'identifiant est 'idEditeur'
     * ou null s'il n'est pas présent
     */
    public Editeur getEditeur(final long idEditeur) {
        return editeurs.get(idEditeur);
    }
    
    /**
     * Retourne la collection des jeux.
     * @return la collection des jeux
     */
    public Map<Long,Jeu> getJeux() {
        return jeux;
    }
    
    /**
     * Retourne le jeu dont l'identifiant est 'idJeu'
     * @param idJeu l'identifiant du jeu à retourner
     * @return le jeu dont l'identifiant est 'idJeu'
     * ou null s'il n'est pas présent
     */
    public Jeu getJeu(final long idJeu) {
        return jeux.get(idJeu);
    }
    
    /**
     * Retourne la colelction des runs.
     * @return la collection des runs
     */
    public Map<Long,Run> getRuns() {
        return runs;
    }
    
    /**
     * Retourne la run dont l'identifiant est 'idRun'
     * @param idRun l'identifiant de la run à retourner
     * @return la run dont l'identifiant est 'idRun'
     * ou null si elle n'est pas présente
     */
    public Run getRun(final long idRun) {
        return runs.get(idRun);
    }
    
    /**
     * Retourne la collection des lives
     * @return la collection des lives
     */
    public Map<Long,Live> getLives() {
        return lives;
    }
    
    /**
     * Retourne le live dont l'identifiant est 'idLive'
     * @param idLive l'identifiant du live à retourner
     * @return le live dont l'identifiant est 'idLive'
     * ou null s'il n'est pas présent
     */
    public Live getLive(final long idLive) {
        return lives.get(idLive);
    }
    
    /**
     * Retourne une chaîne de caractères représentant la BDD
     * @return une chaîne de caractères qui représente la BDD
     */
    @Override
    public String toString() {
        return jeux.toString();
//        Set<Entry<Long,Jeu>> setJeux = jeux.entrySet();
//        for (Entry<Long,Jeu> entry : setJeux) {
//            res += entry.getValue().toString() + "";
//        }
//        return res;
    }
    
    //MUTATEURS
    /**
     * Ajoute une plateformes à la collection des plateformes.
     * @param plateforme la plateforme à ajouter
     */
    public void ajouterPlateforme(final Plateforme plateforme) {
        plateformes.put(plateforme.getID(), plateforme);
    }
    
    /**
     * Supprime la plateforme dont l'identifiant est 'idPlateforme'.
     * @param idPlateforme l'identifiant de la plateforme à supprimer
     */
    public void supprimerPlateforme(final long idPlateforme) {
        plateformes.get(idPlateforme).supprimerPlateforme();
        plateformes.remove(idPlateforme);
    }
    
    /**
     * Ajoute un genre à la collection des genres.
     * @param genre le genre à ajouter
     */
    public void ajouterGenre(final Genre genre) {
        genres.put(genre.getID(), genre);
    }
    
    /**
     * Supprime le genre dont l'identifiant est 'idGenre'.
     * @param idGenre l'identifiant du genre à supprimer
     */
    public void supprimerGenre(final long idGenre) {
        genres.get(idGenre).supprimerGenre();
        genres.remove(idGenre);
    }
    
    /**
     * Ajoute un studio à la collection des studios.
     * @param studio le studio à ajouter
     */
    public void ajouterStudio(final Studio studio) {
        studios.put(studio.getID(), studio);
    }
    
    /**
     * Supprime le studio dont l'identifiant est 'idStudio'.
     * @param idStudio l'identifiant du studio à supprimer
     */
    public void supprimerStudio(final long idStudio) {
        studios.get(idStudio).supprimerStudio();
        studios.remove(idStudio);
    }
    
    /**
     * Ajoute un éditeur à la collection des éditeurs.
     * @param editeur l'éditeur à ajouter
     */
    public void ajouterEditeur(final Editeur editeur) {
        editeurs.put(editeur.getID(),editeur);
    }
    
    /**
     * Supprime l'éditeur dont l'identifiant est 'idEditeur'.
     * @param idEditeur l'identifiant de l'éditeur à supprimer
     */
    public void supprimerEditeur(final long idEditeur) {
        editeurs.get(idEditeur).supprimerEditeur();
        editeurs.remove(idEditeur);
    }
    
    /**
     * Ajoute un jeu à la collection des jeux.
     * @param jeu le jeu à ajouter
     */
    public void ajouterJeu(final Jeu jeu) {
        jeux.put(jeu.getID(), jeu);
    }
    
    /**
     * Supprime le jeu dont l'identifiant est 'idJeu'.
     * @param idJeu l'identifiant du jeu à supprimer
     */
    public void supprimerJeu(final long idJeu) {
        jeux.get(idJeu).supprimerJeu();
        jeux.remove(idJeu);
    }
    
    /**
     * Ajoute une run à la collection des runs.
     * @param run la run à ajouter
     */
    public void ajouterRun(final Run run) {
        runs.put(run.getID(), run);
    }
    
    /**
     * Supprime la run dont l'identifiant est 'idRun'
     * @param idRun l'identifiant de la run à supprimer
     */
    public void supprimerRun(final long idRun) {
        runs.get(idRun).supprimerRun();
        runs.remove(idRun);
    }
    
    /**
     * Ajoute un live à la collection des lives.
     * @param live le live à ajouter
     */
    public void ajouterLive(final Live live) {
        lives.put(live.getID(), live);
    }
    
    /**
     * Supprime le live dont l'identifiant est 'idLive'
     * @param idLive l'identifiant du live à supprimer
     */
    public void supprimerLive(final long idLive) {
        lives.get(idLive).supprimerLive();
        lives.remove(idLive);
    }
    
    /**
     * Renvoie une map initialisée, une nouvelle map si le paramètres map est null
     * ou renvoie la map passée en paramètre après avoir été vidée.
     * @param map la map à réinitialiser ou null pour en créer une autre
     * @return la map en paramètre si 'map' != null ou une nouvelle map si
     * 'map' == null
     */
    private Map initMap(Map map) {
        if (map == null) {
            map = new HashMap();
        }
        else {
            map.clear();
        }
        return map;
    }
    /**
     * Initialise toutes les collections/maps de la BDD
     */
    private void initAll() {
        plateformes = initMap(plateformes);
        genres = initMap(genres);
        studios = initMap(studios);
        editeurs = initMap(editeurs);
        jeux = initMap(jeux);
        runs = initMap(runs);
        lives = initMap(lives);
    }
    
    /**
     * Se connecte à la base de données 'path' grâce à l'objet 'connexion' puis
     * initialise toutes les collections
     * @param connexion la Connexion qui permet de se connecter
     * @param path le chemib de la base de données (fichier)
     */
    public void connecter(Connexion connexion, String path) {
        connexion.connecter(path);
        initAll();
    }
    
    /**
     * Se connecte à la base de données 'serveur' grâce à l'objet 'connexion' puis
     * initialise toutes les collections
     * @param connexion la Connexion qui permet de se connecter
     * @param type le type de base de données (serveur : MySQL / PsotgreSQL)
     * @param serveur la base de données/serveur à laquelle se connecter
     * @param user l'utilisateur avec lequel se connecter
     * @param password le mot de passe à utiliser pour se connecter
     */
    public void connecter(Connexion connexion, TypeDatabase type, String serveur, String user, char[] password) {
        connexion.connecter(type, serveur, user, password);
        initAll();
    }
    
    /**
     * Actualise la BDD, de sorte à ce que la BDD représente la base de données 
     * à laquelle on est connecté.
     * @param connexion la Connexion qui permet de faire des requêtes
     * @throws SQLException 
     */
    public void actualiserBDD(Connexion connexion) throws SQLException {
        initAll();
        String requetePlateformes = TexteConstantesSQL.SELECT_PLATEFORMES;
        String requeteGenres = TexteConstantesSQL.SELECT_GENRES;
        String requeteStudios = TexteConstantesSQL.SELECT_STUDIOS;
        String requeteEditeurs = TexteConstantesSQL.SELECT_EDITEURS;
        String requeteJeuPlateforme = TexteConstantesSQL.SELECT_JEU_PLATEFORME;
        String requeteJeuGenre = TexteConstantesSQL.SELECT_JEU_GENRE;
        String requeteVueGlobale = TexteConstantesSQL.SELECT_VUE_GLOBALE;
        if (connexion.getType().equals(TypeDatabase.Access)) {
            requeteVueGlobale = TexteConstantesSQL.SELECT_VUE_GLOBALE_SQL;
        }
        
        long idPlateforme;
        String nomPlateforme;
        Plateforme plateforme;
        ResultSet resultsPlateformes = connexion.executerRequete(requetePlateformes);
        while (resultsPlateformes.next()) {
            idPlateforme = resultsPlateformes.getLong(TexteConstantesSQL.TABLE_PLATEFORMES_ID);
            nomPlateforme = resultsPlateformes.getString(TexteConstantesSQL.TABLE_PLATEFORMES_NOM);
            plateforme = new Plateforme(idPlateforme,nomPlateforme);
            this.ajouterPlateforme(plateforme);
        }
        
        long idGenre;
        String nomGenre;
        Genre genre;
        ResultSet resultsGenres = connexion.executerRequete(requeteGenres);
        while (resultsGenres.next()) {
            idGenre = resultsGenres.getLong(TexteConstantesSQL.TABLE_GENRES_ID);
            nomGenre = resultsGenres.getString(TexteConstantesSQL.TABLE_GENRES_NOM);
            genre = new Genre(idGenre,nomGenre);
            this.ajouterGenre(genre);
        }
        
        long idStudio;
        String nomStudio;
        Studio studio;
        ResultSet resultsStudios = connexion.executerRequete(requeteStudios);
        while (resultsStudios.next()) {
            idStudio = resultsStudios.getLong(TexteConstantesSQL.TABLE_STUDIOS_ID);
            nomStudio = resultsStudios.getString(TexteConstantesSQL.TABLE_STUDIOS_NOM);
            studio = new Studio(idStudio,nomStudio);
            this.ajouterStudio(studio);
        }
        
        long idEditeur;
        String nomEditeur;
        Editeur editeur;
        ResultSet resultsEditeurs = connexion.executerRequete(requeteEditeurs);
        while (resultsEditeurs.next()) {
            idEditeur = resultsEditeurs.getLong(TexteConstantesSQL.TABLE_EDITEURS_ID);
            nomEditeur = resultsEditeurs.getString(TexteConstantesSQL.TABLE_EDITEURS_NOM);
            editeur = new Editeur(idEditeur,nomEditeur);
            this.ajouterEditeur(editeur);
        }
        
        String titreJeu, titreRun, dateDebut, dateFin;
        Date dateSortie;
        long idJeu, idRun, idLive;
        int morts, boss;
        Jeu jeu;
        Run run;
        Live live;
        ResultSet resultsVueGlobale = connexion.executerRequete(requeteVueGlobale);
        while (resultsVueGlobale.next()) {
            idJeu = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_JEUX_ID);
            titreJeu = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_JEUX_TITRE);
            if (connexion.getType().equals(TypeDatabase.SQLite)) {
                dateSortie = java.sql.Date.valueOf(resultsVueGlobale.getString(TexteConstantesSQL.TABLE_JEUX_DATE_SORTIE));
            }
            else  {
                dateSortie = resultsVueGlobale.getDate(TexteConstantesSQL.TABLE_JEUX_DATE_SORTIE);
            }
            idStudio = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_JEUX_ID_STUDIO);
            Studio jeuStudio = studios.get(idStudio);
            idEditeur = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_JEUX_ID_EDITEUR);
            Editeur jeuEditeur = editeurs.get(idEditeur);
            if (!jeux.containsKey(idJeu) && idJeu > 0) {
                jeu = new Jeu(idJeu,titreJeu,dateSortie,jeuStudio,jeuEditeur);
                this.ajouterJeu(jeu);
                jeuStudio.ajouterJeu(jeu);
            }
            else {
                jeu = jeux.get(idJeu);
            }
            
            idRun = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_RUNS_ID);
            titreRun = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_RUNS_TITRE);
            if (!runs.containsKey(idRun) && idRun > 0) {
                run = new Run(idRun,titreRun);
                this.ajouterRun(run);
//                runs.put(idRun,run);
                jeu.ajouterRun(run);
                run.setJeu(jeu);
            }
            else {
                run = runs.get(idRun);
            }
            
            idLive = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_LIVES_ID);
            dateDebut = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_LIVES_DATE_DEBUT);
            dateFin = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_LIVES_DATE_FIN);
            morts = resultsVueGlobale.getInt(TexteConstantesSQL.TABLE_LIVES_MORTS);
            boss = resultsVueGlobale.getInt(TexteConstantesSQL.TABLE_LIVES_BOSS);
            if (!lives.containsKey(idLive) && idLive > 0) {
                live = new Live(idLive,dateDebut,dateFin,morts,boss);
                this.ajouterLive(live);
//                lives.put(idLive,live);
                run.ajouterLive(live);
                live.setRun(run);
            }
//            else {
//                live = lives.get(idLive);
//            }
        }
        
        ResultSet resultsJeuPlateforme = connexion.executerRequete(requeteJeuPlateforme);
        while (resultsJeuPlateforme.next()) {
            idPlateforme = resultsJeuPlateforme.getLong(TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME);
            idJeu = resultsJeuPlateforme.getLong(TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU);
            plateforme = plateformes.get(idPlateforme);
            jeu = jeux.get(idJeu);
            plateforme.ajouterJeu(jeu);
            jeu.ajouterPlateforme(plateforme);
        }
        
        ResultSet resultsJeuGenre = connexion.executerRequete(requeteJeuGenre);
        while (resultsJeuGenre.next()) {
            idGenre = resultsJeuGenre.getLong(TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE);
            idJeu = resultsJeuGenre.getLong(TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU);
            genre = genres.get(idGenre);
            jeu = jeux.get(idJeu);
            genre.ajouterJeu(jeu);
            jeu.ajouterGenre(genre);
        }
//        
//        ResultSet resultsJeuStudio = connexion.executerRequete(requeteJeuStudio);
//        while (resultsJeuStudio.next()) {
//            idStudio = resultsJeuStudio.getLong(TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO);
//            idJeu = resultsJeuStudio.getLong(TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU);
//            studio = studios.get(idStudio);
//            jeu = jeux.get(idJeu);
//            studio.ajouterJeu(jeu);
//            jeu.setStudio(studio);
//        }
    }
    
    
    //STATIC
    /**
     * Créer un fichier de base de données en créant toutes les tables et/ou vues.
     * @param connexion la Connexion qui permet de créer la base de données et
     * faire les requêtes de création de tables
     * @param database le chemin de la base de données à créer
     * @return une BDD crée et initialisée correctement (en cohérence avec la
     * base de données si celle-ci existe déjà)
     */
    public static BDD creerBDD(Connexion connexion, String database) {
        //vérifie d'abord que le fichier est créé ou non
        File file = new File(database);
        boolean creation = false;
        try {
            creation = file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //si le fichier vient d'être créé, on le supprime car c'est l'objet
        //Connexion qui va créer le fichier
        BDD res;
        if (creation) {
            file.delete();
            connexion.connecter(database);
            TypeDatabase type = connexion.getType();
            String AUTOINCREMENT_ADAPTE;
            if (type.equals(TypeDatabase.Access)) {
                AUTOINCREMENT_ADAPTE = AUTOINCREMENT_ACCESS;
            }
            else {
                AUTOINCREMENT_ADAPTE = AUTOINCREMENT;
            }
            
            //Table Plateformes
            String requete1 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_PLATEFORMES + " ("
                        //champ pla_id clé primaire
                        + TexteConstantesSQL.TABLE_PLATEFORMES_ID + " "
                            + AUTOINCREMENT_ADAPTE + ","
                        //champ pla_Nom
                        + TexteConstantesSQL.TABLE_PLATEFORMES_NOM + " "
                            + TexteConstantesSQL.SQL_TEXT + " "
                            + TexteConstantesSQL.NOT_NULL + ")";
            
            //Table Genres
            String requete2 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_GENRES + " " + "("
                        //champ gen_id clé primaire
                        + TexteConstantesSQL.TABLE_GENRES_ID + " "
                            + AUTOINCREMENT_ADAPTE + ","
                        //champ gen_Nom
                        + TexteConstantesSQL.TABLE_GENRES_NOM + " "
                            + TexteConstantesSQL.SQL_TEXT + " "
                            + TexteConstantesSQL.NOT_NULL + ")";
            
            //Table Studios
            String requete3 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_STUDIOS + " ("
                        //champ stu_id clé primaire
                        + TexteConstantesSQL.TABLE_STUDIOS_ID + " "
                            + AUTOINCREMENT_ADAPTE + ","
                        //champ stu_Nom
                        + TexteConstantesSQL.TABLE_STUDIOS_NOM + " "
                            + TexteConstantesSQL.SQL_TEXT + " "
                            + TexteConstantesSQL.NOT_NULL + ")";
            
            //Table Editeurs
            String requete4 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_EDITEURS + " ("
                        //champ edi_id clé primaire
                        + TexteConstantesSQL.TABLE_EDITEURS_ID + " "
                        + AUTOINCREMENT_ADAPTE + ","
                        //champ edi_Nom
                        + TexteConstantesSQL.TABLE_EDITEURS_NOM + " "
                            + TexteConstantesSQL.SQL_TEXT + " "
                            + TexteConstantesSQL.NOT_NULL + ")";
            
            //Table Jeux
            String requete5 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEUX + " ("
                        //champ jeu_id clé primaire
                        + TexteConstantesSQL.TABLE_JEUX_ID + " "
                        + AUTOINCREMENT_ADAPTE + ","
                        //champ jeu_Titre
                        + TexteConstantesSQL.TABLE_JEUX_TITRE + " "
                            + TexteConstantesSQL.SQL_TEXT + " "
                            + TexteConstantesSQL.NOT_NULL + ","
                        //champ jeu_DateSortie
                        + TexteConstantesSQL.TABLE_JEUX_DATE_SORTIE + " "
                            + TexteConstantesSQL.SQL_DATE + ","
                        //champ jeu_idStudio
                        + TexteConstantesSQL.TABLE_JEUX_ID_STUDIO + " "
                            +  TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_STUDIOS + "(" + TexteConstantesSQL.TABLE_STUDIOS_ID
                            + ") " + CLES_ENTRAGERES_REACTIONS + ","
                        //champ jeu_idEditeur
                        + TexteConstantesSQL.TABLE_JEUX_ID_EDITEUR + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_EDITEURS + "(" + TexteConstantesSQL.TABLE_EDITEURS_ID
                            + ") " + CLES_ENTRAGERES_REACTIONS + ")";
            
            //Table Jeulateforme
            String requete6 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEU_PLATEFORME + " " + "("
                        //champ idJeu
                        + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID + ") "
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ idPlateforme
                        + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_PLATEFORMES + "(" + TexteConstantesSQL.TABLE_PLATEFORMES_ID + ") "
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //clé primaire
                        + TexteConstantesSQL.PRIMARY_KEY + " " + "("
                            + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU + ","
                            + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME + "))";
            
            //Table JeuGenre
            String requete7 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEU_GENRE + "("
                        //champ idJeu
                        + TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU  + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID +  ") "
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ idGenre
                        + TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_GENRES + "(" + TexteConstantesSQL.TABLE_GENRES_ID + ") "
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //clé primaire
                        + TexteConstantesSQL.PRIMARY_KEY + " " + "("
                            + TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU + ","
                            + TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE + "))";
            
            //Table Runs
            String requete8 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_RUNS + "("
                        //champ run_id clé primaire
                        + TexteConstantesSQL.TABLE_RUNS_ID + " "
                            + AUTOINCREMENT_ADAPTE + ","
                        //champ run_idJeu
                        + TexteConstantesSQL.TABLE_RUNS_ID_JEU + " "
                            + TexteConstantesSQL.SQL_INTEGER + " "
                            + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID +") "
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ run_Titre
                        + TexteConstantesSQL.TABLE_RUNS_TITRE + " "
                        + TexteConstantesSQL.SQL_TEXT + " "
                        + TexteConstantesSQL.NOT_NULL + ")";
            
            //Table Lives
            String requete9 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_LIVES + "("
                        //champ liv_id clé primaire
                        + TexteConstantesSQL.TABLE_LIVES_ID + " "
                            + AUTOINCREMENT_ADAPTE + ","
                        //champ liv_DateDebut
                        + TexteConstantesSQL.TABLE_LIVES_DATE_DEBUT + " "
                            + TexteConstantesSQL.SQL_DATETIME + " "
                            + TexteConstantesSQL.NOT_NULL + ","
                        //champ liv_DateFin
                        + TexteConstantesSQL.TABLE_LIVES_DATE_FIN + " "
                            + TexteConstantesSQL.SQL_DATETIME + " "
                            + TexteConstantesSQL.NOT_NULL + ","
                        //champ liv_idRun
                        + TexteConstantesSQL.TABLE_LIVES_ID_RUN + " "
                            + TexteConstantesSQL.SQL_INTEGER + " "
                            + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_RUNS + "(" + TexteConstantesSQL.TABLE_RUNS_ID + ") "
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ liv_Morts
                        + TexteConstantesSQL.TABLE_LIVES_MORTS + " "
                            + TexteConstantesSQL.SQL_INTEGER + ","
                        //champ liv_Boss
                        + TexteConstantesSQL.TABLE_LIVES_BOSS + " "
                            + TexteConstantesSQL.SQL_INTEGER + ")";
            
            //Table Boss
            String requete10 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_BOSS + "("
                        //champ bos_id
                        + TexteConstantesSQL.TABLE_BOSS_ID + " "
                            + AUTOINCREMENT_ADAPTE + ","
                        //champ bos_idJeu
                        + TexteConstantesSQL.TABLE_BOSS_ID_JEU + " "
                            + TexteConstantesSQL.SQL_INTEGER + " "
                            + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID + ")"
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ bos_Nom
                        + TexteConstantesSQL.TABLE_BOSS_NOM + " "
                            + TexteConstantesSQL.SQL_TEXT + ")";
            
            //Table MortsEtVictoires
            String requete11 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_MORTS_ET_VICTOIRES + "("
                        //champ mev_idLive
                        + TexteConstantesSQL.TABLE_MORTS_ET_VICTOIRES_ID_LIVE + " "
                            + TexteConstantesSQL.SQL_INTEGER + " "
                            + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_LIVES + "(" + TexteConstantesSQL.TABLE_LIVES_ID + ")"
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ mev_idBoss
                        + TexteConstantesSQL.TABLE_MORTS_ET_VICTOIRES_ID_BOSS + " "
                            + TexteConstantesSQL.SQL_INTEGER + " "
                            + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_BOSS + "(" + TexteConstantesSQL.TABLE_BOSS_ID + ")"
                            + CLES_ENTRAGERES_REACTIONS + ","
                        //champ mev_Morts
                        + TexteConstantesSQL.TABLE_MORTS_ET_VICTOIRES_MORTS + " "
                            + TexteConstantesSQL.SQL_INTEGER + ","
                        //champ mev_Victoires
                        + TexteConstantesSQL.TABLE_MORTS_ET_VICTOIRES_VICTOIRES + " "
                            + TexteConstantesSQL.SQL_INTEGER + ")";
            
            //vue VueGlobale seulement avec SQLite
            String requete12 = TexteConstantesSQL.CREATE_VIEW + " "
                    + TexteConstantesSQL.VUE_GLOBALE + " AS "
                        + TexteConstantesSQL.SELECT_VUE_GLOBALE_SQL;
            
            connexion.executerUpdate(requete1);
            connexion.executerUpdate(requete2);
            connexion.executerUpdate(requete3);
            connexion.executerUpdate(requete4);
            connexion.executerUpdate(requete5);
            connexion.executerUpdate(requete6);
            connexion.executerUpdate(requete7);
            connexion.executerUpdate(requete8);
            connexion.executerUpdate(requete9);
            connexion.executerUpdate(requete10);
            connexion.executerUpdate(requete11);
            //les basees de données Access n'acceptent pas les vues
            if (!type.equals(TypeDatabase.Access)) {
                connexion.executerUpdate(requete12);
            }
            
            res = new BDD(connexion,database);
            
        } //si le fichier n'est pas créé, alors on se connecter à la base de données déjà exiqtante
        else {
            res = new BDD(connexion,database);
        }
        
        return res;
    }
    
}
