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
import statsmorts.classes.TypeGroup;
import statsmorts.constantes.TexteConstantes;

/**
 *
 * @author Robin
 */
public class AffichageOptions extends JPanel {
    
    //ATTRIBUTS STATIC
    private static final int ROWS_GENERAL = 2;
    private static final int COLS_GENERAL = 1;
    private static final int ROWS_GROUP = 1;
    private static final int COLS_GROUP = 4;
    private static final int ROWS_TEMPS = 1;
    private static final int COLS_TEMPS = 2;
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
        super(new GridLayout(ROWS_GENERAL,COLS_GENERAL));
        
        this.preferences = preferences;
        
        initAll();
        
        setComponents();
        
        reset();
    }
    
    
    //ACCESSEURS
    public TypeGroup getAffichageGroup() {
        return buttonsAffichageGroupToEnumeration();
    }
    
    public Temps getAffichageTemps() {
        return buttonsTempsToEnumeration();
    }
    
    
    //MUTATEURS
    private void initAll() {
        initPanels();
        initButtons();
    }
    private void initPanels() {
        panelAffichageGroup = new JPanel(new GridLayout(ROWS_GROUP,COLS_GROUP));
        panelAffichageGroup.setBorder(new TitledBorder(TexteConstantes.GROUPER_PAR));
        panelAffichageTemps = new JPanel(new GridLayout(ROWS_TEMPS,COLS_TEMPS));
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
    
    public void reset() {
        enumerationToButtonsAffichageGroup(preferences.getAffichageGroup());
        enumerationToButtonsTemps(preferences.getAffichageTemps());
    }
    
    
    //FONCTIONS DE CONVERSION
    private TypeGroup buttonsAffichageGroupToEnumeration() {
        TypeGroup res = TypeGroup.JEUX;
        if (buttonAffichagePlateformes.isSelected()) {
            res = TypeGroup.PLATEFORMES;
        }
        if (buttonAffichageGenres.isSelected()) {
            res = TypeGroup.GENRES;
        }
        if (buttonAffichageStudios.isSelected()) {
            res = TypeGroup.STUDIOS;
        }
        if (buttonAffichageJeux.isSelected()) {
            res = TypeGroup.JEUX;
        }
        return res;
    }
    
    private void enumerationToButtonsAffichageGroup(TypeGroup group) {
       switch (group) {
           case PLATEFORMES :
                buttonAffichagePlateformes.setSelected(true);
                break;
            case GENRES :
                buttonAffichageGenres.setSelected(true);
                break;
            case STUDIOS :
                buttonAffichageStudios.setSelected(true);
                break;
            case JEUX :
                buttonAffichageJeux.setSelected(true);
                break;
            default :
                buttonAffichageJeux.setSelected(true);
       }
    }
    
    private Temps buttonsTempsToEnumeration() {
        Temps res = Temps.HEURES;
        if (buttonTempsHeures.isSelected()) {
            res = Temps.HEURES;
        }
        if (buttonTempsMinutes.isSelected()) {
            res = Temps.MINUTES;
        }
        return res;
    }
    
    private void enumerationToButtonsTemps(Temps temps) {
        switch (temps) {
            case HEURES :
                 buttonTempsHeures.setSelected(true);
                 break;
            case MINUTES :
                buttonTempsMinutes.setSelected(true);
                break;
            default :
                buttonTempsMinutes.setSelected(true);
        }
    }
    
}
