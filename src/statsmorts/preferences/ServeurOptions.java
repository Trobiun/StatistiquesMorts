/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

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
 *
 * @author robin
 */
public class ServeurOptions extends JPanel {
    
    //ATTRIBUTS
    private JPanel panelAdresse;
    private JPanel panelPort;
    private JPanel panelBaseDonnees;
    private JPanel panelUser;
    private JPanel panelPassword;
    private JPanel panelType;
    
    private JTextField fieldAdresse;
    private JTextField fieldPort;
    private JTextField fieldBaseDonnees;
    private JTextField fieldUtilisateur;
    private JPasswordField fieldPassword;
    
    private ButtonGroup groupType;
    private JRadioButton radioFichier;
    private JRadioButton radioMySQL;
    private JRadioButton radioPostgreSQL;
    
    private final Preferences preferences;
    private final boolean acceptFile;
    
    
    //CONSTRUCTEUR
    public ServeurOptions(Preferences preferences, boolean acceptFile) {
        super(new GridBagLayout());
        this.setPreferredSize(new Dimension(400,250));
        this.preferences = preferences;
        this.acceptFile = acceptFile;
        
        initAll();
        
        setComponents();
        
        reset();
    }
    
    //ACCESSEURS
    public String getAdresse() {
        return fieldAdresse.getText();
    }
    
    public String getPort() {
        return fieldPort.getText();
    }
    
    public String getBaseDonnees() {
        return fieldBaseDonnees.getText();
    }
    
    public String getUtilisateur() {
        return fieldUtilisateur.getText();
    }
    
    public String getPassword() {
        return new String(fieldPassword.getPassword());
    }
    
    public String getType() {
        if (radioMySQL.isSelected()) {
            return "MySQL";
        }
        if (radioPostgreSQL.isSelected()) {
            return "PostgreSQL";
        }
        return "Fichier";
    }
    
    //MUTATEURS
    private void initAll() {
        initPanels();
        initFields();
        initRadios();
        initGroup();
    }
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
    private void initRadios() {
        radioFichier = new JRadioButton(TexteConstantesPreferences.FICHIER);
        radioMySQL = new JRadioButton(TexteConstantesPreferences.MYSQL);
        radioPostgreSQL = new JRadioButton(TexteConstantesPreferences.POSTGRESQL);
    }
    private void initGroup() {
        groupType = new ButtonGroup();
    }
    
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
        
        if (acceptFile) {
            gbc.gridy = 3;
            add(panelPassword,gbc);
        }
        
        gbc.gridy = 4;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        add(panelType,gbc);
    }
    
    private void setAddresse(String adresse) {
        fieldAdresse.setText(adresse);
    }
    
    private void setPort(String port) {
        fieldPort.setText(port);
    }
    
    private void setBaseDonnees(String baseDonnees) {
        fieldBaseDonnees.setText(baseDonnees);
    }
    
    private void setUtilisateur(String user) {
        fieldUtilisateur.setText(user);
    }
    
    private void setType(String type) {
        switch(type) {
            case "MySQL" :
                radioMySQL.setSelected(true);
                break;
            case "PostgreSQL" :
                radioPostgreSQL.setSelected(true);
                break;
            default :
                radioFichier.setSelected(true);
        }
    }
    
    
    public void appliquer() {
        preferences.setTypeServeur(getType());
        preferences.setAdresse(fieldAdresse.getText());
        preferences.setPort(fieldPort.getText());
        preferences.setBDDServeur(fieldBaseDonnees.getText());
        preferences.setUtilisateur(fieldUtilisateur.getText());
    }
    
    public void reset() {
        setType(preferences.getTypeServeur());
        setAddresse(preferences.getAdresse());
        setPort(preferences.getPort());
        setBaseDonnees(preferences.getBDDServeur());
        setUtilisateur(preferences.getUtilisateur());
    }
    
}
