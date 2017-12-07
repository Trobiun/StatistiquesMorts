/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public abstract class ObjectDatabasePanels {
    
    //ATTRIBUTS
    protected JPanel idPanel;
    protected JPanel nomPanel;
    protected JComboBox idComboBox;
    protected JTextField nomTextPane;
    
    protected final StatsMortsControler controler;
    
    
    //CONSTRUCTEURS
    public ObjectDatabasePanels(StatsMortsControler controler) {
        this.init();
        this.setComponenents();
        this.controler = controler;
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
        return nomTextPane;
    }
    
    //MUTATEURS
    public void setComponenents() {
        idPanel.add(idComboBox);
        nomPanel.add(nomTextPane);
    }
    
    public void clearFields(boolean empty) {
        nomTextPane.setEditable(true);
        nomTextPane.setText("");
        if (idComboBox.getItemCount() > 0) {
            idComboBox.setSelectedIndex(0);
        }
        if (empty) {
            nomTextPane.setText("");
        }
        else {
            if (idComboBox.getItemCount() > 0) {
                fillItem((Long)idComboBox.getSelectedItem());
            }
        }
    }
    
    private void init() {
        idPanel = new JPanel(new BorderLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"ID"));
        nomPanel = new JPanel(new BorderLayout());
        nomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Nom"));
        idComboBox = new JComboBox();
        idComboBox.addItemListener(new ChangeIDListener());
        nomTextPane = new JTextField();
    }
    
    public void removeAllItems() {
        idComboBox.removeAllItems();
    }
    
    public void addItem(long idItem) {
        idComboBox.addItem(idItem);
    }
    
    public void remvoeItem(long idItem) {
        idComboBox.removeItem(idItem);
    }
    
    public void setNom(String nom) {
        nomTextPane.setText(nom);
    }
    
    abstract public void fillItem(long idItem);
    
    class ChangeIDListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fillItem((long)idComboBox.getSelectedItem());
            }
        }
        
    }
    
}
