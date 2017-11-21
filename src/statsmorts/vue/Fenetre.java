/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Run;
import statsmorts.controler.StatsMortsControler;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;
import statsmorts.preferences.PreferencesDialog;

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
    private DefaultMutableTreeNode rootTreeJeux;
    private HashMap<Long,JeuTreeNode> mapJeux;
    private HashMap<Long,DefaultMutableTreeNode> mapRuns;
    //TEXTPANE
    private JTextPane textPaneInfos;
    
    //MENUS
    private JMenuBar menuBar;
    private JMenu fichierMenu;
    private JMenuItem nouveauMenuItem;
    private JMenuItem ouvrirMenuItem;
//    private JMenuItem actualiser;
    private JMenuItem quitterMenuItem;
    private JMenu outilsMenu;
    private JMenuItem preferencesMenuItem;
    private JMenu affichageMenu;
    private JCheckBoxMenuItem plateformesAffichageMenuItem;
    private JRadioButtonMenuItem noStudiosGenresAffichageMenuItem;
    private JRadioButtonMenuItem studiosAffichageMenuItem;
    private JRadioButtonMenuItem genresAffichageMenuItem;
    private ButtonGroup studiosGenresAffichageGroup;
    private JCheckBoxMenuItem jeuxAffichageMenuItem;
    private JCheckBoxMenuItem runsAffichageMenuItem;
    private JCheckBoxMenuItem livesAffichageMenuItem;
    private JRadioButtonMenuItem heuresAffichageMenuItem;
    private JRadioButtonMenuItem minutesAffichageMenuItem;
    private ButtonGroup tempsAffichageGroup;
    
    //CLASSES PERSO
    private final Preferences preferences;
    private final PreferencesDialog prefsDialog;
    private final StatsMortsControler controler;
    
    
    //CONSTRUCTEUR
    public Fenetre(String titre, StatsMortsControler controler, Preferences preferences) {
        super(titre);
        java.net.URL iconURL = Fenetre.class.getResource("icon.png");
        if (iconURL != null) {
            super.setIconImage(new ImageIcon(iconURL).getImage());
        }
        super.setLayout(new BorderLayout());
        super.setSize(850,700);
        super.setLocationRelativeTo(null);
        
        WindowListener exitListener = new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                controler.deconnecter();
                System.exit(0);
            }
        };
        super.addWindowListener(exitListener);
        
        this.controler = controler;
        this.preferences = preferences;
        this.prefsDialog = new PreferencesDialog(this,"Préférences",false,null,this.preferences);
        
        initAll();
        setComponents();
        
        super.setJMenuBar(menuBar);
        super.add(splitVerticalGlobal);
        
        super.setVisible(true);
        splitHorizontalInGlobal.setDividerLocation(0.5);
        splitVerticalGlobal.setDividerLocation(0.5);
    }
    
    private void setComponents() {
        panelArbre.add(treeJeux);
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
    
    private void initAll() {
        initSplitPanes();
        initPanels();
        initTextPane();
        initTree();
        initJMenuBar();
    }
    private void initSplitPanes() {
        splitVerticalGlobal = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
        splitVerticalGlobal.setOneTouchExpandable(true);
        splitHorizontalInGlobal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
        splitHorizontalInGlobal.setOneTouchExpandable(true);
    }
    private void initPanels() {
        panelArbre = new JPanel(new BorderLayout());
        panelInfos = new JPanel(new BorderLayout());
        panelGraph = new ChartPanel(null);
    }
    private void initTree() {
        rootTreeJeux = new DefaultMutableTreeNode("Jeux");
        treeJeux = new JTree(rootTreeJeux);
        treeJeux.addTreeSelectionListener(new TreeListener());
        mapJeux = new HashMap();
        mapRuns = new HashMap();
    }
    private void initTextPane() {
        textPaneInfos = new JTextPane();
        textPaneInfos.setText("Informations");
        textPaneInfos.setEditable(false);
    }
    private void initJMenuBar() {
        menuBar = new JMenuBar();
        MenuListener listener = new MenuListener();
        
        //MENU FICHIER
        fichierMenu = new JMenu("Fichier");
        
        nouveauMenuItem = new JMenuItem("Nouveau");
        nouveauMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        nouveauMenuItem.addActionListener(listener);
        
        ouvrirMenuItem = new JMenuItem("Ouvrir");
        ouvrirMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        ouvrirMenuItem.addActionListener(listener);
        
        quitterMenuItem = new JMenuItem("Quitter");
        quitterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        quitterMenuItem.addActionListener(listener);
        //AJOUT DES MENUITEMS
        fichierMenu.add(nouveauMenuItem);
        fichierMenu.add(ouvrirMenuItem);
        fichierMenu.add(new JSeparator());
        fichierMenu.add(quitterMenuItem);
        
        
        //MENU OUTILS
        outilsMenu = new JMenu("Outils");
        
        preferencesMenuItem = new JMenuItem("Préférences");
        preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        preferencesMenuItem.addActionListener(listener);
        //AJOUT DU MENUITEM
        outilsMenu.add(preferencesMenuItem);
        
        
        //MENU AFFICHAGE
        affichageMenu = new JMenu("Affichage");
        
        plateformesAffichageMenuItem = new JCheckBoxMenuItem("Plateformes");
        
        //RADIO BUTTONS STUDIO/GENRE
        noStudiosGenresAffichageMenuItem = new JRadioButtonMenuItem("Aucun");
        studiosAffichageMenuItem = new JRadioButtonMenuItem("Studios");
        genresAffichageMenuItem = new JRadioButtonMenuItem("Genres");
        //BUTTONGROUP
        studiosGenresAffichageGroup = new ButtonGroup();
        studiosGenresAffichageGroup.add(noStudiosGenresAffichageMenuItem);
        studiosGenresAffichageGroup.add(studiosAffichageMenuItem);
        studiosGenresAffichageGroup.add(genresAffichageMenuItem);
        studiosGenresAffichageGroup.clearSelection();
        noStudiosGenresAffichageMenuItem.setSelected(true);
        
        
        jeuxAffichageMenuItem = new JCheckBoxMenuItem("Jeux");
        jeuxAffichageMenuItem.setSelected(true);
        runsAffichageMenuItem = new JCheckBoxMenuItem("Runs");
        runsAffichageMenuItem.setSelected(true);
        livesAffichageMenuItem = new JCheckBoxMenuItem("Lives");
        livesAffichageMenuItem.setSelected(true);
        //RADIO BUTTONS TEMPS
        heuresAffichageMenuItem = new JRadioButtonMenuItem("Heures");
        minutesAffichageMenuItem = new JRadioButtonMenuItem("Minutes");
        //BUTTONGROUP
        tempsAffichageGroup = new ButtonGroup();
        tempsAffichageGroup.add(heuresAffichageMenuItem);
        tempsAffichageGroup.add(minutesAffichageMenuItem);
        tempsAffichageGroup.clearSelection();
        heuresAffichageMenuItem.setSelected(true);
        
        
        //AJOUT DES MENUITEMS
        affichageMenu.add(plateformesAffichageMenuItem);
        affichageMenu.add(new JSeparator());
        affichageMenu.add(noStudiosGenresAffichageMenuItem);
        affichageMenu.add(studiosAffichageMenuItem);
        affichageMenu.add(genresAffichageMenuItem);
        affichageMenu.add(new JSeparator());
        affichageMenu.add(jeuxAffichageMenuItem);
        affichageMenu.add(runsAffichageMenuItem);
        affichageMenu.add(livesAffichageMenuItem);
        affichageMenu.add(new JSeparator());
        affichageMenu.add(heuresAffichageMenuItem);
        affichageMenu.add(minutesAffichageMenuItem);
        
        
        //AJOUT DES MENUS
        menuBar.add(fichierMenu);
        menuBar.add(outilsMenu);
        menuBar.add(affichageMenu);
    }

    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //OBSERVER
    @Override
    public void updateJeu(Jeu jeu) {
        JeuTreeNode nodeJeu = new JeuTreeNode(jeu);
        mapJeux.put(jeu.getID(),nodeJeu);
        rootTreeJeux.add(nodeJeu);
    }
    
    @Override
    public void updateRun(long idJeu, Run run) {
        RunTreeNode nodeRun = new RunTreeNode(run);
        mapJeux.get(idJeu).add(nodeRun);
        mapRuns.put(run.getID(), nodeRun);
    }
    
    @Override
    public void updateLive(long idJeu, long idRun, Live live) {
        LiveTreeNode nodeLive = new LiveTreeNode(live);
        mapRuns.get(idRun).add(nodeLive);
    }
    
    @Override
    public void updateDataset(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart("Morts Lives ", "Date live", "", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)chart.getCategoryPlot().getRenderer();
        renderer.setBaseShapesVisible(true);
        panelGraph.setChart(chart);
    }
    
    
    //LISTENERS
    class MenuListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(nouveauMenuItem) || src.equals(ouvrirMenuItem) ) {
                int dialogType;
                String message;
                if (src.equals(nouveauMenuItem)) {
                    dialogType = JFileChooser.SAVE_DIALOG;
                    message = "Créer";
                }
                else {
                    dialogType = JFileChooser.OPEN_DIALOG;
                    message = "Ouvrir";
                }
                FileChooser fileBDD = new FileChooser("Base de données","",JFileChooser.FILES_ONLY,dialogType);
                FileNameExtensionFilter fileFilter;
                if (src.equals(nouveauMenuItem)) {
                    fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
                }
                else {
                    fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.kexi,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","kexi","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
                }
                
                fileBDD.setFilter(fileFilter);
                int res = JOptionPane.showConfirmDialog(null,fileBDD,message + " la base de données",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    if (src.equals(nouveauMenuItem)) {
//                        controler.creerBDD(fileBDD.getPath());
                    }
                    else {
//                        controler.ouvrirBDD(fileBDD.getPath());
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
    
    class TreeListener implements TreeSelectionListener {
        
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            Object src = e.getSource();
            TreePath[] paths = null;
            if (src.equals(treeJeux)) {
                paths = treeJeux.getSelectionPaths();
            }
            if (paths != null) {
                String informations = "";
                for (TreePath path : paths) {
                    Object node = path.getLastPathComponent();
                    if (node instanceof Informations) {
                        informations += ((Informations)node).getInformations() + "\n";
                    }
                    if (node instanceof RunTreeNode) {
                        controler.setRun(((RunTreeNode)node).getRun().getID());
                    }
                }
                textPaneInfos.setText(informations);
                
            }
        }
        
    }
    
}
