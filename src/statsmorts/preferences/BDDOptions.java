/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import statsmorts.constantes.TexteConstantesPreferences;
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
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesBDD;

/**
 * Une classe pour les options en rapport avec la base de données.
 * @author Robin
 */
public class BDDOptions extends JPanel {
    
    //ATTRIBUTS
    /**
     * Le panel pour les options base de données.
     */
    private JPanel panelBaseDonnees;
    /**
     * Le panel pour le fichier de base de données.
     */
    private JPanel panelFichier;
    /**
     * Le panel pour le serveur de base de données.
     */
    private JPanel panelServeur;
    /**
     * Le panel pour le type de base de données.
     */
    private JPanel panelType;
    
    /**
     * Le ButtonGroup pour le type de base de données (fichier/serveur)
     */
    private ButtonGroup groupTypeDatabase;
    /**
     * Le RadioButton pour la base de données fichier.
     */
    private JRadioButton radioFichier;
    /**
     * Le RadioButton pour la base de données serveur.
     */
    private JRadioButton radioServeur;
    
    /**
     * Le fileChooser pour la base de données fichier.
     */
    private FileChooser fileChooser;
    
    /**
     * Le bouton pour afficher les options d serveur.
     */
    private JButton buttonServeur;
    
    /**
     * Les préférences.
     */
    private final Preferences preferences;
    /**
     * Le panel des options du serveur.
     */
    private final ServeurOptions serveurOptions;
    
    
    //CONSTRUCTEUR
    /**
     * Crée le panel pour les options de base de données.
     * @param prefs les préférences
     */
    public BDDOptions(Preferences prefs) {
        super(new BorderLayout());
        
        preferences = prefs;
        serveurOptions = new ServeurOptions(preferences,false);
        
        initAll();
        setComponents();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le type de base de données (serveur/fichier) sélectionné.
     * @return le type de base de données (serveur/fichier) sélectionné
     */
    public TypeServeurFichier getType() {
        if (radioServeur.isSelected()) {
            return TypeServeurFichier.SERVEUR;
        }
        else {
            return TypeServeurFichier.FICHIER;
        }
    }
    
    /**
     * Retourne le chemin de la base de données fichier du FilChooser.
     * @return le chemin de la base de données fichier
     */
    public String getBDDFichier() {
        return fileChooser.getPath();
    }
    
    /**
     * Retourne le panel des options du serveur.
     * @return les options du serveur
     */
    public ServeurOptions getServeurOptions() {
        return serveurOptions;
    }
    
    /**
     * Retourne le type de serveur sélectionné dans les options de serveur.
     * @return le type de serveur sélectionné
     */
    public String getTypeServeur() {
        return serveurOptions.getType();
    }
    
    /**
     * Retourne l'adresse du serveur dans les options de serveur.
     * @return l'adresse du serveur
     */
    public String getAdresse() {
        return serveurOptions.getAdresse();
    }
    
    /**
     * Retourne le numéro de port du serveur dans les options de serveur.
     * @return le numéro de port du serveur
     */
    public int getPort() {
        return serveurOptions.getPort();
    }
    
    /**
     * Retourne l'utilisateur du serveur dans les options de serveur.
     * @return l'utilsiateur du serveur
     */
    public String getUtilisateur() {
        return serveurOptions.getUtilisateur();
    }
    
    /**
     * Retourne la base de données du serveur dans les options de serveur.
     * @return la base de données du serveur
     */
    public String getBDDServeur() {
        return serveurOptions.getBaseDonnees();
    }
    
    
    //MUTATEURS PRIVÉS
    /**
     * Initialise tous les composants graphiques.
     */
    private void initAll() {
        initPanels();
        initFileChooser();
        initRadioButtons();
        initButtonGroup();
        initButtons();
    }
    /**
     * Initialise les panels.
     */
    private void initPanels() {
        panelBaseDonnees = new JPanel(new GridBagLayout());
        panelBaseDonnees.setBorder(new TitledBorder(TexteConstantesBDD.BDD));
        
        panelFichier = new JPanel(new BorderLayout(5,5));
        panelFichier.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.FICHIER));
        
        panelServeur = new JPanel(new GridLayout(1,3));
        panelServeur.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),TexteConstantesBDD.SERVEUR));
        
        panelType = new JPanel(new GridLayout(3,1));
        panelType.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),TexteConstantesBDD.TYPE));
    }
    /**
     * Initialise le FileChooser
     */
    private void initFileChooser() {
        if(preferences.getBDDFichier() != null) {
            fileChooser = new FileChooser(TexteConstantes.EMPTY, preferences.getBDDFichier(), JFileChooser.FILES_ONLY, JFileChooser.OPEN_DIALOG,true);
        }
        else {
            fileChooser = new FileChooser(TexteConstantes.EMPTY, TexteConstantes.EMPTY, JFileChooser.FILES_ONLY, JFileChooser.OPEN_DIALOG,true);
        }
        
        fileChooser.setFilter(new FileNameExtensionFilter(TexteConstantesBDD.BDD + " " + TexteConstantesBDD.EXTENSIONS_BDD,"accdb","mdb","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3"),"sqlite3");
    }
    /**
     * Initialise les RadioButtons.
     */
    private void initRadioButtons() {
        radioFichier = new JRadioButton(TexteConstantesPreferences.FICHIER);
        radioServeur = new JRadioButton(TexteConstantesPreferences.SERVEUR);
        
        setType(preferences.getType());
    }
    /**
     * Initialise le ButtonGroup.
     */
    private void initButtonGroup() {
        groupTypeDatabase = new ButtonGroup();
    }
    /**
     * Initialise les boutons.
     */
    private void initButtons() {
        buttonServeur = new JButton(TexteConstantesPreferences.CONFIGURER);
        buttonServeur.addActionListener(new ServeurListener());
    }
    
    /**
     * Met les RadioButton dans le ButtonGroup.
     */
    private void setButtonGroups() {
        groupTypeDatabase.add(radioFichier);
        groupTypeDatabase.add(radioServeur);
    }
    /**
     * Met les composants les uns dans les autres puis les met dans ce panel.
     */
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
    /**
     * Remet les options en comme dans les préférences.
     */
    public void reset() {
        resetServeurOptions();
        fileChooser.setText(preferences.getBDDFichier());
        setType(preferences.getType());
    }
    /**
     * Remet les optios du serveur comme dans les préférences.
     */
    public void resetServeurOptions() {
        serveurOptions.reset();
    }
    /**
     * Sélectionne le RadioButton approprié par rapport au type passé en paramètre.
     * @param type le type de fichier/serveur à sélectionner
     */
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
    /**
     * Une classe pour afficher les options du serveur.
     */
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
