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
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesFormatDate;

/**
 *
 * @author Robin
 */
public class DateSpinner extends JPanel {
    
    //ATTRIBUTS
    private SpinnerDateModel modelSpinner;
    private JSpinner spinner;
    private JButton insererMaintenant;
    
    
    //CONSTRUCTEUR
    public DateSpinner() {
        super(new GridLayout(1,0,10,10));
        init();
        setComponents();
    }
    
    
    //ACCESSEURS
    public Date getDate() {
        return modelSpinner.getDate();
    }
    
    
    //MUTATEURS
    private void init() {
        modelSpinner = new SpinnerDateModel();
        spinner = new JSpinner(modelSpinner);
        spinner.setEditor(new JSpinner.DateEditor(spinner,TexteConstantesFormatDate.LONG));
        insererMaintenant = new JButton(TexteConstantes.INSERER_MAINTENANT);
        insererMaintenant.addActionListener(new BoutonMaintenantListener());
    }
    
    private void setComponents() {
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        gbc.gridheight = GridBagConstraints.REMAINDER;
//        
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 8;
        add(spinner/*,gbc*/);
//        
//        gbc.gridx = 9;
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(insererMaintenant/*,gbc*/);
    }
    
    public void setDate(Date date) {
        modelSpinner.setValue(date);
    }
    
    
    //LISTENER
    class BoutonMaintenantListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            modelSpinner.setValue(new Date(System.currentTimeMillis()));
        }
        
    }
    
}
