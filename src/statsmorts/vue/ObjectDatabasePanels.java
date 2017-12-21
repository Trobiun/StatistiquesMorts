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
 *
 * @author Robin
 */
public abstract class ObjectDatabasePanels extends JPanel {
    
    //ATTRIBUTS
    //PANELS
    protected JPanel saisiesPanel;
    protected JPanel idPanel;
    protected JPanel nomPanel;
    protected JPanel resetPanel;
    //ENTREES UTILISATEUR
    protected JComboBox idComboBox;
    protected JTextField nomTextField;
    //RESET
    protected JButton resetButton;
    
    protected final StatsMortsControler controler;
    
    
    //CONSTRUCTEURS
    public ObjectDatabasePanels(LayoutManager layout, StatsMortsControler controler, String titleBorder) {
        super(layout);
        this.init();
        this.setComponenents();
        this.controler = controler;
        saisiesPanel.setBorder(BorderFactory.createTitledBorder(titleBorder));
    }
    public ObjectDatabasePanels(StatsMortsControler controler, String titleBorder) {
        this(new GridBagLayout(),controler,titleBorder);
    }
    
    
    //ACCESSEURS
    public JPanel getIDPanel() {
        return idPanel;
    }
    
    public JComboBox getIDComboBox() {
        return idComboBox;
    }
    
    public JPanel getNomPanel() {
        return nomPanel;
    }
    
    public JTextField getNomTextField() {
        return nomTextField;
    }
    
    public int getNbItems() {
        return idComboBox.getItemCount();
    }
    
    public long getSelectedID() {
        long res = -1;
        if (idComboBox.getItemCount() > 0) {
            res = (long)idComboBox.getSelectedItem();
        }
        return res;
    }
    
    public String getNom() {
        return nomTextField.getText();
    }
    
    
    //MUTATEURS
    private void init() {
        initPanels();
        initFields();
    }
    private void initPanels() {
        idPanel = new JPanel(new BorderLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomPanel = new JPanel(new BorderLayout());
        nomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.NOM));
        
        saisiesPanel = new JPanel(new GridBagLayout());
        
        resetPanel = new JPanel(new BorderLayout());
        resetPanel.setBorder(BorderFactory.createTitledBorder(TexteConstantes.REINITIALISATION));
    }
    private void initFields() {
        idComboBox = new JComboBox();
        idComboBox.addItemListener(new ChangeIDListener());
        
        nomTextField = new JTextField();
        
        resetButton = new JButton(TexteConstantes.REINITIALISER);
        resetButton.addActionListener(new BoutonResetListener());
    }
    
    private void setComponenents() {
        //ajout des champs de saisie dans leur panel respectif
        idPanel.add(idComboBox);
        nomPanel.add(nomTextField);
        resetPanel.add(resetButton);
        
        //ajout des panels dans saisiesPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        saisiesPanel.add(idPanel,gbc);
        
        gbc.gridy = 1;
        saisiesPanel.add(nomPanel,gbc);
        
        //met en place saisiesPanel dans cet objet
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.weightx = 1.0;
        gbc2.weighty = 1.0;
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc2.gridheight = 2;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        this.add(saisiesPanel,gbc2);
    }
    
    public void setIDPanelVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            saisiesPanel.add(idPanel,gbc,0);
        }
        else {
            saisiesPanel.remove(idPanel);
        }
    }
    
    public void setResetButtonVisible(boolean visible) {
        if (visible) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridheight = GridBagConstraints.REMAINDER;
            gbc.gridy = 2;
            this.add(resetPanel,gbc);
        }
        else {
            this.remove(resetPanel);
        }
    }
    
    public void clearFields(boolean editable, boolean empty) {
        nomTextField.setEnabled(editable);
        nomTextField.setText(TexteConstantes.EMPTY);
        if (idComboBox.getItemCount() > 0) {
            idComboBox.setSelectedIndex(0);
        }
        if (empty) {
            nomTextField.setText(TexteConstantes.EMPTY);
        }
        else {
            if (idComboBox.getItemCount() > 0) {
                fillItem((Long)idComboBox.getSelectedItem());
            }
        }
    }
    
    public void removeAllItems() {
        idComboBox.removeAllItems();
    }
    
    public void addItem(long idItem) {
        idComboBox.addItem(idItem);
    }
    
    public void removeItem(long idItem) {
        idComboBox.removeItem(idItem);
    }
    
    public void setNom(String nom) {
        nomTextField.setText(nom);
    }
    
    abstract public void fillItem(long idItem);
    
    
    //LISTENER
    class ChangeIDListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fillItem((long)idComboBox.getSelectedItem());
            }
        }
        
    }
    
    class BoutonResetListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(resetButton) && idComboBox.getItemCount() > 0) {
                fillItem((long)idComboBox.getSelectedItem());
            }
        }
        
    }
    
}
