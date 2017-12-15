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
import statsmorts.classes.TypeBasicInputs;

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
    //MÉTHODES AYANT UN IMPACT SEULEMENT SUR LA VUE
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
        if (idPlateforme >= 0) {
            modele.fillPlateformePanel(idPlateforme);
        }
    }
    
    public void fillGenrePanel(long idGenre) {
        if (idGenre >= 0) {
            modele.fillGenrePanel(idGenre);
        }
    }
    
    public void fillStudioPanel(long idStudio) {
        if (idStudio >= 0) {
            modele.fillStudiPanel(idStudio);
        }
    }
    
    public void fillJeuPanel(long idJeu) {
        if (idJeu >= 0) {
            
        }
    }
    
    //MÉTHODES AYANT UN IMPACT SUR LE MODÈLE
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
    
    public void ajouterBasicInputs(TypeBasicInputs typeInputs, String nom ) {
        if (null != typeInputs && null != nom && !nom.isEmpty()) {
            modele.ajouterBasicInputs(typeInputs, nom);
        }
    }
    
    public void modifierBasicInputs(TypeBasicInputs typeInputs, long id, String nom) {
        if (null != typeInputs && id >= 0 && null != nom && !nom.isEmpty()) {
            modele.modifierBasicInputs(typeInputs, id, nom);
        }
    }
    
    public void supprimerBasicInputs(TypeBasicInputs typeInputs, long id) {
        if (null != typeInputs & id >= 0) {
            modele.supprimerBasicInputs(typeInputs, id);
        }
    }
//    
//    public void ajouterPlateforme(String nomPlateforme) {
//        if (null != nomPlateforme && !nomPlateforme.isEmpty()) {
//            modele.ajouterPlateforme(nomPlateforme);
//        }
//    }
    
//    public void modifierPlateforme(long idPlateforme, String nomPlateforme) {
//        if (idPlateforme >= 0 && null != nomPlateforme && !nomPlateforme.isEmpty()) {
//            modele.modifierPlateforme(idPlateforme, nomPlateforme);
//        }
//    }
//    
//    public void supprimerPlateforme(long idPlateforme) {
//        if(idPlateforme >= 0) {
//            modele.supprimerPlateforme(idPlateforme);
//        }
//    }
    
//    public void ajouterGenre(String nomGenre) {
//        if (null != nomGenre && !nomGenre.isEmpty()) {
//            
//        }
//    }
//    
//    public void modifierGenre(long idGenre, String nomGenre) {
//        if (idGenre >= 0 && !nomGenre.isEmpty()) {
//            
//        }
//    }
//    
//    public void supprimerGenre(long idGenre) {
//        if (idGenre >= 0) {
//            
//        }
//    }
    
//    public void ajouterStudio(String nomStudio) {
//        
//    }
//    
//    public void modifierStudio(long idStudio, String nomStudio) {
//        if (idStudio >= 0 && null != nomStudio && !nomStudio.isEmpty()) {
//            
//        }
//    }
//    
//    public void supprimerStudio(long idStudio) {
//        if (idStudio >= 0) {
//            
//        }
//    }
    
    public void ajouterJeu(String titreJeu) {
        if (null != titreJeu && !titreJeu.isEmpty()) {
            
        }
    }
    
    public void modifierJeu(long idJeu) {
        if (idJeu >= 0) {
            
        }
    }
    
    public void supprimerJeu(long idJeu) {
        if (idJeu >= 0) {
            
        }
    }
    
    public void ajouterRun(long idRun, String titreRun, long idJeu) {
        if (idRun >= 0 && idJeu >= 0 && null != titreRun && !titreRun.isEmpty()) {
            
        }
    }
    
    public void modifierRun(long idRun, String titreRun) {
        if (idRun >= 0 && null != titreRun && !titreRun.isEmpty()) {
            
        }
    }
    
    public void supprimerRun(long idRun) {
        if (idRun >= 0) {
            
        }
    }
    
    public void ajouterLive() {
        
    }
    
    public void modifierLive(long idLive) {
        if (idLive >= 0) {
            
        }
    }
    
    public void supprimerLive(long idLive) {
        if (idLive >= 0) {
            
        }
    }
    
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
