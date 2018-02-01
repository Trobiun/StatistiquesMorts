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
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe  gérer les entrées utilisateur pour gérer les runs de la base de
 * données.
 * @author Robin
 */
public class RunPanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    //PANELS
    /**
     * Le JPanel qui contient les champs de saisie pour le jeu.
     */
    private JPanel jeuPanel;
    /**
     * Le JPanel qui contient le champ de saisie pour l'identifiant du jeu.
     */
    private JPanel idJeuPanel;
    /**
     * Le JPanel qui contient le champ de saisie pour le titre du jeu.
     */
    private JPanel nomJeuPanel;
    //ENTREES UTILISATEUR
    /**
     * La JComboBox pour sélectionner un jeu.
     */
    private JComboBox idJeuComboBox;
    /**
     * Le TextField pour le titre du jeu.
     */
    private JTextField nomJeuField;
    
    
    //CONSTRUCTEURS
    /**
     * Construit un RunPanels.
     * @param controler le controleur à utiliser pour remplir les champs de
     * saisie
     */
    public RunPanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.RUN);
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
        if (idJeuComboBox.getItemCount() >  0) {
            res = (long)idJeuComboBox.getSelectedItem();
        }
        return res;
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init() {
        //création des panels
        jeuPanel = new JPanel(new GridLayout(0,1));
        jeuPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.JEU));
        
        idJeuPanel = new JPanel(new BorderLayout());
        idJeuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomJeuPanel = new JPanel(new BorderLayout());
        nomJeuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.TITRE));
        
        //création des champs de saisie utilisateur
        idJeuComboBox = new JComboBox();
        idJeuComboBox.addItemListener(new JeuChangeListener());
        nomJeuField = new JTextField();
        nomJeuField.setEditable(false);
    }
    
    /**
     * Met les composants les uns dans les autres.
     */
    private void setComponents() {
        //ajout des champs de saisie dans leur panel spécifique
        idJeuPanel.add(idJeuComboBox);
        nomJeuPanel.add(nomJeuField);
        
        jeuPanel.add(idJeuPanel);
        jeuPanel.add(nomJeuPanel);
        
        //ajout du panel de jeu à saisiePanels
        //utilisation de GridBagLayout car la classe mère l'utilise
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        gbc.gridy = 2;
        saisiesPanel.add(jeuPanel,gbc);
    }
    
    /**
     * Réinitialise et/ou vide les champs de saisie.
     * @param editable permet de rendre éditable ou non les champs de saisie
     *                 (pour suppression ou non)
     * @param empty booléen pour vider ou non les champs de saisie (vrai pour
     *              les vider, faux sinon)
     */
    @Override
    public void clearFields(boolean editable, boolean empty) {
        super.clearFields(editable, empty);
        if (idJeuComboBox.getItemCount() == 0) {
            nomJeuField.setText(TexteConstantes.EMPTY);
        }
    }
    
    /**
     * Change l'état de la visibilité du bouton réinitialiser.
     * @param visible booléen, vrai pour le rendre visible, faux sinon
     */
    @Override
    public void setResetButtonVisible(boolean visible) {
        if (visible){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor  = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridheight = 1;
            this.add(resetPanel,gbc);
        }
        else {
            this.remove(resetPanel);
        }
    }
    
    /**
     * Ajoute un jeu à la sélection possible.
     * @param idJeu l'identifiant du jeu à ajouter.
     */
    public void ajouterJeu(long idJeu) {
        idJeuComboBox.addItem(idJeu);
    }
    
    /**
     * Supprime tous les jeux à la sélection possible.
     */
    public void supprimerTousJeux() {
        idJeuComboBox.removeAllItems();
    }
    
    /**
     * Supprime le jeu à la sélection qui a pour identifiant idJeu.
     * @param idJeu l'identifiant du jeu à supprimer
     */
    public void supprimerJeu(long idJeu) {
        idJeuComboBox.removeItem(idJeu);
    }
    
    /**
     * Sélectionne le jeu correspondant à l'identifiant donné en paramètre.
     * @param idJeu l'identifiant du jeu à sélectionner
     */
    public void setIDJeu(long idJeu) {
        idJeuComboBox.setSelectedItem(idJeu);
    }
    
    /**
     * Remplit le champ de saisie du titre du jeu avec la chaîne de caractères
     * 'titre'
     * @param titre le titre à mettre dans le champ de saisie du titre
     */
    public void setTitreJeu(String titre) {
        nomJeuField.setText(titre);
    }
    
    /**
     * Demande de remplir les champs de saisie par le controleur.
     * @param idItem l'identifiant de la run à laquelle il faut remplir les champs
     *               de saisie avec ses attributs
     */
    @Override
    public void fillItem(long idItem) {
        controler.fillRunPanel(idItem);
    }
    
    /**
     * Demande de remplir les champs de saisie par le controleur
     * @param idJeu l'identifiant du jeu auquel il faut remplir les champs de
     *              saisie avec ses attributs.
     */
    public void fillItemJeu(long idJeu) {
        controler.fillRunPanelJeu(idJeu);
    }
    
    
    //LISTENER
    /**
     * Classe interne pour écouter les changements de sélection de jeu.
     */
    class JeuChangeListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                long idJeu = (long)idJeuComboBox.getSelectedItem();
                fillItemJeu(idJeu);
            }
        }
        
    }
    
}
