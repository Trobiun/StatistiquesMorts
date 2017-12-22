/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import statsmorts.constantes.TexteConstantes;

/**
 *
 * @author Robin
 */
public class MortsSpinner extends JPanel {
    
    //ATTRIBUTS
    private SpinnerNumberModel modelSpinner;
    private JSpinner spinner;
    private JButton incrementBouton;
    
    
    //CONSTRUCTEUR
    public MortsSpinner() {
        super(new GridLayout(1,0,10,10));
        init();
        setComponents();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void init() {
        modelSpinner = new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1);
        spinner = new JSpinner(modelSpinner);
        incrementBouton = new JButton(TexteConstantes.INCREMENTER);
        incrementBouton.addActionListener(new IncrementListener());
    }
    private void setComponents() {
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        gbc.gridheight = GridBagConstraints.REMAINDER;
//        
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 1;
        add(spinner/*,gbc*/);
        
//        gbc.gridx = 1;
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(incrementBouton/*,gbc*/);
    }
    
    public void setValue(int morts) {
        modelSpinner.setValue(morts);
    }
    
    //LISTENER
    class IncrementListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(incrementBouton)) {
                int current = (int)modelSpinner.getValue();
                modelSpinner.setValue(++current);
            }
        }
        
    }
    
}
