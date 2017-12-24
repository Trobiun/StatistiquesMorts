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
 * Une classe pour gérer les options d'affichage.
 * @author Robin
 */
public class AffichageOptions extends JPanel {
    
    //ATTRIBUTS STATIC
    /**
     * Le nombre de lignes du panel.
     */
    private static final int ROWS_GENERAL = 2;
    /**
     * Le nombre de colonnes du panel.
     */
    private static final int COLS_GENERAL = 1;
    /**
     * Le nombre de lignes pour le panel pour les groupes.
     */
    private static final int ROWS_GROUP = 1;
    /**
     * Le nombre de colonnes pour le panel pour les groupes.
     */
    private static final int COLS_GROUP = 4;
    /**
     * Le nombre de lignes pour le panel pour le temps.
     */
    private static final int ROWS_TEMPS = 1;
    /**
     * Le nombre de colonnes pour le panel pour le temps.
     */
    private static final int COLS_TEMPS = 2;
    //ATTRIBUTS
    /**
     * Le panel pour les groupes.
     */
    private JPanel panelAffichageGroup;
    /**
     * Le panel pour le temps.
     */
    private JPanel panelAffichageTemps;
    
    /**
     * Le ButtonGroup pour les RadioButtons de groupe.
     */
    private ButtonGroup groupAffichageGroup;
    /**
     * Le RadioButton pour le groupe d'affichage Plateformes.
     */
    private JRadioButton buttonAffichagePlateformes;
    /**
     * Le RadioButton pour le groupe d'affichage Genres.
     */
    private JRadioButton buttonAffichageGenres;
    /**
     * Le RadioButton pour le groupe d'affichage Studios.
     */
    private JRadioButton buttonAffichageStudios;
    /**
     * Le RadioButton pour le groupe d'affichage Jeux.
     */
    private JRadioButton buttonAffichageJeux;
    /**
     * Le ButtonGroup pour les RadioButtons de temps.
     */
    private ButtonGroup groupAffichageTemps;
    /**
     * Le RadioButton pour l'affichage heures.
     */
    private JRadioButton buttonTempsHeures;
    /**
     * Le RadioButton pour l'affichage minutes.
     */
    private JRadioButton buttonTempsMinutes;
    
    /**
     * Les préférences.
     */
    private final Preferences preferences;
    
    
    //CONSTRUCTEUR
    /**
     * Crée le panel AffichageOptions avec les préférences, et initialise tous
     * les composants graphiques en fonction des préférences.
     * @param preferences 
     */
    public AffichageOptions(Preferences preferences) {
        super(new GridLayout(ROWS_GENERAL,COLS_GENERAL));
        
        this.preferences = preferences;
        
        initAll();
        
        setComponents();
        
        reset();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le type de groupe d'affichage sélectionné.
     * @return le type de groupe d'affichage sélectionné
     */
    public TypeGroup getAffichageGroup() {
        return buttonsAffichageGroupToEnumeration();
    }
    
    /**
     * Retourne le type de Temps d'affichage sélectionné.
     * @return le type de Temps d'affichage sélectionné
     */
    public Temps getAffichageTemps() {
        return buttonsTempsToEnumeration();
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void initAll() {
        initPanels();
        initButtons();
    }
    /**
     * Initialise les panels.
     */
    private void initPanels() {
        panelAffichageGroup = new JPanel(new GridLayout(ROWS_GROUP,COLS_GROUP));
        panelAffichageGroup.setBorder(new TitledBorder(TexteConstantes.GROUPER_PAR));
        panelAffichageTemps = new JPanel(new GridLayout(ROWS_TEMPS,COLS_TEMPS));
        panelAffichageTemps.setBorder(new TitledBorder(TexteConstantes.AFFICHAGE_TEMPS));
    }
    /**
     * Initialise les boutons (ButtonGroup et RadioButton).
     */
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
    
    /**
     * Met les composants graphiques les uns dans les autres puis les met dans
     * ce panel.
     */
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
    
    /***
     * Remet par défaut la sélection en fonction des préférences.
     */
    public void reset() {
        enumerationToButtonsAffichageGroup(preferences.getAffichageGroup());
        enumerationToButtonsTemps(preferences.getAffichageTemps());
    }
    
    
    //FONCTIONS DE CONVERSION
    /**
     * "Convertit" la sélection des RadioButtons en TypeGroup.
     * @return le TypeGroup sélectionné
     */
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
    
    /**
     * Sélectionne le RadioButton approprié par rapport au TypeGroup en paramètre.
     * @param group le typegroup à sélectionner
     */
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
    
    /**
     * "Convertit" la sélection des RadioButton en Temps.
     * @return le Temps sélectionné
     */
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
    
    /**
     * Sélectionne le RadioButton approprié par rapport au Temps en paramètre.
     * @param temps le temps à sélectionne
     */
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
