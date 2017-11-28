/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public abstract class ObjectDatabasePanel /*extends JPanel*/ {
    
    //ATTRIBUTS
    protected JPanel idPanel;
    protected JPanel nomPanel;
    protected JComboBox idComboBox;
    protected JTextPane nomTextPane;
    
    protected final StatsMortsControler controler;
    
    
    //CONSTRUCTEURS
    public ObjectDatabasePanel(StatsMortsControler controler) {
        //super(new GridLayout(2,1));
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
    
    public JTextPane getNomTextPane() {
        return nomTextPane;
    }
    
    //MUTATEURS
    public void setComponenents() {
        idPanel.add(idComboBox);
        nomPanel.add(nomTextPane);
        //this.add(idPanel);
        //this.add(nomPanel);
    }
    
    public void clearFields() {
        nomTextPane.setText("");
        if (idComboBox.getItemCount() > 0){
            idComboBox.setSelectedIndex(0);
        }
    }
    
    private void init() {
        idPanel = new JPanel(new BorderLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder("ID"));
        nomPanel = new JPanel(new BorderLayout());
        nomPanel.setBorder(BorderFactory.createTitledBorder("Nom"));
        idComboBox = new JComboBox();
        idComboBox.addItemListener(new ChangeIDListener());
        nomTextPane = new JTextPane();
    }
    
    public void addItem(long idItem) {
        idComboBox.addItem(idItem);
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
