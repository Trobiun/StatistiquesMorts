/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts;

import statsmorts.classes.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Robin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Connexion connexion = new Connexion();
        connexion.connecter("S:\\MortsMyGowD.sqlite3");
        ResultSet rs = connexion.executerRequete("SELECT * FROM VueGlobale");
        
        while (rs.next()) {
            System.out.println(rs.getString("liv_DateDebut"));
        }
    }
    
}
