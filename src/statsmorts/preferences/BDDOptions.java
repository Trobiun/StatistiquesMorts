/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import statsmorts.vue.FileChooser;
import statsmorts.vue.TexteConstantes;

/**
 *
 * @author Robin
 */
public class BDDOptions extends JPanel {
    
    //ATTRIBUTS
    private JPanel panelBaseDonnees;
    private JPanel panelFichier;
    private JPanel panelServeur;
    private JPanel panelType;
    
    private ButtonGroup groupTypeDatabase;
    private JRadioButton radioFichier;
    private JRadioButton radioServeur;
    
    private FileChooser fileChooser;
    
    private JButton buttonServeur;
    
    private final Preferences preferences;
    private final ServeurOptions serveurOptions;
    
    
    //CONSTRUCTEUR
    public BDDOptions(Preferences prefs) {
        super(new BorderLayout());
        
        preferences = prefs;
        serveurOptions = new ServeurOptions(preferences,false);
        
        initAll();
        setComponents();
    }
    
    
    //ACCESSEURS
    public TypeServeurFichier getType() {
        if (radioServeur.isSelected()) {
            return TypeServeurFichier.SERVEUR;
        }
        else {
            return TypeServeurFichier.FICHIER;
        }
    }
    
    public String getBDDFichier() {
        return fileChooser.getPath();
    }
    
    public ServeurOptions getServeurOptions() {
        return serveurOptions;
    }
    
    public String getTypeServeur() {
        return serveurOptions.getType();
    }
    
    public String getAdresse() {
        return serveurOptions.getAdresse();
    }
    
    public int getPort() {
        return serveurOptions.getPort();
    }
    
    public String getUtilisateur() {
        return serveurOptions.getUtilisateur();
    }
    
    public String getBDDServeur() {
        return serveurOptions.getBaseDonnees();
    }
    
    
    //MUTATEURS PRIVÉS
    private void initAll() {
        initPanels();
        initFileChooser();
        initRadioButtons();
        initButtonGroup();
        initButtons();
    }
    private void initPanels() {
        panelBaseDonnees = new JPanel(new GridBagLayout());
        panelBaseDonnees.setBorder(new TitledBorder(TexteConstantes.BDD));
        
        panelFichier = new JPanel(new BorderLayout(5,5));
        panelFichier.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.FICHIER));
        
        panelServeur = new JPanel(new GridLayout(1,3));
        panelServeur.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.SERVEUR));
        
        panelType = new JPanel(new GridLayout(3,1));
        panelType.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.TYPE));
    }
    private void initFileChooser() {
        if(preferences.getBDDFichier() != null) {
            fileChooser = new FileChooser(TexteConstantes.EMPTY, preferences.getBDDFichier(), JFileChooser.FILES_ONLY, JFileChooser.OPEN_DIALOG,true);
        }
        else {
            fileChooser = new FileChooser(TexteConstantes.EMPTY, TexteConstantes.EMPTY, JFileChooser.FILES_ONLY, JFileChooser.OPEN_DIALOG,true);
        }
        
        fileChooser.setFilter(new FileNameExtensionFilter(TexteConstantes.BDD + " " + TexteConstantes.EXTENSIONS_BDD,"accdb","mdb","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3"),"sqlite3");
    }
    private void initRadioButtons() {
        radioFichier = new JRadioButton(TexteConstantesPreferences.FICHIER);
        radioServeur = new JRadioButton(TexteConstantesPreferences.SERVEUR);
        
        setType(preferences.getType());
    }
    private void initButtonGroup() {
        groupTypeDatabase = new ButtonGroup();
    }
    private void initButtons() {
        buttonServeur = new JButton(TexteConstantesPreferences.CONFIGURER);
        buttonServeur.addActionListener(new ServeurListener());
    }
    
    private void setButtonGroups() {
        groupTypeDatabase.add(radioFichier);
        groupTypeDatabase.add(radioServeur);
    }
    private void setComponents() {
        setButtonGroups();
        
        panelFichier.add(fileChooser,BorderLayout.CENTER);
        
        panelServeur.add(buttonServeur);
        panelServeur.add(radioServeur);
        panelServeur.add(radioFichier);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBaseDonnees.add(panelFichier,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panelBaseDonnees.add(panelServeur,gbc);
        
        add(panelBaseDonnees);
    }
    
    
    //MUTATEURS PUBLICS
    public void reset() {
        resetServeurOptions();
        fileChooser.setText(preferences.getBDDFichier());
        setType(preferences.getType());
    }
    public void resetServeurOptions() {
        serveurOptions.reset();
    }
    private void setType(TypeServeurFichier type) {
        switch (type) {
            case SERVEUR :
                radioServeur.setSelected(true);
                break;
            default :
                radioFichier.setSelected(true);
        }
    }
    
    
    //LISTENER
    class ServeurListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(buttonServeur)) {
                serveurOptions.setPasswordFieldVisible(false);
                int res = JOptionPane.showConfirmDialog(null,serveurOptions, "Configuration serveur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.CANCEL_OPTION) {
                    resetServeurOptions();
                }
                else {
                    
                }
            }
        }
        
    }
}
