/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.GridBagConstraints;
import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe  gérer les entrées utilisateur pour gérer les runs de la base de
 * données.
 * @author Robin
 */
public class RunPanels extends ObjectDatabaseComplexPanels {
    
    //ATTRIBUTS
    //PANELS
    /**
     * Le JPanel qui contient les champs de saisie pour le jeu.
     */
//    private JPanel jeuPanel;
    /**
     * Le JPanel qui contient le champ de saisie pour l'identifiant du jeu.
     */
//    private JPanel idJeuPanel;
    /**
     * Le JPanel qui contient le champ de saisie pour le titre du jeu.
     */
//    private JPanel nomJeuPanel;
    //ENTRÉES UTILISATEUR
    /**
     * La JComboBox pour sélectionner un jeu.
     */
//    private JComboBox idJeuComboBox;
    /**
     * Le TextField pour le titre du jeu.
     */
//    private JTextField nomJeuField;
    
    
    //CONSTRUCTEURS
    /**
     * Construit un RunPanels.
     * @param controler le controleur à utiliser pour remplir les champs de
     * saisie
     */
    public RunPanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.RUN, TexteConstantes.JEU);
        this.init();
        this.setComponents();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'identifiant du jeu sélectionné.
     * @return l'identifiant (long) du jeu sélectionné
     */
    public long getSelectedJeuID() {
        long res = -1;
        if (idSuperieurComboBox.getItemCount() > 0) {
            res = (long)(idSuperieurComboBox.getSelectedItem());
        }
        return res;
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init() {
        initPanels();
        initFields();
    }
    private void initPanels() {
//        jeuPanel = new JPanel(new GridLayout(0,1));
//        jeuPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.JEU));
//        
//        idJeuPanel = new JPanel(new BorderLayout());
//        idJeuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
//        
//        nomJeuPanel = new JPanel(new BorderLayout());
//        nomJeuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.TITRE));
    }
    private void initFields() {
//        idJeuComboBox = new JComboBox();
//        idJeuComboBox.addItemListener(new JeuChangeListener());
//        nomJeuField = new JTextField();
//        nomJeuField.setEditable(false);
    }
    
    /**
     * Met les composants les uns dans les autres.
     */
    private void setComponents() {/*
        idJeuPanel.add(idJeuComboBox);
        nomJeuPanel.add(nomJeuField);
        
        jeuPanel.add(nomJeuPanel);
        jeuPanel.add(idJeuPanel);
        
        selectionPanel.removeAll();
        
        GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc.gridheight = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
//        selectionPanel.removeAll();
        selectionPanel.add(jeuPanel,gbc);
        
        
        GridBagConstraints gbc2 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        saisiesPanel.add(nouveauNomPanel,gbc);
        
//        this.removeAll();
        GridBagConstraints gbc3 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc3.gridheight = 2;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        this.add(selectionPanel,gbc3);
        
        gbc3.gridheight = 1;
        gbc3.gridy = 2;
        this.add(saisiesPanel,gbc3);
        */
//        ajout des champs de saisie dans leur panel spécifique
//        
        
        //ajout du panel de jeu à saisiePanels
        //utilisation de GridBagLayout car la classe mère l'utilise
//        
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
        
//        gbc.gridy = 2;
//        saisiesPanel.add(jeuPanel,gbc);
//        selectionPanel.add(jeuPanel,gbc);
        
    }
    
    /**
     * Change la visibilité du panel qui contient les champs de sélection 
     * d'item.
     * @param visible vrai pour rendre visible, faux sinon
     */
//    @Override
//    public void setSelectionPanelVisible(boolean visible) {
//        if (visible) {
//            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//            gbc.gridheight = 2;
//            this.add(selectionPanel,gbc,0);
//        }
//        else  {
//            this.remove(selectionPanel);
//        }
//    }
    
    /**
     * Réinitialise et/ou vide les champs de saisie.
     * @param editable permet de rendre éditable ou non les champs de saisie
     *                 (pour suppression ou non)
     * @param empty booléen pour vider ou non les champs de saisie (vrai pour
     *              les vider, faux sinon)
     */
    @Override
    public void clearFields(boolean editable, boolean empty) {
//        super.clearFields(editable, empty);
//        keepEmpty = empty;
        if (idComboBox.getItemCount() > 0) {
            idComboBox.setSelectedIndex(0);
        }
        else {
            nomTextField.setText(TexteConstantes.EMPTY);
            nouveauNomTextField.setText(TexteConstantes.EMPTY);
        }
        if (empty) {
            nomTextField.setText(TexteConstantes.EMPTY);
            nouveauNomTextField.setText(TexteConstantes.EMPTY);
        }
    }
    
    /**
     * Change l'état de la visibilité du bouton réinitialiser.
     * @param visible booléen, vrai pour le rendre visible, faux sinon
     */
//    @Override
//    public void setResetButtonVisible(boolean visible) {
//        if (visible){
//            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//            gbc.gridheight = 1;
//            gbc.gridx = 0;
//            gbc.gridy = 4;
//            this.add(resetPanel,gbc,this.getComponentCount() - 1);
//        }
//        else {
//            this.remove(resetPanel);
//        }
//    }
    
    /**
     * Ajoute un jeu à la sélection possible.
     * @param idJeu l'identifiant du jeu à ajouter.
     */
    public void ajouterJeu(long idJeu) {
        idSuperieurComboBox.addItem(idJeu);
    }
    
    
    public void ajouterPossibleRun(long idRun) {
        idComboBox.addItem(idRun);
    }
    
    public void supprimerToutesRuns() {
        idComboBox.removeAllItems();
    }
    
    /**
     * Supprime tous les jeux à la sélection possible.
     */
    public void supprimerTousJeux() {
        idSuperieurComboBox.removeAllItems();
    }
    
    /**
     * Supprime le jeu à la sélection qui a pour identifiant idJeu.
     * @param idJeu l'identifiant du jeu à supprimer
     */
    public void supprimerJeu(long idJeu) {
        idSuperieurComboBox.removeItem(idJeu);
    }
    
    /**
     * Sélectionne le jeu correspondant à l'identifiant donné en paramètre.
     * @param idJeu l'identifiant du jeu à sélectionner
     */
    public void setIDJeu(long idJeu) {
        idSuperieurComboBox.setSelectedItem(idJeu);
    }
    
    /**
     * Remplit le champ de saisie du titre du jeu avec la chaîne de caractères
     * 'titre'
     * @param titre le titre à mettre dans le champ de saisie du titre
     */
    public void setTitreJeu(String titre) {
        nomSuperieurField.setText(titre);
    }
    
    /**
     * Demande de remplir les champs de saisie par le controleur.
     * @param idItem l'identifiant de la run à laquelle il faut remplir les champs
     *               de saisie avec ses attributs
     */
    @Override
    public void fillItem(long idItem) {
//        if (!keepEmpty) {
            controler.fillRunPanel(idItem);
//        }
    }
    
    /**
     * Demande de remplir les champs de saisie par le controleur
     * @param idJeu l'identifiant du jeu auquel il faut remplir les champs de
     *              saisie avec ses attributs.
     */
    public void fillItemJeu(long idJeu) {
//        controler.fill
    }
    
    /**
     * 
     * @param idSuperieurItem 
     */
    @Override
    public void selectionnerSuperieur(long idSuperieurItem) {
        controler.selectJeuRunPanels(idSuperieurItem);
    }
    
    
    //LISTENER
    /**
     * Classe interne pour écouter les changements de sélection de jeu.
     */
//    private class JeuChangeListener implements ItemListener {
//        
//        @Override
//        public void itemStateChanged(ItemEvent e) {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                long idJeu = (long)idSuperieurComboBox.getSelectedItem();
//                selectionnerSuperieur(idJeu);
//            }
//        }
//        
//    }
    
}
