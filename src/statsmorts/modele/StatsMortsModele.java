/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private TimeUnit timeUnit;
    
    private Observer observer;
    
    //CONSTRUCTEUR
    public StatsMortsModele(Preferences prefs) {
        connexion = new Connexion();
        preferences = prefs;
        timeUnit = TimeUnit.HOURS;
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
    
    public void setTimeUnit(TimeUnit unit) {
        if (!this.timeUnit.equals(unit)) {
            this.timeUnit = unit;
        }
    }
    
    public void actualiser() {
        try {
            bdd.actualiserBDD(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (observer != null) {
            observer.clear();
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
        
        
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
    
//    public void createDataset(FillDataset node) {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        node.fillDataset(dataset, TimeUnit.MINUTES, true);
//        notifyDataset(node.getTitreDataset(), dataset);
//    }
    
    public void createDataset(String titre, ArrayList<FillDataset> nodes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Live> lives = new ArrayList();
        for (FillDataset node : nodes) {
            lives.addAll(node.getLivesList());
        }
        lives.sort(new LiveComparator());
        float moyenne = 0, sommeDureeVie = 0, sommeMoyennes = 0;
        int count = 0, sommeMorts = 0;
        float sommeTime = 0;
//        long sommeMinutes = 0;
        for (Live live : lives) {
            sommeTime += live.getDuration(timeUnit);
            sommeMorts += live.getMorts();
            sommeDureeVie += live.getDureeVieMoyenne(timeUnit);
            count++;
            moyenne = sommeDureeVie / count;
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, timeUnit, false);
            dataset.addValue(moyenne,"Moyenne des durées de vie moyennes",live.getDateDebut());
        }
        if (lives.size() > 1) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", "Total");
        }
        notifyDataset(titre, dataset);
    }
//    
//    public void createChart(Jeu jeu) {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        float moyenne, sommeMPD = 0, sommeHeures = 0, sommeMoyennes = 0;
//        int count = 0, sommeMorts = 0;
//        long sommeMinutes = 0;
//        Set<Entry<Long,Run>> setRuns = jeu.getRuns().entrySet();
//        
//        ArrayList<Live> lives = new ArrayList();
//        for (Entry<Long,Run> entryRun : setRuns) {
//            Set<Entry<Long,Live>> setLives = entryRun.getValue().getLives().entrySet();
//            for (Entry<Long,Live> entryLive : setLives) {
//                Live live = entryLive.getValue();
//                lives.add(live);
//            }
//        }
//        
//        Collections.sort(lives,new LiveComparator());
//        
//        for (Live live : lives) {
//            int morts = live.getMorts();
//            Date dateDebut = live.getDateDebut();
//            long heuresLong = live.getDurationLong(TimeUnit.HOURS);
//            long minutes = live.getDurationLong(TimeUnit.MINUTES);
//            float heures;// = (float)heuresLong + (float)(minutes % 60) / (float)60;
//            heures = live.getDuration(TimeUnit.HOURS);
//            
//            float mpd;// = heures / (float)(morts + 1);
//            mpd = live.getDureeVieMoyenne(TimeUnit.HOURS);
//            count++;
//            sommeMorts += morts;
//            sommeHeures += heures;
//            sommeMinutes += minutes;
//            sommeMPD += mpd;
//            moyenne = sommeMPD / count;
//            sommeMoyennes += moyenne;
//            
//            dataset.addValue(morts, "Morts", dateDebut);
//            dataset.addValue(heures, "Durée du live (heures)", dateDebut);
//            dataset.addValue(mpd, "Durée de vie moyenne", dateDebut);
//            dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", dateDebut);
//        }
//        
//        float totalMPD = (float)sommeHeures / (float)(sommeMorts + 1);
//        moyenne = sommeMoyennes / (float)count;
//        dataset.addValue(sommeMorts, "Morts", "Total");
//        dataset.addValue(totalMPD, "Durée de vie moyenne", "Total");
//        dataset.addValue(sommeHeures, "Durée du live (heures)", "Total");
//        dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", "Total");
//        
//    }
    
    
    //OBSERVER
    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
    
    @Override
    public void notifyJeu(Jeu jeu) {
        if (observer != null) {
            observer.addJeu(jeu);
        }
    }
    
    @Override
    public void notifyRun(Run run) {
        if (observer != null) {
            observer.addRun(run.getJeu().getID(),run);
        }
    }
    
    @Override
    public void notifyLive(Live live) {
        if (observer != null) {
            Run run = live.getRun();
            observer.addLive(run.getJeu().getID(), run.getID(), live);
        }
    }
    
    @Override
    public void notifyDataset(String titre, DefaultCategoryDataset dataset) {
        if (observer != null && dataset != null) {
            observer.updateDataset(titre,dataset);
        }
    }
    
}
