/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class BDD {
    
    //ATTRIBUTS
    private Map<Long,Plateforme> plateformes;
    private Map<Long,Type> types;
    private Map<Long,Studio> studios;
    private Map<Long,Jeu> jeux;
    private Map<Long,Run> runs;
    private Map<Long,Live> lives;
    
    
    //CONSTRUCTEURS
    public BDD(Connexion connexion, String path) {
        this.connecter(connexion,path);
    }
    public BDD(Connexion connexion, TypeDatabase type, String serveur, String user, String password) {
        this.connecter(connexion, type, serveur, user, password);
    }
    
    
    //ACCESSEURS
    public Map<Long,Plateforme> getPlateformes() {
        return plateformes;
    }
    
    public Map<Long,Type> getTypes() {
        return types;
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
        String res = "";
        return jeux.toString();
//        Set<Entry<Long,Jeu>> setJeux = jeux.entrySet();
//        for (Entry<Long,Jeu> entry : setJeux) {
//            res += entry.getValue().toString() + "";
//        }
//        return res;
    }
    
    //MUTATEURS
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
        types = initMap(types);
        studios = initMap(studios);
        jeux = initMap(jeux);
        runs = initMap(runs);
        lives = initMap(lives);
    }
    
   public void connecter(Connexion connexion, String path) {
        connexion.connecter(path);
        initAll();
    }
    
    public void connecter(Connexion connexion, TypeDatabase type, String serveur, String user, String password) {
        connexion.connecter(type, serveur, user, password);
        initAll();
    }
    
    public void actualiserBDD(Connexion connexion) throws SQLException {
        String requetePlateformes = "SELECT * FROM Plateformes";
        String requeteTypes = "SELECT * FROM Types";
        String requeteStudios = "SELECT * FROM Studios";
        String requeteJeuPlateforme = "SELECT * FROM JeuPlateforme";
        String requeteJeuType = "SELECT * FROM JeuType";
        String requeteJeuStudio = "SELECT * FROM JeuStudio";
        String requeteVueGlobale = "SELECT * FROM VueGlobale";
        
        long idPlateforme;
        String nomPlateforme;
        Plateforme plateforme;
        ResultSet resultsPlateformes = connexion.executerRequete(requetePlateformes);
        while (resultsPlateformes.next()) {
            idPlateforme = resultsPlateformes.getLong("pla_id");
            nomPlateforme = resultsPlateformes.getString("pla_Nom");
            plateforme = new Plateforme(idPlateforme,nomPlateforme);
            plateformes.put(idPlateforme,plateforme);
        }
        
        long idType;
        String nomType;
        Type type;
        ResultSet resultsTypes = connexion.executerRequete(requeteTypes);
        while (resultsTypes.next()) {
            idType = resultsTypes.getLong("typ_id");
            nomType = resultsTypes.getString("typ_Nom");
            type = new Type(idType,nomType);
            types.put(idType,type);
        }
        
        long idStudio;
        String nomStudio;
        Studio studio;
        ResultSet resultsStudios = connexion.executerRequete(requeteStudios);
        while (resultsStudios.next()) {
            idStudio = resultsStudios.getLong("stu_id");
            nomStudio = resultsStudios.getString("stu_Nom");
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
            idJeu = resultsVueGlobale.getLong("jeu_id");
            titreJeu = resultsVueGlobale.getString("jeu_Titre");
            anneeSortieJeu = resultsVueGlobale.getInt("jeu_AnneeSortie");
            if (!jeux.containsKey(idJeu) && idJeu > 0) {
                jeu = new Jeu(idJeu,titreJeu,anneeSortieJeu);
                jeux.put(idJeu,jeu);
            }
            else {
                jeu = jeux.get(idJeu);
            }
            
            idRun = resultsVueGlobale.getLong("run_id");
            titreRun = resultsVueGlobale.getString("run_Titre");
            if (!runs.containsKey(idRun) && idRun > 0) {
                run = new Run(idRun,titreRun);
                runs.put(idRun,run);
                jeu.putRun(run);
            }
            else {
                run = runs.get(idRun);
            }
            
            idLive = resultsVueGlobale.getLong("liv_id");
            dateDebut = resultsVueGlobale.getString("liv_DateDebut");
            dateFin = resultsVueGlobale.getString("liv_DateFin");
            morts = resultsVueGlobale.getInt("liv_Morts");
            if (!lives.containsKey(idLive) && idLive > 0) {
                live = new Live(idLive,dateDebut,dateFin,morts);
                lives.put(idLive,live);
                run.putLive(live);
            }
            else {
                live = lives.get(idLive);
            }
        }
        
        ResultSet resultsJeuPlateforme = connexion.executerRequete(requeteJeuPlateforme);
        while (resultsJeuPlateforme.next()) {
            idPlateforme = resultsJeuPlateforme.getLong("jp_idPlateforme");
            idJeu = resultsJeuPlateforme.getLong("jp_idJeu");
            plateforme = plateformes.get(idPlateforme);
            jeu = jeux.get(idJeu);
            plateforme.putJeu(jeu);
            jeu.putPlateforme(plateforme);
        }
        
        ResultSet resultsJeuType = connexion.executerRequete(requeteJeuType);
        while (resultsJeuType.next()) {
            idType = resultsJeuType.getLong("jt_idType");
            idJeu = resultsJeuType.getLong("jt_idJeu");
            type = types.get(idType);
            jeu = jeux.get(idJeu);
            type.putJeu(jeu);
            jeu.putType(type);
        }
        
        ResultSet resultsJeuStudio = connexion.executerRequete(requeteJeuStudio);
        while (resultsJeuStudio.next()) {
            idStudio = resultsJeuStudio.getLong("js_idStudio");
            idJeu = resultsJeuStudio.getLong("js_idJeu");
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
            
            String requete1 = "CREATE TABLE Plateformes ("
                                                      + "pla_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                      + "pla_Nom TEXT)";
            String requete2 = "CREATE TABLE Types ("
                                                + "typ_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                + "typ_Nom TEXT)";
            String requete3 = "CREATE TABLE Studios ("
                                                 + "stu_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                 + "stu_Nom TEXT)";
            String requete4 = "CREATE TABLE Jeux ("
                                               + "jeu_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                               + "jeu_Titre TEXT,"
                                               + "jeu_AnneSortie INTEGER)";
            String requete5 = "CREATE TABLE JeuPlateforme("
                                                       + "jp_idJeu INTEGER REFERENCES Jeux(jeu_id),"
                                                       + "jp_idPlateforme INTEGER REFERENCES Plateforme(pla_id),"
                                                       + "PRIMARY KEY (jp_idJeu,jp_idPlateforme))";
            String requete6 = "CREATE TABLE JeuType("
                                                 + "jt_idJeu INTEGER REFERENCES Jeux(jeu_id),"
                                                 + "jt_idType INTEGER REFERENCES Types(typ_id),"
                                                 + "PRIMARY KEY (jt_idJeu,jt_idType))";
            String requete7 = "CREATE TABLE JeuStudio("
                                                   + "js_idJeu INTEGER REFERENCES Jeux(jeu_id),"
                                                   + "js_idType INTEGER REFERENCES Types(typ_id),"
                                                   + "PRIMARY KEY (js_idJeu,js_idType))";
            String requete8 = "CREATE TABLE Runs("
                                             + "run_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                             + "run_idJeu INTEGER REFERENCES Jeux(jeu_id),"
                                             + "run_Titre TEXT)";
            String requete9 = "CREATE TABLE Lives("
                                              + "liv_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                              + "liv_DateDebut DATETIME NOT NULL,"
                                              + "liv_DateFin DATETIME NOT NULL,"
                                              + "liv_Run INTEGER REFERENCES Runs(run_id),"
                                              + "liv_Morts INTEGER)";
            String requete10 = "CREATE VIEW VueGlobale AS "
                                            + "SELECT * FROM( "
                                            + "Jeux LEFT OUTER JOIN "
                                            + "(Runs LEFT OUTER JOIN Lives ON run_id = liv_Run) ON run_idJeu = jeu_id)";
            
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
            
            res = new BDD(connexion,database);
            
        }
        else {
            res = new BDD(connexion,database);
        }
        
        return res;
    }
    
}
