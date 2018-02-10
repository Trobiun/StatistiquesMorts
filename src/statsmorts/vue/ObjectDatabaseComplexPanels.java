/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import statsmorts.constantes.TexteConstantes;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe abstraite pour gérer les entrées utilisateur pour gérer les objets
 * de la base de données avec une sélection plus complexe que ObjectDatabasePanels.
 * @author robin
 */
public abstract class ObjectDatabaseComplexPanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    //PANELS
    JPanel idSuperieurPanel;
    JPanel nomSuperieurPanel;
    JPanel selectionSuperieurPanel;
    JPanel selectionCurrentPanel;
    //ENTRÉES UTILISATEUR
    JComboBox idSuperieurComboBox;
    JTextField nomSuperieurField;
    
    String superieurNom;
    String currentNom;
    
    
    //CONSTRUCTEURS
    public ObjectDatabaseComplexPanels(LayoutManager layout, StatsMortsControler controler, String titleBorder, String superieuNom) {
        super(layout, controler, titleBorder);
        this.superieurNom = superieuNom;
        this.currentNom = titleBorder;
        this.init();
        this.setComponents();
    }
    public ObjectDatabaseComplexPanels(StatsMortsControler controler, String titleBorder, String superieurNom) {
        this(new GridBagLayout(), controler, titleBorder, superieurNom);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void init() {
        initPanels();
        initFields();
    }
    private void initPanels() {
        idSuperieurPanel = new JPanel(new BorderLayout());
        idSuperieurPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomSuperieurPanel = new JPanel(new BorderLayout());
        nomSuperieurPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.NOM));
        
        selectionSuperieurPanel = new JPanel(new GridBagLayout());
        selectionSuperieurPanel.setBorder(BorderFactory.createTitledBorder(superieurNom));
        
        selectionCurrentPanel = new JPanel(new GridBagLayout());
        selectionCurrentPanel.setBorder(BorderFactory.createTitledBorder(currentNom));
    }
    private void initFields() {
        idSuperieurComboBox= new JComboBox();
        idSuperieurComboBox.addItemListener(new ChangeSelectionSuperieurListener());
        nomSuperieurField = new JTextField();
    }
    
    private void setComponents() {
        this.removeAll();
        selectionPanel.removeAll();
        
        idSuperieurPanel.add(idSuperieurComboBox);
        nomSuperieurPanel.add(nomSuperieurField);
        
        GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc.gridheight = 1;
        selectionSuperieurPanel.add(nomSuperieurPanel,gbc);
        gbc.gridy = 1;
        selectionSuperieurPanel.add(idSuperieurPanel,gbc);
        
        GridBagConstraints gbc2 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc.gridheight = 2;
        selectionPanel.add(selectionSuperieurPanel,gbc2);
        
        GridBagConstraints gbc3 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc3.gridheight = 1;
        selectionCurrentPanel.add(nomPanel,gbc3);
        gbc3.gridy = 2;
        selectionCurrentPanel.add(idPanel,gbc3);
        
        GridBagConstraints gbc4 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc4.gridheight = 2;
        selectionPanel.add(selectionCurrentPanel,gbc4);
        
        GridBagConstraints gbc5 = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        gbc5.gridheight = 2;
        this.add(selectionPanel,gbc5);
        gbc5.gridy = 3;
        this.add(saisiesPanel,gbc5);
    }
    
    @Override
    public void setResetButtonVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
            gbc.gridy = GridBagConstraints.PAGE_END;
            this.add(resetPanel,gbc);
        }
        else {
            this.remove(resetPanel);
        }
    }
    
    public void setSelectionCurrentPanelVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
            gbc.gridheight = 2;
            selectionPanel.add(selectionCurrentPanel,gbc);
        }
        else {
            selectionPanel.remove(selectionCurrentPanel);
        }
    }
    
    /**
     * 
     * @param editable
     * @param empty 
     */
    @Override
    public void clearFields(boolean editable, boolean empty) {
        super.clearFields(editable, empty);
        if (idSuperieurComboBox.getItemCount() > 0) {
            idSuperieurComboBox.setSelectedIndex(0);
        }
        else {
            nomTextField.setText(TexteConstantes.EMPTY);
            nouveauNomTextField.setText(TexteConstantes.EMPTY);
        }
        if (empty) {
            nomSuperieurField.setText(TexteConstantes.EMPTY);
            nouveauNomTextField.setText(TexteConstantes.EMPTY);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    abstract public void fillItem(long idItem);
    
    abstract public void selectionnerSuperieur(long idSuperieurItem);
    
    
    //CLASSES INTERNES
    /**
     * Classe d'écouteur pour la sélection de la JComboBox 'idSuperieurComboBox'.
     */
    private class ChangeSelectionSuperieurListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource().equals(idSuperieurComboBox) && e.getStateChange() == ItemEvent.SELECTED && idSuperieurComboBox.getItemCount() > 0) {
                long id = (long)(idSuperieurComboBox.getSelectedItem());
                selectionnerSuperieur((long)idSuperieurComboBox.getSelectedItem());
            }
            if (e.getSource().equals(idSuperieurComboBox) && e.getStateChange() == ItemEvent.SELECTED && idSuperieurComboBox.getItemCount() == 0) {
                clearFields(true,true);
            }
        }
        
    }
    
}
