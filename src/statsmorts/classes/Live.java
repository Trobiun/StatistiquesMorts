/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class Live {
    
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
    public int getMorts() {
        return morts;
    } 
    
    public long getDuration(TimeUnit unit) {
        return unit.convert(dateFin.getTime() - dateDebut.getTime(),TimeUnit.MILLISECONDS);
    }
    
    public float getTempsParMorts(TimeUnit unit) {
        return (float)getDuration(unit) / (float)(morts + 1);
    }
    
    @Override
    public String toString() {
        return "Live : durée = " + getDuration(TimeUnit.MINUTES) + " min, morts : " + morts + ", Temps par vie : " + getTempsParMorts(TimeUnit.MINUTES);
    }
    
    
    //MUTATEURS
    public void setRun(Run run) {
        this.run = run;
    }
    
}
