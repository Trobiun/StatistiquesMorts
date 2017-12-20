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
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
import statsmorts.classes.TypeGroup;
import statsmorts.controler.StatsMortsControler;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;
import statsmorts.preferences.PreferencesDialog;
import statsmorts.preferences.Temps;

/**
 *
 * @author Robin
 */
public class Fenetre extends JFrame implements Observer {
    
    
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
    
    private Temps temps;
    private TypeGroup typeGroup;
    
    //CLASSES PERSO
    //ENSEMBLE DE PANELS POUR LES ENTRÉES UTILISATEUR
    private PlateformePanels plateformePanels;
    private GenrePanels genrePanels;
    private StudioPanels studioPanels;
    private JeuPanels jeuPanels;
    private RunPanels runPanels;
    
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
        panelGraph = new ChartPanel(null);
    }
    private void initPersoPanels() {
        plateformePanels = new PlateformePanels(controler);
        genrePanels = new GenrePanels(controler);
        studioPanels = new StudioPanels(controler);
        jeuPanels = new JeuPanels(controler);
        runPanels = new RunPanels(controler);
    }
    private void initTree() {
        rootTree = new SortableTreeNode(TexteConstantes.JEUX, false);
        treeJeux = new JTree(rootTree);
        treeJeux.addTreeSelectionListener(new TreeListener());
        treeJeux.addMouseListener(new TreeMouseListener());
        mapPlateformes = new HashMap();
        mapGenres = new HashMap();
        mapStudios = new HashMap();
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
        
        jeuxAffichageRacineMenuItem = new JRadioButtonMenuItem(TexteConstantes.JEUX);
        jeuxAffichageRacineMenuItem.addItemListener(listenerRacine);
        
        //BUTTONGROUP AFFICHAGE RACINE
        racineAffichageGroup = new ButtonGroup();
        racineAffichageGroup.add(plateformesAffichageRacineMenuItem);
        racineAffichageGroup.add(genresAffichageRacineMenuItem);
        racineAffichageGroup.add(studiosAffichageRacineMenuItem);
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
        ajouterJeuPopupMenuItem = new JMenuItem(TexteConstantes.JEU);
        ajouterRunPopupMenuItem = new JMenuItem(TexteConstantes.RUN);
        ajouterLivePopupMenuItem = new JMenuItem(TexteConstantes.LIVE);
        
        //SOUS MENU MODIFIER
        modifierSousPopupMenu = new JMenu(TexteConstantes.MODIFIER);
        modifierPlateformePopupMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        modifierGenrePopupMenuItem = new JMenuItem(TexteConstantes.GENRE);
        modifierStudioPopupMenuItem = new JMenuItem(TexteConstantes.STUDIO);
        modifierJeuPopupMenuItem = new JMenuItem(TexteConstantes.JEU);
        modifierRunPopupMenuItem = new JMenuItem(TexteConstantes.RUN);
        modifierLivePopupMenuItem = new JMenuItem(TexteConstantes.LIVE);
        
        //SOUS MENU SUPPRIMER
        supprimerSousPopupMenu = new JMenu(TexteConstantes.SUPPRIMER);
        supprimerPlateformePopupMenuItem = new JMenuItem(TexteConstantes.PLATEFORME);
        supprimerGenrePopupMenuItem = new JMenuItem(TexteConstantes.GENRE);
        supprimerStudioPopupMenuItem = new JMenuItem(TexteConstantes.STUDIO);
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
        plateformePanels.removeAllItems();
        genrePanels.removeAllItems();
        studioPanels.removeAllItems();
        jeuPanels.clearLists();
        jeuPanels.removeAllItems();
        runPanels.removeAllItems();
        runPanels.removeAllJeux();
        rootTree.setUserObject(type.getNom());
        ((DefaultTreeModel) treeJeux.getModel()).reload(rootTree);
        panelGraph.setChart(null);
        textPaneInfos.setText(TexteConstantes.EMPTY);
    }
    
    @Override
    public void addPlateforme(Plateforme plateforme) {
        SortableTreeNode nodePlateforme = new SortableTreeNode(plateforme, true);
        mapPlateformes.put(plateforme.getID(), nodePlateforme);
        plateformePanels.addItem(plateforme.getID());
        jeuPanels.addPlateforme(plateforme);
        if (typeGroup.equals(TypeGroup.PLATEFORMES)) {
            rootTree.add(nodePlateforme);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
            //treeJeux.updateUI();
        }
    }
    
    @Override
    public void addGenre(Genre genre) {
        SortableTreeNode nodeGenre = new SortableTreeNode(genre, true);
        mapGenres.put(genre.getID(), nodeGenre);
        genrePanels.addItem(genre.getID());
        jeuPanels.addGenre(genre);
        if (typeGroup.equals(TypeGroup.GENRES)) {
            rootTree.add(nodeGenre);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
    }
    
    @Override
    public void addStudio(Studio studio) {
        SortableTreeNode nodeStudio = new SortableTreeNode(studio, true);
        mapStudios.put(studio.getID(), nodeStudio);
        studioPanels.addItem(studio.getID());
        jeuPanels.addStudio(studio);
        if (typeGroup.equals(TypeGroup.STUDIOS)) {
            rootTree.add(nodeStudio);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
        }
    }
    
    @Override
    public void addJeu(Jeu jeu) {
        jeuPanels.addItem(jeu.getID());
        runPanels.addJeu(jeu.getID());
        if (typeGroup.equals(TypeGroup.PLATEFORMES)) {
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
        if (typeGroup.equals(TypeGroup.GENRES)) {
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
        if (typeGroup.equals(TypeGroup.STUDIOS)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu, true);
            mapJeux.putIfAbsent(jeu.getID(),new ArrayList());
            mapJeux.get(jeu.getID()).add(nodeJeu);
            if (null != jeu.getStudio()) {
                mapStudios.get(jeu.getStudio().getID()).add(nodeJeu);
            }
        }
        if (typeGroup.equals(TypeGroup.JEUX)) {
            SortableTreeNode nodeJeu = new SortableTreeNode(jeu, true);
//            ArrayList<SortableTreeNode> arrayJeu = mapJeux.getOrDefault(jeu, new ArrayList());
            mapJeux.putIfAbsent(jeu.getID(), new ArrayList());
            mapJeux.get(jeu.getID()).add(nodeJeu);
            rootTree.add(nodeJeu);
            rootTree.sort();
            ((DefaultTreeModel)treeJeux.getModel()).reload();
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
    public void removePlateforme(long idPlateforme) {
        if (typeGroup.equals(TypeGroup.PLATEFORMES)) {
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapPlateformes.get(idPlateforme));
        }
        mapPlateformes.remove(idPlateforme);
        plateformePanels.removeItem(idPlateforme);
        jeuPanels.removePlateforme(idPlateforme);
    }
    
    @Override
    public void removeGenre(long idGenre) {
        if (typeGroup.equals(TypeGroup.GENRES)) {
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapGenres.get(idGenre));
        }
        mapPlateformes.remove(idGenre);
        plateformePanels.removeItem(idGenre);
        jeuPanels.removeGenre(idGenre);
    }
    
    @Override
    public void removeStudio(long idStudio) {
        if (typeGroup.equals(TypeGroup.STUDIOS)) {
            ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(mapStudios.get(idStudio));
        }
        mapStudios.remove(idStudio);
        studioPanels.removeItem(idStudio);
        jeuPanels.removeStudio(idStudio);
    }
    
    @Override
    public void removeJeu(long idJeu) {
        if (typeGroup.equals(TypeGroup.JEUX)) {
            ArrayList<SortableTreeNode> nodesJeu = mapJeux.get(idJeu);
            for (SortableTreeNode nodeJeu : nodesJeu) {
                ((DefaultTreeModel)treeJeux.getModel()).removeNodeFromParent(nodeJeu);
            }
        }
        mapJeux.remove(idJeu);
        jeuPanels.removeItem(idJeu);
    }
    
    @Override
    public void removeRun(long idRun) {
        
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
        panelGraph.setChart(chart);
    }
    
    @Override
    public void fillPlateforme(long idPlateforme, String nomPlateforme) {
        plateformePanels.setNom(nomPlateforme);
    }
    
    @Override
    public void fillGenre(long idGenre, String nomGenre) {
        genrePanels.setNom(nomGenre);
    }
    
    @Override
    public void fillStudio(long idStudio, String nomStudio) {
        studioPanels.setNom(nomStudio);
    }
    
    @Override
    public void fillJeu(long idJeu, String titreJeu, int anneeSortie, Long[] listPlateformesID, Long[] listGenresID, long idStudio) {
        jeuPanels.setNom(titreJeu);
        jeuPanels.setAnneeSortie(anneeSortie);
        jeuPanels.setPlateformesSelection(listPlateformesID);
        jeuPanels.setGenresSelection(listGenresID);
        jeuPanels.setStudioSelection(idStudio);
    }
    
    @Override
    public void fillRun(long idRun, String titreRun, long idJeu, String titreJeu) {
        runPanels.setNom(titreRun);
        runPanels.setIDJeu(idJeu);
    }
    
    @Override
    public void fillRunJeu(long idJeu, String titreJeu) {
        runPanels.setTitreJeu(titreJeu);
    }
    
    @Override
    public void fillLive(long idLive) {
        
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
            default :
                inputs = null;
        }
        if (null != inputs) {
            inputs.setIDPanelVisible(!mode.equals(ModeGestion.AJOUTER));
            inputs.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
            if (mode.equals(ModeGestion.AJOUTER)) {
//                inputs.setIDPanelVisible(false);
                inputs.clearFields(true,true);
            }
            else {
                boolean editable = mode.equals(ModeGestion.MODIFIER);
//                inputs.setIDPanelVisible(true);
                inputs.clearFields(editable,false);
            }
            
            String[] options = { mode.getAction(), TexteConstantes.ANNULER };
            int res = JOptionPane.showOptionDialog(this, inputs, mode.getAction() + " " + typeInputs.getNom(),
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (res == JOptionPane.YES_OPTION) {
                switch (mode) {
                    case AJOUTER:
                        controler.ajouterBasicInputs(typeInputs, inputs.getNom());
                        break;
                    case MODIFIER:
                        controler.modifierBasicInputs(typeInputs, inputs.getSelectedID(), inputs.getNom());
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
        jeuPanels.setIDPanelVisible(!mode.equals(ModeGestion.AJOUTER));
        jeuPanels.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
        if (mode.equals(ModeGestion.AJOUTER)) {
//            jeuPanels.setIDPanelVisible(false);
            jeuPanels.clearFields(true,true);
        }
        else {
            boolean editable = mode.equals(ModeGestion.MODIFIER);
//            jeuPanels.setIDPanelVisible(true);
            jeuPanels.clearFields(editable,false);
        }
        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
        int res = JOptionPane.showOptionDialog(this, jeuPanels, mode.getAction()
                + " " + TexteConstantes.JEU.toLowerCase(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            long idJeu = jeuPanels.getSelectedID();
            String titreJeu = jeuPanels.getNom();
            int anneeSortie = jeuPanels.getAnneeSortie();
            List<Long> listPlateformes = jeuPanels.getPlateformes();
            List<Long> listGenres = jeuPanels.getGenres();
            long idStudio = jeuPanels.getStudioID();
            switch (mode) {
                case AJOUTER :
                    controler.ajouterJeu(titreJeu, anneeSortie , listPlateformes, listGenres, idStudio);
                    break;
                case MODIFIER :
                    controler.modifierJeu(idJeu, titreJeu, anneeSortie, listPlateformes, listGenres, idStudio);
                    break;
                case SUPPRIMER :
                    controler.supprimerJeu(idJeu);
                    break;
                default :
            }
        }
    }
    
    private void gererRunsInputs(ModeGestion mode) {
        runPanels.setIDPanelVisible(!mode.equals(ModeGestion.AJOUTER));
        runPanels.setResetButtonVisible(mode.equals(ModeGestion.MODIFIER));
        if (mode.equals(ModeGestion.AJOUTER)) {
//            jeuPanels.setIDPanelVisible(false);
            runPanels.clearFields(true,true);
        }
        else {
            boolean editable = mode.equals(ModeGestion.MODIFIER);
//            jeuPanels.setIDPanelVisible(true);
            runPanels.clearFields(editable,false);
        }
        String[] options = { mode.getAction(), TexteConstantes.ANNULER };
        int res = JOptionPane.showOptionDialog(this, runPanels, mode.getAction()
                + " " + TexteConstantes.RUN.toLowerCase(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            long idRun = runPanels.getSelectedID();
            String titreRun = runPanels.getNom();
            long idJeu = runPanels.getSelectedJeuID();
            switch (mode) {
                case AJOUTER :
                    controler.ajouterRun(idRun, titreRun, idJeu);
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
    
//    
//    private void gererPlateformeInputs(ModeGestion mode) {
//        if (mode.equals(ModeGestion.AJOUTER)) {
//            plateformePanels.setIDPanelVisible(false);
//        }
//        else {
//            plateformePanels.setIDPanelVisible(true);
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
//            genrePanels.setIDPanelVisible(false);
//        }
//        else {
//            genrePanels.setIDPanelVisible(true);
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
//            studioPanels.setIDPanelVisible(false);
//        }
//        else {
//            studioPanels.setIDPanelVisible(true);
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
            if (src.equals(ajouterPlateformeMenuItem)) {
                gererBasicInputs(ModeGestion.AJOUTER, TypeBasicInputs.PLATEFORMES);
            }
            if (src.equals(ajouterGenreMenuItem)) {
                gererBasicInputs(ModeGestion.AJOUTER, TypeBasicInputs.GENRES);
            }
            if (src.equals(ajouterStudioMenuItem)) {
                gererBasicInputs(ModeGestion.AJOUTER, TypeBasicInputs.STUDIOS);
            }
            if (src.equals(ajouterJeuMenuItem)) {
                gererJeuxInputs(ModeGestion.AJOUTER);
            }
            if (src.equals(ajouterRunMenuItem)) {
                gererRunsInputs(ModeGestion.AJOUTER);
            }
            if (src.equals(ajouterLiveMenuItem)) {
                
            }
            //GESTION MODIFIER
            if (src.equals(modifierPlateformeMenuItem)) {
                gererBasicInputs(ModeGestion.MODIFIER, TypeBasicInputs.PLATEFORMES);
            }
            if (src.equals(modifierGenreMenuItem)) {
                gererBasicInputs(ModeGestion.MODIFIER, TypeBasicInputs.GENRES);
            }
            if (src.equals(modifierStudioMenuItem)) {
                gererBasicInputs(ModeGestion.MODIFIER, TypeBasicInputs.STUDIOS);
            }
            if (src.equals(modifierJeuMenuItem)) {
                gererJeuxInputs(ModeGestion.MODIFIER);
            }
            if (src.equals(modifierRunMenuItem)) {
                gererRunsInputs(ModeGestion.MODIFIER);
            }
            if (src.equals(modifierLiveMenuItem)) {
                
            }
            //GESTION SUPPRIMER
            if (src.equals(supprimerPlateformeMenuItem)) {
                gererBasicInputs(ModeGestion.SUPPRIMER, TypeBasicInputs.PLATEFORMES);
            }
            if (src.equals(supprimerGenreMenuItem)) {
                gererBasicInputs(ModeGestion.SUPPRIMER, TypeBasicInputs.GENRES);
            }
            if (src.equals(supprimerStudioMenuItem)) {
                gererBasicInputs(ModeGestion.SUPPRIMER, TypeBasicInputs.STUDIOS);
            }
            if (src.equals(supprimerJeuMenuItem)) {
                gererJeuxInputs(ModeGestion.SUPPRIMER);
            }
            if (src.equals(supprimerRunMenuItem)) {
                gererRunsInputs(ModeGestion.SUPPRIMER);
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
                    if (node instanceof SortableTreeNode && ((SortableTreeNode)node).isInformations()) {
                        informations += ((Informations) node).getInformations() + TexteConstantes.NEW_LINE;
                        nodes.add(((Informations)node).getObjectFillDataset());
                    }
                    else {
                        panelGraph.setChart(null);
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
                    controler.setTimeUnit(TimeUnit.HOURS);
                }
                if (item.equals(minutesAffichageTempsMenuItem)) {
                    temps = Temps.MINUTES;
                    controler.setTimeUnit(TimeUnit.MINUTES);
                }
            }
        }
        
    }
    
}
