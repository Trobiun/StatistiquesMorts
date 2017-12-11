/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 *
 * @author robin
 */
public enum TypeDatabase {
    
    //ENUMERATION
    Access("net.ucanaccess.jdbc.UcanaccessDriver","jdbc:ucanaccess://",";newdatabaseversion=V2010"),
    SQLite("org.sqlite.JDBC","jdbc:sqlite:"),
    MySQL("com.mysql.jdbc.Driver","jdbc:mysql://"),
    PostgreSQL("org.postgresql.Driver","jdbc:postgresql://");
    
    
    //ATTRIBUTS
    private final String driver;
    private final String prefixe;
    private String suffixe;
    
    
    //CONSTRUCTEUR
    private TypeDatabase(String driver, String prefixe, String suffixe) {
        this.driver = driver;
        this.prefixe = prefixe;
        this.suffixe = suffixe;
    }
    private TypeDatabase(String driver, String prefixe) {
        this(driver,prefixe,"");
    }
    
    
    //ACCESSEURS
    public String getDriver() {
        return driver;
    }
    
    public String getPrefixe() {
        return prefixe;
    }
    
    public String getSuffixe() {
        return suffixe;
    }
    
    
    //MUTATEURS
    
}
