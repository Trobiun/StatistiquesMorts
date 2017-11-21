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
    
    public String getType() {
        return ini.fetch("Base de donnees", "type");
    }
    public String getBDDFichier() {
        return ini.fetch("Base de donnees", "bdd_fichier");
    }
    public String getTypeServeur() {
        return ini.fetch("Base de donnees", "type_serveur");
    }
    public String getAdresse() {
        return ini.fetch("Base de donnees", "adresse");
    }
    public String getPort() {
        return ini.fetch("Base de donnees", "port");
    }
    public String getBDDServeur() {
        return ini.fetch("Base de donnees", "bdd_serveur");
    }
    public String getUtilisateur() {
        return ini.fetch("Base de donnees", "utilisateur");
    }
    
    //MUTATEURS
    public void load() {
        try {
            ini.load();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setType(String type) {
        ini.put("Base de donnees", "type", type);
    }
    public void setBDDFichier(String path) {
        ini.put("Base de donnees", "bdd_fichier", path);
    }
    public void setTypeServeur(String type) {
        ini.put("Base de donnees", "type_serveur", type);
    }
    public void setAdresse(String adresse) {
        ini.put("Base de donnees", "adresse", adresse);
    }
    public void setPort(String port) {
        ini.put("Base de donnees", "port", port);
    }
    public void setBDDServeur(String bdd) {
        ini.put("Base de donnees", "bdd_serveur", bdd);
    }
    public void setUtilisateur(String user) {
        ini.put("Base de donnees", "utilisateur", user);
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
        
        Section bdd = ini.add("Base de donnees");
        bdd.add("type", "Fichier");
        bdd.add("bdd_fichier", "");
        bdd.add("type_serveur", "MySQL");
        bdd.add("adresse","localhost");
        bdd.add("port","");
        bdd.add("bdd_serveur","");
        bdd.add("utilisateur",System.getProperty("user.name"));
        
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PreferencesDialog prefsDialog = new PreferencesDialog(null, "Initialisation des Préferences", true, null, this);
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
