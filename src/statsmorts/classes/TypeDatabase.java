/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 * Une numération qui permet de déterminer comment se connecter à la base de données.
 * @author robin
 */
public enum TypeDatabase {
    
    //ENUMERATION
    /**
     * Type Access pour les bases de données Microsoft Access. (fichier)
     */
    Access("net.ucanaccess.jdbc.UcanaccessDriver","jdbc:ucanaccess://",";newdatabaseversion=V2010"),
    /**
     * Type SQLite pour les bases de données SQLite. (fichier)
     */
    SQLite("org.sqlite.JDBC","jdbc:sqlite://"),
    /**
     * Type MysQL pour les bases de données MySQL. (serveur)
     */
    MySQL("com.mysql.jdbc.Driver","jdbc:mysql://"),
    /**
     * Type PostgreSQL pour les bases de données PostgreSQL. (serveur)
     */
    PostgreSQL("org.postgresql.Driver","jdbc:postgresql://");
    
    
    //ATTRIBUTS
    /**
     * Le driver utilisé avec Class.forName(driver).
     */
    private final String driver;
    /**
     * Le préfixe de l'URL/chemin vers la base de données.
     */
    private final String prefixe;
    /**
     * Le suffixe de l'URL/chemin vers la base de données.
     * (utilisé surtout pour le type Access pour créer la base de données)
     */
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
    /**
     * Retourne le driver du type de base de données.
     * @return le driver à utiliser.
     */
    public String getDriver() {
        return driver;
    }
    
    /**
     * Retourne le préfixe pour se connecter à la base de données.
     * @return le préfixe utilisé pour préfixer le chemin de la base de données.
     */
    public String getPrefixe() {
        return prefixe;
    }
    
    /**
     * Retourne le suffixe à pour se connecter à la base de données.
     * @return le préfixe utilisé pour suffixer le chemin de la base de données.
     */
    public String getSuffixe() {
        return suffixe;
    }
    
    
    //MUTATEURS
    
}
