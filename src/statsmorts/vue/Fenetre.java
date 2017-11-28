/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
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
import javax.swing.tree.TreePath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.FillDataset;
import statsmorts.classes.Genre;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Run;
import statsmorts.classes.Studio;
import statsmorts.classes.TypeRacine;
import statsmorts.controler.StatsMortsControler;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;
import statsmorts.preferences.PreferencesDialog;

/**
 *
 * @author Robin
 */
public class Fenetre extends JFrame implements Observer {
    
    //CONSTANTES
    private static final String FICHIER = "Fichier";
    private static final String NOUVEAU = "Nouveau";
    private static final String CREER = "Créer";
    private static final String OUVRIR = "Ouvrir";
    private static final String ANNULER = "Annuler";
    private static final String GESTION = "Gestion";
    private static final String QUITTER = "Quitter";
    private static final String OUTILS = "Outils";
    private static final String PREFERENCES = "Préférences";
    private static final String AFFICHAGE = "Affichage";
    private static final String RACINE = "Racine";
    private static final String AFFICHER = "Afficher";
    private static final String AJOUTER = "Ajouter";
    private static final String MODIFIER = "Modifier";
    private static final String SUPPRIMER = "Supprimer";
    private static final String PLATEFORMES = "Plateformes";
    private static final String PLATEFORME = "Plateforme";
    private static final String GENRES = "Genres";
    private static final String GENRE = "Genre";
    private static final String STUDIOS = "Studios";
    private static final String STUDIO = "Studio";
    private static final String JEUX = "Jeux";
    private static final String JEU = "Jeu";
    private static final String RUNS = "Runs";
    private static final String RUN = "Run";
    private static final String LIVES = "Lives";
    private static final String LIVE = "Live";
    private static final String TEMPS = "Temps";
    private static final String HEURES = "Heures";
    private static final String MINUTES = "Minutes";
    private static final String EMPTY = "";
    private static final String BDD = "Base de données";
    private static final String EXTENSIONS_BDD = "(.accdb,.mdb,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)";
    
    //ATTRIBUTS
    //SPLIT PANES
    private JSplitPane splitVerticalGlobal;
    private JSplitPane splitHorizontalInGlobal;
    
    //JPANELS
    private JPanel panelArbre;
    private JPanel panelInfos;
    private ChartPanel panelGraph;
    
    //ARBRES
    private JTree treeJeux;
    private SortableTreeNode rootTree;
    private HashMap<Long, SortableTreeNode> mapPlateformes;
    private HashMap<Long, SortableTreeNode> mapGenres;
    private HashMap<Long, SortableTreeNode> mapStudios;
    private HashMap<Long, ArrayList<SortableTreeNode>> mapJeux;
    private HashMap<Long, ArrayList<SortableTreeNode>> mapRuns;
    //TEXTPANE
    private JTextPane textPaneInfos;
    
    //MENUS
    private JMenuBar menuBar;
    //MENU FICHIER
    private JMenu fichierMenu;
    private JMenuItem nouveauMenuItem;
    private JMenuItem ouvrirMenuItem;
    private JMenu gestionBaseDonnees;
    //SOUS MENU GESTION BASE DE DONNEES
    //SOUS MENU AJOUTER
    private JMenu ajouterSousMenu;
    private JMenuItem ajouterPlateformeMenuItem;
    private JMenuItem ajouterGenreMenuItem;
    private JMenuItem ajouterStudioMenuItem;
    private JMenuItem ajouterJeuMenuItem;
    private JMenuItem ajouterRunMenuItem;
    private JMenuItem ajouterLiveMenuItem;
    //SOUS MENU MODIFIER
    private JMenu modifierSousMenu;
    private JMenuItem modifierPlateformeMenuItem;
    private JMenuItem modifierGenreMenuItem;
    private JMenuItem modifierStudioMenuItem;
    private JMenuItem modifierJeuMenuItem;
    private JMenuItem modifierRunMenuItem;
    private JMenuItem modifierLiveMenuItem;
    //SOUS MENU SUPPRIMER
    private JMenu supprimerSousMenu;
    private JMenuItem supprimerPlateformeMenuItem;
    private JMenuItem supprimerGenreMenuItem;
    private JMenuItem supprimerStudioMenuItem;
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
    private JRadioButtonMenuItem studiosAffichageRacineMenuItem;
    private JRadioButtonMenuItem genresAffichageRacineMenuItem;
    private JRadioButtonMenuItem jeuxAffichageRacineMenuItem;
    private ButtonGroup racineAffichageGroup;
    private JMenu affichageTreeNodesSousMenu;
    private JCheckBoxMenuItem jeuxAffichageTreeNodesMenuItem;
    private JCheckBoxMenuItem runsAffichageTreeNodesMenuItem;
    private JCheckBoxMenuItem livesAffichageTreeNodesMenuItem;
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
    private JMenuItem ajouterJeuPopupMenuItem;
    private JMenuItem ajouterRunPopupMenuItem;
    private JMenuItem ajouterLivePopupMenuItem;
    //SOUS MENU MODIFIER POPUP
    private JMenu modifierSousPopupMenu;
    private JMenuItem modifierPlateformePopupMenuItem;
    private JMenuItem modifierGenrePopupMenuItem;
    private JMenuItem modifierStudioPopupMenuItem;
    private JMenuItem modifierJeuPopupMenuItem;
    private JMenuItem modifierRunPopupMenuItem;
    private JMenuItem modifierLivePopupMenuItem;
    //SOUS MENU SUPPRIMER POPUP
    private JMenu supprimerSousPopupMenu;
    private JMenuItem supprimerPlateformePopupMenuItem;
    private JMenuItem supprimerGenrePopupMenuItem;
    private JMenuItem supprimerStudioPopupMenuItem;
    private JMenuItem supprimerJeuPopupMenuItem;
    private JMenuItem supprimerRunPopupMenuItem;
    private JMenuItem supprimerLivePopupMenuItem;
    
    private TimeUnit unit;
    private TypeRacine typeRacine;
    
    //AFFICHAGE JEUX, RUNS et LIVES
    private boolean affichageJeuxBool;
    private boolean affichageRunsBool;
    private boolean affichageLivesBool;
    
    //CLASSES PERSO
    private final PlateformePanel plateformePanel;
    private final Preferences preferences;
    private final PreferencesDialog prefsDialog;
    private final StatsMortsControler controler;
    
    
    //CONSTRUCTEUR
    public Fenetre(String titre, StatsMortsControler controler, Preferences preferences) {
        super(titre);
        java.net.URL iconURL = Fenetre.class.getResource("icon.jpg");
        if (iconURL != null) {
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
        
        this.unit = TimeUnit.HOURS;
        this.typeRacine = preferences.getAffichageRacine();
        this.controler = controler;
        this.preferences = preferences;
        this.prefsDialog = new PreferencesDialog(this, PREFERENCES, false, this.controler, this.preferences);
        this.plateformePanel = new PlateformePanel(this.controler);
        
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
        splitVerticalGlobal.setBottomComponent(panelGraph);
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
        ajouterSousMenu.add(ajouterJeuMenuItem);
        ajouterSousMenu.add(ajouterRunMenuItem);
        ajouterSousMenu.add(ajouterLiveMenuItem);
        //SOUS SOUS MENU MODIFIER
        gestionBaseDonnees.add(modifierSousMenu);
        modifierSousMenu.add(modifierPlateformeMenuItem);
        modifierSousMenu.add(modifierGenreMenuItem);
        modifierSousMenu.add(modifierStudioMenuItem);
        modifierSousMenu.add(modifierJeuMenuItem);
        modifierSousMenu.add(modifierRunMenuItem);
        modifierSousMenu.add(modifierLiveMenuItem);
        //SOUS SOUS MENU SUPPRIMER
        gestionBaseDonnees.add(supprimerSousMenu);
        supprimerSousMenu.add(supprimerPlateformeMenuItem);
        supprimerSousMenu.add(supprimerGenreMenuItem);
        supprimerSousMenu.add(supprimerStudioMenuItem);
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
        affichageRacineSousMenu.add(jeuxAffichageRacineMenuItem);
        
        //AJOUT DES MENU ITEMS DE L'AFFICHAGE TREE NODES
        affichageTreeNodesSousMenu.add(jeuxAffichageTreeNodesMenuItem);
        affichageTreeNodesSousMenu.add(runsAffichageTreeNodesMenuItem);
        affichageTreeNodesSousMenu.add(livesAffichageTreeNodesMenuItem);
        
        //AJOUT DES MENU ITEMS DE L'AFFICHAGE TEMPS
        affichageTempsSousMenu.add(heuresAffichageTempsMenuItem);
        affichageTempsSousMenu.add(minutesAffichageTempsMenuItem);
        
        //AJOUT DES SOUS MENU D'AFFICHAGE
        affichageMenu.add(affichageRacineSousMenu);
        affichageMenu.add(affichageTreeNodesSousMenu);
        affichageMenu.add(affichageTempsSousMenu);
        
        //AJOUT DES MENUS
        menuBar.add(fichierMenu);
        menuBar.add(outilsMenu);
        menuBar.add(affichageMenu);
    }
    private void setPopupMenu() {
        treeJeux.setComponentPopupMenu(popupMenu);

        //SOUS MENU AJOUTER
        ajouterSousPopupMenu.add(ajouterPlateformePopupMenuItem);
        ajouterSousPopupMenu.add(ajouterGenrePopupMenuItem);
        ajouterSousPopupMenu.add(ajouterStudioPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterJeuPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterRunPopupMenuItem);
        ajouterSousPopupMenu.add(ajouterLivePopupMenuItem);
        //SOUS MENU MODIFIER
        modifierSousPopupMenu.add(modifierPlateformePopupMenuItem);
        modifierSousPopupMenu.add(modifierGenrePopupMenuItem);
        modifierSousPopupMenu.add(modifierStudioPopupMenuItem);
        modifierSousPopupMenu.add(modifierJeuPopupMenuItem);
        modifierSousPopupMenu.add(modifierRunPopupMenuItem);
        modifierSousPopupMenu.add(modifierLivePopupMenuItem);
        //SOUS MENU SUPPRIMER
        supprimerSousPopupMenu.add(supprimerPlateformePopupMenuItem);
        supprimerSousPopupMenu.add(supprimerGenrePopupMenuItem);
        supprimerSousPopupMenu.add(supprimerStudioPopupMenuItem);
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
        initTextPane();
        initTree();
        initMenuBar();
        initPopupMenu();
    }
    private void initSplitPanes() {
        splitVerticalGlobal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        splitVerticalGlobal.setOneTouchExpandable(true);
        splitHorizontalInGlobal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitHorizontalInGlobal.setOneTouchExpandable(true);
    }
    private void initPanels() {
        panelArbre = new JPanel(new BorderLayout());
        panelInfos = new JPanel(new BorderLayout());
        panelGraph = new ChartPanel(null);
    }
    private void initTree() {
        rootTree = new SortableTreeNode(JEUX, false);
        treeJeux = new JTree(rootTree);
        treeJeux.addTreeSelectionListener(new TreeListener());
        mapPlateformes = new HashMap();
        mapGenres = new HashMap();
        mapStudios = new HashMap();
        mapJeux = new HashMap();
        mapRuns = new HashMap();
    }
    private void initTextPane() {
        textPaneInfos = new JTextPane();
        textPaneInfos.setText("Informations");
        textPaneInfos.setEditable(false);
    }
    private void initMenuBar() {
        menuBar = new JMenuBar();
        MenuListener listener = new MenuListener();
        
        //MENU FICHIER
        fichierMenu = new JMenu(FICHIER);
        
        nouveauMenuItem = new JMenuItem(NOUVEAU);
        nouveauMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        nouveauMenuItem.addActionListener(listener);
        
        ouvrirMenuItem = new JMenuItem(OUVRIR);
        ouvrirMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        ouvrirMenuItem.addActionListener(listener);
        
        quitterMenuItem = new JMenuItem(QUITTER);
        quitterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        quitterMenuItem.addActionListener(listener);
        
        //SOUS MENU GESTION BASE DE DONNEES
        gestionBaseDonnees = new JMenu(GESTION);
        MenuGestionListener gestionListener = new MenuGestionListener();
        //SOUS MENU AJOUTER
        ajouterSousMenu = new JMenu(AJOUTER);
        ajouterPlateformeMenuItem = new JMenuItem(PLATEFORME);
        ajouterPlateformeMenuItem.addActionListener(gestionListener);
        ajouterGenreMenuItem = new JMenuItem(GENRE);
        ajouterGenreMenuItem.addActionListener(gestionListener);
        ajouterStudioMenuItem = new JMenuItem(STUDIO);
        ajouterStudioMenuItem.addActionListener(gestionListener);
        ajouterJeuMenuItem = new JMenuItem(JEU);
        ajouterJeuMenuItem.addActionListener(gestionListener);
        ajouterRunMenuItem = new JMenuItem(RUN);
        ajouterRunMenuItem.addActionListener(gestionListener);
        ajouterLiveMenuItem = new JMenuItem(LIVE);
        ajouterLiveMenuItem.addActionListener(gestionListener);
        //SOUS MENU MODIFIER
        modifierSousMenu = new JMenu(MODIFIER);
        modifierPlateformeMenuItem = new JMenuItem(PLATEFORME);
        modifierPlateformeMenuItem.addActionListener(gestionListener);
        modifierGenreMenuItem = new JMenuItem(GENRE);
        modifierGenreMenuItem.addActionListener(gestionListener);
        modifierStudioMenuItem = new JMenuItem(STUDIO);
        modifierStudioMenuItem.addActionListener(gestionListener);
        modifierJeuMenuItem = new JMenuItem(JEU);
        modifierJeuMenuItem.addActionListener(gestionListener);
        modifierRunMenuItem = new JMenuItem(RUN);
        modifierRunMenuItem.addActionListener(gestionListener);
        modifierLiveMenuItem = new JMenuItem(LIVE);
        modifierLiveMenuItem.addActionListener(gestionListener);
        //SOUS MENU SUPPRIMER
        supprimerSousMenu = new JMenu(SUPPRIMER);
        supprimerPlateformeMenuItem = new JMenuItem(PLATEFORME);
        supprimerPlateformeMenuItem.addActionListener(gestionListener);
        supprimerGenreMenuItem = new JMenuItem(GENRE);
        supprimerGenreMenuItem.addActionListener(gestionListener);
        supprimerStudioMenuItem = new JMenuItem(STUDIO);
        supprimerStudioMenuItem.addActionListener(gestionListener);
        supprimerJeuMenuItem = new JMenuItem(JEU);
        supprimerJeuMenuItem.addActionListener(gestionListener);
        supprimerRunMenuItem = new JMenuItem(RUN);
        supprimerRunMenuItem.addActionListener(gestionListener);
        supprimerLiveMenuItem = new JMenuItem(LIVE);
        supprimerLiveMenuItem.addActionListener(gestionListener);
        
        //MENU OUTILS
        outilsMenu = new JMenu(OUTILS);
        
        preferencesMenuItem = new JMenuItem(PREFERENCES);
        preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        preferencesMenuItem.addActionListener(listener);
        
        //MENU AFFICHAGE
        affichageMenu = new JMenu(AFFICHAGE);
        //SOUS MENU RACINE
        affichageRacineSousMenu = new JMenu(RACINE);
        AffichageRacineListener listenerRacine = new AffichageRacineListener();
        
        //RADIO BUTTONS RACINE
        plateformesAffichageRacineMenuItem = new JRadioButtonMenuItem(PLATEFORMES);
        plateformesAffichageRacineMenuItem.addItemListener(listenerRacine);
        
        genresAffichageRacineMenuItem = new JRadioButtonMenuItem(GENRES);
        genresAffichageRacineMenuItem.addItemListener(listenerRacine);
        
        studiosAffichageRacineMenuItem = new JRadioButtonMenuItem(STUDIOS);
        studiosAffichageRacineMenuItem.addItemListener(listenerRacine);
        
        jeuxAffichageRacineMenuItem = new JRadioButtonMenuItem(JEUX);
        jeuxAffichageRacineMenuItem.addItemListener(listenerRacine);
        
        //BUTTONGROUP AFFICHAGE RACINE
        racineAffichageGroup = new ButtonGroup();
        racineAffichageGroup.add(plateformesAffichageRacineMenuItem);
        racineAffichageGroup.add(genresAffichageRacineMenuItem);
        racineAffichageGroup.add(studiosAffichageRacineMenuItem);
        racineAffichageGroup.add(jeuxAffichageRacineMenuItem);
        racineAffichageGroup.clearSelection();
        switch (preferences.getAffichageRacine()) {
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
            default:
                jeuxAffichageRacineMenuItem.setSelected(true);
        }
        
        //SOUS MENU AFFICHAGE TREENODES
        affichageTreeNodesSousMenu = new JMenu(AFFICHER);
        AffichageTreeNodesListener listenerTreeNodes = new AffichageTreeNodesListener();
        //CHECKBOXES AFFICHAGE TREENODES
        jeuxAffichageTreeNodesMenuItem = new JCheckBoxMenuItem(JEUX);
        jeuxAffichageTreeNodesMenuItem.addItemListener(listenerTreeNodes);
        jeuxAffichageTreeNodesMenuItem.setSelected(preferences.getAffichageJeux());
        
        runsAffichageTreeNodesMenuItem = new JCheckBoxMenuItem(RUNS);
        runsAffichageTreeNodesMenuItem.setSelected(preferences.getAffichageRuns());
        runsAffichageTreeNodesMenuItem.addItemListener(listenerTreeNodes);
        
        livesAffichageTreeNodesMenuItem = new JCheckBoxMenuItem(LIVES);
        livesAffichageTreeNodesMenuItem.setSelected(preferences.getAffichageLives());
        livesAffichageTreeNodesMenuItem.addItemListener(listenerTreeNodes);
        
        //SOUS MENU AFFICHAGE TEMPS
        affichageTempsSousMenu = new JMenu(TEMPS);
        AffichageTempsListener listenerTemps = new AffichageTempsListener();
        //RADIO BUTTONS TEMPS
        heuresAffichageTempsMenuItem = new JRadioButtonMenuItem(HEURES);
        heuresAffichageTempsMenuItem.addItemListener(listenerTemps);
        minutesAffichageTempsMenuItem = new JRadioButtonMenuItem(MINUTES);
        minutesAffichageTempsMenuItem.addItemListener(listenerTemps);
        //BUTTONGROUP TEMPS
        tempsAffichageGroup = new ButtonGroup();
        tempsAffichageGroup.add(heuresAffichageTempsMenuItem);
        tempsAffichageGroup.add(minutesAffichageTempsMenuItem);
        tempsAffichageGroup.clearSelection();
        heuresAffichageTempsMenuItem.setSelected(true);
    }
    private void initPopupMenu() {
        popupMenu = new JPopupMenu();
        
        //SOUS MENU AJOUTER
        ajouterSousPopupMenu = new JMenu(AJOUTER);
        ajouterPlateformePopupMenuItem = new JMenuItem(PLATEFORME);
        ajouterGenrePopupMenuItem = new JMenuItem(GENRE);
        ajouterStudioPopupMenuItem = new JMenuItem(STUDIO);
        ajouterJeuPopupMenuItem = new JMenuItem(JEU);
        ajouterRunPopupMenuItem = new JMenuItem(RUN);
        ajouterLivePopupMenuItem = new JMenuItem(LIVE);
        
        //SOUS MENU MODIFIER
        modifierSousPopupMenu = new JMenu(MODIFIER);
        modifierPlateformePopupMenuItem = new JMenuItem(PLATEFORME);
        modifierGenrePopupMenuItem = new JMenuItem(GENRE);
        modifierStudioPopupMenuItem = new JMenuItem(STUDIO);
        modifierJeuPopupMenuItem = new JMenuItem(JEU);
        modifierRunPopupMenuItem = new JMenuItem(RUN);
        modifierLivePopupMenuItem = new JMenuItem(LIVE);
        
        //SOUS MENU SUPPRIMER
        supprimerSousPopupMenu = new JMenu(SUPPRIMER);
        supprimerPlateformePopupMenuItem = new JMenuItem(PLATEFORME);
        supprimerGenrePopupMenuItem = new JMenuItem(GENRE);
        supprimerStudioPopupMenuItem = new JMenuItem(STUDIO);
        supprimerJeuPopupMenuItem = new JMenuItem(JEU);
        supprimerRunPopupMenuItem = new JMenuItem(RUN);
        supprimerLivePopupMenuItem = new JMenuItem(LIVE);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //OBSERVER
    @Override
    public void clear(TypeRacine type) {
        rootTree.removeAllChildren();
        switch (type) {
            case PLATEFORMES:
                rootTree.setUserObject(PLATEFORMES);
                break;
            case GENRES:
                rootTree.setUserObject(GENRES);
                break;
            case STUDIOS:
                rootTree.setUserObject(STUDIOS);
                break;
            case JEUX:
                rootTree.setUserObject(JEUX);
                break;
            default:
                rootTree.setUserObject(JEUX);
        }
        ((DefaultTreeModel) treeJeux.getModel()).reload(rootTree);
        panelGraph.setChart(null);
        textPaneInfos.setText(EMPTY);
    }
    
    @Override
    public void addPlateforme(Plateforme plateforme) {
        SortableTreeNode nodePlateforme = new SortableTreeNode(plateforme, true);
        mapPlateformes.put(plateforme.getID(), nodePlateforme);
        plateformePanel.addItem(plateforme.getID());
        if (typeRacine.equals(TypeRacine.PLATEFORMES)) {
            rootTree.add(nodePlateforme);
            rootTree.sort();
        }
    }
    
    @Override
    public void addGenre(Genre genre) {
        SortableTreeNode nodeGenre = new SortableTreeNode(genre, true);
        mapGenres.put(genre.getID(), nodeGenre);
        if (typeRacine.equals(TypeRacine.GENRES)) {
            rootTree.add(nodeGenre);
            rootTree.sort();
        }
    }
    
    @Override
    public void addStudio(Studio studio) {
        SortableTreeNode nodeStudio = new SortableTreeNode(studio, true);
        mapStudios.put(studio.getID(), nodeStudio);
        if (typeRacine.equals(TypeRacine.STUDIOS)) {
            rootTree.add(nodeStudio);
            rootTree.sort();
        }
    }
    
    @Override
    public void addJeu(Jeu jeu) {
        if (typeRacine.equals(TypeRacine.PLATEFORMES)) {
            Set<Entry<Long, Plateforme>> setPlateformes = jeu.getPlateformes().entrySet();
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
//            arrayJeu.add(nodeJeu);
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
//            mapJeux.getOrDefault(jeu.getID(),new ArrayList()).add(nodeJeu);
            for (Entry<Long, Plateforme> entryPlateforme : setPlateformes) {
                SortableTreeNode nodeJeu = new SortableTreeNode(jeu, true);
                mapJeux.get(jeu.getID()).add(nodeJeu);
                mapPlateformes.get(entryPlateforme.getKey()).add(nodeJeu);
                mapPlateformes.get(entryPlateforme.getKey()).sort();
            }
        }
        if (typeRacine.equals(TypeRacine.GENRES)) {
            Set<Entry<Long, Genre>> setGenres = jeu.getGenres().entrySet();
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
//            arrayJeu.add(nodeJeu);
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
//            mapJeux.getOrDefault(jeu.getID(),new ArrayList()).add(nodeJeu);
            for (Entry<Long, Genre> entryGenre : setGenres) {
                SortableTreeNode nodeJeu = new SortableTreeNode(jeu, true);
                mapJeux.get(jeu.getID()).add(nodeJeu);
                mapGenres.get(entryGenre.getKey()).add(nodeJeu);
                mapGenres.get(entryGenre.getKey()).sort();
            }
        }
        if (typeRacine.equals(TypeRacine.STUDIOS)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu, true);
            mapStudios.get(jeu.getStudio().getID()).add(nodeJeu);
        }
        if (typeRacine.equals(TypeRacine.JEUX)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu, true);
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
            mapJeux.get(jeu.getID()).add(nodeJeu);
            rootTree.add(nodeJeu);
            rootTree.sort();
        }
    }
    
    @Override
    public void addRun(long idJeu, Run run) {
        ArrayList<SortableTreeNode> arrayJeu = mapJeux.get(idJeu);
        mapRuns.putIfAbsent(run.getID(), new ArrayList());
        for (SortableTreeNode nodeJeu : arrayJeu) {
            SortableTreeNode nodeRun = new SortableTreeNode(run, true);
            mapRuns.get(run.getID()).add(nodeRun);
            nodeJeu.add(nodeRun);
            nodeJeu.sort();
        }
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
    public void addLive(long idRun, Live live) {
        ArrayList<SortableTreeNode> arrayRun = mapRuns.getOrDefault(idRun,new ArrayList());
        for (SortableTreeNode nodeRun : arrayRun) {
            SortableTreeNode nodeLive = new SortableTreeNode(live, true);
            nodeRun.add(nodeLive);
            nodeRun.sort();
        }
        mapRuns.putIfAbsent(idRun, arrayRun);
//        mapRuns.get(idRun).add(nodeLive);
//        mapRuns.get(idRun).sort();
    }
    
    @Override
    public void updateDataset(String titre, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart("Morts lives " + titre, "Date live", EMPTY, dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.addSubtitle(new TextTitle(unit.equals(TimeUnit.HOURS) ? TEMPS + " en heures" : TEMPS +" en minutes"));
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setBaseShapesVisible(true);
        panelGraph.setChart(chart);
    }
    
    @Override
    public void fillPlateforme(long idPlateforme, String nomPlateforme) {
        plateformePanel.setNom(nomPlateforme);
    }
    
    
    //MÉTHODES GRAPHIQUES
    private void ajouterPlateformeInputs() {
        plateformePanel.clearFields();
        JPanel inputs = new JPanel(new BorderLayout());
        JPanel nomPanel = plateformePanel.getNomPanel();
        inputs.add(nomPanel);
        JTextPane nomTextPane = plateformePanel.getNomTextPane();
        String[] options = {AJOUTER, ANNULER};
        int res = JOptionPane.showOptionDialog(this, inputs, "Ajouter plateforme", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            
        }
    }
    
    private void modifierPlateformeInputs() {
//        plateformePanel.setComponenents();
        plateformePanel.clearFields();
        JPanel inputs = new JPanel(new GridLayout(2, 1));
        JPanel idPanel = plateformePanel.getIDPanel();
        JPanel nomPanel = plateformePanel.getNomPanel();
//        inputs.add(plateformePanel);
        inputs.add(idPanel);
        inputs.add(nomPanel);
        String[] options = {MODIFIER, ANNULER};
        int res = JOptionPane.showOptionDialog(this, inputs, "Ajouter plateforme", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            
        }
    }
    
    
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
                    message = CREER;
                }
                else {
                    dialogType = JFileChooser.OPEN_DIALOG;
                    message = OUVRIR;
                }
                FileChooser fileBDD = new FileChooser(BDD, EMPTY, JFileChooser.FILES_ONLY, dialogType);
                FileNameExtensionFilter fileFilter;
                if (src.equals(nouveauMenuItem)) {
                    fileFilter = new FileNameExtensionFilter(BDD + " " + EXTENSIONS_BDD, "accdb", "mdb", "db", "sdb", "sqlite", "db2", "s2db", "sqlite2", "sl2", "db3", "s3db", "sqlite3", "sl3");
                }
                else {
                    fileFilter = new FileNameExtensionFilter(BDD + " " + EXTENSIONS_BDD, "accdb", "mdb", "kexi", "db", "sdb", "sqlite", "db2", "s2db", "sqlite2", "sl2", "db3", "s3db", "sqlite3", "sl3");
                }
                fileBDD.setFilter(fileFilter);
                int res = JOptionPane.showConfirmDialog(null, fileBDD, message + " la base de données", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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
            if (src.equals(ajouterPlateformeMenuItem)) {
                ajouterPlateformeInputs();
            }
            if (src.equals(ajouterGenreMenuItem)) {
                
            }
            if (src.equals(ajouterStudioMenuItem)) {
                
            }
            if (src.equals(ajouterJeuMenuItem)) {
                
            }
            if (src.equals(ajouterRunMenuItem)) {
                
            }
            if (src.equals(ajouterLiveMenuItem)) {
                
            }
            //GESTION MODIFIER
            if (src.equals(modifierPlateformeMenuItem)) {
                modifierPlateformeInputs();
            }
            if (src.equals(modifierGenreMenuItem)) {
                
            }
            if (src.equals(modifierStudioMenuItem)) {
                
            }
            if (src.equals(modifierJeuMenuItem)) {
                
            }
            if (src.equals(modifierRunMenuItem)) {
                
            }
            if (src.equals(modifierLiveMenuItem)) {
                
            }
            //GESTION SUPPRIMER
            if (src.equals(supprimerPlateformeMenuItem)) {
                
            }
            if (src.equals(supprimerGenreMenuItem)) {
                
            }
            if (src.equals(supprimerStudioMenuItem)) {
                
            }
            if (src.equals(supprimerJeuMenuItem)) {
                
            }
            if (src.equals(supprimerRunMenuItem)) {
                
            }
            if (src.equals(supprimerLiveMenuItem)) {
                
            }
        }
        
    }

    class GestionPopupMenuListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            
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
            if (paths != null) {
                String informations = EMPTY;
                ArrayList<FillDataset> nodes = new ArrayList();
                String titreGraph = null;
                if (paths.length > 1) {
                    titreGraph = "sélection multiple";
                }
                if (paths.length == 1) {
                    Object node = paths[0].getLastPathComponent();
                    if (node instanceof SortableTreeNode) {
                        if (((SortableTreeNode) node).isInformations()) {
                            titreGraph = ((Informations) node).getObjectFillDataset().
                                    getTitreDataset();
                        }
                    }
                }
                for (TreePath path : paths) {
                    Object node = path.getLastPathComponent();
                    for (TreePath path1 : paths) {
                        if (path != path1 && path.isDescendant(path1)) {
                            treeJeux.clearSelection();
                            panelGraph.setChart(null);
                            treeJeux.setSelectionPath(e.getNewLeadSelectionPath());
                            return;
                        }
                    }
                    if (node instanceof SortableTreeNode && ((SortableTreeNode) node).isInformations()) {
                        informations += ((Informations) node).getInformations() + "\n";
                        nodes.add(((Informations) node).getObjectFillDataset());
                    }
                    else {
                        panelGraph.setChart(null);
                        textPaneInfos.setText(EMPTY);
                        return;
                    }
                }
                controler.createDataset(titreGraph, nodes);
                textPaneInfos.setText(informations);
            }
        }
        
    }
    
    class AffichageRacineListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            if (selected) {
                Object item = e.getItem();
                if (item.equals(plateformesAffichageRacineMenuItem)) {
                    typeRacine = TypeRacine.PLATEFORMES;
                }
                if (item.equals(genresAffichageRacineMenuItem)) {
                    typeRacine = TypeRacine.GENRES;
                }
                if (item.equals(studiosAffichageRacineMenuItem)) {
                    typeRacine = TypeRacine.STUDIOS;
                }
                if (item.equals(jeuxAffichageRacineMenuItem)) {
                    typeRacine = TypeRacine.JEUX;
                }
                controler.setRacine(typeRacine);
            }
        }
        
    }
    
    class AffichageTreeNodesListener implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            Object item = e.getItem();
            System.out.println("lol");
            if (item.equals(jeuxAffichageTreeNodesMenuItem)) {
                affichageJeuxBool = e.getStateChange() == ItemEvent.SELECTED;
            }
            if (item.equals(runsAffichageTreeNodesMenuItem)) {
                affichageRunsBool = e.getStateChange() == ItemEvent.SELECTED;
            }
            if (item.equals(livesAffichageTreeNodesMenuItem)) {
                affichageLivesBool = e.getStateChange() == ItemEvent.SELECTED;
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
                    unit = TimeUnit.HOURS;
                    controler.setTimeUnit(TimeUnit.HOURS);
                }
                if (item.equals(minutesAffichageTempsMenuItem)) {
                    unit = TimeUnit.MINUTES;
                    controler.setTimeUnit(TimeUnit.MINUTES);
                }
            }
        }
        
    }
    
}
