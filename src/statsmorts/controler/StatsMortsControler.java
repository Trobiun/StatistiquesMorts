/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.controler;

import statsmorts.modele.StatsMortsModele;

/**
 *
 * @author robin
 */
public class StatsMortsControler {
    
    //ATTRIBUTS
    private final StatsMortsModele modele;
    
    
    //CONSTRUCTEUR
    public StatsMortsControler(StatsMortsModele modele) {
        this.modele = modele;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void actualiser() {
        modele.actualiser();
    }
    
    public void setRun(long idRun) {
        modele.setRun(idRun);
    }
    
    
    
    public void creerBDD(String pathBDD) {
        String lowerCase = pathBDD.toLowerCase();
        if (lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
         || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
         || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
         || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
         || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
         || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
         || lowerCase.endsWith(".sl3")) {
            modele.creerBDD(pathBDD);
        }
    }
    
    public void ouvrirBDD(String pathBDD) {
        String lowerCase = pathBDD.toLowerCase();
        if (lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
         || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
         || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
         || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
         || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
         || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
         || lowerCase.endsWith(".sl3") || lowerCase.endsWith(".kexi")) {
            modele.ouvrirBDD(pathBDD);
        }
    }
    
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
