/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import statsmorts.constantes.TexteConstantes;

/**
 * Une classe pour un JSpinner pour compter.
 * @author Robin
 */
public class CompteurSpinner extends JPanel {
    
    //ATTRIBUTS
    /**
     * Le modèle du JSpinner.
     */
    private SpinnerNumberModel modelSpinner;
    /**
     * Le JSpinner.
     */
    private JSpinner spinner;
    /**
     * Le bouton pour incrémenter la valeur du JSpinner de 1.
     */
    private JButton incrementBouton;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un CompteurSpinner.
     */
    public CompteurSpinner() {
        super(new GridBagLayout(/*1,0,10,10*/));
        init();
        setComponents();
    }
    
    //ACCESSEURS
    /**
     * Retourne la valeur actuelle du compteur.
     * @return la valeur du compteur
     */
    public int getValue() {
        return (int)(modelSpinner.getValue());
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init() {
        modelSpinner = new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1);
        spinner = new JSpinner(modelSpinner);
        incrementBouton = new JButton(TexteConstantes.INCREMENTER);
        incrementBouton.addActionListener(new IncrementListener());
    }
    /**
     * Met les composants graphiques les uns dans les autres.
     */
    private void setComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.90;
        add(spinner,gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.10;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(incrementBouton,gbc);
    }
    
    /**
     * Change la valeur du compteur.
     * @param count le nombre à mettre dans le modèle du JSpinner.
     */
    public void setValue(int count) {
        modelSpinner.setValue(count);
    }
    
    
    //LISTENER
    /**
     * La classe pour écouter l'appui sur le bouton d'incrémentation.
     */
    class IncrementListener implements ActionListener {
        
        /**
         * Incrémente la valeur du modèle du JSpinner.
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(incrementBouton)) {
                int current = (int)modelSpinner.getValue();
                modelSpinner.setValue(++current);
            }
        }
        
    }
    
}
