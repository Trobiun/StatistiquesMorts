/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.BDD;
import statsmorts.classes.Connexion;
import statsmorts.classes.FillDataset;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.LiveComparator;
import statsmorts.classes.Run;
import statsmorts.classes.TypeDatabase;
import statsmorts.observer.Observable;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;

/**
 *
 * @author Robin
 */
public class StatsMortsModele implements Observable {
    
    //ATTRIBUTS
    private final Preferences preferences;
    private final Connexion connexion;
    private BDD bdd;
    
    private Observer observer;
    
    //CONSTRUCTEUR
    public StatsMortsModele(Preferences prefs) {
        connexion = new Connexion();
        preferences = prefs;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void creerBDD(String pathBDD) {
        deconnecter();
        bdd = BDD.creerBDD(connexion,pathBDD);
        actualiser();
    }
    
    public void ouvrirBDD(String pathBDD) {
        deconnecter();
        bdd = new BDD(connexion,pathBDD);
        actualiser();
    }
    
    public void connecter(String database) {
        bdd = new BDD(connexion,database);
    }
    
    public void connecter(TypeDatabase type, String serveur, String user, String password) {
        bdd = new BDD(connexion, type, serveur, user, password);
    }
    
    public void actualiser() {
        try {
            bdd.actualiserBDD(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
        String titre = "DLC The Depths";
        Set<Entry<Long,Jeu>> set = bdd.getJeux().entrySet();
        for (Entry<Long,Jeu> entry : set) {
            Jeu jeu = entry.getValue();
            notifyJeu(jeu);
            Map<Long,Run> runsMap = jeu.getRuns();
            Set<Entry<Long,Run>> runsSet = runsMap.entrySet();
            for (Entry<Long,Run> runEntry : runsSet) {
                Run run = runEntry.getValue();
                notifyRun(run);
                Map<Long,Live> livesMap = run.getLives();
                Set<Entry<Long,Live>> livesSet = livesMap.entrySet();
                for (Entry<Long,Live> liveEntry : livesSet) {
                    Live live = liveEntry.getValue();
                    notifyLive(live);
                }
            }
        }
        
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
    public void setRun(long idRun) {
        createChart(bdd.getRuns().get(idRun));
    }
    
    public void createChart(FillDataset object) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        object.fillDataset(dataset, TimeUnit.MINUTES, true);
        notifyDataset(dataset);
    }
    
    public void createChart(Run run) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        run.fillDataset(dataset, TimeUnit.MINUTES, true);
        
        notifyDataset(dataset);
//        ChartFrame frame = new ChartFrame("Morts sur " + run.getTitre(),chart);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void createChart(Jeu jeu) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        float moyenne, sommeMPD = 0, sommeHeures = 0, sommeMoyennes = 0;
        int count = 0, sommeMorts = 0;
        long sommeMinutes = 0;
        Set<Entry<Long,Run>> setRuns = jeu.getRuns().entrySet();
        
        ArrayList<Live> lives = new ArrayList();
        for (Entry<Long,Run> entryRun : setRuns) {
            Set<Entry<Long,Live>> setLives = entryRun.getValue().getLives().entrySet();
            for (Entry<Long,Live> entryLive : setLives) {
                Live live = entryLive.getValue();
                lives.add(live);
            }
        }
        
        Collections.sort(lives,new LiveComparator());
        
        for (Live live : lives) {
            int morts = live.getMorts();
            Date dateDebut = live.getDateDebut();
            long heuresLong = live.getDurationLong(TimeUnit.HOURS);
            long minutes = live.getDurationLong(TimeUnit.MINUTES);
            float heures;// = (float)heuresLong + (float)(minutes % 60) / (float)60;
            heures = live.getDuration(TimeUnit.HOURS);
            
            float mpd;// = heures / (float)(morts + 1);
            mpd = live.getDureeVieMoyenne(TimeUnit.HOURS);
            count++;
            sommeMorts += morts;
            sommeHeures += heures;
            sommeMinutes += minutes;
            sommeMPD += mpd;
            moyenne = sommeMPD / count;
            sommeMoyennes += moyenne;
            
            dataset.addValue(morts, "Morts", dateDebut);
            dataset.addValue(heures, "Durée du live (heures)", dateDebut);
            dataset.addValue(mpd, "Durée de vie moyenne", dateDebut);
            dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", dateDebut);
        }
        
        float totalMPD = (float)sommeHeures / (float)(sommeMorts + 1);
        moyenne = sommeMoyennes / (float)count;
        dataset.addValue(sommeMorts, "Morts", "Total");
        dataset.addValue(totalMPD, "Durée de vie moyenne", "Total");
        dataset.addValue(sommeHeures, "Durée du live (heures)", "Total");
        dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", "Total");
        
        JFreeChart chart = ChartFactory.createLineChart("Morts Lives " + jeu.getTitre(), "Date live", "", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)chart.getCategoryPlot().getRenderer();
        renderer.setBaseShapesVisible(true);
        
        ChartFrame frame = new ChartFrame("Morts sur " + jeu.getTitre(),chart);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    //OBSERVER
    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
    
    @Override
    public void notifyJeu(Jeu jeu) {
        if (observer != null) {
            observer.updateJeu(jeu);
        }
    }
    
    @Override
    public void notifyRun(Run run) {
        if (observer != null) {
            observer.updateRun(run.getJeu().getID(),run);
        }
    }
    
    @Override
    public void notifyLive(Live live) {
        if (observer != null) {
            Run run = live.getRun();
            observer.updateLive(run.getJeu().getID(), run.getID(), live);
        }
    }
    
    @Override
    public void notifyDataset(DefaultCategoryDataset dataset) {
        if (observer != null && dataset != null) {
            observer.updateDataset(dataset);
        }
    }
    
}
