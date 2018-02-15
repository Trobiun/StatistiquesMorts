/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe abstraite pour gérer les entrées utilisateur pour gérer les objets
 * de la base de données.
 * @author Robin
 */
public abstract class ObjectDatabasePanels extends JPanel {
    
    //ATTRIBUTS
    //PANELS
    JPanel selectionPanel;
    /**
     * Le panel qui contient tous les panels qui contiennent les champs de saisie.
     */
    JPanel saisiesPanel;
    /**
     * Le panel qui contient le champ de saisie pour l'identifiant.
     */
    JPanel idPanel;
    /**
     * Le panel qui contient le champ de saisie pour le nom.
     */
    JPanel nomPanel;
    /**
     * Le panel qui contient le champ de saisie pour le nouveau nom.
     */
    JPanel nouveauNomPanel;
    /**
     * Le panel qui contient le bouton pour réinitialiser les champs de saisie.
     */
    JPanel resetPanel;
    //ENTREES UTILISATEUR
    /**
     * La JComboBox pour sélectionner l'identifiant.
     */
    JComboBox idComboBox;
    /**
     * Le JTextField pour entrer le nom. (fonction recherche)
     */
    JTextField nomTextField;
    /**
     * Le JTextField pour entrer le nouveau nom.
     */
    JTextField nouveauNomTextField;
    //RESET
    /**
     * Le bouton pour réinitialiser les champs de saisie.
     */
    JButton resetButton;
    
    /**
     * Le controleur pour demander au modèle de remplir les champs de saisie.
     */
    final StatsMortsControler controler;
    
    
    //CONSTRUCTEURS
    /**
     * Crée un ObjectDatabasePanels.
     * @param layout le LayoutManager à utiliser pour placer les composantes graphiques
     * @param controler le controleur pour demander au modèle de remplir les champs de saisie
     * @param titleBorder le titre pour le Border du panel qui contient tous les panels de saisie
     */
    public ObjectDatabasePanels(LayoutManager layout, StatsMortsControler controler, String titleBorder) {
        super(layout);
        this.init();
        this.setComponenents();
        this.controler = controler;
        saisiesPanel.setBorder(BorderFactory.createTitledBorder(titleBorder));
    }
    /**
     * Crée un ObjectDatabasePanels avec un GridBagLayout comme LayoutManager
     * par défaut.
     * @param controler le controleur pour demander au modèle de remplir les champs de saisie
     * @param titleBorder le titre pour le Border du panel qui contient tous les panels de saisie
     */
    public ObjectDatabasePanels(StatsMortsControler controler, String titleBorder) {
        this(new GridBagLayout(),controler,titleBorder);
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le nombre d'items dans la JComboBox d'identifiant
     * @return le nombre d'item sélectionnables
     */
    public int getNbItems() {
        return idComboBox.getItemCount();
    }
    
    /**
     * Retourne l'identifiant sélectionné dans la JComboBox, -1 s'il n'y a pas
     * d'identifiant sélectionnables.
     * @return l'identifiant sélectionné dans la JComboBox, -1 si erreur
     */
    public long getSelectedID() {
        long res = -1;
        if (idComboBox.getItemCount() > 0) {
            res = (long)idComboBox.getSelectedItem();
        }
        return res;
    }
    
    /**
     * Retourne le nom entré dans le champ de saisie du nom.
     * @return le nom entré
     */
    public String getNom() {
        return nomTextField.getText();
    }
    
    /**
     * Retourne le nouveau nom dans le champ de saisie du nouvea nom.
     * @return le nom entré
     */
    public String getNouveauNom() {
        return nouveauNomTextField.getText();
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init() {
        initPanels();
        initFields();
    }
    /**
     * Initialise les panels.
     */
    private void initPanels() {
        selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.SELECTION));
        
        idPanel = new JPanel(new BorderLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomPanel = new JPanel(new BorderLayout());
        nomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.NOM));
        
        nouveauNomPanel = new JPanel(new BorderLayout());
        nouveauNomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.NOUVEAU_NOM));
        
        saisiesPanel = new JPanel(new GridBagLayout());
        
        resetPanel = new JPanel(new BorderLayout());
        resetPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.REINITIALISATION));
    }
    /**
     * Initialise les champs de saisie.
     */
    private void initFields() {
        idComboBox = new JComboBox();
        idComboBox.addItemListener(new ChangeIDListener());
        
        nomTextField = new JTextField();
        
        nouveauNomTextField = new JTextField();
        
        resetButton = new JButton(TexteConstantes.REINITIALISER);
        resetButton.addActionListener(new BoutonResetListener());
    }
    
    /**
     * Met les composants les uns dans les autres puis dans ce panel.
     */
    private void setComponenents() {
        //ajout des champs de saisie dans leur panel respectif
        idPanel.add(idComboBox);
        nomPanel.add(nomTextField);
        nouveauNomPanel.add(nouveauNomTextField);
        resetPanel.add(resetButton);
        
        GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        
        gbc.gridheight = 1;
        selectionPanel.add(nomPanel,gbc);
        
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        selectionPanel.add(idPanel,gbc);
        
        //ajout des panels dans saisiesPanel
        GridBagConstraints gbc2 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        
        gbc2.gridheight = 2;
        saisiesPanel.add(nouveauNomPanel,gbc2);
        
        
        //met en place saisiesPanel dans cet objet
        GridBagConstraints gbc3 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        
        gbc3.gridheight = 2;
        this.add(selectionPanel,gbc3);
        
        gbc3.gridheight = 1;
        gbc3.gridy = 2;
        this.add(saisiesPanel,gbc3);
    }
    
    /**
     * Change la visibilité du panel qui contient les champs de sélection 
     * d'item.
     * @param visible vrai pour rendre visible, faux sinon
     */
    public void setSelectionPanelVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//            gbc.gridheight = 2;
            this.add(selectionPanel,gbc,0);
        }
        else {
            this.remove(selectionPanel);
        }
    }
    
    /**
     * Change la visibilité du panel qui contient les champs de saisie des données.
     * @param visible vrai por rendre visible, faux sinon
     */
    public void setSaisiesPanelVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
            this.add(saisiesPanel,gbc);
        }
        else {
            this.remove(saisiesPanel);
        }
    }
    
    /**
     * Change la visibilité du panel pour le nouveau nom de l'item.
     * @param visible vrai pour rendre visible, faux sinon
     */
    public void setNouveauNomPanelVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
            saisiesPanel.add(nouveauNomPanel,gbc,0);
        }
        else {
            saisiesPanel.remove(nouveauNomPanel);
        }
    }
    
    /**
     * Change la visibilité du bouton de réinitialisation.
     * @param visible vrai pour rendre visible, faux sinon
     */
    public void setResetButtonVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
            gbc.gridheight = GridBagConstraints.REMAINDER;
//            gbc.gridy = 2;
            this.add(resetPanel,gbc);
        }
        else {
            this.remove(resetPanel);
        }
    }
    
    /**
     * Vide/réinitialise les champs de saisie et la sélection.
     * @param editable permet de rendre éditable ou non les champs de saisie
     *                 (pour suppression ou non)
     * @param empty booléen pour vider ou non les champs de saisie (vrai pour
     *              les vider, faux sinon)
     */
    public void clearFields(boolean editable, boolean empty) {
//        nomTextField.setEnabled(editable);
        nomTextField.setText(TexteConstantes.EMPTY);
        nouveauNomTextField.setText(TexteConstantes.EMPTY);
        //remet l'élément sélectionné au premier élément (s'il y en a)
        if (idComboBox.getItemCount() > 0) {
            idComboBox.setSelectedIndex(0);
        }
        if (empty) {
            //vide le champ de saisie du nom et du nouveau nom
            nomTextField.setText(TexteConstantes.EMPTY);
            nouveauNomTextField.setText(TexteConstantes.EMPTY);
        }
        else {
            if (idComboBox.getItemCount() > 0) {
                fillItem((long)idComboBox.getSelectedItem());
            }
        }
    }
    
    /**
     * Supprime tous le identfiiants de la JComboBox.
     */
    public void supprimerTousItems() {
        idComboBox.removeAllItems();
    }
    
    /**
     * Ajoute un identfiant dans la JComboBox.
     * @param idItem l'identifiant à ajouter
     */
    public void ajouterItem(long idItem) {
        idComboBox.addItem(idItem);
    }
    
    /**
     * Supprime un identifiant de la JComboBox.
     * @param idItem l'identifiant à supprimer
     */
    public void supprimerItem(long idItem) {
        idComboBox.removeItem(idItem);
    }
    
    /**
     * Change le nom dans le champ de saisie pour le nom.
     * @param nom le nom à mettre dans le champ de saisie pour le nom
     */
    public void setNom(String nom) {
        nomTextField.setText(nom);
        nouveauNomTextField.setText(nom);
    }
    
    /**
     * Demande de remplir les champs de saisie par le controleur.
     * @param idItem l'identifiant de l'objet auquel il faut remplir les champs
     *              de saisie avec ses attributs
     */
    abstract public void fillItem(long idItem);
    
    
    //LISTENER
    /**
     * Classe pour écouter les changements de sélection de la JComboBox.
     */
    private class ChangeIDListener implements ItemListener {
        
        /**
         * Si un item a été sélectionné : demande à remplir les champs de saisie.
         * @param e 
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fillItem((long)idComboBox.getSelectedItem());
            }
            if (idComboBox.getItemCount() == 0) {
                clearFields(true, true);
            }
        }
        
    }
    
    /**
     * Classe pour réinitialiser les champs de saisie.
     */
    private class BoutonResetListener implements ActionListener {
        
        /**
         * Si le bouton réinitialiser a été appuyé : réinitialise les champs de
         * saisie avec les données de l'identifiant sélectionné.
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(resetButton) && idComboBox.getItemCount() > 0) {
                //remplit les champs de saisie en fonction de l'item sélectionné
                fillItem((long)idComboBox.getSelectedItem());
            }
            if (/*e.getSource().equals(resetButton) && */idComboBox.getItemCount() == 0) {
                //doit remettre à vide les champs de saisie
                nomTextField.setText(TexteConstantes.EMPTY);
                nouveauNomTextField.setText(TexteConstantes.EMPTY);
            }
        }
        
    }
    
}
