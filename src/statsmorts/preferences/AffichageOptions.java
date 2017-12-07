/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.preferences;

import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import statsmorts.vue.TexteConstantes;

/**
 *
 * @author Robin
 */
public class AffichageOptions extends JPanel {
    
    //ATTRIBUTS
    private JPanel panelAffichageGroup;
    private JPanel panelAffichageTemps;
    
    private ButtonGroup groupAffichageGroup;
    private JRadioButton buttonAffichagePlateformes;
    private JRadioButton buttonAffichageGenres;
    private JRadioButton buttonAffichageStudios;
    private JRadioButton buttonAffichageJeux;
    private ButtonGroup groupAffichageTemps;
    private JRadioButton buttonTempsHeures;
    private JRadioButton buttonTempsMinutes;
    
    private final Preferences preferences;
    
    
    //CONSTRUCTEUR
    public AffichageOptions(Preferences preferences) {
        super(new GridLayout(2,1));
        
        this.preferences = preferences;
        
        initAll();
        
        setComponents();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initPanels();
        initButtons();
    }
    private void initPanels() {
        panelAffichageGroup = new JPanel(new GridLayout(1,4));
        panelAffichageGroup.setBorder(new TitledBorder(TexteConstantes.GROUPER_PAR));
        panelAffichageTemps = new JPanel(new GridLayout(1,2));
        panelAffichageTemps.setBorder(new TitledBorder(TexteConstantes.AFFICHAGE_TEMPS));
    }
    private void initButtons() {
        groupAffichageGroup = new ButtonGroup();
        buttonAffichagePlateformes = new JRadioButton(TexteConstantes.PLATEFORMES);
        buttonAffichageGenres = new JRadioButton(TexteConstantes.GENRES);
        buttonAffichageStudios = new JRadioButton(TexteConstantes.STUDIOS);
        buttonAffichageJeux = new JRadioButton(TexteConstantes.JEUX);
        groupAffichageGroup.add(buttonAffichagePlateformes);
        groupAffichageGroup.add(buttonAffichageGenres);
        groupAffichageGroup.add(buttonAffichageStudios);
        groupAffichageGroup.add(buttonAffichageJeux);
        
        groupAffichageTemps = new ButtonGroup();
        buttonTempsHeures = new JRadioButton(TexteConstantes.HEURES);
        buttonTempsMinutes = new JRadioButton(TexteConstantes.MINUTES);
        groupAffichageTemps.add(buttonTempsHeures);
        groupAffichageTemps.add(buttonTempsMinutes);
    }
    
    private void setComponents() {
        panelAffichageGroup.add(buttonAffichagePlateformes);
        panelAffichageGroup.add(buttonAffichageGenres);
        panelAffichageGroup.add(buttonAffichageStudios);
        panelAffichageGroup.add(buttonAffichageJeux);
        
        panelAffichageTemps.add(buttonTempsHeures);
        panelAffichageTemps.add(buttonTempsMinutes);
        
        add(panelAffichageGroup);
        add(panelAffichageTemps);
    }
    
}
