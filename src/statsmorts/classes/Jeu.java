/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Robin
 */
public class Jeu {
    
    //ATTRIBUTS
    private final long id;
    private final String titre;
    private final int anneeSortie;
    private Studio studio;
    private final Map<Long,Plateforme> plateformes;
    private final Map<Long,Type> types;
    private final Map<Long,Run> runs;
    
    
    //CONSTRUCTEURS
    public Jeu(long id, String titre, int anneeSortie) {
        this.id = id;
        this.titre = titre;
        this.anneeSortie = anneeSortie;
        plateformes = new HashMap();
        types = new HashMap();
        runs = new HashMap();
    }
    
    
    //ACCESSEURS
    public long getID() {
        return id;
    }
    
    public Map<Long,Type> getTypes() {
        return types;
    }
    
    public Map<Long,Run> getRuns() {
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
    
    @Override
    public String toString() {
        String res = "Jeu : " + titre + "\n"
                + "    " + studio.toString() + "\n"
                + "    Année de sortie : " + anneeSortie + "\n"
                + "    Plateformes : " + plateformes.toString() + "\n"
                + "    Types : " + types.toString() + "\n"
                + "    Runs : " + runs.toString();
        return res;
    }
    
    //MUTATEURS
    public void setStudio(Studio studio) {
        this.studio = studio;
    }
    
    public void putPlateforme(Plateforme plateforme) {
        plateformes.put(plateforme.getID(),plateforme);
    }
    
    public void putType(Type type) {
        types.put(type.getID(),type);
    }
    
    public void putRun(Run run) {
        runs.put(run.getID(),run);
    }
    
}
