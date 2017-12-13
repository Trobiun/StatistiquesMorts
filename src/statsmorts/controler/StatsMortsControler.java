/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.controler;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import statsmorts.classes.FillDataset;
import statsmorts.classes.TypeDatabase;
import statsmorts.classes.TypeGroup;
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
    
    public void setTimeUnit(TimeUnit unit) {
        modele.setTimeUnit(unit);
    }
    
    public void setGroup(TypeGroup type) {
        modele.setGroup(type);
    }
    
    public void createDataset(String titre, ArrayList<FillDataset> objets) {
        modele.createDataset(titre, objets);
    }
    
    public void fillPlateformePanel(long idPlateforme) {
        modele.fillPlateformePanel(idPlateforme);
    }
    
    public void fillGenrePanel(long idGenre) {
        
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
    
    public void connecterServeur(TypeDatabase typeBDD, String adresseServeur, int port, String nomBDD, String utilisateur, char[] password) {
        modele.connecter(typeBDD, nomBDD, nomBDD, password);
    }
    
    public void ajouterPlateforme(String nomPlateforme) {
        if (!nomPlateforme.isEmpty()) {
            modele.ajouterPlateforme(nomPlateforme);
        }
    }
    
    public void modifierPlateforme(long idPlateforme, String nomPlateforme) {
        if (!nomPlateforme.isEmpty()) {
            modele.modifierPlateforme(idPlateforme, nomPlateforme);
        }
    }
    
    public void supprimerPlateforme(long idPlateforme) {
        modele.supprimerPlateforme(idPlateforme);
    }
    
    public void ajouterGenre(String nomGenre) {
        if (!nomGenre.isEmpty()) {
            
        }
    }
    
    public void modifierGenre(long idGenre, String nomGenre) {
        if (!nomGenre.isEmpty()) {
            
        }
    }
    
    public void supprimerGenre(long idGenre) {
        
    }
    
    public void ajouterStudio(String nomStudio) {
        
    }
    
    public void modifierStudio(long idStudio, String nomStudio) {
        if (!nomStudio.isEmpty()) {
            
        }
    }
    
    public void supprimerStudio(long idStudio) {
        
    }
    
    public void ajouterJeu(String titreJeu) {
        if (!titreJeu.isEmpty()) {
            
        }
    }
    
    public void modifierJeu(long idJeu) {
        
    }
    
    public void supprimerJeu(long idJeu) {
        
    }
    
    public void ajouterRun(long idRun, String titreRun, long idJeu) {
        if (!titreRun.isEmpty()) {
            
        }
    }
    
    public void modifierRun(long idRun, String titreRun) {
        if (!titreRun.isEmpty()) {
            
        }
    }
    
    public void supprimerRun(long idRun) {
        
    }
    
    public void ajouterLive() {
        
    }
    
    public void modifierLive(long idLive) {
        
    }
    
    public void supprimerLive(long idLive) {
        
    }
    
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
