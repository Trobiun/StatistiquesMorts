/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import statsmorts.constantes.TexteConstantesPreferences;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Une classe pour les options de serveur.
 * @author robin
 */
public class ServeurOptions extends JPanel {
    
    //ATTRIBUTS
    /**
     * Le panel pour l'adresse.
     */
    private JPanel panelAdresse;
    /**
     * Le panel pour le numéro de port.
     */
    private JPanel panelPort;
    /**
     * Le panel pour le nom de la base de données.
     */
    private JPanel panelBaseDonnees;
    /**
     * Le panel pour le nom d'utilisateur.
     */
    private JPanel panelUser;
    /**
     * Le panel pour le mot de passe.
     */
    private JPanel panelPassword;
    /**
     * Le panel pour le type de serveur.
     */
    private JPanel panelType;
    
    /**
     * Le champ de saisie de l'adresse.
     */
    private JTextField fieldAdresse;
    /**
     * Le champ de saisie du port.
     */
    private JTextField fieldPort;
    /**
     * Le champ de saisie du nom de la base de données.
     */
    private JTextField fieldBaseDonnees;
    /**
     * Le champ de saisie du nom d'utilisateur.
     */
    private JTextField fieldUtilisateur;
    /**
     * Le champ de saisie pour le mot de passe.
     */
    private JPasswordField fieldPassword;
    
    /**
     * Le BoutonGroup pour le type de serveur (+ fichier pour la connexion au
     * lancement, si la connexion au serveur ne fonctionne pas).
     */
    private ButtonGroup groupType;
    /**
     * Le RadioButton pour se connecter à la base de données fichier (au cas où
     * la connexion serveur ne fonctionne pas).
     */
    private JRadioButton radioFichier;
    /**
     * Le RadioButton pour MySQL.
     */
    private JRadioButton radioMySQL;
    /**
     * Le RadioButton pour PostgreSQL.
     */
    private JRadioButton radioPostgreSQL;
    
    /**
     * Les préférences.
     */
    private final Preferences preferences;
    /**
     * Booléen qui détermine si le RadioButton 'fichier' s'affiche ou pas.
     */
    private final boolean acceptFile;
    
    
    //CONSTRUCTEUR
    /**
     * Crée le panek pour les options de serveur.
     * @param preferences les préférences
     * @param acceptFile booléen qui détermine si le RadioButton 'fichier' s'affiche.
     */
    public ServeurOptions(Preferences preferences, boolean acceptFile) {
        super(new GridBagLayout());
        if (System.getProperty("os.name").startsWith("Linux")) {
            this.setPreferredSize(new Dimension(400,250));
        }
        this.preferences = preferences;
        this.acceptFile = acceptFile;
        
        initAll();
        
        setComponents();
        
        reset();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'adresse du serveur saisie dans les options.
     * @return l'adresse du serveur saisie
     */
    public String getAdresse() {
        return fieldAdresse.getText();
    }
    
    /**
     * Retourne le numéro de port saisi dans les options.
     * @return le numéro de port saisi.
     */
    public int getPort() {
        int res = 0;
        try {
            res = Integer.parseInt(fieldPort.getText());
        } catch (NumberFormatException e) {
            
        }
        return res;
    }
    
    /**
     * Retourne le nom de la base de données du serveur saisi dans les options.
     * @return le nom de la base de données du serveur saisi.
     */
    public String getBaseDonnees() {
        return fieldBaseDonnees.getText();
    }
    
    /**
     * Retourne le nom d'utilsiateur du serveur saisi dans les options.
     * @return le nom d'utilisateur du serveur saisi.
     */
    public String getUtilisateur() {
        return fieldUtilisateur.getText();
    }
    
    /**
     * Retourne le mot de passe saisi dans la connexion au serveur.
     * @return le mot de passe saisi pour se connecter au serveur
     */
    public char[] getMotDePasse() {
        return fieldPassword.getPassword();
    }
    
    /**
     * Retourne le type de serveur en chaîne de caractères (ou 'fichier'
     * pour la connexion au lancement)
     * @return le type de serveur
     */
    public String getType() {
        if (radioMySQL.isSelected()) {
            return TexteConstantesPreferences.MYSQL;
        }
        if (radioPostgreSQL.isSelected()) {
            return TexteConstantesPreferences.POSTGRESQL;
        }
        return TexteConstantesPreferences.FICHIER;
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void initAll() {
        initPanels();
        initFields();
        initRadios();
        initGroup();
    }
    /**
     * Initialise les panels.
     */
    private void initPanels() {
        panelAdresse = new JPanel(new BorderLayout());
        panelAdresse.setPreferredSize(new Dimension(250,40));
        panelAdresse.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Adresse :"));
        
        panelPort = new JPanel(new BorderLayout());
        panelPort.setPreferredSize(new Dimension(75,40));
        panelPort.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Port :"));
        
        panelBaseDonnees = new JPanel(new BorderLayout());
        panelBaseDonnees.setPreferredSize(new Dimension(250,40));
        panelBaseDonnees.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Base de données :"));
        
        panelUser = new JPanel(new BorderLayout());
        panelUser.setPreferredSize(new Dimension(250,40));
        panelUser.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Utilisaterur :"));
        
        panelPassword = new JPanel(new BorderLayout());
        panelPassword.setPreferredSize(new Dimension(250,40));
        panelPassword.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Mot de passe :"));
        
        panelType = new JPanel(new FlowLayout());
        panelType.setPreferredSize(new Dimension(250,40));
        panelType.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Type :"));
    }
    /**
     * Initialise les champs de saisie.
     */
    private void initFields() {
        fieldAdresse = new JTextField();
        fieldPort = new JTextField();
        fieldBaseDonnees = new JTextField();
        fieldUtilisateur = new JTextField();
        fieldPassword = new JPasswordField() {
            @Override
            public void addNotify() {
                super.addNotify();
                requestFocusInWindow();
            }
        };
    }
    /**
     * Initialise les RadioButtons.
     */
    private void initRadios() {
        radioFichier = new JRadioButton(TexteConstantesPreferences.FICHIER);
        radioMySQL = new JRadioButton(TexteConstantesPreferences.MYSQL);
        radioPostgreSQL = new JRadioButton(TexteConstantesPreferences.POSTGRESQL);
    }
    /**
     * Initialise le ButtonGroup.
     */
    private void initGroup() {
        groupType = new ButtonGroup();
    }
    
    /**
     * Met les composants les uns dans les autres puis dans ce panel.
     */
    private void setComponents() {
        panelAdresse.add(fieldAdresse);
        panelPort.add(fieldPort);
        panelBaseDonnees.add(fieldBaseDonnees);
        panelUser.add(fieldUtilisateur);
        panelPassword.add(fieldPassword);
        if (acceptFile) {
            panelType.add(radioFichier);
        }
        panelType.add(radioMySQL);
        panelType.add(radioPostgreSQL);
        
        groupType.add(radioFichier);
        groupType.add(radioMySQL);
        groupType.add(radioPostgreSQL);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 5;
        gbc.weighty = 5;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        add(panelAdresse,gbc);
        
        gbc.gridx = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(panelPort,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(panelBaseDonnees,gbc);
        
        
        gbc.gridy = 2;
        add(panelUser,gbc);
        
        gbc.gridy = 3;
        add(panelPassword,gbc);
        
        gbc.gridy = 4;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        add(panelType,gbc);
    }
    
    /**
     * Met l'adresse dans le champ de saisie approprié.
     * @param adresse l'adresse à mettre dans le champ de saisie
     */
    private void setAddresse(String adresse) {
        fieldAdresse.setText(adresse);
    }
    /**
     * Met le numéro de port dans le champ de saisie approprié.
     * @param port le numéro de port
     */
    private void setPort(String port) {
        fieldPort.setText(port);
    }
    /**
     * Met le nom de la base de données dans le champ de saisie approprié.
     * @param baseDonnees le nom de la base de données
     */
    private void setBaseDonnees(String baseDonnees) {
        fieldBaseDonnees.setText(baseDonnees);
    }
    /**
     * Met le nom d'utilisateur dans le champ de saisie approprié.
     * @param user le nom d'utilisateur
     */
    private void setUtilisateur(String user) {
        fieldUtilisateur.setText(user);
    }
    /**
     * Sélectionne le RadioButton approprié en fonction du type de serveur 
     * donné en paramètre
     * @param type le type de serveur à sélectionner
     */
    private void setType(String type) {
        switch(type) {
            case TexteConstantesPreferences.MYSQL :
                radioMySQL.setSelected(true);
                break;
            case TexteConstantesPreferences.POSTGRESQL :
                radioPostgreSQL.setSelected(true);
                break;
            default :
                radioFichier.setSelected(true);
        }
    }
    
    /**
     * Met le champ de saisie du mot de passe visible ou non.
     * @param isVisible booléen qui détermine si le champ de saisie du mot de passe
     * est visible ou non
     */
    public void setPasswordFieldVisible(boolean isVisible) {
        panelPassword.setVisible(isVisible);
//        panelPassword.getParent().revalidate();
//        panelPassword.getParent().repaint();
    }
    
    /**
     * Remet les champs de saisie en cohérence avec les préférences.
     */
    public void reset() {
        setType(preferences.getTypeServeur());
        setAddresse(preferences.getAdresse());
        setPort(preferences.getPort());
        setBaseDonnees(preferences.getBDDServeur());
        setUtilisateur(preferences.getUtilisateur());
    }
    
}
