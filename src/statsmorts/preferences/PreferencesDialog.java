/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public class PreferencesDialog extends JDialog {
    
    //ATTRIBUTS
    private final Preferences preferences;
    private final StatsMortsControler controler;
    private boolean stateChanged;
    
    private JTabbedPane tabbedPane;
    private BDDOptions panelBDD;
    private JPanel panelBoutons;
    
    private JButton ok;
    private JButton appliquer;
    private JButton annuler;
    
    
    //CONSTRUCTEUR
    public PreferencesDialog(JFrame parent, String title, boolean modal, StatsMortsControler controler, Preferences prefs) {
        super(parent, title, modal);
        this.controler = controler;
        this.preferences = prefs;
        stateChanged = false;
        
        setPreferredSize(new Dimension(520,550));
        setSize(getPreferredSize());
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10,5));
        
        initAll();
        setComponents();
        
        add(tabbedPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void showDialog() {
        setVisible(true);
    }
    
    private void setComponents() {
        tabbedPane.add("Base de données", panelBDD);
        
        panelBoutons.add(ok);
        if (controler != null) {
            panelBoutons.add(annuler);
            panelBoutons.add(appliquer);
        }
    }
    
    private void initAll() {
        initTabbedPane();
        initPanels();
        initButtons();
    }
    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
    }
    private void initPanels() {
        panelBDD = new BDDOptions(preferences);
        
        panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoutons.setPreferredSize(new Dimension(480,40));
    }
    private void initButtons() {
        BoutonsListener boutonListener = new BoutonsListener();
        
        ok = new JButton("OK");
        ok.addActionListener(boutonListener);
        annuler = new JButton("Annuler");
        annuler.addActionListener(boutonListener);
        appliquer = new JButton("Applquer");
        appliquer.addActionListener(boutonListener);
    }
    
    
    //LISTENERS
    class BoutonsListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(ok)) {
                stateChanged = true;
                appliquer();
                preferences.savePreferences();
                stateChanged = false;
                setVisible(false);
            }
            if (src.equals(appliquer)) {
                stateChanged = true;
                appliquer();
            }
            if (src.equals(annuler)) {
                preferences.load();
                panelBDD.reset();
                if (stateChanged) {
                    appliquer();
                }
                stateChanged = false;
                setVisible(false);
            }
        }
        
        private void appliquer() {
            String pathBDD = panelBDD.getBDDFichier();
            boolean bddChanged = !pathBDD.equals(preferences.getBDDFichier());
            
            preferences.setBDDFichier(pathBDD);
            preferences.setType(panelBDD.getType());
            preferences.setTypeServeur(panelBDD.getTypeServeur());
            preferences.setAdresse(panelBDD.getAdresse());
            preferences.setPort(panelBDD.getPort());
            preferences.setBDDServeur(panelBDD.getBDDServeur());
            preferences.setUtilisateur(panelBDD.getUtilisateur());
            if (stateChanged) {
                if (bddChanged) {
                    controler.ouvrirBDD(pathBDD);
                }
                if (!bddChanged) {
                    controler.actualiserTout();
                }
            }
        }
        
    }
    
}
