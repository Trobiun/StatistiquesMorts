/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import statsmorts.classes.BDD;
import statsmorts.classes.Connexion;
import statsmorts.classes.TypeDatabase;
import statsmorts.preferences.Preferences;

/**
 *
 * @author Robin
 */
public class Modele {
    
    //ATTRIBUTS
    private final Preferences preferences;
    private final Connexion connexion;
    private BDD bdd;
    
    
    //CONSTRUCTEUR
    public Modele(Preferences prefs) {
        connexion = new Connexion();
        preferences = prefs;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void creerBDD(String pathBDD) {
        deconnecter();
        bdd = BDD.creerBDD(connexion,pathBDD);
        actualiser();
    }
    
    public void ouvrirBDD(String pathBDD) {
        long start = System.currentTimeMillis();
        deconnecter();
        bdd = new BDD(connexion,pathBDD);
        actualiser();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    
    public void connecter(String database) {
        bdd = new BDD(connexion,database);
    }
    
    public void connecter(TypeDatabase type, String serveur, String user, String password) {
        bdd = new BDD(connexion, type, serveur, user, password);
    }
    
    public void actualiser() {
        try {
            bdd.actualiserBDD(connexion);
            System.out.println(bdd.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
}
