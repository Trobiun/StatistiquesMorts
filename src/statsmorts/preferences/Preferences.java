/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import statsmorts.classes.TypeGroup;


/**
 *
 * @author Robin
 */
public class Preferences {
    
    //ATTRIBUTS
    private Ini ini;
    private File filePreferences;
    
    //CONSTRUCTEURS
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
    @Override
    public String toString() {
        String res = "Base de données : " + getBDDFichier() + "\n";
        res += "Type base de données : " + getType() + "\n";
        return res;
    }
    
    public TypeServeurFichier getType() {
        return TypeServeurFichier.valueOf(ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE));
    }
    public String getBDDFichier() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_FICHIER);
    }
    public String getTypeServeur() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE_SERVEUR);
    }
    public String getAdresse() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.ADRESSE);
    }
    public String getPort() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.PORT);
    }
    public String getBDDServeur() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_SERVEUR);
    }
    public String getUtilisateur() {
        return ini.fetch(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.UTILISATEUR);
    }
    public TypeGroup getAffichageRacine() {
        return TypeGroup.valueOf(ini.fetch(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_GROUPE));
    }
    public Temps getAffichageTemps() {
        return Temps.valueOf(ini.fetch(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_TEMPS));
    }
    
    
    //MUTATEURS
    public void load() {
        try {
            ini.load();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setType(TypeServeurFichier type) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE, type);
    }
    public void setBDDFichier(String path) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_FICHIER, path);
    }
    public void setTypeServeur(String type) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.TYPE_SERVEUR, type);
    }
    public void setAdresse(String adresse) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.ADRESSE, adresse);
    }
    public void setPort(String port) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.PORT, port);
    }
    public void setBDDServeur(String bdd) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.BDD_SERVEUR, bdd);
    }
    public void setUtilisateur(String user) {
        ini.put(TexteConstantesPreferences.BASE_DE_DONNEES, TexteConstantesPreferences.UTILISATEUR, user);
    }
    public void setAffichageGroup(TypeGroup group) {
        ini.put(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_GROUPE, group);
    }
    public void setAffichageTemps(Temps temps) {
        ini.put(TexteConstantesPreferences.AFFICHAGE, TexteConstantesPreferences.AFFICHAGE_TEMPS, temps);
    }
    
    public void savePreferences() {
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void createPreferencesFile() {
        ini.clear();
        ini.setFile(filePreferences);
        
        Section bdd = ini.add(TexteConstantesPreferences.BASE_DE_DONNEES);
        bdd.add(TexteConstantesPreferences.TYPE, TypeServeurFichier.FICHIER);
        bdd.add(TexteConstantesPreferences.BDD_FICHIER, TexteConstantesPreferences.EMPTY);
        bdd.add(TexteConstantesPreferences.TYPE_SERVEUR, TexteConstantesPreferences.TYPE_SERVEUR_DEFAULT);
        bdd.add(TexteConstantesPreferences.ADRESSE, TexteConstantesPreferences.ADRESSE_DEFAULT);
        bdd.add(TexteConstantesPreferences.PORT, TexteConstantesPreferences.EMPTY);
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
        
        PreferencesDialog prefsDialog = new PreferencesDialog(null, TexteConstantesPreferences.INITIALISATION_TEXTE, true, null, this);
        prefsDialog.showDialog();
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void destroyPreferencesFile() {
        filePreferences.delete();
    }
    
}
