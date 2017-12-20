/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
    protected JPanel idPanel;
    protected JPanel nomPanel;
    protected JComboBox idComboBox;
    protected JTextField nomTextField;
    
    protected JPanel resetPanel;
    protected JButton resetButton;
    
    protected final StatsMortsControler controler;
    
    
    //CONSTRUCTEURS
    public ObjectDatabasePanels(LayoutManager layout, StatsMortsControler controler) {
        super(layout);
        this.init();
        this.setComponenents();
        this.controler = controler;
    }
    public ObjectDatabasePanels(StatsMortsControler controler) {
        this(new GridLayout(0,1),controler);
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
        idPanel = new JPanel(new BorderLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ID));
        
        nomPanel = new JPanel(new BorderLayout());
        nomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.NOM));
        
        idComboBox = new JComboBox();
        idComboBox.addItemListener(new ChangeIDListener());
        
        nomTextField = new JTextField();
        
        resetPanel = new JPanel(new BorderLayout());
        resetPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.REINITIALISATION));
        
        resetButton = new JButton(TexteConstantes.REINITIALISER);
        resetButton.addActionListener(new BoutonResetListener());
    }
    
    private void setComponenents() {
        idPanel.add(idComboBox);
        nomPanel.add(nomTextField);
        resetPanel.add(resetButton);
        this.add(idPanel);
        this.add(nomPanel);
        this.add(resetPanel);
    }
    
    public void setIDPanelVisible(boolean visible) {
        if (visible) {
            this.add(idPanel, 0);
        }
        else {
            this.remove(idPanel);
        }
    }
    
    public void setResetButtonVisible(boolean visible) {
        if (visible) {
            this.add(resetPanel);
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
