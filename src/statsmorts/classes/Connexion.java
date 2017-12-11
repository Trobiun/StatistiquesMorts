/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

/**
 *
 * @author Robin
 */

import java.sql.*;
import java.util.logging.*;

public class Connexion {
    
    //ATTRIBUTS
    private TypeDatabase type;
    private String database;
    private Connection connexion;
    private Statement statement;
    private PreparedStatement preparedStatement;
    
    //CONSTRUCTEUR
    public Connexion() {
        
    }
    
    
    //ACCESSSEURS
    public TypeDatabase getType() {
        return type;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
    
    //MUTATEURS
    private void setType(TypeDatabase type) {
        this.type = type;
    }
    
    private void setDatabase(String db) {
        database = db;
    }
    
    private void setConnection(String user, String password) {
        try {
            Class.forName(type.getDriver());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            connexion = DriverManager.getConnection(database,user,password);
            statement = connexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connecter(String path) {
        String lowerCase = path.toLowerCase();
        boolean isAccess = lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb");
        boolean isSQLite = lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
                        || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db3")
                        || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
                        || lowerCase.endsWith(".sl3") || lowerCase.endsWith(".db2")
                        || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
                        || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".kexi");
        
        if (isAccess) {
            setType(TypeDatabase.Access);
        }
        if (isSQLite) {
            setType(TypeDatabase.SQLite);
        }
        setDatabase(type.getPrefixe() + path + type.getSuffixe());
        setConnection(System.getProperty("user.name"),"");
        
        try {
            statement.setQueryTimeout(30);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connecter(TypeDatabase type, String serveur, String user, char[] password) {
        setType(type);
        setDatabase(type.getPrefixe() + serveur + type.getSuffixe());
        setConnection(user,new String(password));
        try {
            statement.setQueryTimeout(30);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void preparedSetParameters(PreparedStatement prepared, String ... args) throws SQLException {
        //mise en place des paramètres pour le PreparedStatement
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("(int)")) {
                prepared.setInt(i+1, Integer.parseInt(args[i].replace("(int)","")));
            }
            else {
                if (args[i].startsWith("(bool)")) {
                    prepared.setBoolean(i+1, Boolean.parseBoolean(args[i].replace("(bool)","")));
                }
                else {
                    prepared.setString(i+1, args[i]);
                }
            }
        }
    }
    
    public ResultSet executerPreparedSelect(String requete, String ... args) {
        if (requete.toUpperCase().startsWith("SELECT")) {
            try {
                preparedStatement = connexion.prepareStatement(requete, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                
                preparedSetParameters(preparedStatement, args);
                
                return preparedStatement.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet executerPreparedUpdate(String requete, String  ... args) {
        String upperCase = requete.substring(0,7).toUpperCase();
        ResultSet res = null;
        if (upperCase.startsWith("UPDATE") || upperCase.startsWith("INSERT") || upperCase.startsWith("DELETE")) {
            try {
                preparedStatement = connexion.prepareStatement(requete, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                //mise en place des paramètres pour le PreparedStatement
                for (int i = 0; i < args.length; i++) {
                    if (args[i].startsWith("(int)")) {
                        preparedStatement.setInt(i+1, Integer.parseInt(args[i].replace("(int)","")));
                    }
                    else {
                        if (args[i].startsWith("(bool)")) {
                            preparedStatement.setBoolean(i+1, Boolean.parseBoolean(args[i].replace("(bool)","")));
                        }
                        else {
                            if (args[i].startsWith("(long)")) {
                                preparedStatement.setLong(i+1,Long.parseLong(args[i].replace("(long)","")));
                            }
                            else {
                                preparedStatement.setString(i+1, args[i]);
                            }
                        }
                    }
                }
                
                preparedStatement.executeUpdate();
//                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }
    
    public ResultSet executerRequete(String requete) {
        ResultSet res = null;
        try {
            res = statement.executeQuery(requete);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public void executerUpdate(String requete) {
        try {
            statement.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deconnecter() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
