/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constantes;

/**
 *
 * @author Robin
 */
public class TexteConstantesSQL {
    
    //TABLES ET CHAMPS
    public static final String TABLE_PLATEFORMES = "Plateformes";
    public static final String TABLE_PLATEFORMES_ID = "pla_id";
    public static final String TABLE_PLATEFORMES_NOM = "pla_Nom";
    
    public static final String TABLE_GENRES = "Genres";
    public static final String TABLE_GENRES_ID = "gen_id";
    public static final String TABLE_GENRES_NOM = "gen_Nom";
    
    public static final String TABLE_STUDIOS = "Studios";
    public static final String TABLE_STUDIOS_ID = "stu_id";
    public static final String TABLE_STUDIOS_NOM = "stu_Nom";
    
    public static final String TABLE_JEUX = "Jeux";
    public static final String TABLE_JEUX_ID = "jeu_id";
    public static final String TABLE_JEUX_TITRE = "jeu_Titre";
    public static final String TABLE_JEUX_ANNEE_SORTIE = "jeu_AnneeSortie";
    
    public static final String TABLE_JEU_PLATEFORME = "JeuPlateforme";
    public static final String TABLE_JEU_PLATEFORME_ID_JEU = "jp_idJeu";
    public static final String TABLE_JEU_PLATEFORME_ID_PLATEFORME = "jp_idPlateforme";
    
    public static final String TABLE_JEU_GENRE = "JeuGenre";
    public static final String TABLE_JEU_GENRE_ID_JEU = "jg_idJeu";
    public static final String TABLE_JEU_GENRE_ID_GENRE = "jg_idGenre";
    
    public static final String TABLE_JEU_STUDIO = "JeuStudio";
    public static final String TABLE_JEU_STUDIO_ID_JEU = "js_idJeu";
    public static final String TABLE_JEU_STUDIO_ID_STUDIO = "js_idStudio";
    
    public static final String TABLE_RUNS = "Runs";
    public static final String TABLE_RUNS_ID = "run_id";
    public static final String TABLE_RUNS_ID_JEU = "run_idJeu";
    public static final String TABLE_RUNS_TITRE = "run_Titre";
    
    public static final String TABLE_LIVES = "Lives";
    public static final String TABLE_LIVES_ID = "liv_id";
    public static final String TABLE_LIVES_DATE_DEBUT = "liv_DateDebut";
    public static final String TABLE_LIVES_DATE_FIN = "liv_DateFin";
    public static final String TABLE_LIVES_RUN = "liv_Run";
    public static final String TABLE_LIVES_MORTS = "liv_Morts";
    
    public static final String VUE_GLOBALE = "VueGlobale";
    
    //TYPES DE DONNEES
    public static final String SQL_TEXT = "TEXT";
    public static final String SQL_INTEGER = "INTEGER";
    public static final String SQL_DATETIME = "DATETIME";
    
    //CREATE ET CONTRAINTES
    public static final String CREATE_TABLE = "CREATE TABLE";
    public static final String CREATE_VIEW = "CREATE VIEW";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String AUTOINCREMENT = "AUTOINCREMENT";
    public static final String REFERENCES = "REFERENCES";
    public static final String NOT_NULL = "NOT NULL";
    public static final String CREATE_VUE_GLOBALE = CREATE_VIEW + " " + VUE_GLOBALE + " AS "
                                                   + TexteConstantesSQL.SELECT_VUE_GLOBALE_SQL;
    //REQUETES SELECT
    public static final String SELECT_PLATEFORMES = "SELECT * FROM Plateformes";
    public static final String SELECT_GENRES = "SELECT * FROM Genres";
    public static final String SELECT_STUDIOS = "SELECT * FROM Studios";
    public static final String SELECT_JEU_PLATEFORME = "SELECT * FROM JeuPlateforme";
    public static final String SELECT_JEU_GENRE = "SELECT * FROM JeuGenre";
    public static final String SELECT_JEU__STUDIO = "SELECT * FROM JeuStudio";
    public static final String SELECT_VUE_GLOBALE = "SELECT * FROM VueGlobale";
    public static final String SELECT_VUE_GLOBALE_SQL = "SELECT * FROM "
                                                         + TABLE_JEUX + " LEFT OUTER JOIN "
                                                         + "(" + TABLE_RUNS +" LEFT OUTER JOIN " + TABLE_LIVES + " ON "
                                                         + TABLE_RUNS_ID + " = " + TABLE_LIVES_RUN + ") ON "
                                                            + TABLE_RUNS_ID_JEU + " = " + TABLE_JEUX_ID;
    //GESTION DE BASE DE DONNÉES (INSERT, UPDATE, DELETE)
    public static final String INSERT = "INSERT INTO";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE FROM";
    public static final String VALUES = "VALUES";
    public static final String SET = "SET";
    public static final String WHERE = "WHERE";
    
}
