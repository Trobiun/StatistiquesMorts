/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.classes.BDD;
import statsmorts.classes.Connexion;
import statsmorts.classes.FillDataset;
import statsmorts.classes.Genre;
import statsmorts.classes.Jeu;
import statsmorts.classes.Live;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Run;
import statsmorts.classes.Studio;
import statsmorts.classes.TypeDatabase;
import statsmorts.classes.TypeGroup;
import statsmorts.observer.Observable;
import statsmorts.observer.Observer;
import statsmorts.preferences.Preferences;

/**
 *
 * @author Robin
 */
public class StatsMortsModele implements Observable {
    
    //ATTRIBUTS
    private final Preferences preferences;
    private final Connexion connexion;
    private BDD bdd;
    private TimeUnit timeUnit;
    private TreeSet<Live> livesForDataset;
    private String titreForDataset;
    private TypeGroup typeGroup;
    
    private Observer observer;
    
    
    //CONSTRUCTEUR
    public StatsMortsModele(Preferences prefs) {
        connexion = new Connexion();
        preferences = prefs;
        timeUnit = preferences.getAffichageTemps().getTimeUnit();
        typeGroup = preferences.getAffichageGroup();
        livesForDataset = new TreeSet();
    }
    
    
    //ACCESSEURS
    public boolean hasObserver() {
        return observer != null;
    }
    
    
    //MUTATEURS
    public void creerBDD(String pathBDD) {
        deconnecter();
        bdd = BDD.creerBDD(connexion,pathBDD);
        actualiser();
    }
    
    public void ouvrirBDD(String pathBDD) {
        deconnecter();
        bdd = new BDD(connexion,pathBDD);
        actualiser();
    }
    
    public void connecter(String database) {
        deconnecter();
        bdd = new BDD(connexion,database);
    }
    
    public void connecter(TypeDatabase type, String serveur, String user, char[] password) {
        deconnecter();
        bdd = new BDD(connexion, type, serveur, user, password);
    }
    
    public void ajouterPlateforme(String nomPlateforme) {
        String requete = "INSERT INTO Plateformes (pla_Nom) VALUES (?)";
        try {
            ResultSet result = connexion.executerPreparedUpdate(requete, nomPlateforme);
            ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
            if (resultID.next()) {
                System.out.println("lol");
                Plateforme plateforme = new Plateforme(resultID.getInt(1),nomPlateforme);
                bdd.ajouterPlateforme(plateforme);
                notifyAddPlateforme(plateforme);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modifierPlateforme(long idPlateforme, String nomPlateforme) {
        String requete = "UPDATE Plateformes SET pla_Nom = ? WHERE pla_id = ?";
        ResultSet result = connexion.executerPreparedUpdate(requete,nomPlateforme,"(long)" + idPlateforme);
        bdd.getPlateformes().get(idPlateforme).rename(nomPlateforme);
    }
    
    public void supprimerPlateforme(long idPlateforme) {
        String requete = "DELETE FROM Plateformes WHERE pla_id = ?";
        ResultSet result = connexion.executerPreparedUpdate(requete,"(long)" + idPlateforme);
        bdd.getPlateformes().remove(idPlateforme);
        notifyRemovePlateforme(idPlateforme);
        //actualiser();
    }
    
    
    public void setTimeUnit(TimeUnit unit) {
        if (!this.timeUnit.equals(unit)) {
            this.timeUnit = unit;
            if (!livesForDataset.isEmpty()) {
                actualiserDataset();
            }
        }
    }
    
    public void actualiser() {
        try {
            bdd.actualiserBDD(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (observer != null) {
            observer.clear(this.typeGroup);
//            if (typeRacine.equals(TypeGroup.PLATEFORMES)) {
                Set<Entry<Long, Plateforme>> plateformes = bdd.getPlateformes().entrySet();
                for (Entry<Long, Plateforme> entryPlateforme : plateformes) {
                    Plateforme plateforme = entryPlateforme.getValue();
                    notifyAddPlateforme(plateforme);
                }
//            }
//            if (typeRacine.equals(TypeGroup.GENRES)) {
                Set<Entry<Long, Genre>> genres = bdd.getGenres().entrySet();
                for (Entry<Long, Genre> entryGenre : genres) {
                    Genre genre = entryGenre.getValue();
                    notifyAddGenre(genre);
                }
//            }
//            if (typeRacine.equals(TypeGroup.STUDIOS)){
                Set<Entry<Long, Studio>> studios = bdd.getStudios().entrySet();
                for (Entry<Long, Studio> entryStudio : studios) {
                    Studio studio = entryStudio.getValue();
                    notifyAddStudio(studio);
                }
//            }
            Set<Entry<Long,Jeu>> set = bdd.getJeux().entrySet();
            for (Entry<Long,Jeu> entryJeu : set) {
                Jeu jeu = entryJeu.getValue();
                notifyAddJeu(jeu);
                Map<Long,Run> runsMap = jeu.getRuns();
                Set<Entry<Long,Run>> runsSet = runsMap.entrySet();
                for (Entry<Long,Run> runEntry : runsSet) {
                    Run run = runEntry.getValue();
                    notifyAddRun(run);
                    Map<Long,Live> livesMap = run.getLives();
                    Set<Entry<Long,Live>> livesSet = livesMap.entrySet();
                    for (Entry<Long,Live> liveEntry : livesSet) {
                        Live live = liveEntry.getValue();
                        notifyAddLive(live);
                    }
                }
            }
        }
    }
    
    public void fillPlateformePanel(long idPlateforme) {
        notifyFillPlateforme(idPlateforme);
    }
    
    public void fillGenrePanel(long idGenre) {
        notifyFillGenre(idGenre);
    }
    
    public void createDataset(String titre, ArrayList<FillDataset> nodes) {
        livesForDataset = new TreeSet();
        for (FillDataset node : nodes) {
            livesForDataset.addAll(node.getLivesList());
        }
        this.titreForDataset = titre;
        actualiserDataset();
    }
    
    public void actualiserDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        float moyenne = 0, sommeDureeVie = 0, sommeMoyennes = 0;
        int count = 0, sommeMorts = 0;
        float sommeTime = 0;
        for (Live live : livesForDataset) {
            sommeTime += live.getDuration(timeUnit);
            sommeMorts += live.getMorts();
            sommeDureeVie += live.getDureeVieMoyenne(timeUnit);
            count++;
            moyenne = sommeDureeVie / (float)count;
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, timeUnit, false);
            dataset.addValue(moyenne,"Moyenne des durées de vie",live.getDateDebut());
        }
        if (livesForDataset.size() > 1) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie", "Total");
        }
        notifyDataset(titreForDataset, dataset);
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
    
    //OBSERVER
    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
    
    @Override
    public void setGroup(TypeGroup type) {
        this.typeGroup = type;
        this.actualiser();
    }
    
    @Override
    public void notifyAddPlateforme(Plateforme plateforme) {
        if (hasObserver()) {
            observer.addPlateforme(plateforme);
        }
    }
    
    @Override
    public void notifyAddGenre(Genre genre) {
        if(hasObserver()) {
            observer.addGenre(genre);
        }
    }
    
    @Override
    public void notifyAddStudio(Studio studio) {
        if (hasObserver()) {
            observer.addStudio(studio);
        }
    }
    
    @Override
    public void notifyAddJeu(Jeu jeu) {
        if (hasObserver()) {
            observer.addJeu(jeu);
        }
    }
    
    @Override
    public void notifyAddRun(Run run) {
        if (hasObserver()) {
            observer.addRun(run.getJeu().getID(),run);
        }
    }
    
    @Override
    public void notifyAddLive(Live live) {
        if (hasObserver()) {
            Run run = live.getRun();
            observer.addLive(run.getID(), live);
        }
    }
    
    @Override
    public void notifyRemovePlateforme(long idPlateforme) {
        if (hasObserver()) {
            observer.removePlateforme(idPlateforme);
        }
    }
    
    @Override
    public void notifyRemoveGenre(long idGenre) {
        if (hasObserver()) {
            
        }
    }
    
    @Override
    public void notifyRemoveStudio(long idStudio) {
        if (hasObserver()) {
            
        }
    }
    
    @Override
    public void notifyRemoveJeu(long idJeu) {
        if (hasObserver()) {
            
        }
    }
    
    @Override
    public void notifyRemoveRun(long idRun) {
        if (hasObserver()) {
            
        }
    }
    
    @Override
    public void notifyRemoveLive(long idLive) {
        if (hasObserver()) {
            
        }
    }
    
    @Override
    public void notifyDataset(String titre, DefaultCategoryDataset dataset) {
        if (observer != null && dataset != null) {
            observer.updateDataset(titre,dataset);
        }
    }
    
    @Override
    public void notifyFillPlateforme(long idPlateforme) {
        if (observer != null) {
            observer.fillPlateforme(idPlateforme,bdd.getPlateformes().get(idPlateforme).getTitre());
        }
    }
    
    @Override
    public void notifyFillGenre(long idGenre) {
        if (observer != null) {
            observer.fillGenre(idGenre,bdd.getGenres().get(idGenre).getTitre());
        }
    }
    
}
