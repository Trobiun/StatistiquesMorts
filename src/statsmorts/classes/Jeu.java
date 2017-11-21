/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
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
public class Jeu implements FillDataset {
    
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

    public String getTitre() {
        return titre;
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
    
    public long getTotalDuration(TimeUnit unit) {
        long res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getTotalDuration(unit);
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
        res += genresToString();
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
        
    }
    
}
