/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import constantes.TexteConstantes;
import constantes.TexteConstantesSQL;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class BDD {
    
    //ATTRIBUTS STATIC
    private static final String AUTOINCREMENT_ACCESS = TexteConstantesSQL.AUTOINCREMENT
            + " " + TexteConstantesSQL.PRIMARY_KEY;
    
    private static final String AUTOINCREMENT = TexteConstantesSQL.SQL_INTEGER + " "
            + TexteConstantesSQL.PRIMARY_KEY + " " + TexteConstantesSQL.AUTOINCREMENT;
    
    
    //ATTRIBUTS
    private Map<Long,Plateforme> plateformes;
    private Map<Long,Genre> genres;
    private Map<Long,Studio> studios;
    private Map<Long,Jeu> jeux;
    private Map<Long,Run> runs;
    private Map<Long,Live> lives;
    
    
    //CONSTRUCTEURS
    public BDD(Connexion connexion, String path) {
        this.connecter(connexion,path);
    }
    public BDD(Connexion connexion, TypeDatabase type, String serveur, String user, char[] password) {
        this.connecter(connexion, type, serveur, user, password);
    }
    
    
    //ACCESSEURS
    public Map<Long,Plateforme> getPlateformes() {
        return plateformes;
    }
    
    public Map<Long,Genre> getGenres() {
        return genres;
    }
    
    public Map<Long,Studio> getStudios() {
        return studios;
    }
    
    public Map<Long,Jeu> getJeux() {
        return jeux;
    }
    
    public Map<Long,Run> getRuns() {
        return runs;
    }
    
    public Map<Long,Live> getLives() {
        return lives;
    }
    
    @Override
    public String toString() {
        String res = TexteConstantes.EMPTY;
        return jeux.toString();
//        Set<Entry<Long,Jeu>> setJeux = jeux.entrySet();
//        for (Entry<Long,Jeu> entry : setJeux) {
//            res += entry.getValue().toString() + "";
//        }
//        return res;
    }
    
    //MUTATEURS
    public void ajouterPlateforme(Plateforme plateforme) {
        plateformes.put(plateforme.getID(), plateforme);
    }
    
    public void ajouterGenre(Genre genre) {
        genres.put(genre.getID(), genre);
    }
    
    public void ajouterStudio(Studio studio) {
        studios.put(studio.getID(), studio);
    }
    
    private Map initMap(Map map) {
        if (map == null) {
            map = new HashMap();
        }
        else {
            map.clear();
        }
        return map;
    }
    private void initAll() {
        plateformes = initMap(plateformes);
        genres = initMap(genres);
        studios = initMap(studios);
        jeux = initMap(jeux);
        runs = initMap(runs);
        lives = initMap(lives);
    }
    
   public void connecter(Connexion connexion, String path) {
        connexion.connecter(path);
        initAll();
    }
    
    public void connecter(Connexion connexion, TypeDatabase type, String serveur, String user, char[] password) {
        connexion.connecter(type, serveur, user, password);
        initAll();
    }
    
    public void actualiserBDD(Connexion connexion) throws SQLException {
        String requetePlateformes = TexteConstantesSQL.SELECT_PLATEFORMES;
        String requeteGenres = TexteConstantesSQL.SELECT_GENRES;
        String requeteStudios = TexteConstantesSQL.SELECT_STUDIOS;
        String requeteJeuPlateforme = TexteConstantesSQL.SELECT_JEU_PLATEFORME;
        String requeteJeuGenre = TexteConstantesSQL.SELECT_JEU_GENRE;
        String requeteJeuStudio = TexteConstantesSQL.SELECT_JEU__STUDIO;
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
            plateformes.put(idPlateforme,plateforme);
        }
        
        long idGenre;
        String nomGenre;
        Genre genre;
        ResultSet resultsGenres = connexion.executerRequete(requeteGenres);
        while (resultsGenres.next()) {
            idGenre = resultsGenres.getLong(TexteConstantesSQL.TABLE_GENRES_ID);
            nomGenre = resultsGenres.getString(TexteConstantesSQL.TABLE_GENRES_NOM);
            genre = new Genre(idGenre,nomGenre);
            genres.put(idGenre,genre);
        }
        
        long idStudio;
        String nomStudio;
        Studio studio;
        ResultSet resultsStudios = connexion.executerRequete(requeteStudios);
        while (resultsStudios.next()) {
            idStudio = resultsStudios.getLong(TexteConstantesSQL.TABLE_STUDIOS_ID);
            nomStudio = resultsStudios.getString(TexteConstantesSQL.TABLE_STUDIOS_NOM);
            studio = new Studio(idStudio,nomStudio);
            studios.put(idStudio, studio);
        }
        
        String titreJeu, titreRun, dateDebut, dateFin;
        long idJeu, idRun, idLive;
        int anneeSortieJeu, morts;
        Jeu jeu;
        Run run;
        Live live;
        ResultSet resultsVueGlobale = connexion.executerRequete(requeteVueGlobale);
        while (resultsVueGlobale.next()) {
            idJeu = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_JEUX_ID);
            titreJeu = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_JEUX_TITRE);
            anneeSortieJeu = resultsVueGlobale.getInt(TexteConstantesSQL.TABLE_JEUX_ANNEE_SORTIE);
            if (!jeux.containsKey(idJeu) && idJeu > 0) {
                jeu = new Jeu(idJeu,titreJeu,anneeSortieJeu);
                jeux.put(idJeu,jeu);
            }
            else {
                jeu = jeux.get(idJeu);
            }
            
            idRun = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_RUNS_ID);
            titreRun = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_RUNS_TITRE);
            if (!runs.containsKey(idRun) && idRun > 0) {
                run = new Run(idRun,titreRun);
                runs.put(idRun,run);
                jeu.putRun(run);
                run.setJeu(jeu);
            }
            else {
                run = runs.get(idRun);
            }
            
            idLive = resultsVueGlobale.getLong(TexteConstantesSQL.TABLE_LIVES_ID);
            dateDebut = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_LIVES_DATE_DEBUT);
            dateFin = resultsVueGlobale.getString(TexteConstantesSQL.TABLE_LIVES_DATE_FIN);
            morts = resultsVueGlobale.getInt(TexteConstantesSQL.TABLE_LIVES_MORTS);
            if (!lives.containsKey(idLive) && idLive > 0) {
                live = new Live(idLive,dateDebut,dateFin,morts);
                lives.put(idLive,live);
                run.putLive(live);
                live.setRun(run);
            }
            else {
                live = lives.get(idLive);
            }
        }
        
        ResultSet resultsJeuPlateforme = connexion.executerRequete(requeteJeuPlateforme);
        while (resultsJeuPlateforme.next()) {
            idPlateforme = resultsJeuPlateforme.getLong(TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME);
            idJeu = resultsJeuPlateforme.getLong(TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU);
            plateforme = plateformes.get(idPlateforme);
            jeu = jeux.get(idJeu);
            plateforme.putJeu(jeu);
            jeu.putPlateforme(plateforme);
        }
        
        ResultSet resultsJeuGenre = connexion.executerRequete(requeteJeuGenre);
        while (resultsJeuGenre.next()) {
            idGenre = resultsJeuGenre.getLong(TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE);
            idJeu = resultsJeuGenre.getLong(TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU);
            genre = genres.get(idGenre);
            jeu = jeux.get(idJeu);
            genre.putJeu(jeu);
            jeu.putGenre(genre);
        }
        
        ResultSet resultsJeuStudio = connexion.executerRequete(requeteJeuStudio);
        while (resultsJeuStudio.next()) {
            idStudio = resultsJeuStudio.getLong(TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO);
            idJeu = resultsJeuStudio.getLong(TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU);
            studio = studios.get(idStudio);
            jeu = jeux.get(idJeu);
            studio.putJeu(jeu);
            jeu.setStudio(studio);
        }
    }
    
    
    //STATIC
    public static BDD creerBDD(Connexion connexion, String database) {
        File file = new File(database);
        boolean creation = false;
        try {
            creation = file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BDD res;
        if (creation) {
            file.delete();
            connexion.connecter(database);
            TypeDatabase type = connexion.getType();
            
            //table Plateformes
            String requete1 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_PLATEFORMES + " " + "(";
            //champ pla_id clé primaire
            requete1 += TexteConstantesSQL.TABLE_PLATEFORMES_ID + " ";
            requete1 += type.equals(TypeDatabase.Access) ?  AUTOINCREMENT_ACCESS : AUTOINCREMENT;
            //champ pla_Nom
            requete1 += "," + TexteConstantesSQL.TABLE_PLATEFORMES_NOM + " "
                    + TexteConstantesSQL.SQL_TEXT + ")";
            
            //table Genres
            String requete2 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_GENRES + " " + "(";
            //champ gen_id clé primaire
            requete2 += TexteConstantesSQL.TABLE_GENRES_ID + " ";
            requete2 += type.equals(TypeDatabase.Access) ?  AUTOINCREMENT_ACCESS : AUTOINCREMENT;
            //champ gen_Nom
            requete2 += "," + TexteConstantesSQL.TABLE_GENRES_NOM + " "
                    + TexteConstantesSQL.SQL_TEXT + ")";
            
            //table Studios
            String requete3 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_STUDIOS + " (";
            //champ stu_id clé primaire
            requete3 += TexteConstantesSQL.TABLE_STUDIOS_ID + " ";
            requete3 += type.equals(TypeDatabase.Access) ?  AUTOINCREMENT_ACCESS : AUTOINCREMENT;
            //champ stu_Nom
            requete3 += "," + TexteConstantesSQL.TABLE_STUDIOS_NOM + " "
                    + TexteConstantesSQL.SQL_TEXT + ")";
            
            //table Jeux
            String requete4 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEUX + " " + "(";
            //champ jeu_id clé primaire
            requete4 += TexteConstantesSQL.TABLE_JEUX_ID + " ";
            requete4 += type.equals(TypeDatabase.Access) ?  AUTOINCREMENT_ACCESS : AUTOINCREMENT;
            //champ jeu_Titre
            requete4 += "," + TexteConstantesSQL.TABLE_JEUX_TITRE + " "
                    + TexteConstantesSQL.SQL_TEXT;
            //champ jeu_AnneeSortie
            requete4 += "," + TexteConstantesSQL.TABLE_JEUX_ANNEE_SORTIE + " "
                    + TexteConstantesSQL.SQL_INTEGER + ")";
            
            //table Plateformes
            String requete5 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEU_PLATEFORME + " " + "("
                        //champ idJeu
                        + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID + "),"
                        //champ idPlateforme
                        + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_PLATEFORMES + "(" + TexteConstantesSQL.TABLE_PLATEFORMES_ID + "),"
                        //clé primaire
                        + TexteConstantesSQL.PRIMARY_KEY + " " + "("
                            + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_JEU + ","
                            + TexteConstantesSQL.TABLE_JEU_PLATEFORME_ID_PLATEFORME + "))";
            
            //table JeuGenre
            String requete6 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEU_GENRE + "("
                        //champ idJeu
                        + TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU  + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID +  "),"
                        //champ idGenre
                        + TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_GENRES + "(" + TexteConstantesSQL.TABLE_GENRES_ID + "),"
                        //clé primaire
                        + TexteConstantesSQL.PRIMARY_KEY + " " + "("
                            + TexteConstantesSQL.TABLE_JEU_GENRE_ID_JEU + ","
                            + TexteConstantesSQL.TABLE_JEU_GENRE_ID_GENRE + "))";
            
            //table JeuStudio
            String requete7 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_JEU_STUDIO + "("
                        //champ js_idJeu
                        + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_JEUX + "(" + TexteConstantesSQL.TABLE_JEUX_ID + "),"
                        //champ js_idStudio
                        + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO + " "
                            + TexteConstantesSQL.SQL_INTEGER + " " + TexteConstantesSQL.REFERENCES + " "
                            + TexteConstantesSQL.TABLE_STUDIOS + "(" + TexteConstantesSQL.TABLE_STUDIOS_ID + "),"
                        //clé primaire
                        + TexteConstantesSQL.PRIMARY_KEY + " " + "("
                            + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_JEU + ","
                            + TexteConstantesSQL.TABLE_JEU_STUDIO_ID_STUDIO + "))";
            
            //table Runs
            String requete8 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_RUNS + "(";
            //champ run_id clé primaire
            requete8 += TexteConstantesSQL.TABLE_RUNS_ID + " ";
            requete8 += type.equals(TypeDatabase.Access) ?  AUTOINCREMENT_ACCESS : AUTOINCREMENT;
            //champ run_idJeu
            requete8 += "," + TexteConstantesSQL.TABLE_RUNS_ID_JEU + " "
                    + TexteConstantesSQL.SQL_INTEGER + " "
                    + TexteConstantesSQL.REFERENCES + " "
                    + TexteConstantesSQL.TABLE_JEUX +"(" + TexteConstantesSQL.TABLE_JEUX_ID +")";
            //champ run_Titre
            requete8 += "," + TexteConstantesSQL.TABLE_RUNS_TITRE +TexteConstantesSQL.SQL_TEXT + ")";
            
            //table Lives
            String requete9 = TexteConstantesSQL.CREATE_TABLE + " "
                    + TexteConstantesSQL.TABLE_LIVES + "(";
            //champ liv_id clé primaire
            requete9 += TexteConstantesSQL.TABLE_LIVES_ID + " ";
            requete9 += type.equals(TypeDatabase.Access) ?  AUTOINCREMENT_ACCESS : AUTOINCREMENT;
            //champ liv_DateDebut
            requete9 += "," + TexteConstantesSQL.TABLE_LIVES_DATE_DEBUT + " "
                    + TexteConstantesSQL.SQL_DATETIME + " "
                    + TexteConstantesSQL.NOT_NULL;
            //champ liv_DateFin
            requete9 += "," + TexteConstantesSQL.TABLE_LIVES_DATE_FIN + " "
                    + TexteConstantesSQL.SQL_DATETIME + " "
                    + TexteConstantesSQL.NOT_NULL;
            //champ liv_Run
            requete9 += "," + TexteConstantesSQL.TABLE_LIVES_RUN + " "
                    + TexteConstantesSQL.SQL_INTEGER + " "
                    + TexteConstantesSQL.REFERENCES + " "
                    + TexteConstantesSQL.TABLE_RUNS + "(" + TexteConstantesSQL.TABLE_RUNS_ID + ")";
            //champ liv_Morts
            requete9 += "," + TexteConstantesSQL.TABLE_LIVES_MORTS + " "
                    + TexteConstantesSQL.SQL_INTEGER + ")";
            
            //vue VueGlobale
            String requete10 = TexteConstantesSQL.CREATE_VIEW + " "
                    + TexteConstantesSQL.VUE_GLOBALE + " AS "
                        + TexteConstantesSQL.SELECT_VUE_GLOBALE_SQL;
//                    CTexteConstantesSQL.CREATE_VIEW + "VueGlobale AS "
//                                            + "SELECT * FROM( "
//                                            + "Jeux LEFT OUTER JOIN "
//                                            + "(Runs LEFT OUTER JOIN Lives ON run_id = liv_Run) ON run_idJeu = jeu_id)";
            
            connexion.executerUpdate(requete1);
            connexion.executerUpdate(requete2);
            connexion.executerUpdate(requete3);
            connexion.executerUpdate(requete4);
            connexion.executerUpdate(requete5);
            connexion.executerUpdate(requete6);
            connexion.executerUpdate(requete7);
            connexion.executerUpdate(requete8);
            connexion.executerUpdate(requete9);
            if (!type.equals(TypeDatabase.Access)) {
                connexion.executerUpdate(requete10);
            }
            
            res = new BDD(connexion,database);
            
        }
        else {
            res = new BDD(connexion,database);
        }
        
        return res;
    }
    
}
