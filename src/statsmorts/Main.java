/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import statsmorts.classes.TypeDatabase;
import statsmorts.controler.StatsMortsControler;
import statsmorts.modele.StatsMortsModele;
import statsmorts.preferences.Preferences;
import statsmorts.preferences.ServeurOptions;
import statsmorts.preferences.TypeServeurFichier;
import statsmorts.vue.Fenetre;

/**
 *
 * @author Robin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Préférences : ");
        long start = System.currentTimeMillis();
        File dossPrefs = new File(System.getProperty("user.home") + File.separator + ".StatsMorts");
        dossPrefs.mkdir();
        Preferences preferences = new Preferences(dossPrefs.getPath() + File.separator + "Preferences.ini");
        long endPrefs = System.currentTimeMillis();
        System.out.println("    Total : " + (endPrefs - start) + " ms");
        
        System.out.println("Modèle : ");
        long startModele = System.currentTimeMillis();
        StatsMortsModele modele = new StatsMortsModele(preferences);
        TypeServeurFichier type = preferences.getType();
        if (type.equals(TypeServeurFichier.FICHIER)) {
            modele.connecter(preferences.getBDDFichier());
        }
        else {
            ServeurOptions options = new ServeurOptions(preferences,true);
            Object[] boutons = {"Se connecter","Quitter"};
            int res;
            res = JOptionPane.showOptionDialog(null,options,"Se connecter à une base de données",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,boutons,null);
            if (res == JOptionPane.YES_OPTION && !options.getType().equals("Fichier")) {
                modele.connecter(TypeDatabase.valueOf(options.getType()), options.getAdresse() + ":" + options.getPort() + "/" + options.getBaseDonnees(), options.getUtilisateur(),options.getPassword());
            }
            if (res == JOptionPane.YES_OPTION  && options.getType().equals("Fichier")) {
                modele.connecter(preferences.getBDDFichier());
            }
            if (res == JOptionPane.NO_OPTION) {
                long end = System.currentTimeMillis();
                System.out.println("Total : " + (end - start) + " ms");
                System.exit(0);
            }
        }
        long endModele= System.currentTimeMillis();
        System.out.println("    Total: " + (endModele - startModele) + " ms");
        
        System.out.println("Controler : ");
        long startControler = System.currentTimeMillis();
        StatsMortsControler controler = new StatsMortsControler(modele);
        long endControler = System.currentTimeMillis();
        System.out.println("    Total : " + (endControler - startControler) + " ms");
        
        System.out.println("Fenêtre : ");
        long startFenetre = System.currentTimeMillis();
        Fenetre fenetre = new Fenetre("Statistiques des morts de MyGowD", controler, preferences);
        long endFenetre = System.currentTimeMillis();
        System.out.println("    Total : " + (endFenetre - startFenetre) + " ms");
        
        System.out.println("Actualisation : ");
        long startActu = System.currentTimeMillis();
        modele.setObserver(fenetre);
        modele.actualiser();
        long end = System.currentTimeMillis();
        System.out.println("    Total: " + (end - startActu) + " ms");
        System.out.println("Total : " + (end - start) + " ms");
//        Fenetre fenetre = new Fenetre("Test");
//        Connexion connexion = new Connexion();
//        connexion.connecter(preferences.getBDDFichier());
//        
//        String titre = "Darkwood";
//        String requete = "SELECT * FROM VueGlobale WHERE jeu_Titre = ? ORDER BY liv_DateDebut ASC";
//        ResultSet rs = connexion.executerPreparedSelect(requete,titre);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
//        DefaultCategoryDataset datasetMPD = new DefaultCategoryDataset();
//        float moyenne, sommeMPD = 0, sommeHeures = 0, sommeMoyennes = 0;
//        int count = 0, sommeMorts = 0;
//        long sommeMinutes = 0;
//        while (rs.next()) {
//            String dateDebutString = rs.getString("liv_DateDebut");
//            String dateFinString = rs.getString("liv_DateFin");
//            int morts = rs.getInt("liv_Morts");
//            //calculs heures + minutes
//            if(dateDebutString == null) {
//                continue;
//            }
//            Date dateDebut = df.parse(dateDebutString);
//            Date dateFin = df.parse(dateFinString);
//            long diff = dateFin.getTime() - dateDebut.getTime();
//            long heuresLong = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
//            long minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
//            float heures = (float)heuresLong + (float)(minutes % 60) / (float)60;
//            
//            float mpd = heures / (float)(morts + 1);
//            count++;
//            sommeMorts += morts;
//            sommeHeures += heures;
//            sommeMinutes += minutes;
//            sommeMPD += mpd;
//            moyenne = sommeMPD / count;
//            sommeMoyennes += moyenne;
//            
//            datasetMPD.addValue(morts, "Morts", dateDebut);
//            datasetMPD.addValue(heures, "Durée du live (heures)", dateDebut);
//            datasetMPD.addValue(mpd, "Durée de vie moyenne", dateDebut);
//            datasetMPD.addValue(moyenne, "Moyenne des durées de vie moyennes", dateDebut);
//        }
//        
//        float totalMPD = (float)sommeHeures / (float)(sommeMorts + 1);
//        moyenne = sommeMoyennes / (float)count;
//        datasetMPD.addValue(sommeMorts, "Morts", "Total");
//        datasetMPD.addValue(totalMPD, "Durée de vie moyenne", "Total");
//        datasetMPD.addValue(sommeHeures, "Durée du live (heures)", "Total");
//        datasetMPD.addValue(moyenne, "Moyenne des durées de vie moyennes", "Total");
//        
//        JFreeChart chart = ChartFactory.createLineChart("Morts Lives " + titre, "Date live", "", datasetMPD, PlotOrientation.VERTICAL, true, true, false);
//        
//        LineAndShapeRenderer renderer = (LineAndShapeRenderer)chart.getCategoryPlot().getRenderer();
//        renderer.setBaseShapesVisible(true);
//        
//        ChartFrame frame = new ChartFrame("Morts sur " + titre,chart);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
