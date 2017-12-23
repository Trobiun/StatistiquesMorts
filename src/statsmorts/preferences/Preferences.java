/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import statsmorts.constantes.TexteConstantesPreferences;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import statsmorts.classes.TypeGroup;


/**
 * Une classe pour gérer les préférences de l'application.
 * @author Robin
 */
public class Preferences {
    
    //ATTRIBUTS
    /**
     * L'objet Ini pour gérer les préférences.
     */
    private Ini ini;
    /**
     * Le fichier de préférences.
     */
    private File filePreferences;
    
    //CONSTRUCTEURS
    /**
     * Crée une objet Preferences avec comme fichier filePreferences.
     * @param filePreferences le fichier des préférences
     */
    public Preferences(File filePreferences) {
        this.filePreferences = filePreferences;
        boolean created = false;
        try {
            created = this.filePreferences.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                ini = new Wini(this.filePreferences);
                
            }
            else {
                ini = new Ini(this.filePreferences);
            }
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        ini.getConfig().setFileEncoding(Charset.forName("utf8"));
        if (created) {
            createPreferencesFile();
        }
    }
    public Preferences(String pathPreferences) {
        this(new File(pathPreferences));
    }
    
    
    //ACCESSEURS
    /**
     * Retourne une chaîne de caractères qui représente l'objet Preferences.
     * @return une chaîne de caractères qui représente l'objet Preferences
     */
    @Override
    public String toString() {
        String res = "Base de données : " + getBDDFichier() + "\n";
        res += "Type base de données : " + getType() + "\n";
        return res;
    }
    
    /**
     * Retourne le type de base de données préféré (serveur / fichier)
     * @return le type de base de données préféré (serveur / fichier)
     */
    public TypeServeurFichier getType() {
        return TypeServeurFichier.valueOf(ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE));
    }
    /**
     * Retourne le chemin de la base de données fichier dans les préférences.
     * @return le chemin de la base de données fichier dans les préférences
     */
    public String getBDDFichier() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_FICHIER);
    }
    /**
     * Retourne le type de serveur en chaîne de caractères (MySQL / PostgreSQL)
     * dans les préférences.
     * @return  le type de serveur en chaîne de caractères (MySQL / PostgreSQL)
     */
    public String getTypeServeur() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE_SERVEUR);
    }
    /**
     * Retourne l'adresse du serveur dans les préférences.
     * @return l'adresse du serveur dans les préférences
     */
    public String getAdresse() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.ADRESSE);
    }
    /**
     * Retourne le numéro de port du serveur dans les préférences.
     * @return le numéro de port du serveur dans les préférences
     */
    public String getPort() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.PORT);
    }
    /**
     * Retouurne la base de données à laquelle se connecter sur le serveur dans 
     * les préférences.
     * @return la base de donnée à laquelle se connecter sur le serveur dans
     * les préférences
     */
    public String getBDDServeur() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_SERVEUR);
    }
    /**
     * Retourne l'utilisateur à utiliser pour le serveur dans les préférences.
     * @return l'utilisateur à utiliser pour le serveur dans les préférences
     */
    public String getUtilisateur() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.UTILISATEUR);
    }
    /**
     * Retourne le type de groupement des jeux par défaut pour l'affichage dans les préférences.
     * @return le type de groupement des jeux par défaut pour l'affichage dans les préférences
     */
    public TypeGroup getAffichageGroup() {
        return TypeGroup.valueOf(ini.fetch(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_GROUPE));
    }
    /**
     * Retourne le type de temps à afficher par défaut pour l'affichage du temps
     * dans les préférences.
     * @return le type de temps à afficher par défaut pour l'affichage du temps
     * dans les préférences
     */
    public Temps getAffichageTemps() {
        return Temps.valueOf(ini.fetch(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_TEMPS));
    }
    
    
    //MUTATEURS
    /**
     * Recharge le fichier ini.
     */
    public void load() {
        try {
            ini.load();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Met le type de base de données serveur/fichier dans les préférences.
     * @param type le type de base de données serveur/fichier
     */
    public void setType(TypeServeurFichier type) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE, type);
    }
    /**
     * Met la base de données fichier dans les préférences.
     * @param path le chemin de la base de données
     */
    public void setBDDFichier(String path) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_FICHIER, path);
    }
    /**
     * Met le type de serveur dans les préférences.
     * @param type le type de serveur
     */
    public void setTypeServeur(String type) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE_SERVEUR, type);
    }
    /**
     * Met l'adresse du serveur de base de données dans les préférences.
     * @param adresse l'adresse du serveur
     */
    public void setAdresse(String adresse) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.ADRESSE, adresse);
    }
    /**
     * Met le numéro de port du serveur de base de données dans les préférences.
     * @param port le numéro du port du serveur de base de données
     */
    public void setPort(int port) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.PORT, port);
    }
    /**
     * Met la base de données du serveur de base de données dans les préférences.
     * @param bdd le nom de la base de données du serveur
     */
    public void setBDDServeur(String bdd) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_SERVEUR, bdd);
    }
    /**
     * Met l'utilisateur du serveur de base de données dans les préférences.
     * @param user l'utilisateur du serveur
     */
    public void setUtilisateur(String user) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.UTILISATEUR, user);
    }
    /**
     * Met le groupement pour l'affichage dans les préférences.
     * @param group le groupement d'affichage
     */
    public void setAffichageGroup(TypeGroup group) {
        ini.put(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_GROUPE, group);
    }
    /**
     * Met le type d'affichage du temps dans les préférences.
     * @param temps le type d'affichage du temps
     */
    public void setAffichageTemps(Temps temps) {
        ini.put(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_TEMPS, temps);
    }
    
    /**
     * Sauvegarde les préférences.
     */
    public void savePreferences() {
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Crée le fichier de préférences et l'intialise avec quelques valeurs par défaut. 
     */
    public void createPreferencesFile() {
        ini.clear();
        ini.setFile(filePreferences);
        
        Section bdd = ini.add(TexteConstantesPreferences.BASE_DE_DONNEES);
        bdd.add(TexteConstantesPreferences.TYPE, TypeServeurFichier.FICHIER);
        bdd.add(TexteConstantesPreferences.BDD_FICHIER, TexteConstantesPreferences.EMPTY);
        bdd.add(TexteConstantesPreferences.TYPE_SERVEUR, TexteConstantesPreferences.TYPE_SERVEUR_DEFAULT);
        bdd.add(TexteConstantesPreferences.ADRESSE, TexteConstantesPreferences.ADRESSE_DEFAULT);
        bdd.add(TexteConstantesPreferences.PORT, 3306);
        bdd.add(TexteConstantesPreferences.BDD_SERVEUR, TexteConstantesPreferences.EMPTY);
        bdd.add(TexteConstantesPreferences.UTILISATEUR,System.getProperty("user.name"));
        
        Section affichage = ini.add(TexteConstantesPreferences.AFFICHAGE);
        affichage.add(TexteConstantesPreferences.AFFICHAGE_GROUPE,TypeGroup.JEUX);
        affichage.add(TexteConstantesPreferences.AFFICHAGE_TEMPS,Temps.HEURES);
        
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFrame framePrefs = new JFrame();
        framePrefs.setVisible(true);
        framePrefs.setUndecorated(true);
        framePrefs.setLocationRelativeTo(null);
        PreferencesDialog prefsDialog = new PreferencesDialog(framePrefs, TexteConstantesPreferences.INITIALISATION_TEXTE, true, null, this);
        prefsDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        prefsDialog.showDialog();
        framePrefs.dispose();
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Détruit le fichier de préférences.
     */
    public void destroyPreferencesFile() {
        filePreferences.delete();
    }
    
}
