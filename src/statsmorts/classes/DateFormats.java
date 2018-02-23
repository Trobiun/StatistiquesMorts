/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import statsmorts.constantes.TexteConstantesFormatDate;

/**
 * Une classe pour parser et formater les dates.
 * @author Robin
 */
public class DateFormats {
    
    //CONSTANTES
    /**
     * SimpleDateFormat avec comme pattern "yyyy-MM-dd HH:mm:ss"
     */
    public static final SimpleDateFormat DATE_FORMAT_SQL = new SimpleDateFormat(TexteConstantesFormatDate.SQL);
    /**
     * SimpleDateFormat avec comme pattern "yyyy-MM-dd"
     */
    public static final SimpleDateFormat DATE_FORMAT_SQL_SHORT = new SimpleDateFormat(TexteConstantesFormatDate.SQL_SHORT);
    /**
     * SimpleDateFormat avec comme pattern "dd/MM/yyyy"
     */
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat(TexteConstantesFormatDate.SHORT);
    /**
     * SimpleDateFormat avec comme pattern "dd/MM/yyyy HH:mm"
     */
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat(TexteConstantesFormatDate.LONG);
    
    
    //CONSTRUCTEUR PRIVÉ
    private DateFormats() { }
    
    
    //MÉTHODES STATIQUES
    public static final String utilDateShortStringToSQLShortString(final String date) {
        String res = "";
        try {
            res = DATE_FORMAT_SQL_SHORT.format(DATE_FORMAT_SHORT.parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(DateFormats.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static final String utilDateStringToSQLString(final String date) {
        String res = "";
        try {
            res = DATE_FORMAT_SQL.format(DATE_FORMAT_LONG.parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(DateFormats.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static final Date SQLStringToUtilDate(final String dateString) {
        Date res = null;
        try {
            res = DATE_FORMAT_SQL.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(DateFormats.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static final java.sql.Date SQLStringToSQLDate(final String dateString) {
        java.sql.Date res = null;
        try {
            res = new java.sql.Date(DATE_FORMAT_SQL.parse(dateString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(DateFormats.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    } 
    
}
