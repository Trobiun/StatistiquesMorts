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
public class Run {
    
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
    
    public String getTitre() {
        return titre;
    }
    
    public Map getLives() {
        return lives;
    }
    
    public long getTotalDuration(TimeUnit unit) {
        long res = 0;
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            res += liveEntry.getValue().getDuration(unit);
        }
        return res;
    }
    
    @Override
    public String toString() {
        String res = "Run : " + titre + "\n";
        res += "        Lives : " + lives.toString() + "\n";
        return res;
    }
    
    
    //MUTATEURS
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
    
    public void putLive(Live live) {
        lives.put(live.getID(),live);
        live.setRun(this);
    }
    
}
