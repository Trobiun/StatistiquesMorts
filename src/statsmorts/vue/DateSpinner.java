/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import statsmorts.constantes.TexteConstantes;

/**
 * Une classe pour gérer un JSpinner pour une date.
 * @author Robin
 */
public class DateSpinner extends JPanel {
    
    //ATTRIBUTS
    /**
     * Le modèle du JSpinner.
     */
    private SpinnerDateModel modelSpinner;
    /**
     * Le JSpinner.
     */
    private JSpinner spinner;
    /**
     * Le bouton pour mettre la date du JSpinner à l'instant ou le bouton est
     * appuyé.
     */
    private JButton insererMaintenant;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un DateSpinner.
     * @param dateFormatPattern
     */
    public DateSpinner(final String dateFormatPattern) {
        super(new GridLayout(1,0,10,10));
        init(dateFormatPattern);
        setComponents();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne la date du modèle du spinner.
     * @return la date du modèle du spinner
     */
    public Date getDate() {
        return modelSpinner.getDate();
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init(String dateFromatPattern) {
        modelSpinner = new SpinnerDateModel();
        spinner = new JSpinner(modelSpinner);
        spinner.setEditor(new JSpinner.DateEditor(spinner,dateFromatPattern));
        insererMaintenant = new JButton(TexteConstantes.INSERER_MAINTENANT);
        insererMaintenant.addActionListener(new BoutonMaintenantListener());
    }
    
    /**
     * Met les composants dans ce panel.
     */
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
    
    /**
     * Met la date du modèle à la date donnée.
     * @param date la date à mettre sur le modèle
     */
    public void setDate(Date date) {
        modelSpinner.setValue(date);
    }
    
    
    //LISTENER
    /**
     * Classe pour écouter le bouton pour insérer la date d'appui du bouton dans
     * le spinner.
     */
    class BoutonMaintenantListener implements ActionListener {
        
        /**
         * Met la date courante dans le modèle. 
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(insererMaintenant)) {
                setDate(new Date(System.currentTimeMillis()));
            }
        }
        
    }
    
}
