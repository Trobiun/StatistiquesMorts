/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import statsmorts.constantes.TexteConstantesPreferences;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import statsmorts.classes.TypeDatabase;
import statsmorts.controler.StatsMortsControler;
import statsmorts.vue.Fenetre;
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesBDD;

/**
 * Une classe pour afficher un dialogue avec les options pour changer les préférences.
 * @author Robin
 */
public class PreferencesDialog extends JDialog {
    
    //ATTRIBUTS STATIC
    private static final int HGAP = 10;
    private static final int VGAP = 5;
    //ATTRIBUTS
    /**
     * Les préférences.
     */
    private final Preferences preferences;
    /**
     * Le controleur pour controler le modèle en fonction des changements dans
     * les options.
     */
    private final StatsMortsControler controler;
    /**
     * La fenêtre parent de ce dialog.
     */
    private Fenetre fenetreParent;
    
    /**
     * Le type de base de données serveur/fichier.
     */
    private TypeServeurFichier typeBDD;
    /**
     * Le type de serveur.
     */
    private String typeServeur;
    
    /**
     * Le TabbedPane pour séparer les options de base de données à celles de
     * de l'affichage.
     */
    private JTabbedPane tabbedPane;
    /**
     * Le panel pour les options de base de données.
     */
    private BDDOptions panelBDD;
    /**
     * Le panel pour les options d'affichage.
     */
    private AffichageOptions panelAffichage;
    /**
     * Le panel pour les boutons.
     */
    private JPanel panelBoutons;
    
    /**
     * Le bouton pour valider (appliquer les changements puis fermer le dialogue).
     */
    private JButton valider;
    /**
     * Le bouton pour appliquer les changements mais garder le dialogue.
     */
    private JButton appliquer;
    /**
     * Le bouton pour enlever les changement appliqués puis fermer le dialogue.
     */
    private JButton annuler;
    
    
    //CONSTRUCTEUR
    /**
     * Crée une dialogue pour les options/préférences.
     * @param parent la fenêtre parent
     * @param title le titre du dialogue
     * @param modal 
     * @param controler le controleur pour appliquer les changements
     * @param prefs le préférences pour les changer ou les lire
     * 
     */
    public PreferencesDialog(JFrame parent, String title, boolean modal, StatsMortsControler controler, Preferences prefs) {
        super(parent,title,modal);
        this.controler = controler;
        this.preferences = prefs;
        this.fenetreParent = null;
        
        typeServeur = preferences.getTypeServeur();
        typeBDD = preferences.getType();
        
        setLayout(new BorderLayout(HGAP,VGAP));
        
        initAll();
        setComponents();
        
        add(tabbedPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    /**
     * Crée un dialogue pour les options/préférences.
     * @param parent la fenêtre parent
     * @param title le titre du dialogue
     * @param modal
     * @param controler le controleur pour appliquer les changements
     * @param prefs les préférences pour les changes ou les lire
     */
    public PreferencesDialog(Fenetre parent, String title, boolean modal, StatsMortsControler controler, Preferences prefs) {
        this((JFrame)parent, title, modal, controler, prefs);
        this.fenetreParent = parent;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Affiche le dialog
     */
    public void showDialog() {
        setVisible(true);
    }
    
    /**
     * Met les composants les uns dans les autres puis dans ce dialogue.
     */
    private void setComponents() {
        tabbedPane.add(TexteConstantesBDD.BDD, panelBDD);
        tabbedPane.add(TexteConstantes.AFFICHAGE,panelAffichage);
        
        panelBoutons.add(valider);
        if (controler != null) {
            panelBoutons.add(appliquer);
            panelBoutons.add(annuler);
        }
    }
    
    /**
     * Initialise tous les composants graphiques.
     */
    private void initAll() {
        initTabbedPane();
        initPanels();
        initButtons();
    }
    /**
     * Initialise le TabbedPane.
     */
    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
    }
    /**
     * Initialise les panels.
     */
    private void initPanels() {
        panelBDD = new BDDOptions(preferences);
        
        panelAffichage = new AffichageOptions(preferences);
        
        panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoutons.setPreferredSize(new Dimension(480,40));
    }
    /**
     * Initialise les boutons et leur ajouté le listener.
     */
    private void initButtons() {
        BoutonsListener boutonListener = new BoutonsListener();
        
        valider = new JButton(TexteConstantes.VALIDER);
        valider.addActionListener(boutonListener);
        annuler = new JButton(TexteConstantes.ANNULER);
        annuler.addActionListener(boutonListener);
        appliquer = new JButton(TexteConstantes.APPLIQUER);
        appliquer.addActionListener(boutonListener);
    }
    
    //LISTENERS
    /**
     * Une classe pourr écouter les boutons.
     */
    class BoutonsListener implements ActionListener {
       
        /**
         * Affiche un dialogue pour la connexion au serveur.
         */
        private void showServeurConnectionDialog() {
            ServeurOptions options = panelBDD.getServeurOptions();
            options.setPasswordFieldVisible(true);
            Object[] boutons = {TexteConstantes.CONNECTER, TexteConstantes.QUITTER};
            int res;
            res = JOptionPane.showOptionDialog(null, options, TexteConstantesPreferences.TITRE_SERVEUR_OPTIONS, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, boutons, null);
            //connexion à un serveur
            if (res == JOptionPane.YES_OPTION && !options.getType().equals(TexteConstantesPreferences.FICHIER)) {
                controler.connecterServeur(TypeDatabase.valueOf(options.getType()), options.getAdresse(), options.getPort(), options.getBaseDonnees(), options.getUtilisateur(), options.getMotDePasse());
            }
            //connexion à un fichier
            if (res == JOptionPane.YES_OPTION && options.getType().equals(TexteConstantesPreferences.FICHIER)) {
                controler.ouvrirBDD(preferences.getBDDFichier());
            }
            //pas de connexion
            if (res == JOptionPane.NO_OPTION) {
                
            }
        }
        
        
        /**
         * Quand le bouton valider est appuyé : applique les changements puis
         *      sauvegarde les préférences et ferme ce dialogue.
         * Quand le bouton appliquer est appuyé : applique les changements
         * Quand le bouton annuler est appuyé : réapplique les préférences
         *      puis ferme ce dialogue.
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(valider)) {
                appliquer(false);
                preferences.savePreferences();
                setVisible(false);
            }
            if (src.equals(appliquer)) {
                appliquer(false);
            }
            if (src.equals(annuler)) {
                preferences.load();
                panelBDD.reset();
                panelAffichage.reset();
                appliquer(true);
                setVisible(false);
            }
        }
        
        /**
         * Applique les changements dans le modèle et dans la vue.
         */
        private void appliquer(boolean annulation) {
            String pathBDD = panelBDD.getBDDFichier();
            File fileBDD = new File(pathBDD);
            
            boolean bddFichierChanged = !preferences.getBDDFichier().equals(pathBDD) || annulation;
            boolean typeBDDChanged = !preferences.getType().equals(panelBDD.getType()) || annulation;
            boolean typeServeurChanged = !preferences.getTypeServeur().equals(panelBDD.getTypeServeur()) || annulation;
            boolean adresseServeurChanged = !preferences.getAdresse().equals(panelBDD.getAdresse()) || annulation;
            boolean portChanged = Integer.parseInt(preferences.getPort()) != panelBDD.getPort() || annulation;
            boolean bddServeurChanged = !preferences.getBDDServeur().equals(panelBDD.getBDDServeur()) || annulation;
            boolean utilisateurChanged = !preferences.getUtilisateur().equals(panelBDD.getUtilisateur()) || annulation;
            boolean affichageGroupChanged = !preferences.getAffichageGroup().equals(panelAffichage.getAffichageGroup()) || annulation;
            boolean affichageTempsChanged = !preferences.getAffichageTemps().equals(panelAffichage.getAffichageTemps()) || annulation;
            
            boolean stateChanged = bddFichierChanged
                    || typeBDDChanged
                    || typeServeurChanged
                    || adresseServeurChanged
                    || portChanged
                    || bddServeurChanged
                    || utilisateurChanged
                    || affichageGroupChanged
                    || affichageTempsChanged
                    || annulation;
            System.out.println(stateChanged);
            System.out.println(pathBDD);
            System.out.println(bddFichierChanged);
            if (stateChanged) {
                preferences.setBDDFichier(pathBDD);
                preferences.setType(panelBDD.getType());
                preferences.setTypeServeur(panelBDD.getTypeServeur());
                preferences.setAdresse(panelBDD.getAdresse());
                preferences.setPort(panelBDD.getPort());
                preferences.setBDDServeur(panelBDD.getBDDServeur());
                preferences.setUtilisateur(panelBDD.getUtilisateur());
                
                preferences.setAffichageGroup(panelAffichage.getAffichageGroup());
                preferences.setAffichageTemps(panelAffichage.getAffichageTemps());
                
                typeBDDChanged = !preferences.getType().equals(typeBDD) || annulation;
                
                if (typeBDDChanged) {
                    typeBDD = panelBDD.getType();
                    if (typeBDD.equals(TypeServeurFichier.FICHIER)) {
                        if (controler != null) {
                            if (fileBDD.isFile()) {
                                controler.ouvrirBDD(pathBDD);
                            }
                            else {
                                controler.creerBDD(pathBDD);
                            }
                        }
                    }
                    else {
                        showServeurConnectionDialog();
//                        ServeurOptions options = panelBDD.getServeurOptions();
//                        options.setPasswordFieldVisible(true);
//                        Object[] boutons = {"Se connecter", "Quitter"};
//                        int res;
//                        res = JOptionPane.showOptionDialog(null, options, "Se connecter à une base de données", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, boutons, null);
//                        if (res == JOptionPane.YES_OPTION && !options.getType().equals("Fichier")) {
//                            controler.connecterServeur(TypeDatabase.valueOf(options.getType()), options.getAdresse(), options.getPort(), options.getBaseDonnees(), options.getUtilisateur(), options.getMotDePasse());
//                        }
//                        if (res == JOptionPane.YES_OPTION && options.getType().equals("Fichier")) {
//                            controler.ouvrirBDD(preferences.getBDDFichier());
//                        }
//                        if (res == JOptionPane.NO_OPTION) {
//                            
//                        }
                    }
                }
                else {
                    if (typeBDD.equals(TypeServeurFichier.FICHIER) && bddFichierChanged) {
                        if (controler != null) {
                            if (fileBDD.exists()) {
                                controler.ouvrirBDD(pathBDD);
                            }
                            else {
                                controler.creerBDD(pathBDD);
                            }
                        }
                    }
                    boolean paramServeurChanged = (typeServeurChanged
                            || adresseServeurChanged
                            || portChanged
                            || bddServeurChanged
                            || utilisateurChanged);
                    if (typeBDD.equals(TypeServeurFichier.SERVEUR) && paramServeurChanged) {
                        showServeurConnectionDialog();
//                        ServeurOptions options = panelBDD.getServeurOptions();
//                        options.setPasswordFieldVisible(true);
//                        Object[] boutons = {"Se connecter", "Quitter"};
//                        int res;
//                        res = JOptionPane.showOptionDialog(null, options, "Se connecter à une base de données", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, boutons, null);
//                        if (res == JOptionPane.YES_OPTION && !options.getType().equals("Fichier")) {
//                            controler.connecterServeur(TypeDatabase.valueOf(options.getType()), options.getAdresse(), options.getPort(), options.getBaseDonnees(), options.getUtilisateur(), options.getMotDePasse());
//                        }
//                        if (res == JOptionPane.YES_OPTION && options.getType().equals("Fichier")) {
//                            controler.ouvrirBDD(preferences.getBDDFichier());
//                        }
//                        if (res == JOptionPane.NO_OPTION) {
//                            
//                        }
                    }
                }
                if (affichageGroupChanged) {
                    if (fenetreParent != null) {
                        fenetreParent.setGroup(preferences.getAffichageGroup());
                    }
                }
                if (affichageTempsChanged) {
                    if (fenetreParent != null) {
                        fenetreParent.setTemps(preferences.getAffichageTemps());
                    }
                }
            }
            
            /*
            if (stateChanged) {
                if (typeBDDChanged) {
                    if (typeBDD.equals(TypeServeurFichier.FICHIER)) {
                        if (controler != null) {
//                            File fileBDD = new File(pathBDD);
                            if (fileBDD.exists()) {
                                controler.ouvrirBDD(pathBDD);
                            }
                            else {
                                controler.creerBDD(pathBDD);
                            }
                        }
                    }
                    else {
                        ServeurOptions options;// = new ServeurOptions(preferences, true);
                        options = panelBDD.getServeurOptions();
                        Object[] boutons = {"Se connecter", "Quitter"};
                        int res;
                        res = JOptionPane.showOptionDialog(null, options, "Se connecter à une base de données", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, boutons, null);
                        if (res == JOptionPane.YES_OPTION && !options.getType().equals("Fichier")) {
                            controler.connecterServeur(TypeDatabase.valueOf(options.getType()), options.getAdresse(), options.getPort(), options.getBaseDonnees(), options.getUtilisateur(), options.getMotDePasse());
                        }
                        if (res == JOptionPane.YES_OPTION && options.getType().equals("Fichier")) {
                            controler.ouvrirBDD(preferences.getBDDFichier());
                        }
                    }
                }
                else {
                    if (bddChanged) {
                        if (controler != null) {
//                            File fileBDD = new File(pathBDD);
                            if (fileBDD.exists()) {
                                controler.ouvrirBDD(pathBDD);
                            }
                            else {
                                controler.creerBDD(pathBDD);
                            }
                        }
                    }
                    else {
                        if (controler != null) {
                            controler.actualiser();
                        }
                    }
                }
            }*/
        }
        
    }
    
}
