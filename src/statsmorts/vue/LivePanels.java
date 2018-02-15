/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesFormatDate;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe  gérer les entrées utilisateur pour gérer les lives de la base de
 * données.
 * @author Robin
 */
public class LivePanels extends ObjectDatabaseComplexPanels {
    
    //ATTRIBUTS
    //PANELS
    private JPanel runPanel;
    private JPanel idRunPanel;
    private JPanel nomRunPanel;
    private JPanel datesPanel;
    private JPanel compteursPanel;
    private JPanel mortsPanel;
    private JPanel bossPanel;
    //ENTREES UTILISATEUR
    /**
     * Le champ de saisie du nom de la run.
     */
    private JTextField nomRunField;
    /**
     * Une JComboBox pour sélectionner la run du live.
     */
    private JComboBox idRunComboBox;
    /**
     * Spinner pour la date de début du live.
     */
    private DateSpinner dateDebutSpinner;
    /**
     * Spinner pour la date de fin du live.
     */
    private DateSpinner dateFinSpinner;
    /**
     * Compteur de morts.
     */
    private CompteurSpinner mortsCompteur;
    /**
     * Compteur de boss.
     */
    private CompteurSpinner bossCompteur;
    
    
    //CONSTRUCTEURS
    public LivePanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.LIVE, TexteConstantes.JEU);
        this.init();
        this.setComponents();
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphqiues.
     */
    private void init() {
        initPanels();
        initFields();
    }
    /**
     * Initialise les JPanels.
     */
    private void initPanels() {
        //panels pour la run
        runPanel = new JPanel(new GridLayout(0,1));
        runPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.RUN));
        
        idRunPanel = new JPanel(new BorderLayout());
        idRunPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomRunPanel = new JPanel(new BorderLayout());
        nomRunPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.TITRE));
        
        //panel pour les dates du live
        datesPanel = new JPanel(new GridLayout(0,1));
        datesPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.DATES));
        
        
        //panels pour les compteurs
        compteursPanel = new JPanel(new GridLayout(0,1));
        compteursPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.COMPTEURS));
        
        mortsPanel = new JPanel(new BorderLayout());
        mortsPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.MORTS));
        
        bossPanel = new JPanel(new BorderLayout());
        bossPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.BOSS));
    }
    /**
     * Initialise les champs de saisie.
     */
    private void initFields() {
        idRunComboBox = new JComboBox();
        idRunComboBox.addItemListener(new RunChangeListener());
        
        nomRunField = new JTextField();
        nomRunField.setEditable(false);
        
        dateDebutSpinner = new DateSpinner(TexteConstantesFormatDate.LONG);
        dateDebutSpinner.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.DATE_DEBUT));
        
        dateFinSpinner = new DateSpinner(TexteConstantesFormatDate.LONG);
        dateFinSpinner.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.DATE_FIN));
        
        mortsCompteur = new CompteurSpinner();
        bossCompteur = new CompteurSpinner();
    }
    
    /**
     * Met les composants les uns dans les autres.
     */
    private void setComponents() {
        //ajout des champs de saisie dans leur panel respectif
        idRunPanel.add(idRunComboBox);
        nomRunPanel.add(nomRunField);
        
        datesPanel.add(dateDebutSpinner);
        datesPanel.add(dateFinSpinner);
        
        mortsPanel.add(mortsCompteur);
        bossPanel.add(bossCompteur);
        
        compteursPanel.add(mortsPanel);
        compteursPanel.add(bossPanel);
        
        runPanel.add(nomRunPanel);
        runPanel.add(idRunPanel);
        
        //met les panels de sélection (de jeu, de run et de live) dans le panel selectionPanel
        GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        //ajoute le panel 'selectionSuperieur' (sélection jeu)
        gbc.gridheight = 2;
        selectionPanel.add(selectionSuperieurPanel,gbc);
        //ajoute le panel 'runPanel' (sélection run)
        gbc.gridy = 2;
        selectionPanel.add(runPanel,gbc);
        //enlève le panel 'nomPanel' du panel 'selectionCurrentPanel' car un live n'a pas de nom
        selectionCurrentPanel.remove(nomPanel);
        //ajoute le panel 'selectionCurrentPane' (sélection live)
        gbc.gridy = 4;
        selectionPanel.add(selectionCurrentPanel,gbc);
        
        //met les panels de saisies (dates,compteurs) dans le panel 'saisiesPanel'
        GridBagConstraints gbc2 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        //ajoute le panel de dates
        gbc2.gridy = 5;
        gbc2.gridheight = 2;
        saisiesPanel.add(datesPanel, gbc2);
        //ajoute le panel de compteurs
        gbc2.gridy = 7;
        gbc2.gridheight = 1;
        saisiesPanel.add(compteursPanel, gbc2);
        
//        //supprime tous les éléments ajoutés par la classe mère (ObjectDatabaseComplexPanels)
//        this.removeAll();
//        GridBagConstraints gbc3 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//        //ajoute le panel 'selectionPanel'
//        gbc3.gridheight = 6;
//        this.add(selectionPanel, gbc3);
//        //ajoute le panel 'saisiePanel'
//        gbc3.gridheight = 4;
//        gbc3.gridy = 6;
//        this.add(saisiesPanel, gbc3);

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearFields(boolean editable, boolean empty) {
        if (idRunComboBox.getItemCount() == 0) {
            nomRunField.setText(TexteConstantes.EMPTY);
            nouveauNomTextField.setText(TexteConstantes.EMPTY);
        }
        if (idComboBox.getItemCount() == 0) {
            
        }
        if (empty) {
            Date now = new Date(System.currentTimeMillis());
            dateDebutSpinner.setDate(now);
            dateFinSpinner.setDate(now);
            mortsCompteur.setValue(0);
            bossCompteur.setValue(0);
        }
        else {
            if (idComboBox.getItemCount() > 0) {
                fillItem((long)(idComboBox.getSelectedItem()));
            }
            if (idRunComboBox.getItemCount() > 0) {
                fillItemRun((long)(idRunComboBox.getSelectedItem()));
            }
        }
    }
    
//    @Override
//    public void setSelectionCurrentPanelVisible(boolean visible) {
//        if (visible) {
//            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//            gbc.gridheight = 2;
//            gbc.gridy = 4;
//            selectionPanel.add(selectionCurrentPanel,gbc);
//        }
//        else {
//            selectionPanel.remove(selectionCurrentPanel);
//        }
//    }
    
    /**
     * Change l'état de la visibilité du bouton réinitialiser.
     * @param visible booléen, vrai pour le rendre visible, faux sinon 
     */
//    @Override
//    public void setResetButtonVisible(boolean visible) {
//        if (visible){
//            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//            gbc.gridy = GridBagConstraints.PAGE_END;
//            gbc.gridheight = 1;
//            this.add(resetPanel,gbc);
//        }
//        else {
//            this.remove(resetPanel);
//        }
//    }
    
    /**
     * Ajoute un jeu à la sélection.
     * @param idJeu l'identifiant du jeu à ajouter
     */
    public void ajouterJeu(long idJeu) {
        idSuperieurComboBox.addItem(idJeu);
    }
    
    /**
     * Ajoute une run à la liste de sélection possible.
     * @param idRun l'identifiant de la run à ajouter
     */
    public void ajouterPossibleRun(long idRun) {
        idRunComboBox.addItem(idRun);
    }
    
    /**
     * Ajoute un live possible pour la sélection.
     * @param idLive l'identifiant du live à ajouter
     */
    public void ajouterPossibleLive(long idLive) {
        idComboBox.addItem(idLive);
    }
    
    /**
     * Supprime toutes les runs dans la liste de sélection possible.
     */
    public void supprimerToutesRuns() {
        idRunComboBox.removeAllItems();
    }
    
    /**
     * Supprime une run dans la liste de sélection possible.
     * @param idRun l'identifiant de la run à supprimer
     */
    public void supprimerRun(long idRun) {
        idRunComboBox.removeItem(idRun);
    }
    
    /**
     * Supprime tous les lives pour la sélection.
     */
    public void supprimerTousLives() {
        idComboBox.removeAllItems();
    }
    
    /**
     * Sélectionne une run dans la JComboBox.
     * @param idRun l'identifiant de la run à sélectionner.
     */
    public void setIDRun(long idRun) {
        idRunComboBox.setSelectedItem(idRun);
    }
    
    /**
     * Change le titre du jeu du live dans le champ de saisie concerné
     * @param titreJeu le titre du jeu à mettre dans le champ de saisie
     */
    public void setTitreJeu(String titreJeu) {
//        nomJeuField.setText(titreJeu);
        nomSuperieurTextField.setText(titreJeu);
    }
    
    /**
     * Change le titre de la run dans le champ de saisie concerné
     * @param titreRun le titre de la run à mettre dans le champ de saisie
     */
    public void setTitreRun(String titreRun) {
        nomRunField.setText(titreRun);
    }
    
    /**
     * Change la date de début du live dans le Spinner concerné.
     * @param dateDebut la date de début à mettre dans le Spinner
     */
    public void setDateDebut(Date dateDebut) {
        dateDebutSpinner.setDate(dateDebut);
    }
    
    /**
     * Change la date de fin de la run dans le Spinner concerné.
     * @param dateFin la date de fin à mettre dans le Spinner
     */
    public void setDateFin(Date dateFin) {
        dateFinSpinner.setDate(dateFin);
    }
    
    /**
     * Met le nombre de morts dans le compteur de morts.
     * @param morts le nombre de morts à mettre dans le compteur
     */
    public void setMorts(int morts) {
        mortsCompteur.setValue(morts);
    }
    
    /**
     * Met le nombre de boss vaincus dans le compteur de boss.
     * @param boss le nombre de boss vaincus à mettre dans le compteur
     */
    public void setBoss(int boss) {
        bossCompteur.setValue(boss);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void fillItem(long idItem) {
        controler.fillLivePanel(idItem);
    }
    
    /**
     * Demande au contrôleur de remplir les champs de saisie de la run avec les
     * données du modèle.
     * @param idRun l'identifiant de la run à laquele chercher les données
     */
    public void fillItemRun(long idRun) {
        controler.fillRunOnPanelLive(idRun);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void selectionnerSuperieur(long idSuperieurItem) {
        controler.selectJeuLivePanels(idSuperieurItem);
    }
    
    /**
     * Sélectionne une run.
     * @param idRun l'identifiant de la run à sélectionner.
     */
    public void selectionnerRun(long idRun) {
        controler.selectRunLivePanels(idRun);
    }
    
    
    //LISTENERS
    /**
     * Classe interne pour écouter les changements de sélection de run.
     */
    private class RunChangeListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                long idRun = (long)idRunComboBox.getSelectedItem();
                selectionnerRun(idRun);
            }
        }
        
    }
    
}
