/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Robin
 */
public class Run implements FillDataset, Comparable {
    
    //ATTRIBUTS
    private final long id;
    private final String titre;
    private Jeu jeu;
    private final Map<Long,Live> lives;
    
    
    //CONSTRUCTEURS
    public Run(long id, String titre) {
        this.id = id;
        this.titre = titre;
        this.lives = new HashMap();
    }
    
    
    
    //ACCESSEURS
    public long getID() {
        return id;
    }
    
    public Jeu getJeu() {
        return jeu;
    }
    
    public Map getLives() {
        return lives;
    }
    
    public int getNombreLives() {
        return lives.size();
    }
    
    public float getTotalDuration(TimeUnit unit) {
        float res = 0;
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            res += liveEntry.getValue().getDuration(unit);
        }
        return res;
    }
    
    public int getTotalMorts() {
        int res = 0;
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            res += liveEntry.getValue().getMorts();
        }
        return res;
    }
    
    public float getDureeVieMoyenne(float temps, int morts) {
        return (temps) / (float)(morts + 1);
    }
    
    public float getMoyenneDureesVie(TimeUnit unit) {
        float moyenneDesMoyennes = 0, sommeDureeVie = 0, moyenne, sommeMoyennes = 0;
        int count = 0;
        TreeSet<Live> livesTreeSet = new TreeSet();
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            livesTreeSet.add(liveEntry.getValue());
        }
        for (Live live : livesTreeSet) {
//            Live live = liveEntry.getValue();
            count++;
            sommeDureeVie += live.getDureeVieMoyenne(unit);
            moyenne = sommeDureeVie / (float)count;
            sommeMoyennes += moyenne;
//            System.out.println("DureeVie : " + live.getDureeVieMoyenne(unit));
//            System.out.println("sommeMoyennes = " + sommeDureeVie);
//            System.out.println("moyenneDureeVie = " + moyenne);
//            System.out.println("Count = " + count);
//            System.out.println();
        }
        moyenneDesMoyennes = sommeMoyennes / (float)count;
//        System.out.println("Moyenne des moyennes : " + moyenneDesMoyennes);
        return moyenneDesMoyennes;
    }
    
    @Override
    public String toString() {
        int mortsTotales = getTotalMorts();
        float heures = getTotalDuration(TimeUnit.HOURS);
        float minutes = getTotalDuration(TimeUnit.MINUTES);
        String res = "Titre : " + titre + "\n"
                + "Nombre de lives : " + getNombreLives() + "\n\n"
                + "Durée totale : " + heures + " heures\n"
                + "Durée totale : " + (int)(heures) + "h" + (int)(minutes % 60) + "m\n"
                + "Durée totale : " + minutes + " minutes\n\n"
                + "Morts totales : " + mortsTotales + "\n\n"
                + "Durée de vie moyenne totale : " + getDureeVieMoyenne(heures, mortsTotales) + " heures\n"
                + "Durée de vie moyenne totale : " + getDureeVieMoyenne(minutes, mortsTotales) + " minutes\n\n"
                + "Moyenne des durées de vie : " + getMoyenneDureesVie(TimeUnit.HOURS) + " heures\n"
                + "Moyenne des durées de vie : " + getMoyenneDureesVie(TimeUnit.MINUTES) + " minutes\n";
        return res;
    }
    
    
    //MUTATEURS
    public void clearLives() {
        Set<Entry<Long, Live>> setLives = lives.entrySet();
        for (Entry<Long, Live> entry : setLives) {
            entry.getValue().setRun(null);
        }
        lives.clear();
    }
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
    
    public void ajouterLive(Live live) {
        lives.put(live.getID(),live);
        live.setRun(this);
    }
    
    public void supprimerLive(long idLive) {
        lives.remove(idLive);
    }
    
    /**
     * Supprime tous les lives liés à cette run
     * et l'occurence de cette run dans le jeu lié à cette run.
     */
    public void supprimerRun() {
        clearLives();
        jeu.supprimerRun(id);
    }
    
    //INTERFACE FILLDATASET
    @Override
    public String getTitre() {
        return titre;
    }
    
    @Override
    public String getTitreDataset() {
        return titre;
    }
    
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> livesList = new ArrayList();
        Set<Entry<Long,Live>> setLives = this.lives.entrySet();
        for (Entry<Long,Live> entryLive : setLives) {
            Live live = entryLive.getValue();
            livesList.add(live);
        }
        return livesList;
    }
    
    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        ArrayList<Live> livesList = this.getLivesList();
        Collections.sort(livesList);
        
        float moyenne = 0, sommeDureeVie = 0, sommeMoyennes = 0;
        int count = 0, sommeMorts = 0;
//        long sommeMinutes = 0;
        float sommeTime = 0;
        for (Live live : livesList) {
            sommeTime += live.getDuration(unit);
            sommeMorts += live.getMorts();
            sommeDureeVie += live.getDureeVieMoyenne(unit);
            count++;
            moyenne = sommeDureeVie / count;
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, unit, false);
            dataset.addValue(moyenne,"Moyenne des durées de vie",live.getDateDebut());
        }
        if (total) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie", "Total");
        }
        
    }
    
    
    //INTERFACE COMPARABLE
    @Override
    public int compareTo(Object o) {
        if (o instanceof Run) {
            return this.titre.compareTo(((Run)o).titre);
        }
        else {
            return this.titre.compareTo(o.toString());
        }
    }
    
}
