/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.constantes.TexteConstantes;
import statsmorts.constantes.TexteConstantesFormatDate;

/**
 * Une classe pour représenter un live.
 * @author Robin
 */
public class Live extends ObjectDatabase implements FillDataset, Comparable<Live> {
    
    //ATTRIBUTS STATIC
    public static final SimpleDateFormat DATE_FORMAT_SQL = new SimpleDateFormat(TexteConstantesFormatDate.SQL);
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat(TexteConstantesFormatDate.SHORT);
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat(TexteConstantesFormatDate.LONG);
    
    //ATTRIBUTS
    /**
     * La date (date + heure) de début du live.
     */
    private Date dateDebut;
    /**
     * La date (date + heure) de fin du live.
     */
    private Date dateFin;
    /**
     * La run/partie durant laquelle a été fait ce live (ou partie du live).
     */
    private Run run;
    /**
     * Le nombre de morts durant ce live (ou partie du live).
     */
    private int morts;
    /**
     * Le nombre de boss vaincus durant ce live (ou partie du live).
     */
    private int boss;
    
    //CONSTRUCTEURS
    /**
     * Crée un live.
     * @param id l'idenfiant du live dans la base de données
     * @param dateDebutString la date de début (en String) du live
     * @param dateFinString la date de fin (en String) du live
     * @param morts le nombre de morts
     * @param boss le nombre de boss vaincus
     */
    public Live(final long id, final String dateDebutString, final String dateFinString, final int morts, final int boss) {
        super(id);
        try {
            this.dateDebut = DATE_FORMAT_SQL.parse(dateDebutString);
            this.dateFin = DATE_FORMAT_SQL.parse(dateFinString);
        } catch (ParseException ex) {
            this.dateDebut = new Date(0);
            this.dateFin = new Date(0);
            Logger.getLogger(Live.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.morts = morts;
        this.boss = boss;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne la run durant laquelle est fait le live.
     * @return la run durant laquelle est fait le live
     */
    public Run getRun() {
        return run;
    }
    
    /**
     * Retourne le nombre de morts durant ce live.
     * @return le nombre de morts durant ce live
     */
    public int getMorts() {
        return morts;
    }
    
    /**
     * Retourne le nombre de boss vaincus durant ce live.
     * @return le nombre de boss vaincus durant ce live
     */
    public int getBoss() {
        return boss;
    }
    
    /**
     * Retourne la date de début du live.
     * @return la date de début du live
     */
    public Date getDateDebut() {
        return dateDebut;
    }
    
    /**
     * Retourne la date de fin du live.
     * @return la date de fin du live
     */
    public Date getDateFin() {
        return dateFin;
    }
    
    /**
     * Retourne la durée du live en long en unité de temps 'unit'.
     * @param unit l'unité de temps dans laquelle convertir la duée du live
     * @return la durée du live en long converti en unité de temps 'unit'
     */
    private long getDurationLong(TimeUnit unit) {
        return unit.convert(dateFin.getTime() - dateDebut.getTime(),TimeUnit.MILLISECONDS);
    }
    
    /**
     * Retourne la durée de temps en float en unité de temps 'unit'.
     * @param unit l'unité de temps dans laquelle calculer la durée (heures ou minutes)
     * @return la durée de temps en float (pour heures) en unité de temps 'unit'
     * 
     */
    public float getDuration(TimeUnit unit) {
        float res;
        long heures = getDurationLong(TimeUnit.HOURS);
        long minutes = getDurationLong(TimeUnit.MINUTES);
        if (unit.equals(TimeUnit.HOURS)) {
            res = (float)heures + (float)(minutes % 60) / (float)60;
        }
        else {
            res = minutes;
        }
        return res;
    }
    
    /**
     * Retourne la durée de vie moyenne de ce live ( = (temps / (morts + 1)) en unité de temps
     * 'unit'
     * @param unit l'unité de temps dans laquelle calculer la durée de vie moyenne
     * @return la durée de vie moyenne de ce live
     * @see getDuration(TimeUnit unit)
     */
    public float getDureeVieMoyenne(TimeUnit unit) {
        return (float)this.getDuration(unit) / (float)(morts + 1);
    }
    
    /**
     * Retourne une chaîne de caractères représentant le live, avec ses attributs
     * et des variables calculées (les durées et durées de vie moyennes).
     * Utilisée par la méthode getInformations de l'interface Informations.
     * @return une chaîne de caractères représentant le live
     * @see statsmorts.vue.Informations#getInformations()
     */
    @Override
    public String toString() {
        float heures = this.getDuration(TimeUnit.HOURS);
        float minutes = this.getDuration(TimeUnit.MINUTES);
        float mortsParBoss = (boss == 0) ? 0 : (float)(morts) / (float)(boss);
        float minutesParBoss = (boss == 0) ? 0 : minutes / (float)(boss);
        float heuresParBoss = (boss == 0) ? 0 : heures / (float)(boss);
        String res = "Date début : " + dateDebut.toString() + TexteConstantes.NEW_LINE
                + "Date fin : " + dateFin.toString() + TexteConstantes.NEW_LINE
                + "Durée : " + heures + " heures" + TexteConstantes.NEW_LINE
                + "Durée : " + (int)(heures) + "h" + (int)(minutes % 60) + "m" +TexteConstantes.NEW_LINE
                + "Durée : " + minutes + " minutes" + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Morts  : " + morts + TexteConstantes.NEW_LINE
                + "Boss vaincus : " + boss + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Durée de vie moyenne : " + getDureeVieMoyenne(TimeUnit.HOURS) + " heures" + TexteConstantes.NEW_LINE
                + "Durée de vie moyenne : " + getDureeVieMoyenne(TimeUnit.MINUTES) + " minutes"
                + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Boss vaincus : " + boss + TexteConstantes.NEW_LINE  + TexteConstantes.NEW_LINE
                + "Moyenne de morts par boss : " + mortsParBoss + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Temps moyen par boss : " + minutesParBoss + " minutes" + TexteConstantes.NEW_LINE
                + "Temsp moyen par boss : " + heuresParBoss + "heures" + TexteConstantes.NEW_LINE;
        return res;
    }
    
    
    //MUTATEURS
    /**
     * Change la run/partie actuelle par 'run'.
     * @param run la run du live
     */
    public void setRun(Run run) {
        this.run = run;
    }
    
    /**
     * Supprime l'occurence de ce live dans la run liée à ce live.
     */
    public void supprimerLive() {
        this.run.supprimerLive(super.getID());
    }
    
    
    //INTERFACE FILLDATASET
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitre() {
        return DATE_FORMAT_LONG.format(dateDebut) + " - " + DATE_FORMAT_LONG.format(dateFin);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitreDataset() {
        String date = DATE_FORMAT_SHORT.format(dateDebut);
        return this.run.getTitreDataset() + " le " + date;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> lives = new ArrayList();
        lives.add(this);
        return lives;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        float temps  = this.getDuration(unit);
        float dureeVie = this.getDureeVieMoyenne(unit);
        float mortsParBoss = (boss == 0) ? 0 : (float)(morts) / (float)(boss);
        float tempsParBoss = (boss == 0) ? 0 :temps / (float)(boss);
        dataset.addValue(morts, "Morts", dateDebut);
        dataset.addValue(boss, "Boss", dateDebut);
        dataset.addValue(tempsParBoss, "Temps par boss", dateDebut);
        dataset.addValue(mortsParBoss, "Morts par boss", dateDebut);
        dataset.addValue(temps, "Durée du live", dateDebut);
        dataset.addValue(dureeVie, "Durée de vie moyenne", dateDebut);
    }
    
    
    //INTERFACE COMPARABLE
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Live o) {
        return this.dateDebut.compareTo(((Live)o).dateDebut);
    }
    
}
