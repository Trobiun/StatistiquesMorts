/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.main;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import statsmorts.classes.TypeDatabase;
import statsmorts.controler.StatsMortsControler;
import statsmorts.modele.StatsMortsModele;
import statsmorts.preferences.Preferences;
import statsmorts.preferences.ServeurOptions;
import statsmorts.constantes.TexteConstantesPreferences;
import statsmorts.preferences.TypeServeurFichier;
import statsmorts.vue.Fenetre;
import statsmorts.constantes.TexteConstantes;

/**
 *
 * @author Robin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Préférences : ");
        long start = System.currentTimeMillis();
        File dossPrefs = new File(System.getProperty("user.home") + File.separator + ".StatsMorts");
        dossPrefs.mkdir();
        Preferences preferences = new Preferences(dossPrefs.getPath() + File.separator + "Preferences.ini");
        long endPrefs = System.currentTimeMillis();
        System.out.println("    Total : " + (endPrefs - start) + " ms");
        
        System.out.println("Modèle : ");
        long startModele = System.currentTimeMillis();
        StatsMortsModele modele = new StatsMortsModele(preferences);
        boolean connexionReussie = false;
        TypeServeurFichier type = preferences.getType();
        if (type.equals(TypeServeurFichier.FICHIER)) {
            String pathBDD = preferences.getBDDFichier();
            File fileBDD = new File(pathBDD);
            if (fileBDD.exists()) {
                modele.connecter(pathBDD);
            }
            else  {
                modele.creerBDD(pathBDD);
            }
        }
        else {
            ServeurOptions options = new ServeurOptions(preferences,true);
            Object[] boutons = {TexteConstantes.CONNEECTER,TexteConstantes.QUITTER};
            int res;
            res = JOptionPane.showOptionDialog(null,options,TexteConstantesPreferences.TITRE_SERVEUR_OPTIONS,JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,boutons,null);
            //connexion à un serveur
            if (res == JOptionPane.YES_OPTION && !options.getType().equals(TexteConstantesPreferences.FICHIER)) {
                modele.connecter(TypeDatabase.valueOf(options.getType()), options.getAdresse() + ":" + options.getPort() + "/" + options.getBaseDonnees(), options.getUtilisateur(),options.getMotDePasse());
            }
            //connexion à un fichier
            if (res == JOptionPane.YES_OPTION  && options.getType().equals(TexteConstantesPreferences.FICHIER)) {
                modele.connecter(preferences.getBDDFichier());
            }
            //quitter
            if (res == JOptionPane.NO_OPTION) {
                long end = System.currentTimeMillis();
                System.out.println("Total : " + (end - start) + " ms");
                System.exit(0);
            }
        }
        long endModele= System.currentTimeMillis();
        System.out.println("    Total: " + (endModele - startModele) + " ms");
        
        System.out.println("Controler : ");
        long startControler = System.currentTimeMillis();
        StatsMortsControler controler = new StatsMortsControler(modele);
        long endControler = System.currentTimeMillis();
        System.out.println("    Total : " + (endControler - startControler) + " ms");
        
        System.out.println("Fenêtre : ");
        long startFenetre = System.currentTimeMillis();
        Fenetre fenetre = new Fenetre(TexteConstantes.TITRE_FENETRE, controler, preferences);
        long endFenetre = System.currentTimeMillis();
        System.out.println("    Total : " + (endFenetre - startFenetre) + " ms");
        
        System.out.println("Actualisation : ");
        long startActu = System.currentTimeMillis();
        modele.setObserver(fenetre);
        modele.actualiser();
        long end = System.currentTimeMillis();
        System.out.println("    Total: " + (end - startActu) + " ms");
        System.out.println("Total : " + (end - start) + " ms");
    }
    
}
