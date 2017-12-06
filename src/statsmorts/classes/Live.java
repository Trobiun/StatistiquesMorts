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

/**
 *
 * @author Robin
 */
public class Live implements FillDataset, Comparable {
    
    //ATTRIBUTS STATIC
    public static final SimpleDateFormat DATE_FORMAT_SQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    //ATTRIBUTS
    private final long id;
    private Date dateDebut;
    private Date dateFin;
    private Run run;
    private final int morts;
    
    
    //CONSTRUCTEURS
    public Live(long id, String dateDebutString, String dateFinString, int morts) {
        this.id = id;
        try {
            this.dateDebut = DATE_FORMAT_SQL.parse(dateDebutString);
            this.dateFin = DATE_FORMAT_SQL.parse(dateFinString);
        } catch (ParseException ex) {
            this.dateDebut = new Date(0);
            this.dateFin = new Date(0);
            Logger.getLogger(Live.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.morts = morts;
    }
    
    
    //ACCESSEURS
    public long getID() {
        return id;
    }
    
    public Run getRun() {
        return run;
    }
    
    public int getMorts() {
        return morts;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public long getDurationLong(TimeUnit unit) {
        return unit.convert(dateFin.getTime() - dateDebut.getTime(),TimeUnit.MILLISECONDS);
    }
    
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
    
    public float getDureeVieMoyenne(TimeUnit unit) {
        return (float)this.getDuration(unit) / (float)(morts + 1);
    }
    
    @Override
    public String toString() {
        float heures = this.getDuration(TimeUnit.HOURS);
        float minutes = this.getDuration(TimeUnit.MINUTES);
        String res = "Date début : " + dateDebut.toString() + "\n"
                + "Date fin : " + dateFin.toString() + "\n"
                + "Durée : " + heures + " heures\n"
                + "Durée : " + (int)(heures) + "h" + (int)(minutes % 60) + "m\n"
                + "Durée : " + minutes + " minutes\n\n"
                + "Morts  : " + morts + "\n\n"
                + "Durée de vie moyenne : " + getDureeVieMoyenne(TimeUnit.HOURS) + " heures\n"
                + "Durée de vie moyenne : " + getDureeVieMoyenne(TimeUnit.MINUTES) + " minutes\n";
        return res;
    }
    
    
    //MUTATEURS
    public void setRun(Run run) {
        this.run = run;
    }
    
    
    //INTERFACE FILLDATASET
    @Override
    public String getTitre() {
        return DATE_FORMAT_LONG.format(dateDebut) + " - " + DATE_FORMAT_LONG.format(dateFin);
    }
    
    @Override
    public String getTitreDataset() {
        String date = DATE_FORMAT_SHORT.format(dateDebut);
        return this.run.getTitreDataset() + " le " + date;
    }
    
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> lives = new ArrayList();
        lives.add(this);
        return lives;
    }
    
    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        float temps  = this.getDuration(unit);
        float dureeVie = this.getDureeVieMoyenne(unit);
        dataset.addValue(morts, "Morts", dateDebut);
        dataset.addValue(temps, "Durée du live", dateDebut);
        dataset.addValue(dureeVie, "Durée de vie moyenne", dateDebut);
    }
    
    
    //INTERFACE COMPARABLE
    @Override
    public int compareTo(Object o) {
         if (o instanceof Live) {
            return this.dateDebut.compareTo(((Live)o).dateDebut);
        }
        else {
            return this.toString().compareTo(o.toString());
        }
    }
    
}
