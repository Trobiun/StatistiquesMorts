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
public class Live implements FillDataset {
    
    //ATTRIBUTS STATIC
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    
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
            this.dateDebut = DATE_FORMAT.parse(dateDebutString);
            this.dateFin = DATE_FORMAT.parse(dateFinString);
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
        String res = "Date début : " + dateDebut.toString() + "\n"
                + "Date fin : " + dateFin.toString() + "\n"
                + "Durée : " + this.getDuration(TimeUnit.HOURS) + " heures\n"
                + "Durée : " + this.getDuration(TimeUnit.MINUTES) + " minutes\n"
                + "Morts  : " + morts + "\n"
                + "Durée de vie moyenne : " + getDureeVieMoyenne(TimeUnit.HOURS) + " heures\n"
                + "Durée de vie moyenne : " + getDureeVieMoyenne(TimeUnit.MINUTES) + " minutes\n";
        return res;
//        return dateDebut.toString();
//        return "Live : durée = " + getDuration(TimeUnit.MINUTES) + " min, morts : " + morts + ", Temps par vie : " + getDuration(TimeUnit.MINUTES);
    }
    
    
    //MUTATEURS
    public void setRun(Run run) {
        this.run = run;
    }
    
    
    //INTERFACE FILLDATASET
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
    
}
