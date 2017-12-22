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
 *
 * @author Robin
 */
public class RunPanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    //PANELS
    private JPanel jeuPanel;
    private JPanel idJeuPanel;
    private JPanel nomJeuPanel;
    //ENTREES UTILISATEUR
    private JComboBox idJeuComboBox;
    private JTextField nomJeuField;
    
    
    //CONSTRUCTEURS
    public RunPanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.RUN);
        this.init();
        this.setComponents();
    }
    
    
    //ACCESSEURS
    public long getSelectedJeuID() {
        long res = -1;
        if (idJeuComboBox.getItemCount() >  0) {
            res = (long)idJeuComboBox.getSelectedItem();
        }
        return res;
    }
    
    
    //MUTATEURS
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
    
    @Override
    public void clearFields(boolean editable, boolean empty) {
        super.clearFields(editable, empty);
        if (idJeuComboBox.getItemCount() == 0) {
            nomJeuField.setText(TexteConstantes.EMPTY);
        }
    }
    
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
    
    public void ajouterJeu(long idJeu) {
        idJeuComboBox.addItem(idJeu);
    }
    
    public void supprimerTousJeux() {
        idJeuComboBox.removeAllItems();
    }
    
    public void supprimerJeu(long idJeu) {
        idJeuComboBox.removeItem(idJeu);
    }
    
    public void setIDJeu(long idJeu) {
        idJeuComboBox.setSelectedItem(idJeu);
    }
    
    public void setTitreJeu(String titre) {
        nomJeuField.setText(titre);
    }
    
    @Override
    public void fillItem(long idItem) {
        controler.fillRunPanel(idItem);
    }
    
    public void fillItemJeu(long idJeu) {
        controler.fillRunPanelJeu(idJeu);
    }
    
    
    //LISTENER
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
