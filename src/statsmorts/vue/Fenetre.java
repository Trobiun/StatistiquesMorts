/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.classes.TypeBasicInputs;
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesBDD;
import statsmorts.constantes.TexteConstantesChart;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.Editeur;
import statsmorts.classes.FillDataset;
import statsmorts.classes.Genre;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Run;
import statsmorts.classes.Studio;
import statsmorts.classes.TypeGroup;
import statsmorts.controler.StatsMortsControler;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;
import statsmorts.preferences.PreferencesDialog;
import statsmorts.preferences.Temps;

/**
 * Une classe pour toute l'interface graphique principale.
 * @author Robin
 */
public class Fenetre extends JFrame implements Observer {
    
    
    //ATTRIBUTS
    //SPLIT PANES
    /**
     * Le JSplitPane le plus global, qui contient l'autre JSplitPane.
     */
    private JSplitPane splitVerticalGlobal;
    /**
     * Le JSplitPane horizontal qui contient le panel qui contient l'arbre, et
     * le panel qui contient les informations.
     */
    private JSplitPane splitHorizontalInGlobal;
    
    //JPANELS
    /**
     * Le panel qui contient l'arbre.
     */
    private JPanel panelArbre;
    /**
     * Le panel qui contient les informations.
     */
    private JPanel panelInfos;
    /**
     * Le panel qui contient le graphique (chart).
     */
    private ChartPanel panelChart;
    
    //ARBRES
    /**
     * L'arbre qui représente la base de données.
     */
    private JTree treeJeux;
    /**
     * La racine de l'arbre.
     */
    private SortableTreeNode rootTree;
    /**
     * La collection des nœuds des plateformes.
     */
    private HashMap<Long, SortableTreeNode> mapPlateformes;
    /**
     * La collection des nœuds des genres.
     */
    private HashMap<Long, SortableTreeNode> mapGenres;
    /**
     * La collection des nœuds des studios.
     */
    private HashMap<Long, SortableTreeNode> mapStudios;
    /**
     * La collection des nœuds des éditeurs.
     */
    private HashMap<Long, SortableTreeNode> mapEditeurs;
    /**
     * La collection des nœuds des jeux. Utilisation d'ArrayList 
     */
    private HashMap<Long, ArrayList<SortableTreeNode>> mapJeux;
    /**
     * 
     */
    private HashMap<Long, ArrayList<SortableTreeNode>> mapRuns;
    
    //TEXTPANE
    /**
     * Le JTextPane pour afficher les informations sur les plateformes, genres,
     * studios, jeux, runs et lives.
     */
    private JTextPane textPaneInfos;
    
    //MENUS
    /**
     * Le JMenuBar.
     */
    private JMenuBar menuBar;
    //MENU FICHIER
    /**
     * Le menu "Fichier", qui regroupe la création, l'ouverture et le gestion de
     * base de données et pour quitter.
     */
    private JMenu fichierMenu;
    /**
     * Le menuItem pour la création d'une base de données.
     */
    private JMenuItem nouveauMenuItem;
    /**
     * Le menuItem pour l'ouverture d'une base de données.
     */
    private JMenuItem ouvrirMenuItem;
    //SOUS MENU GESTION BASE DE DONNEES
    /**
     * Le sous menu pour gérer la base de données.
     */
    private JMenu gestionBaseDonnees;
    //SOUS MENU AJOUTER
    private JMenu ajouterSousMenu;
    private JMenuItem ajouterPlateformeMenuItem;
    private JMenuItem ajouterGenreMenuItem;
    private JMenuItem ajouterStudioMenuItem;
    private JMenuItem ajouterEditeurMenuItem;
    private JMenuItem ajouterJeuMenuItem;
    private JMenuItem ajouterRunMenuItem;
    private JMenuItem ajouterLiveMenuItem;
    //SOUS MENU MODIFIER
    private JMenu modifierSousMenu;
    private JMenuItem modifierPlateformeMenuItem;
    private JMenuItem modifierGenreMenuItem;
    private JMenuItem modifierStudioMenuItem;
    private JMenuItem modifierEditeurMenuItem;
    private JMenuItem modifierJeuMenuItem;
    private JMenuItem modifierRunMenuItem;
    private JMenuItem modifierLiveMenuItem;
    //SOUS MENU SUPPRIMER
    private JMenu supprimerSousMenu;
    private JMenuItem supprimerPlateformeMenuItem;
    private JMenuItem supprimerGenreMenuItem;
    private JMenuItem supprimerStudioMenuItem;
    private JMenuItem supprimerEditeurMenuItem;
    private JMenuItem supprimerJeuMenuItem;
    private JMenuItem supprimerRunMenuItem;
    private JMenuItem supprimerLiveMenuItem;
    
//    private JMenuItem actualiser;
    private JMenuItem quitterMenuItem;
    private JMenu outilsMenu;
    private JMenuItem preferencesMenuItem;
    //MENU AFFICHAGE
    private JMenu affichageMenu;
    private JMenu affichageRacineSousMenu;
    private JRadioButtonMenuItem plateformesAffichageRacineMenuItem;
    private JRadioButtonMenuItem genresAffichageRacineMenuItem;
    private JRadioButtonMenuItem studiosAffichageRacineMenuItem;
    private JRadioButtonMenuItem editeursAffichageRacineMenuItem;
    private JRadioButtonMenuItem jeuxAffichageRacineMenuItem;
    private ButtonGroup racineAffichageGroup;
    private JMenu affichageTempsSousMenu;
    private JRadioButtonMenuItem heuresAffichageTempsMenuItem;
    private JRadioButtonMenuItem minutesAffichageTempsMenuItem;
    private ButtonGroup tempsAffichageGroup;
    
    //POPUPMENU
    private JPopupMenu popupMenu;
    //SOUS MENU AJOUTER POPUP
    private JMenu ajouterSousPopupMenu;
    private JMenuItem ajouterPlateformePopupMenuItem;
    private JMenuItem ajouterGenrePopupMenuItem;
    private JMenuItem ajouterStudioPopupMenuItem;
    private JMenuItem ajouterEditeurPopupMenuItem;
    private JMenuItem ajouterJeuPopupMenuItem;
    private JMenuItem ajouterRunPopupMenuItem;
    private JMenuItem ajouterLivePopupMenuItem;
    //SOUS MENU MODIFIER POPUP
    private JMenu modifierSousPopupMenu;
    private JMenuItem modifierPlateformePopupMenuItem;
    private JMenuItem modifierGenrePopupMenuItem;
    private JMenuItem modifierStudioPopupMenuItem;
    private JMenuItem modifierEditeurPopupMenuItem;
    private JMenuItem modifierJeuPopupMenuItem;
    private JMenuItem modifierRunPopupMenuItem;
    private JMenuItem modifierLivePopupMenuItem;
    //SOUS MENU SUPPRIMER POPUP
    private JMenu supprimerSousPopupMenu;
    private JMenuItem supprimerPlateformePopupMenuItem;
    private JMenuItem supprimerGenrePopupMenuItem;
    private JMenuItem supprimerStudioPopupMenuItem;
    private JMenuItem supprimerEditeurPopupMenuItem;
    private JMenuItem supprimerJeuPopupMenuItem;
    private JMenuItem supprimerRunPopupMenuItem;
    private JMenuItem supprimerLivePopupMenuItem;
    
    private Temps temps;
    private TypeGroup typeGroup;
    
    //CLASSES PERSO
    //ENSEMBLE DE PANELS POUR LES ENTRÉES UTILISATEUR
    private PlateformePanels plateformePanels;
    private GenrePanels genrePanels;
    private StudioPanels studioPanels;
    private EditeurPanels editeurPanels;
    private JeuPanels jeuPanels;
    private RunPanels runPanels;
    private LivePanels livePanels;
    
    private final Preferences preferences;
    private final PreferencesDialog prefsDialog;
    private final StatsMortsControler controler;
    
    
    //CONSTRUCTEUR
    public Fenetre(String titre, StatsMortsControler controler, Preferences preferences) {
        super(titre);
        java.net.URL iconURL = Fenetre.class.getResource("res/icon.jpg");
        if (null != iconURL) {
            super.setIconImage(new ImageIcon(iconURL).getImage());
        }
        super.setLayout(new BorderLayout());
        super.setSize(850, 700);
        super.setLocationRelativeTo(null);
        
        WindowListener exitListener = new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                controler.deconnecter();
                System.exit(0);
            }
            
        };
        super.addWindowListener(exitListener);
        
        this.temps = preferences.getAffichageTemps();
        this.typeGroup = preferences.getAffichageGroup();
        this.controler = controler;
        this.preferences = preferences;
        this.prefsDialog = new PreferencesDialog(this, TexteConstantes.PREFERENCES, false, this.controler, this.preferences);
        
        initAll();
        setComponents();
        setMenu();
        setPopupMenu();
        
        super.setJMenuBar(menuBar);
        super.add(splitVerticalGlobal);
        
        super.setVisible(true);
        splitHorizontalInGlobal.setDividerLocation(0.5);
        splitVerticalGlobal.setDividerLocation(0.5);
    }
    
    private void setComponents() {
        panelArbre.add(treeJeux);
        treeJeux.setComponentPopupMenu(popupMenu);
        panelInfos.add(textPaneInfos);

        JScrollPane scrollPanelArbre = new JScrollPane(panelArbre);
        scrollPanelArbre.getVerticalScrollBar().setUnitIncrement(20);

        JScrollPane scrollTextPane = new JScrollPane(textPaneInfos);
        scrollTextPane.getVerticalScrollBar().setUnitIncrement(20);

        splitHorizontalInGlobal.setLeftComponent(scrollPanelArbre);
        splitHorizontalInGlobal.setRightComponent(scrollTextPane);
        splitHorizontalInGlobal.setDividerLocation(0.5);
        
        splitVerticalGlobal.setTopComponent(splitHorizontalInGlobal);
        splitVerticalGlobal.setBottomComponent(panelChart);
        splitVerticalGlobal.setDividerLocation(0.5);
    }
    private void setMenu() {
        //AJOUT DES MENU ITEMD DU MENU FICHIER
        fichierMenu.add(nouveauMenuItem);
        fichierMenu.add(ouvrirMenuItem);
        fichierMenu.addSeparator();
        fichierMenu.add(gestionBaseDonnees);
        fichierMenu.addSeparator();
        fichierMenu.add(quitterMenuItem);
        
        //SOUS MENU GESTION
        gestionBaseDonnees.add(ajouterSousMenu);
        //SOUS SOUS MENU AJOUTER
        ajouterSousMenu.add(ajouterPlateformeMenuItem);
        ajouterSousMenu.add(ajouterGenreMenuItem);
        ajouterSousMenu.add(ajouterStudioMenuItem);
        ajouterSousMenu.add(ajouterEditeurMenuItem);
        ajouterSousMenu.add(ajouterJeuMenuItem);
        ajouterSousMenu.add(ajouterRunMenuItem);
        ajouterSousMenu.add(ajouterLiveMenuItem);
        //SOUS SOUS MENU MODIFIER
        gestionBaseDonnees.add(modifierSousMenu);
        modifierSousMenu.add(modifierPlateformeMenuItem);
        modifierSousMenu.add(modifierGenreMenuItem);
        modifierSousMenu.add(modifierStudioMenuItem);
        modifierSousMenu.add(modifierEditeurMenuItem);
        modifierSousMenu.add(modifierJeuMenuItem);
        modifierSousMenu.add(modifierRunMenuItem);
        modifierSousMenu.add(modifierLiveMenuItem);
        //SOUS SOUS MENU SUPPRIMER
        gestionBaseDonnees.add(supprimerSousMenu);
        supprimerSousMenu.add(supprimerPlateformeMenuItem);
        supprimerSousMenu.add(supprimerGenreMenuItem);
        supprimerSousMenu.add(supprimerStudioMenuItem);
        supprimerSousMenu.add(supprimerEditeurMenuItem);
        supprimerSousMenu.add(supprimerJeuMenuItem);
        supprimerSousMenu.add(supprimerRunMenuItem);
        supprimerSousMenu.add(supprimerLiveMenuItem);
        
        //AJOUT DU MENU ITEM DU MENU OUTILS
        outilsMenu.add(preferencesMenuItem);
        
        //AJOUT DES MENU ITEMS DU MENU AFFICHAGE
        //AJOUT DES MENU ITEMS DE L'AFFICHAGE RACINE
        affichageRacineSousMenu.add(plateformesAffichageRacineMenuItem);
        affichageRacineSousMenu.add(genresAffichageRacineMenuItem);
        affichageRacineSousMenu.add(studiosAffichageRacineMenuItem);
        affichageRacineSousMenu.add(editeursAffichageRacineMenuItem);
        affichageRacineSousMenu.add(jeuxAffichageRacineMenuItem);
        
        //AJOUT DES MENU ITEMS DE L'AFFICHAGE TEMPS
        affichageTempsSousMenu.add(heuresAffichageTempsMenuItem);
        affichageTempsSousMenu.add(minutesAffichageTempsMenuItem);
        
        //AJOUT DES SOUS MENU D'AFFICHAGE
        affichageMenu.add(affichageRacineSousMenu);
//        affichageMenu.add(affichageTreeNodesSousMenu);
        affichageMenu.add(affichageTempsSousMenu);
        
        //AJOUT DES MENUS
        menuBar.add(fichierMenu);
        menuBar.add(outilsMenu);
        menuBar.add(affichageMenu);
    }
    private void setPopupMenu() {
        treeJeux.setComponentPopupMenu(popupMenu);
        treeJeux.addMouseListener(null);
        
        //SOUS MENU AJOUTER
        ajouterSousPopupMenu.add(ajouterPlateformePopupMenuItem);
        ajouterSousPopupMenu.add(ajouterGenrePopupMenuItem);
        ajouterSousPopupMenu.add(ajouterStudioPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterEditeurPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterJeuPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterRunPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterLivePopupMenuItem);
        //SOUS MENU MODIFIER
        modifierSousPopupMenu.add(modifierPlateformePopupMenuItem);
        modifierSousPopupMenu.add(modifierGenrePopupMenuItem);
        modifierSousPopupMenu.add(modifierStudioPopupMenuItem);
        modifierSousPopupMenu.add(modifierEditeurPopupMenuItem);
        modifierSousPopupMenu.add(modifierJeuPopupMenuItem);
        modifierSousPopupMenu.add(modifierRunPopupMenuItem);
        modifierSousPopupMenu.add(modifierLivePopupMenuItem);
        //SOUS MENU SUPPRIMER
        supprimerSousPopupMenu.add(supprimerPlateformePopupMenuItem);
        supprimerSousPopupMenu.add(supprimerGenrePopupMenuItem);
        supprimerSousPopupMenu.add(supprimerStudioPopupMenuItem);
        supprimerSousPopupMenu.add(supprimerEditeurPopupMenuItem);
        supprimerSousPopupMenu.add(supprimerJeuPopupMenuItem);
        supprimerSousPopupMenu.add(supprimerRunPopupMenuItem);
        supprimerSousPopupMenu.add(supprimerLivePopupMenuItem);
        //AJOUT DES SOUS MENU
        popupMenu.add(ajouterSousPopupMenu);
        popupMenu.add(modifierSousPopupMenu);
        popupMenu.add(supprimerSousPopupMenu);
    }
    
    private void initAll() {
        initSplitPanes();
        initPanels();
        initPersoPanels();
        initTextPane();
        initTree();
        initMenuBar();
        initPopupMenu();
    }
    private void initSplitPanes() {
        splitVerticalGlobal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        splitVerticalGlobal.setOneTouchExpandable(true);
        splitVerticalGlobal.setDividerSize(7);
        splitHorizontalInGlobal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitHorizontalInGlobal.setOneTouchExpandable(true);
        splitHorizontalInGlobal.setDividerSize(7);
    }
    private void initPanels() {
        panelArbre = new JPanel(new BorderLayout());
        panelInfos = new JPanel(new BorderLayout());
        panelChart = new ChartPanel(null);
    }
    private void initPersoPanels() {
        plateformePanels = new PlateformePanels(controler);
        genrePanels = new GenrePanels(controler);
        studioPanels = new StudioPanels(controler);
        editeurPanels = new EditeurPanels(controler);
        jeuPanels = new JeuPanels(controler);
        jeuPanels.addPlateformesActionListener(new PlateformesActionListener());
        jeuPanels.addGenresActionListener(new GenresActionListener());
        jeuPanels.addStudiosActionListener(new StudiosActionListener());
        runPanels = new RunPanels(controler);
        livePanels = new LivePanels(controler);
    }
    private void initTree() {
        rootTree = new SortableTreeNode(TexteConstantes.JEUX);
        treeJeux = new JTree(rootTree);
        treeJeux.addTreeSelectionListener(new TreeListener());
        treeJeux.addMouseListener(new TreeMouseListener());
        mapPlateformes = new HashMap();
        mapGenres = new HashMap();
        mapStudios = new HashMap();
        mapEditeurs = new HashMap();
        mapJeux = new HashMap();
        mapRuns = new HashMap();
    }
    private void initTextPane() {
        textPaneInfos = new JTextPane();
        textPaneInfos.setEditable(false);
    }
    private void initMenuBar() {
        menuBar = new JMenuBar();
        MenuListener listener = new MenuListener();
        
        //MENU FICHIER
        fichierMenu = new JMenu(TexteConstantes.FICHIER);
        
        nouveauMenuItem = new JMenuItem(TexteConstantes.NOUVEAU);
        nouveauMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        nouveauMenuItem.addActionListener(listener);
        
        ouvrirMenuItem = new JMenuItem(TexteConstantes.OUVRIR);
        ouvrirMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        ouvrirMenuItem.addActionListener(listener);
        
        quitterMenuItem = new JMenuItem(TexteConstantes.QUITTER);
        quitterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        quitterMenuItem.addActionListener(listener);
        
        //SOUS MENU GESTION BASE DE DONNEES
        gestionBaseDonnees = new JMenu(TexteConstantes.GESTION);
        MenuGestionListener gestionListener = new MenuGestionListener();
        //SOUS MENU AJOUTER
        ajouterSousMenu = new JMenu(TexteConstantes.AJOUTER);
        ajouterPlateformeMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        ajouterPlateformeMenuItem.addActionListener(gestionListener);
        ajouterGenreMenuItem = new JMenuItem(TexteConstantes.GENRE);
        ajouterGenreMenuItem.addActionListener(gestionListener);
        ajouterStudioMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        ajouterStudioMenuItem.addActionListener(gestionListener);
        ajouterEditeurMenuItem = new JMenuItem(TexteConstantes.EDITEURS);
        ajouterEditeurMenuItem.addActionListener(gestionListener);
        ajouterJeuMenuItem = new JMenuItem(TexteConstantes.JEU);
        ajouterJeuMenuItem.addActionListener(gestionListener);
        ajouterRunMenuItem = new JMenuItem(TexteConstantes.RUN);
        ajouterRunMenuItem.addActionListener(gestionListener);
        ajouterLiveMenuItem = new JMenuItem(TexteConstantes.LIVE);
        ajouterLiveMenuItem.addActionListener(gestionListener);
        //SOUS MENU MODIFIER
        modifierSousMenu = new JMenu(TexteConstantes.MODIFIER);
        modifierPlateformeMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        modifierPlateformeMenuItem.addActionListener(gestionListener);
        modifierGenreMenuItem = new JMenuItem(TexteConstantes.GENRE);
        modifierGenreMenuItem.addActionListener(gestionListener);
        modifierStudioMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        modifierStudioMenuItem.addActionListener(gestionListener);
        modifierEditeurMenuItem = new JMenuItem(TexteConstantes.EDITEURS);
        modifierEditeurMenuItem.addActionListener(gestionListener);
        modifierJeuMenuItem = new JMenuItem(TexteConstantes.JEU);
        modifierJeuMenuItem.addActionListener(gestionListener);
        modifierRunMenuItem = new JMenuItem(TexteConstantes.RUN);
        modifierRunMenuItem.addActionListener(gestionListener);
        modifierLiveMenuItem = new JMenuItem(TexteConstantes.LIVE);
        modifierLiveMenuItem.addActionListener(gestionListener);
        //SOUS MENU SUPPRIMER
        supprimerSousMenu = new JMenu(TexteConstantes.SUPPRIMER);
        supprimerPlateformeMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        supprimerPlateformeMenuItem.addActionListener(gestionListener);
        supprimerGenreMenuItem = new JMenuItem(TexteConstantes.GENRE);
        supprimerGenreMenuItem.addActionListener(gestionListener);
        supprimerStudioMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        supprimerStudioMenuItem.addActionListener(gestionListener);
        supprimerEditeurMenuItem = new JMenuItem(TexteConstantes.EDITEURS);
        supprimerEditeurMenuItem.addActionListener(gestionListener);
        supprimerJeuMenuItem = new JMenuItem(TexteConstantes.JEU);
        supprimerJeuMenuItem.addActionListener(gestionListener);
        supprimerRunMenuItem = new JMenuItem(TexteConstantes.RUN);
        supprimerRunMenuItem.addActionListener(gestionListener);
        supprimerLiveMenuItem = new JMenuItem(TexteConstantes.LIVE);
        supprimerLiveMenuItem.addActionListener(gestionListener);
        
        //MENU OUTILS
        outilsMenu = new JMenu(TexteConstantes.OUTILS);
        
        preferencesMenuItem = new JMenuItem(TexteConstantes.PREFERENCES);
        preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        preferencesMenuItem.addActionListener(listener);
        
        //MENU AFFICHAGE
        affichageMenu = new JMenu(TexteConstantes.AFFICHAGE);
        //SOUS MENU RACINE
        affichageRacineSousMenu = new JMenu(TexteConstantes.GROUP);
        AffichageGroupeListener listenerRacine = new AffichageGroupeListener();
        
        //RADIO BUTTONS RACINE
        plateformesAffichageRacineMenuItem = new JRadioButtonMenuItem(TexteConstantes.PLATEFORMES);
        plateformesAffichageRacineMenuItem.addItemListener(listenerRacine);
        genresAffichageRacineMenuItem = new JRadioButtonMenuItem(TexteConstantes.GENRES);
        genresAffichageRacineMenuItem.addItemListener(listenerRacine);
        studiosAffichageRacineMenuItem = new JRadioButtonMenuItem(TexteConstantes.STUDIOS);
        studiosAffichageRacineMenuItem.addItemListener(listenerRacine);
        editeursAffichageRacineMenuItem = new JRadioButtonMenuItem(TexteConstantes.EDITEURS);
        editeursAffichageRacineMenuItem.addItemListener(listenerRacine);
        jeuxAffichageRacineMenuItem = new JRadioButtonMenuItem(TexteConstantes.JEUX);
        jeuxAffichageRacineMenuItem.addItemListener(listenerRacine);
        
        //BUTTONGROUP AFFICHAGE RACINE
        racineAffichageGroup = new ButtonGroup();
        racineAffichageGroup.add(plateformesAffichageRacineMenuItem);
        racineAffichageGroup.add(genresAffichageRacineMenuItem);
        racineAffichageGroup.add(studiosAffichageRacineMenuItem);
        racineAffichageGroup.add(editeursAffichageRacineMenuItem);
        racineAffichageGroup.add(jeuxAffichageRacineMenuItem);
        racineAffichageGroup.clearSelection();
        switch (typeGroup) {
            case PLATEFORMES:
                plateformesAffichageRacineMenuItem.setSelected(true);
                break;
            case GENRES:
                genresAffichageRacineMenuItem.setSelected(true);
                break;
            case STUDIOS:
                studiosAffichageRacineMenuItem.setSelected(true);
                break;
            case JEUX:
                jeuxAffichageRacineMenuItem.setSelected(true);
                break;
            case EDITEURS:
                editeursAffichageRacineMenuItem.setSelected(true);
                break;
            default:
                jeuxAffichageRacineMenuItem.setSelected(true);
        }
        
        //SOUS MENU AFFICHAGE TEMPS
        affichageTempsSousMenu = new JMenu(TexteConstantes.TEMPS);
        AffichageTempsListener listenerTemps = new AffichageTempsListener();
        //RADIO BUTTONS TEMPS
        heuresAffichageTempsMenuItem = new JRadioButtonMenuItem(TexteConstantes.HEURES);
        heuresAffichageTempsMenuItem.addItemListener(listenerTemps);
        minutesAffichageTempsMenuItem = new JRadioButtonMenuItem(TexteConstantes.MINUTES);
        minutesAffichageTempsMenuItem.addItemListener(listenerTemps);
        //BUTTONGROUP TEMPS
        tempsAffichageGroup = new ButtonGroup();
        tempsAffichageGroup.add(heuresAffichageTempsMenuItem);
        tempsAffichageGroup.add(minutesAffichageTempsMenuItem);
        tempsAffichageGroup.clearSelection();
        
        switch (temps) {
            case HEURES :
                heuresAffichageTempsMenuItem.setSelected(true);
                break;
            case MINUTES :
                minutesAffichageTempsMenuItem.setSelected(true);
                break;
            default : 
                heuresAffichageTempsMenuItem.setSelected(true);
        }
    }
    private void initPopupMenu() {
        popupMenu = new JPopupMenu();
        
        //SOUS MENU AJOUTER
        ajouterSousPopupMenu = new JMenu(TexteConstantes.AJOUTER);
        ajouterPlateformePopupMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        ajouterGenrePopupMenuItem = new JMenuItem(TexteConstantes.GENRE);
        ajouterStudioPopupMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        ajouterEditeurPopupMenuItem = new JMenuItem(TexteConstantes.EDITEUR);
        ajouterJeuPopupMenuItem = new JMenuItem(TexteConstantes.JEU);
        ajouterRunPopupMenuItem = new JMenuItem(TexteConstantes.RUN);
        ajouterLivePopupMenuItem = new JMenuItem(TexteConstantes.LIVE);
        
        //SOUS MENU MODIFIER
        modifierSousPopupMenu = new JMenu(TexteConstantes.MODIFIER);
        modifierPlateformePopupMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        modifierGenrePopupMenuItem = new JMenuItem(TexteConstantes.GENRE);
        modifierStudioPopupMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        modifierEditeurPopupMenuItem = new JMenuItem(TexteConstantes.EDITEUR);
        modifierJeuPopupMenuItem = new JMenuItem(TexteConstantes.JEU);
        modifierRunPopupMenuItem = new JMenuItem(TexteConstantes.RUN);
        modifierLivePopupMenuItem = new JMenuItem(TexteConstantes.LIVE);
        
        //SOUS MENU SUPPRIMER
        supprimerSousPopupMenu = new JMenu(TexteConstantes.SUPPRIMER);
        supprimerPlateformePopupMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        supprimerGenrePopupMenuItem = new JMenuItem(TexteConstantes.GENRE);
        supprimerStudioPopupMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        supprimerEditeurPopupMenuItem = new JMenuItem(TexteConstantes.EDITEUR);
        supprimerJeuPopupMenuItem = new JMenuItem(TexteConstantes.JEU);
        supprimerRunPopupMenuItem = new JMenuItem(TexteConstantes.RUN);
        supprimerLivePopupMenuItem = new JMenuItem(TexteConstantes.LIVE);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void setGroup(TypeGroup group) {
        switch (group) {
            case PLATEFORMES :
                plateformesAffichageRacineMenuItem.setSelected(true);
                break;
            case GENRES :
                genresAffichageRacineMenuItem.setSelected(true);
                break;
            case STUDIOS :
                studiosAffichageRacineMenuItem.setSelected(true);
                break;
            case JEUX :
                jeuxAffichageRacineMenuItem.setSelected(true);
                break;
            case EDITEURS:
                editeursAffichageRacineMenuItem.setSelected(true);
            default :
                jeuxAffichageRacineMenuItem.setSelected(true);
        }
    }
    
    public void setTemps(Temps temps) {
        switch (temps) {
            case HEURES :
                heuresAffichageTempsMenuItem.setSelected(true);
                break;
            case MINUTES :
                minutesAffichageTempsMenuItem.setSelected(true);
                break;
            default :
                minutesAffichageTempsMenuItem.setSelected(true);
        }
    }
    
    
    //OBSERVER
    @Override
    public void clear(TypeGroup type) {
        rootTree.removeAllChildren();
        plateformePanels.supprimerTousItems();
        genrePanels.supprimerTousItems();
        studioPanels.supprimerTousItems();
        jeuPanels.clearLists();
        jeuPanels.supprimerTousItems();
        runPanels.supprimerTousItems();
        runPanels.supprimerTousSuperieurs();
        runPanels.clearFields(true, true);
        livePanels.supprimerTousItems();
        livePanels.supprimerToutesRuns();
        livePanels.supprimerTousLives();
        livePanels.supprimerTousSuperieurs();
        livePanels.clearFields(true, true);
        rootTree.setUserObject(type.getNom());
        ((DefaultTreeModel) treeJeux.getModel()).reload(rootTree);
        panelChart.setChart(null);
        textPaneInfos.setText(TexteConstantes.EMPTY);
    }
    
    @Override
    public void addPlateforme(Plateforme plateforme) {
        SortableTreeNode nodePlateforme = new SortableTreeNode(plateforme);
        mapPlateformes.put(plateforme.getID(), nodePlateforme);
        if (typeGroup.equals(TypeGroup.PLATEFORMES)) {
            rootTree.add(nodePlateforme);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
        plateformePanels.ajouterItem(plateforme.getID());
        jeuPanels.addPlateforme(plateforme);
    }
    
    @Override
    public void addGenre(Genre genre) {
        SortableTreeNode nodeGenre = new SortableTreeNode(genre);
        mapGenres.put(genre.getID(), nodeGenre);
        if (typeGroup.equals(TypeGroup.GENRES)) {
            rootTree.add(nodeGenre);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
        genrePanels.ajouterItem(genre.getID());
        jeuPanels.addGenre(genre);
    }
    
    @Override
    public void addStudio(Studio studio) {
        SortableTreeNode nodeStudio = new SortableTreeNode(studio);
        mapStudios.put(studio.getID(), nodeStudio);
        if (typeGroup.equals(TypeGroup.STUDIOS)) {
            rootTree.add(nodeStudio);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
        studioPanels.ajouterItem(studio.getID());
        jeuPanels.addStudio(studio);
    }
    
    @Override
    public void addEditeur(Editeur editeur) {
        SortableTreeNode nodeEditeur = new SortableTreeNode(editeur);
        mapEditeurs.put(editeur.getID(), nodeEditeur);
        if (typeGroup.equals(TypeGroup.EDITEURS)) {
            rootTree.add(nodeEditeur);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
        editeurPanels.ajouterItem(editeur.getID());
        jeuPanels.addEditeur(editeur);
    }
    
    @Override
    public void addJeu(Jeu jeu) {
        if (typeGroup.equals(TypeGroup.PLATEFORMES)) {
            Set<Entry<Long, Plateforme>> setPlateformes = jeu.getPlateformes().entrySet();
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
//            arrayJeu.add(nodeJeu);
                mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
//            mapJeux.getOrDefault(jeu.getID(),new ArrayList()).add(nodeJeu);
                for (Entry<Long, Plateforme> entryPlateforme : setPlateformes) {
                    SortableTreeNode nodeJeu = new SortableTreeNode(jeu);
                    mapJeux.get(jeu.getID()).add(nodeJeu);
                    SortableTreeNode parent = mapPlateformes.get(entryPlateforme.getKey());
//                mapPlateformes.get(entryPlateforme.getKey()).add(nodeJeu);
//                mapPlateformes.get(entryPlateforme.getKey()).sort();
                    parent.add(nodeJeu);
                    parent.sort();
                    ((DefaultTreeModel) treeJeux.getModel()).reload(parent);
                }
        }
        if (typeGroup.equals(TypeGroup.GENRES)) {
            Set<Entry<Long, Genre>> setGenres = jeu.getGenres().entrySet();
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
//            arrayJeu.add(nodeJeu);
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
//            mapJeux.getOrDefault(jeu.getID(),new ArrayList()).add(nodeJeu);
            for (Entry<Long, Genre> entryGenre : setGenres) {
                SortableTreeNode nodeJeu = new SortableTreeNode(jeu);
                mapJeux.get(jeu.getID()).add(nodeJeu);
                SortableTreeNode parent = mapGenres.get(entryGenre.getKey());
//                mapGenres.get(entryGenre.getKey()).add(nodeJeu);
//                mapGenres.get(entryGenre.getKey()).sort();
                parent.add(nodeJeu);
                parent.sort();
                ((DefaultTreeModel)treeJeux.getModel()).reload(parent);
            }
        }
        if (typeGroup.equals(TypeGroup.STUDIOS)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu);
            mapJeux.putIfAbsent(jeu.getID(),new ArrayList());
            mapJeux.get(jeu.getID()).add(nodeJeu);
            if (null != jeu.getStudio()) {
                SortableTreeNode parent = mapStudios.get(jeu.getStudio().getID());
                parent.add(nodeJeu);
//                mapStudios.get(jeu.getStudio().getID()).add(nodeJeu);
                ((DefaultTreeModel)treeJeux.getModel()).reload(parent);
            }
        }
        if (typeGroup.equals(TypeGroup.EDITEURS)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu);
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
            mapJeux.get(jeu.getID()).add(nodeJeu);
            if (null != jeu.getEditeur()) {
                SortableTreeNode parent = mapEditeurs.get(jeu.getEditeur().getID());
                parent.add(nodeJeu);
                ((DefaultTreeModel)treeJeux.getModel()).reload(parent);
            }
        }
        if (typeGroup.equals(TypeGroup.JEUX)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu);
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
            mapJeux.get(jeu.getID()).add(nodeJeu);
            rootTree.add(nodeJeu);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
//        ((DefaultTreeModel)treeJeux.getModel()).reload();
        jeuPanels.ajouterItem(jeu.getID());
        runPanels.ajouterJeu(jeu.getID());
        livePanels.ajouterJeu(jeu.getID());
    }
    
    @Override
    public void addRun(Run run) {
        ArrayList<SortableTreeNode> arrayJeu = mapJeux.get(run.getJeu().getID());
        mapRuns.putIfAbsent(run.getID(), new ArrayList());
        for (SortableTreeNode nodeJeu : arrayJeu) {
            SortableTreeNode nodeRun = new SortableTreeNode(run);
            mapRuns.get(run.getID()).add(nodeRun);
            nodeJeu.add(nodeRun);
            nodeJeu.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload(nodeJeu);
        }
//        runPanels.ajouterItem(run.getID());
//        livePanels.ajouterPossibleRun(run.getID());
//        ArrayList<SortableTreeNode> arrayRun = mapRuns.getOrDefault(run.getID(), new ArrayList());
//        mapRuns.putIfAbsent(idJeu, arrayRun);
//        arrayRun.add(nodeRun);
//        arrayRun.add(nodeRun);
//        mapJeux.get(idJeu).add(nodeRun);
//        mapRuns.getOrDefault(run.getID(),new ArrayList()).add(nodeRun);
//        mapRuns.put(run.getID(), nodeRun);
//        mapJeux.get(idJeu).sort();
//        rootTree.sort();
    }
    
    @Override
    public void addLive(Live live) {
        long idRun = live.getRun().getID();
        ArrayList<SortableTreeNode> arrayRun = mapRuns.getOrDefault(idRun,new ArrayList());
        for (SortableTreeNode nodeRun : arrayRun) {
            SortableTreeNode nodeLive = new SortableTreeNode(live);
            nodeRun.add(nodeLive);
            nodeRun.sort();
        }
        mapRuns.putIfAbsent(idRun, arrayRun);
//        livePanels.ajouterItem(live.getID());
//        mapRuns.get(idRun).add(nodeLive);
//        mapRuns.get(idRun).sort();
    }
    
    @Override
    public void addPossibleRunOnRunPanels(long idRun) {
        runPanels.ajouterPossibleRun(idRun);
    }
    
    @Override
    public void addPossibleRunOnLivePanels(long idRun) {
        livePanels.ajouterPossibleRun(idRun);
    }
    
    @Override
    public void addPossibleLiveOnLivePanels(long idLive) {
        livePanels.ajouterPossibleLive(idLive);
    }
    
    @Override
    public void removeAllPlateformes() {
        
    }
    
    @Override
    public void removeAllGenres() {
        
    }
    
    @Override
    public void removeAllStudios() { 
        
    }
    
    @Override
    public void removeAllJeux() { 
        
    }
    
    @Override
    public void removeAllRunsOnRunPanels() {
        runPanels.supprimerToutesRuns();
    }
    
    @Override
    public void removeAllRunsOnLivePanels() {
        livePanels.supprimerToutesRuns();
    }
    
    @Override
    public void removeAllLivesOnLivePanels() {
        livePanels.supprimerTousLives();
    }
    
    
    @Override
    public void removeAllLives() {
        
    }
    
    
    @Override
    public void removePlateforme(long idPlateforme) {
        if (typeGroup.equals(TypeGroup.PLATEFORMES)) {
            mapPlateformes.get(idPlateforme).removeAllChildren();
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapPlateformes.get(idPlateforme));
        }
        mapPlateformes.remove(idPlateforme);
        plateformePanels.supprimerItem(idPlateforme);
        jeuPanels.removePlateforme(idPlateforme);
    }
    
    @Override
    public void removeGenre(long idGenre) {
        if (typeGroup.equals(TypeGroup.GENRES)) {
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapGenres.get(idGenre));
        }
        mapGenres.remove(idGenre);
        plateformePanels.supprimerItem(idGenre);
        jeuPanels.removeGenre(idGenre);
    }
    
    @Override
    public void removeStudio(long idStudio) {
        if (typeGroup.equals(TypeGroup.STUDIOS)) {
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapStudios.get(idStudio));
        }
        mapStudios.remove(idStudio);
        studioPanels.supprimerItem(idStudio);
        jeuPanels.removeStudio(idStudio);
    }
    
    @Override
    public void removeEditeur(long idEditeur) {
        if (typeGroup.equals(TypeGroup.EDITEURS)) {
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapEditeurs.get(idEditeur));
        }
        mapEditeurs.remove(idEditeur);
        editeurPanels.supprimerItem(idEditeur);
        jeuPanels.removeEditeur(idEditeur);
    }
    
    @Override
    public void removeJeu(long idJeu) {
//        Set<Entry<Long, SortableTreeNode>> setStudios = mapStudios.entrySet();
//        for (Entry<Long, SortableTreeNode> entry : setStudios) {
//            if (entry.getValue().isInformations()) {
//                FillDataset object = entry.getValue().getObjectFillDataset();
//                if (object instanceof Jeu) {
//                    entry.getValue().removeFromParent();
//                }
//            }
//        }
//        if (typeGroup.equals(TypeGroup.JEUX)) {
//            ArrayList<SortableTreeNode> nodesJeu = mapJeux.get(idJeu);
//            for (SortableTreeNode nodeJeu : nodesJeu) {
//                nodeJeu.removeAllChildren();
//                nodeJeu.removeFromParent();
//                if(typeGroup.equals(TypeGroup.JEUX)) {
//                    ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(nodeJeu);
//                    ((DefaultTreeModel)treeJeux.getModel()).reload();
//                }
//            }
//        }
        ArrayList<SortableTreeNode> nodesJeu = mapJeux.get(idJeu);
        for (SortableTreeNode nodeJeu : nodesJeu) {
            nodeJeu.removeAllChildren();
            TreeNode parent = nodeJeu.getParent();
            nodeJeu.removeFromParent();
            ((DefaultTreeModel)treeJeux.getModel()).reload(parent);
        }
        mapJeux.remove(idJeu);
//        ((DefaultTreeModel)treeJeux.getModel()).reload();
        jeuPanels.supprimerItem(idJeu);
        runPanels.supprimerJeu(idJeu);
    }
    
    @Override
    public void removeRun(long idRun) {
        ArrayList<SortableTreeNode> nodesRun = mapRuns.get(idRun);
        for (SortableTreeNode nodeRun : nodesRun) {
            nodeRun.removeAllChildren();
            nodeRun.removeFromParent();
        }
        mapRuns.remove(idRun);
        ((DefaultTreeModel)treeJeux.getModel()).reload();
    }
    
    @Override
    public void removeLive(long idLive) {
        
    }
    
    @Override
    public void updateDataset(String titre, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(TexteConstantesChart.DEBUT_TITRE + " " + titre,TexteConstantesChart.DATE_LIVE, TexteConstantes.EMPTY, dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.addSubtitle(new TextTitle(TexteConstantes.TEMPS + " en " + temps.getNom()));
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)chart.getCategoryPlot().getRenderer();
        renderer.setBaseShapesVisible(true);
        panelChart.setChart(chart);
    }
    
    @Override
    public void fillPlateforme(String nomPlateforme) {
        plateformePanels.setNom(nomPlateforme);
    }
    
    @Override
    public void fillGenre(String nomGenre) {
        genrePanels.setNom(nomGenre);
    }
    
    @Override
    public void fillStudio(String nomStudio) {
        studioPanels.setNom(nomStudio);
    }
    
    @Override
    public void fillJeu(String titreJeu, String dateSortieString, Long[] listPlateformesID, Long[] listGenresID, long idStudio) {
        jeuPanels.setNom(titreJeu);
        jeuPanels.setDateSortie(dateSortieString);
        jeuPanels.setPlateformesSelection(listPlateformesID);
        jeuPanels.setGenresSelection(listGenresID);
        jeuPanels.setStudioSelection(idStudio);
    }
    
    @Override
    public void fillRun(String titreRun/*, long idJeu*/) {
        runPanels.setNom(titreRun);
//        runPanels.setIDJeu(idJeu);
    }
    
    @Override
    public void fillJeuOnRunPanels(String titreJeu) {
        runPanels.setTitreJeu(titreJeu);
    }
    
    @Override
    public void fillJeuOnLivePanels(String titreJeu) {
        livePanels.setTitreJeu(titreJeu);
    }
    
    @Override
    public void fillRunOnLivePanels(String titreRun) {
        livePanels.setTitreRun(titreRun);
    }
    
    @Override
    public void fillLive(long idRun, Date dateDebut, Date dateFin, int morts, int boss) {
        livePanels.setIDRun(idRun);
        livePanels.setDateDebut(dateDebut);
        livePanels.setDateFin(dateFin);
        livePanels.setMorts(morts);
        livePanels.setBoss(boss);
    }
    
    @Override
    public void fillLiveRun(long idRun, String titreRun, String titreJeu) {
        livePanels.setIDRun(idRun);
        livePanels.setTitreRun(titreRun);
        livePanels.setTitreJeu(titreJeu);
    }
    
    
    //MÉTHODES GRAPHIQUES DE SAISIE UTILISATEUR
    private void gererBasicInputs(ModeGestion mode, TypeBasicInputs typeInputs) {
        ObjectDatabasePanels inputs;
        switch (typeInputs) {
            case PLATEFORMES :
                inputs = plateformePanels;
                break;
            case GENRES :
                inputs = genrePanels;
                break;
            case STUDIOS :
                inputs = studioPanels;
                break;
            case EDITEURS :
                inputs = editeurPanels;
                break;
            default :
                inputs = null;
        }
        if (null != inputs) {
            inputs.setSelectionPanelVisible(!mode.equals(ModeGestion.AJOUTER));
            if (mode.equals(ModeGestion.AJOUTER)) {
//                inputs.setSelectionPanelVisible(false);
                inputs.setSaisiesPanelVisible(true);
                inputs.clearFields(true,true);
            }
            else {
                boolean editable = mode.equals(ModeGestion.MODIFIER);
//                inputs.setSelectionPanelVisible(true);
                inputs.setSaisiesPanelVisible(editable);
                inputs.clearFields(editable,false);
            }
            
            inputs.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
            String[] options = { mode.getAction(), TexteConstantes.ANNULER };
            int res = JOptionPane.showOptionDialog(this, inputs, mode.getAction() + " " + typeInputs.getNom(),
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (res == JOptionPane.YES_OPTION) {
                switch (mode) {
                    case AJOUTER:
                        controler.ajouterBasicInputs(typeInputs, inputs.getNouveauNom());
                        break;
                    case MODIFIER:
                        controler.modifierBasicInputs(typeInputs, inputs.getSelectedID(), inputs.getNouveauNom());
                        treeJeux.updateUI();
                        break;
                    case SUPPRIMER:
                        controler.supprimerBasicInputs(typeInputs, inputs.getSelectedID());
                        break;
                    default:
                }
            }
        }
    }
    
    private void gererJeuxInputs(ModeGestion mode) {
        jeuPanels.setSelectionPanelVisible(!mode.equals(ModeGestion.AJOUTER));
        jeuPanels.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
        if (mode.equals(ModeGestion.AJOUTER)) {
            jeuPanels.setNouveauNomPanelVisible(true);
            jeuPanels.clearFields(true,true);
        }
        else {
            boolean editable = mode.equals(ModeGestion.MODIFIER);
            jeuPanels.setNouveauNomPanelVisible(editable);
            jeuPanels.clearFields(editable,false);
        }
        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
        int res = JOptionPane.showOptionDialog(this, jeuPanels, mode.getAction()
                + " " + TexteConstantes.JEU.toLowerCase(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            long idJeu = jeuPanels.getSelectedID();
            String titreJeu = jeuPanels.getNouveauNom();
            String dateSortie = jeuPanels.getDateSortie();
            List<Long> listPlateformes = jeuPanels.getPlateformes();
            List<Long> listGenres = jeuPanels.getGenres();
            long idStudio = jeuPanels.getStudioID();
            long idEditeur = 0;
            switch (mode) {
                case AJOUTER :
                    controler.ajouterJeu(titreJeu, dateSortie , listPlateformes, listGenres, idStudio, idEditeur);
                    break;
                case MODIFIER :
                    controler.modifierJeu(idJeu, titreJeu, dateSortie, listPlateformes, listGenres, idStudio, idEditeur);
                    break;
                case SUPPRIMER :
                    controler.supprimerJeu(idJeu);
                    break;
                default :
            }
        }
    }
    
    private void gererRunsInputs(ModeGestion mode) {
//        runPanels.setSelectionPanelVisible(!mode.equals(ModeGestion.AJOUTER));
        if (mode.equals(ModeGestion.AJOUTER)) {
            runPanels.setSelectionCurrentPanelVisible(false);
            runPanels.setNouveauNomPanelVisible(true);
            runPanels.setSaisiesPanelVisible(true);
            runPanels.clearFields(true,true);
        }
        else {
            boolean editable = mode.equals(ModeGestion.MODIFIER);
            runPanels.setSelectionCurrentPanelVisible(true);
            runPanels.setSaisiesPanelVisible(editable);
            runPanels.setNouveauNomPanelVisible(editable);
            runPanels.clearFields(editable,false);
        }
        runPanels.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
        int res = JOptionPane.showOptionDialog(this, runPanels, mode.getAction()
                + " " + TexteConstantes.RUN.toLowerCase(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            long idRun = runPanels.getSelectedID();
            String titreRun = runPanels.getNouveauNom();
            long idJeu = runPanels.getSelectedJeuID();
            switch (mode) {
                case AJOUTER :
                    controler.ajouterRun(titreRun, idJeu);
                    break;
                case MODIFIER :
                    controler.modifierRun(idRun, titreRun, idJeu);
                    break;
                case SUPPRIMER :
                    controler.supprimerRun(idRun);
                    break;
                default :
            }
        }
    }
    
    private void gererLivesInputs(ModeGestion mode) {
        livePanels.setNouveauNomPanelVisible(false);
        livePanels.setSelectionCurrentPanelVisible(!mode.equals(ModeGestion.AJOUTER));
        livePanels.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
        if (mode.equals(ModeGestion.AJOUTER)) {
            livePanels.clearFields(true,true);
        }
        else {
            boolean editable = mode.equals(ModeGestion.MODIFIER);
            livePanels.clearFields(editable,false);
        }
        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
        int res = JOptionPane.showOptionDialog(this, livePanels, mode.getAction()
                + " " + TexteConstantes.LIVE.toLowerCase(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            switch (mode) {
                case AJOUTER :
                    
                    break;
                case MODIFIER :
                    
                    break;
                case SUPPRIMER :
                    
                    break;
                default :
            }
        }
    }
    
//    
//    private void gererPlateformeInputs(ModeGestion mode) {
//        if (mode.equals(ModeGestion.AJOUTER)) {
//            plateformePanels.setSelectionPanelVisible(false);
//        }
//        else {
//            plateformePanels.setSelectionPanelVisible(true);
//        }
//        
//        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
//        int res = JOptionPane.showOptionDialog(this, plateformePanels, mode.getAction() + " " + TexteConstantes.PLATEFORME,
//                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            switch (mode) {
//                case AJOUTER :
//                    controler.ajouterPlateforme(plateformePanels.getNom());
//                    break;
//                case MODIFIER :
//                    controler.modifierPlateforme(plateformePanels.getSelectedID(), plateformePanels.getNom());
//                    break;
//                case SUPPRIMER :
//                    controler.supprimerPlateforme(plateformePanels.getSelectedID());
//                    break;
//                default :
//                    
//            }
//        }
//    }
//    
//    private void gererGenresInputs(ModeGestion mode) {
//        if (mode.equals(ModeGestion.AJOUTER)) {
//            genrePanels.setSelectionPanelVisible(false);
//        }
//        else {
//            genrePanels.setSelectionPanelVisible(true);
//        }
//        
//        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
//        int res = JOptionPane.showOptionDialog(this, genrePanels, mode.getAction() + " " + TexteConstantes.PLATEFORME,
//                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            switch (mode) {
//                case AJOUTER :
//                    controler.ajouterGenre(genrePanels.getNom());
//                    break;
//                case MODIFIER :
//                    controler.modifierGenre(genrePanels.getSelectedID(), genrePanels.getNom());
//                    break;
//                case SUPPRIMER :
//                    controler.supprimerGenre(genrePanels.getSelectedID());
//                    break;
//                default :
//                    
//            }
//        }
//    }
//    
//    private void gererStudiosInputs(ModeGestion mode) {
//        if (mode.equals(ModeGestion.AJOUTER)) {
//            studioPanels.setSelectionPanelVisible(false);
//        }
//        else {
//            studioPanels.setSelectionPanelVisible(true);
//        }
//        
//        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
//        int res = JOptionPane.showOptionDialog(this, studioPanels, mode.getAction() + " " + TexteConstantes.PLATEFORME,
//                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            switch (mode) {
//                case AJOUTER :
//                    controler.ajouterStudio(studioPanels.getNom());
//                    break;
//                case MODIFIER :
//                    controler.modifierStudio(studioPanels.getSelectedID(), studioPanels.getNom());
//                    break;
//                case SUPPRIMER :
//                    controler.supprimerStudio(studioPanels.getSelectedID());
//                    break;
//                default :
//                    
//            }
//        }
//    }
//    
//    
//    private void ajouterPlateformeInputs() {
//        plateformePanels.clearFields(true);
//        JPanel inputs = new JPanel(new BorderLayout());
//        JPanel nomPanel = plateformePanels.getNomPanel();
//        inputs.add(nomPanel);
//        JTextField nomTextField = plateformePanels.getNomTextField();
//        String[] options = {TexteConstantes.AJOUTER, TexteConstantes.ANNULER};
//        int res = JOptionPane.showOptionDialog(this, inputs, TexteConstantes.AJOUTER + " " + TexteConstantes.PLATEFORME,
//                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            controler.ajouterPlateforme(nomTextField.getText());
//        }
//    }
//    
//    private void modifierPlateformeInputs() {
//        plateformePanels.clearFields(false);
//        JPanel inputs = new JPanel(new GridLayout(2, 1));
//        JPanel idPanel = plateformePanels.getIDPanel();
//        JPanel nomPanel = plateformePanels.getNomPanel();
//        
//        inputs.add(idPanel);
//        inputs.add(nomPanel);
//        JComboBox idComboBox = plateformePanels.getIDComboBox();
//        JTextField nomTextField = plateformePanels.getNomTextField();
//        String[] options = {TexteConstantes.MODIFIER, TexteConstantes.ANNULER};
//        int res = JOptionPane.showOptionDialog(this, inputs, TexteConstantes.MODIFIER + " " + TexteConstantes.PLATEFORME, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            controler.modifierPlateforme((long)idComboBox.getSelectedItem(),nomTextField.getText());
//        }
//    }
//    
//    private void supprimerPlateformeInputs() {
//        plateformePanels.clearFields(false);
//        JPanel inputs = new JPanel(new GridLayout(2, 1));
//        JPanel idPanel = plateformePanels.getIDPanel();
//        JPanel nomPanel = plateformePanels.getNomPanel();
//        
//        inputs.add(idPanel);
//        inputs.add(nomPanel);
//        
//        JComboBox idComboBox = plateformePanels.getIDComboBox();
//        JTextField nomTextField = plateformePanels.getNomTextField();
//        nomTextField.setEditable(false);
//        String[] options = {TexteConstantes.SUPPRIMER, TexteConstantes.ANNULER};
//        int res = JOptionPane.showOptionDialog(this, inputs, TexteConstantes.SUPPRIMER + " " + TexteConstantes.PLATEFORME, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            controler.supprimerPlateforme((long)idComboBox.getSelectedItem());
//            if (treeJeux.isSelectionEmpty()) {
//                textPaneInfos.setText(TexteConstantes.EMPTY);
//                panelGraph.setChart(null);
//            }
//        }
//    }
//    
//    private void ajouterGenreInputs() {
//        genrePanels.clearFields(true);
//        JPanel inputs = new JPanel(new BorderLayout());
//        JPanel nomPanel = genrePanels.getNomPanel();
//        
//        inputs.add(nomPanel);
//        JTextField nomTextField = genrePanels.getNomTextField();
//        String[] options = { TexteConstantes.AJOUTER, TexteConstantes.ANNULER };
//        int res = JOptionPane.showOptionDialog(this, inputs, TexteConstantes.AJOUTER + " " + TexteConstantes.GENRE, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            
//        }
//    }
//    
//    private void modifierGenreInputs() {
//        genrePanels.clearFields(false);
//        JPanel inputs = new JPanel(new GridLayout(2,1));
//        JPanel idPanel = genrePanels.getIDPanel();
//        JPanel nomPanel = genrePanels.getNomPanel();
//        
//        inputs.add(idPanel);
//        inputs.add(nomPanel);
//        
//        JTextField nomTextField = genrePanels.getNomTextField();
//        String[] options = { TexteConstantes.MODIFIER, TexteConstantes.ANNULER };
//        int res = JOptionPane.showOptionDialog(this, inputs, TexteConstantes.MODIFIER + " " + TexteConstantes.GENRE, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            
//        }
//        
//    }
//    
//    private void supprimerGenreInputs() {
//        genrePanels.clearFields(false);
//        JPanel inputs = new JPanel(new GridLayout(2,1));
//        JPanel idPanel = genrePanels.getIDPanel();
//        JPanel nomPanel = genrePanels.getNomPanel();
//        
//        inputs.add(idPanel);
//        inputs.add(nomPanel);
//        
//        JTextField nomTextField = genrePanels.getNomTextField();
//        nomTextField.setEditable(false);
//        String[] options = { TexteConstantes.SUPPRIMER, TexteConstantes.ANNULER };
//        int res = JOptionPane.showOptionDialog(this, inputs, TexteConstantes.SUPPRIMER + " " + TexteConstantes.GENRE, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (res == JOptionPane.YES_OPTION) {
//            
//        }
//    }
    
    //LISTENERS
    class MenuListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(nouveauMenuItem) || src.equals(ouvrirMenuItem)) {
                int dialogType;
                String message;
                if (src.equals(nouveauMenuItem)) {
                    dialogType = JFileChooser.SAVE_DIALOG;
                    message = TexteConstantes.CREER;
                }
                else {
                    dialogType = JFileChooser.OPEN_DIALOG;
                    message = TexteConstantes.OUVRIR;
                }
                FileChooser fileBDD = new FileChooser(TexteConstantesBDD.BDD, TexteConstantes.EMPTY, JFileChooser.FILES_ONLY, dialogType,false);
                FileNameExtensionFilter fileFilter;
                if (src.equals(nouveauMenuItem)) {
                    fileFilter = new FileNameExtensionFilter(TexteConstantesBDD.BDD + " " + TexteConstantesBDD.EXTENSIONS_BDD,
                            TexteConstantesBDD.ACCDB, TexteConstantesBDD.MDB,
                            TexteConstantesBDD.DB, TexteConstantesBDD.SDB,
                            TexteConstantesBDD.SQLITE, TexteConstantesBDD.DB2,
                            TexteConstantesBDD.S2DB, TexteConstantesBDD.SQLITE2,
                            TexteConstantesBDD.SL2, TexteConstantesBDD.DB3,
                            TexteConstantesBDD.S3DB, TexteConstantesBDD.SQLITE3,
                            TexteConstantesBDD.SL3);
                }
                else {
                    fileFilter = new FileNameExtensionFilter(TexteConstantesBDD.BDD + " " + TexteConstantesBDD.EXTENSIONS_BDD,
                            TexteConstantesBDD.ACCDB, TexteConstantesBDD.MDB,
                            TexteConstantesBDD.KEXI, TexteConstantesBDD.DB,
                            TexteConstantesBDD.SDB, TexteConstantesBDD.SQLITE,
                            TexteConstantesBDD.DB2, TexteConstantesBDD.S2DB,
                            TexteConstantesBDD.SQLITE2, TexteConstantesBDD.SL2,
                            TexteConstantesBDD.DB3, TexteConstantesBDD.S3DB,
                            TexteConstantesBDD.SQLITE3, TexteConstantesBDD.SL3);
                }
                fileBDD.setFilter(fileFilter,TexteConstantesBDD.DEFAULT_EXTENSION);
                int res = JOptionPane.showConfirmDialog(null, fileBDD, message + " une base de données", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    if (src.equals(nouveauMenuItem)) {
                        controler.creerBDD(fileBDD.getPath());
                    }
                    else {
                        controler.ouvrirBDD(fileBDD.getPath());
                    }
                }
            }
            if (src.equals(preferencesMenuItem)) {
                prefsDialog.showDialog();
            }
            if (src.equals(quitterMenuItem)) {
                controler.deconnecter();
                System.exit(0);
            }
            
        }
        
    }
    
    class MenuGestionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            //GESTION AJOUTER
            ModeGestion modeGestion = ModeGestion.AJOUTER;
            if (src.equals(ajouterPlateformeMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.PLATEFORMES);
            }
            if (src.equals(ajouterGenreMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.GENRES);
            }
            if (src.equals(ajouterStudioMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.STUDIOS);
            }
            if (src.equals(ajouterEditeurMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.EDITEURS);
            }
            if (src.equals(ajouterJeuMenuItem)) {
                gererJeuxInputs(modeGestion);
            }
            if (src.equals(ajouterRunMenuItem)) {
                gererRunsInputs(modeGestion);
            }
            if (src.equals(ajouterLiveMenuItem)) {
                gererLivesInputs(modeGestion);
            }
            //GESTION MODIFIER
            modeGestion = ModeGestion.MODIFIER;
            if (src.equals(modifierPlateformeMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.PLATEFORMES);
            }
            if (src.equals(modifierGenreMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.GENRES);
            }
            if (src.equals(modifierStudioMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.STUDIOS);
            }
            if (src.equals(modifierEditeurMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.EDITEURS);
            }
            if (src.equals(modifierJeuMenuItem)) {
                gererJeuxInputs(modeGestion);
            }
            if (src.equals(modifierRunMenuItem)) {
                gererRunsInputs(modeGestion);
            }
            if (src.equals(modifierLiveMenuItem)) {
                gererLivesInputs(modeGestion);
            }
            //GESTION SUPPRIMER
            modeGestion = ModeGestion.SUPPRIMER;
            if (src.equals(supprimerPlateformeMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.PLATEFORMES);
            }
            if (src.equals(supprimerGenreMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.GENRES);
            }
            if (src.equals(supprimerStudioMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.STUDIOS);
            }
            if (src.equals(supprimerEditeurMenuItem)) {
                gererBasicInputs(modeGestion, TypeBasicInputs.EDITEURS);
            }
            if (src.equals(supprimerJeuMenuItem)) {
                gererJeuxInputs(modeGestion);
            }
            if (src.equals(supprimerRunMenuItem)) {
                gererRunsInputs(modeGestion);
            }
            if (src.equals(supprimerLiveMenuItem)) {
                gererLivesInputs(modeGestion);
            }
        }
        
    }

    class GestionPopupMenuListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            
        }
        
    }
    
    class TreeMouseListener extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) {
            int selRow = treeJeux.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = treeJeux.getPathForLocation(e.getX(), e.getY());
            if (selRow != -1) {
                if (e.getClickCount() == 1) {
                    if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                        treeJeux.setSelectionPath(selPath);
                    }
                }
            }
        }
        
    }
    
    class TreeListener implements TreeSelectionListener {
        
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            Object src = e.getSource();
            TreePath[] paths = null;
            if (src.equals(treeJeux)) {
                paths = treeJeux.getSelectionPaths();
            }
            if (null != paths) {
                String informations = TexteConstantes.EMPTY;
                ArrayList<FillDataset> nodes = new ArrayList();
                String titreGraph = null;
                if (paths.length > 1) {
                    titreGraph = TexteConstantesChart.SELECTION_MULTIPLE;
                }
                if (paths.length == 1) {
                    Object node = paths[0].getLastPathComponent();
                    if (node instanceof SortableTreeNode) {
                        if (((SortableTreeNode) node).isInformations()) {
                            titreGraph = ((Informations)node).getObjectFillDataset().
                                    getTitreDataset();
                        }
                    }
                }
                for (TreePath path : paths) {
                    Object node = path.getLastPathComponent();
                    for (TreePath path1 : paths) {
                        if (path != path1 && path.isDescendant(path1)) {
                            treeJeux.clearSelection();
                            panelChart.setChart(null);
                            treeJeux.setSelectionPath(e.getNewLeadSelectionPath());
                            return;
                        }
                    }
                    if (node instanceof SortableTreeNode && ((SortableTreeNode)node).isInformations()) {
                        informations += ((Informations) node).getInformations() + TexteConstantes.NEW_LINE;
                        nodes.add(((Informations)node).getObjectFillDataset());
                    }
                    else {
                        panelChart.setChart(null);
                        textPaneInfos.setText(TexteConstantes.EMPTY);
                        return;
                    }
                }
                controler.createDataset(titreGraph, nodes);
                textPaneInfos.setText(informations);
            }
        }
        
    }
    
    class AffichageGroupeListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            if (selected) {
                Object item = e.getItem();
                if (item.equals(plateformesAffichageRacineMenuItem)) {
                    typeGroup = TypeGroup.PLATEFORMES;
                }
                if (item.equals(genresAffichageRacineMenuItem)) {
                    typeGroup = TypeGroup.GENRES;
                }
                if (item.equals(studiosAffichageRacineMenuItem)) {
                    typeGroup = TypeGroup.STUDIOS;
                }
                if (item.equals(editeursAffichageRacineMenuItem)) {
                    typeGroup = TypeGroup.EDITEURS;
                }
                if (item.equals(jeuxAffichageRacineMenuItem)) {
                    typeGroup = TypeGroup.JEUX;
                }
                controler.setGroup(typeGroup);
            }
        }
        
    }
    
    class AffichageTempsListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            if (selected) {
                Object item = e.getItem();
                if (item.equals(heuresAffichageTempsMenuItem)) {
                    temps = Temps.HEURES;
//                    controler.setTimeUnit(TimeUnit.HOURS);
                }
                if (item.equals(minutesAffichageTempsMenuItem)) {
                    temps = Temps.MINUTES;
//                    controler.setTimeUnit(TimeUnit.MINUTES);
                }
                controler.setTimeUnit(temps.getTimeUnit());
            }
        }
        
    }
    
    class PlateformesActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(TexteConstantes.AJOUTER)) {
                gererBasicInputs(ModeGestion.AJOUTER, TypeBasicInputs.PLATEFORMES);
            }
        }
        
    }
    
    class GenresActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(TexteConstantes.AJOUTER)) {
                gererBasicInputs(ModeGestion.AJOUTER, TypeBasicInputs.GENRES);
            }
        }
        
    }
    
    class StudiosActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(TexteConstantes.AJOUTER)) {
                gererBasicInputs(ModeGestion.AJOUTER, TypeBasicInputs.STUDIOS);
            }
        }
        
    }
    
}
