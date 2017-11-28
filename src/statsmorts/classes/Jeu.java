/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Robin
 */
public class Jeu implements FillDataset, Comparable {
    
    //ATTRIBUTS
    private final long id;
    private final String titre;
    private final int anneeSortie;
    private Studio studio;
    private final HashMap<Long,Plateforme> plateformes;
    private final HashMap<Long,Genre> genres;
    private final HashMap<Long,Run> runs;
    
    
    //CONSTRUCTEURS
    public Jeu(long id, String titre, int anneeSortie) {
        this.id = id;
        this.titre = titre;
        this.anneeSortie = anneeSortie;
        plateformes = new HashMap();
        genres = new HashMap();
        runs = new HashMap();
    }
    
    
    //ACCESSEURS
    public long getID() {
        return id;
    }
    
    public Studio getStudio() {
        return studio;
    }
    
    public HashMap<Long,Plateforme> getPlateformes() {
        return plateformes;
    }
    
    public HashMap<Long,Genre> getGenres() {
        return genres;
    }
    
    public HashMap<Long,Run> getRuns() {
        return runs;
    }
    
    public float getTotalDuration(TimeUnit unit) {
        float res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getTotalDuration(unit);
        }
        return res;
    }
    
    public int getTotalLives() {
        int res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getNombreLives();
        }
        return res;
    }
    
    public int getTotalMorts() {
        int res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getTotalMorts();
        }
        return res;
    }
    
    private String plateformesToString() {
        String res = "Plateformes : ";
        Set<Entry<Long,Plateforme>> plateformesSet = plateformes.entrySet();
        Iterator<Entry<Long,Plateforme>> plateformesIterator = plateformesSet.iterator();
        while (plateformesIterator.hasNext()) {
            Entry<Long,Plateforme> plateformeEntry = plateformesIterator.next();
            res += plateformeEntry.getValue().toString();
            if (plateformesIterator.hasNext()) {
                res += ", ";
            }
        }
        res += "\n";
        return res;
    }
    
    private String genresToString() {
        String res = "Genres : ";
        Set<Entry<Long,Genre>> genresSet = genres.entrySet();
        Iterator<Entry<Long,Genre>> genresIterator = genresSet.iterator();
        while (genresIterator.hasNext()) {
            Entry<Long,Genre> genreEntry = genresIterator.next();
            res += genreEntry.getValue().toString();
            if (genresIterator.hasNext()) {
                res += ", ";
            }
        }
        res += "\n";
        return res;
    }
    
    @Override
    public String toString() {
        String res = "Titre : " + titre + "\n"
                + "" + studio.toString() + "\n"
                + "Année de sortie : " + anneeSortie + "\n";
        res += plateformesToString();
        res += genresToString() + "\n";
        res += "Total de runs : " + runs.size() + "\n";
        res += "Total de lives : " + getTotalLives() + "\n\n";
        float dureeTotaleHeures = getTotalDuration(TimeUnit.HOURS);
        float dureeTotaleMinutes = dureeTotaleHeures * 60;
        res += "Durée totale : " + dureeTotaleHeures + " heures\n";
        res += "Durée totale : " + dureeTotaleMinutes + " minutes\n\n";
        int totalMorts = getTotalMorts();
        res += "Total de morts : " + totalMorts + "\n\n";
        res += "Durée de vie moyenne : " + (dureeTotaleHeures / ((float)totalMorts + 1)) + " heures\n";
        res += "Durée de vie moyenne : " + (dureeTotaleMinutes / ((float)totalMorts + 1)) + " minutes\n\n";
        return res;
    }
    
    //MUTATEURS
    public void setStudio(Studio studio) {
        this.studio = studio;
    }
    
    public void putPlateforme(Plateforme plateforme) {
        plateformes.put(plateforme.getID(),plateforme);
    }
    
    public void putGenre(Genre genre) {
        genres.put(genre.getID(),genre);
    }
    
    public void putRun(Run run) {
        runs.put(run.getID(),run);
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
        Set<Entry<Long,Run>> setRuns = runs.entrySet();
        for (Entry<Long,Run> entryRun : setRuns) {
            livesList.addAll(entryRun.getValue().getLivesList());
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
            dataset.addValue(moyenne,"Moyenne des durées de vie moyennes",Live.DATE_FORMAT_SHORT.format(live.getDateDebut()));
        }
        if (total) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", "Total");
        }
        
    }
    
    
    //INTERFACE COMPARABLE
    @Override
    public int compareTo(Object o) {
        if (o instanceof Jeu){
            return this.titre.compareTo(((Jeu)o).titre);
        }
        else {
            return this.titre.compareTo(o.toString());
        }
    }
    
}
