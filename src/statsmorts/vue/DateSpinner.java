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
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesFormatDate;

/**
 *
 * @author Robin
 */
public class DateSpinner extends JPanel {
    
    //ATTRIBUTS
    private SpinnerDateModel model;
    private JSpinner spinner;
    private JButton insererMaintenant;
    
    
    //CONSTRUCTEUR
    public DateSpinner() {
        super(new GridBagLayout());
        init();
        setComponents();
    }
    
    
    //ACCESSEURS
    public Date getDate() {
        return model.getDate();
    }
    
    
    //MUTATEURS
    private void init() {
        model = new SpinnerDateModel();
        spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner,TexteConstantesFormatDate.LONG));
        insererMaintenant = new JButton(TexteConstantes.INSERER_MAINTENANT);
        insererMaintenant.addActionListener(new BoutonMaintenantListener());
    }
    
    private void setComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 8;
        add(spinner,gbc);
        
        gbc.gridx = 9;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(insererMaintenant,gbc);
    }
    
    public void setDate(Date date) {
        model.setValue(date);
    }
    
    
    //LISTENER
    class BoutonMaintenantListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setValue(new Date(System.currentTimeMillis()));
        }
        
    }
    
}
