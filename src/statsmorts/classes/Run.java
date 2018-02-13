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
 * Une classe pour représenter une run/partie d'un jeu.
 * @author Robin
 */
public class Run extends ObjectDatabaseWithTitle implements FillDataset, Comparable<Run> {
    
    //ATTRIBUTS
    /**
     * Le jeu sur lequel la run est jouée.
     */
    private Jeu jeu;
    /**
     * La collection des lives de la run.
     */
    private final Map<Long,Live> lives;
    
    
    //CONSTRUCTEURS
    /**
     * Crée une run sans live.
     * @param id l'identifiant de la run
     * @param titre le titre de la run
     */
    public Run(long id, String titre) {
        super(id,titre);
        this.lives = new HashMap();
    }
    
    
    
    //ACCESSEURS
    /**
     * Retourne le jeu sur lequel a été fait la run.
     * @return le jeu sur lequel a été fait la run
     */
    public Jeu getJeu() {
        return jeu;
    }
    
    /**
     * Retourne la map des lives de la run.
     * @return la map des lives de la run
     */
    public Map getLives() {
        return lives;
    }
    
    /**
     * Retourne le nombre total de lives.
     * @return le nombre total de lives
     */
    public int getNombreLives() {
        return lives.size();
    }
    
    /**
     * Retourne la durée totale de la run en float (fais la somme des durées de
     * tous les lives) en unité de temps 'unit'
     * @param unit l'unité de temps dans laquelle calculer la durée totale
     * @return la durée totale de la run
     * @see statsmorts.classes.Live#getDuration(TimeUnit) 
     */
    public float getTotalDuration(TimeUnit unit) {
        float res = 0;
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            res += liveEntry.getValue().getDuration(unit);
        }
        return res;
    }
    
    /**
     * Retourne le nombre total de morts sur cette run (fais la somme des morts
     * de tous les lives).
     * @return le nombre total de morts sur cette run
     * @see statsmorts.classes.Live#morts
     */
    public int getTotalMorts() {
        int res = 0;
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            res += liveEntry.getValue().getMorts();
        }
        return res;
    }
    
    /**
     * Retourne le nombre total de boss vaincus sur cette run (fais laa somme des
     * boss de tous les lives).
     * @return le nombre total de boss vaincus
     * @see statsmorts.classes.Live#boss
     */
    public int getTotalBoss() {
        int res = 0;
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            res += liveEntry.getValue().getBoss();
        }
        return res;
    }
    
    /**
     * Retourne une durée de vie moyenne ( = temps / (morts + 1)).
     * @param temps le temps passé
     * @param morts le nombre de morts
     * @return une durée de vie moyenne en float
     */
    private float getDureeVieMoyenne(float temps, int morts) {
        return (temps) / (float)(morts + 1);
    }
    
    /**
     * Retourne la moyenne des durées de vie sur cette run en unité de temps 'unit'.
     * @param unit l'unité de temps dans laquelle calculer la moyenne
     * @return la moyenne des durées de vie sur cette run
     * @see statsmorts.classes.Live#getDureeVieMoyenne(TimeUnit) 
     */
    public float getMoyenneDureesVie(TimeUnit unit) {
        float moyenneDesMoyennes = 0, sommeDureeVie = 0, moyenne, sommeMoyennes = 0;
        int count = 0;
        //utilisation de TreeSet pour trier dans l'ordre chronologique les lives
        //sans ce tri, des erreurs de moyenne se font
        TreeSet<Live> livesTreeSet = new TreeSet();
        Set<Entry<Long,Live>> livesSet = lives.entrySet();
        for (Entry<Long,Live> liveEntry : livesSet) {
            livesTreeSet.add(liveEntry.getValue());
        }
        for (Live live : livesTreeSet) {
            count++;
            sommeDureeVie += live.getDureeVieMoyenne(unit);
            moyenne = sommeDureeVie / (float)count;
            sommeMoyennes += moyenne;
        }
        moyenneDesMoyennes = sommeMoyennes / (float)count;
        return moyenneDesMoyennes;
    }
    
    /**
     * Retourne une chaîne de caractères représentant la run, avec ses attributs
     * et des variables calculées (les durées, durées de vie moyennes, morts etc).
     * Utilisée par la méthode getInformations de l'interface Informations.
     * @return une chaîne de caractères représentant la run
     * @see statsmorts.vue.Informations#getInformations()
     */
    @Override
    public String toString() {
        int mortsTotales = getTotalMorts();
        int boss = getTotalBoss();
        float heures = getTotalDuration(TimeUnit.HOURS);
        float minutes = getTotalDuration(TimeUnit.MINUTES);
        float mortsParBoss = (boss == 0) ? 0 : (float)(mortsTotales) / (float)(boss);
        float minutesParBoss = (boss == 0) ? 0 : (float)(minutes) / (float)(boss);
        float heuresParBoss = (boss == 0) ? 0 : (float)(heures) / (float)(boss);
        String res = "Titre : " + super.getTitre() + "\n"
                + "Nombre de lives : " + getNombreLives() + "\n\n"
                + "Durée totale : " + heures + " heures\n"
                + "Durée totale : " + (int)(heures) + "h" + (int)(minutes % 60) + "m\n"
                + "Durée totale : " + minutes + " minutes\n\n"
                + "Morts totales : " + mortsTotales + "\n\n"
                + "Durée de vie moyenne totale : " + getDureeVieMoyenne(heures, mortsTotales) + " heures\n"
                + "Durée de vie moyenne totale : " + getDureeVieMoyenne(minutes, mortsTotales) + " minutes\n\n"
                + "Moyenne des durées de vie : " + getMoyenneDureesVie(TimeUnit.HOURS) + " heures\n"
                + "Moyenne des durées de vie : " + getMoyenneDureesVie(TimeUnit.MINUTES) + " minutes\n\n"
                + "Boss vaincus : " + boss + "\n\n"
                + "Moyenne de morts par boss : " + mortsParBoss + "\n\n"
                + "Temps moyen par boss : " + minutesParBoss + " minutes\n"
                + "Temps moyen par boss : " + heuresParBoss + " heures\n\n";
        return res;
    }
    
    
    //MUTATEURS
    /**
     * Vide la map des lives tout en enlevant supprimant la run dans les lives.
     */
    public void clearLives() {
        Set<Entry<Long, Live>> setLives = lives.entrySet();
        for (Entry<Long, Live> entry : setLives) {
            entry.getValue().setRun(null);
        }
        lives.clear();
    }
    
    /**
     * Modifie le jeu de la run par 'jeu'
     * @param jeu le jeu sur lequel a été faite la run
     */
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
    
    /**
     * Ajoute un live à la map des lives de la run, et ajoute la run au jeu ajouté
     * avec la méthode setRun.
     * @param live le live à ajouter
     * @see statsmorts.classes.Live#setRun(Run) 
     */
    public void ajouterLive(Live live) {
        lives.put(live.getID(),live);
        live.setRun(this);
    }
    
    /**
     * Supprime le live qui a pour identifiant 'id' de la map des lives de la run.
     * @param idLive l'identifiant du live à supprimer
     */
    public void supprimerLive(long idLive) {
        lives.remove(idLive);
    }
    
    /**
     * Supprime tous les lives liés à cette run
     * et l'occurence de cette run dans le jeu lié à cette run.
     */
    public void supprimerRun() {
        clearLives();
        jeu.supprimerRun(super.getID());
    }
    
    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitreDataset() {
        return super.getTitre();
    }
    
    /**
     * {@inheritDoc}
     */
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void fillDataset(final DefaultCategoryDataset dataset, final TimeUnit unit, final boolean total) {
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
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Run o) {
        return super.getTitre().compareTo(((Run)o).getTitre());
    }
    
}
