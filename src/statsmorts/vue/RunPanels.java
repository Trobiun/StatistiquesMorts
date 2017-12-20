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
    private JPanel runPanel;
    //ENTREES UTILISATEUR
    private JComboBox idJeuComboBox;
    private JTextField nomJeuField;
    
    
    //CONSTRUCTEURS
    public RunPanels(StatsMortsControler controler) {
        super(new GridBagLayout(),controler);
        this.removeAll();
        init();
        setComponents();
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
        nomJeuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.NOM));
        
        runPanel = new JPanel(new GridLayout(0,1));
        runPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.RUN));
        
        //création des champs d'entrées utilisateur
        idJeuComboBox = new JComboBox();
        idJeuComboBox.addItemListener(new JeuChangeListener());
        nomJeuField = new JTextField();
        nomJeuField.setEditable(false);
    }
    
    private void setComponents() {
        idJeuPanel.add(idJeuComboBox);
        nomJeuPanel.add(nomJeuField);
        
        jeuPanel.add(idJeuPanel);
        jeuPanel.add(nomJeuPanel);
        
        runPanel.add(idPanel);
        runPanel.add(nomPanel);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 5;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        this.add(runPanel,gbc);
        
        gbc.gridy = 2;
        this.add(jeuPanel,gbc);
    }
    
    @Override
    public void setIDPanelVisible(boolean visible) {
        if (visible) {
            runPanel.add(idPanel, 0);
        }
        else {
           runPanel.remove(idPanel);
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
    
    public void removeAllJeux() {
        idJeuComboBox.removeAllItems();
    }
    
    public void addJeu(long idJeu) {
        idJeuComboBox.addItem(idJeu);
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
