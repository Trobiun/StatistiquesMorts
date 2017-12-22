/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public class LivePanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    //PANELS
    private JPanel runPanel;
    private JPanel idRunPanel;
    private JPanel nomRunPanel;
    private JPanel jeuPanel;
    private JPanel nomJeuPanel;
    private JPanel datesPanel;
    private JPanel mortsPanel;
    //ENTREES UTILISATEUR
    private JTextField nomRunField;
    private JTextField nomJeuField;
    private JComboBox idRunComboBox;
    private DateSpinner dateDebutSpinner;
    private DateSpinner dateFinSpinner;
    private MortsSpinner mortsSpinner;
    
    
    //CONSTRUCTEURS
    public LivePanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.LIVE);
        this.init();
        this.setComponents();
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void init() {
        initPanels();
        initFields();
    }
    private void initPanels() {
        //panels pour la run
        runPanel = new JPanel(new GridBagLayout());
        runPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.RUN));
        
        idRunPanel = new JPanel(new BorderLayout());
        idRunPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomRunPanel = new JPanel(new BorderLayout());
        nomRunPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.TITRE));
        
        //panels pour le jeu de la run
        jeuPanel = new JPanel(new BorderLayout());
        jeuPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.JEU));
        
        nomJeuPanel = new JPanel(new BorderLayout());
        nomJeuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.TITRE));
        
        //panel pour les dates du live
        datesPanel = new JPanel(new GridLayout(0,1));
        datesPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.DATES));
        
        mortsPanel = new JPanel(new BorderLayout());
        mortsPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.MORTS));
    }
    private void initFields() {
        idRunComboBox = new JComboBox();
        idRunComboBox.addItemListener(new RunChangeListener());
        
        nomRunField = new JTextField();
        nomRunField.setEditable(false);
        
        nomJeuField = new JTextField();
        nomJeuField.setEditable(false);
        
        dateDebutSpinner = new DateSpinner();
        dateDebutSpinner.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.DATE_DEBUT));
        
        dateFinSpinner = new DateSpinner();
        dateFinSpinner.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.DATE_FIN));
        
        mortsSpinner = new MortsSpinner();
    }
    
    private void setComponents() {
        //ajout des champs de saisie dans leur panel respectif
        idRunPanel.add(idRunComboBox);
        nomRunPanel.add(nomRunField);
        
        nomJeuPanel.add(nomJeuField);
        jeuPanel.add(nomJeuPanel);
        
        datesPanel.add(dateDebutSpinner);
        datesPanel.add(dateFinSpinner);
        
        mortsPanel.add(mortsSpinner);
        
        //GridBagConstraints pour le panel runPanel
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.weightx = 1.0;
        gbc1.weighty = 1.0;
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridheight = 1;
        runPanel.add(idRunPanel,gbc1);
        
        gbc1.gridy = 2;
        runPanel.add(nomRunPanel,gbc1);
        
        gbc1.gridy = 3;
        gbc1.gridheight = GridBagConstraints.REMAINDER;
        runPanel.add(jeuPanel,gbc1);
        
        //supprime le panel du nom qui est inutile pour un live
        saisiesPanel.remove(nomPanel);
        
        //GidBagConstraints pour le panel saisiesPanel
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 1.0;
        gbc2.weighty = 1.0;
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.gridheight = 4;
        saisiesPanel.add(runPanel,gbc2);
        
        gbc2.gridy = 5;
        gbc2.gridheight = 2;
        saisiesPanel.add(datesPanel,gbc2);
        
        gbc2.gridy = 7;
        gbc2.gridheight = 1;
        saisiesPanel.add(mortsPanel,gbc2);
    }
    
    @Override
    public void clearFields(boolean editable, boolean empty) {
        super.clearFields(editable, empty);
        if (idRunComboBox.getItemCount() == 0) {
            nomRunField.setText(TexteConstantes.EMPTY);
        }
    }
    
    @Override
    public void setResetButtonVisible(boolean visible) {
        if (visible){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor  = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridheight = 1;
            this.add(resetPanel,gbc);
        }
        else {
            this.remove(resetPanel);
        }
    }
    
    public void ajouterRun(long idRun) {
        idRunComboBox.addItem(idRun);
    }
    
    public void supprimerToutesRuns() {
        idRunComboBox.removeAllItems();
    }
    
    public void supprimerRun(long idRun) {
        idRunComboBox.removeItem(idRun);
    }
    
    public void setIDRun(long idRun) {
        idRunComboBox.setSelectedItem(idRun);
    }
    
    public void setTitreRun(String titreRun) {
        nomRunField.setText(titreRun);
    }
    
    public void setTitreJeu(String titreJeu) {
        nomJeuField.setText(titreJeu);
    }
    
    public void setDateDebut(Date dateDebut) {
        dateDebutSpinner.setDate(dateDebut);
    }
    
    public void setDateFin(Date dateFin) {
        dateFinSpinner.setDate(dateFin);
    }
    
    public void setMorts(int morts) {
        mortsSpinner.setValue(morts);
    }
    
    
    @Override
    public void fillItem(long idItem) {
        controler.fillLivePanel(idItem);
    }
    
    public void fillItemRun(long idRun) {
        controler.fillLivePanelRun(idRun);
    }
    
    
    //LISTENERS
    class RunChangeListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                long idRun = (long)idRunComboBox.getSelectedItem();
                fillItemRun(idRun);
            }
        }
        
    }
    
}
