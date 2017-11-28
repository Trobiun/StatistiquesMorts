/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

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
import statsmorts.classes.TypeRacine;
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
    private TypeRacine typeRacine;
    
    private Observer observer;
    
    //CONSTRUCTEUR
    public StatsMortsModele(Preferences prefs) {
        connexion = new Connexion();
        preferences = prefs;
        timeUnit = TimeUnit.HOURS;
        typeRacine = preferences.getAffichageRacine();
    }
    
    
    //ACCESSEURS
    
    
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
        bdd = new BDD(connexion,database);
    }
    
    public void connecter(TypeDatabase type, String serveur, String user, String password) {
        bdd = new BDD(connexion, type, serveur, user, password);
    }
    
    public void setTimeUnit(TimeUnit unit) {
        if (!this.timeUnit.equals(unit)) {
            this.timeUnit = unit;
            actualiserDataset();
        }
    }
    
    public void actualiser() {
        try {
            bdd.actualiserBDD(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (observer != null) {
            observer.clear(this.typeRacine);
            if (typeRacine.equals(TypeRacine.PLATEFORMES)) {
                Set<Entry<Long, Plateforme>> plateformes = bdd.getPlateformes().entrySet();
                for (Entry<Long, Plateforme> entryPlateforme : plateformes) {
                    Plateforme plateforme = entryPlateforme.getValue();
                    notifyPlateforme(plateforme);
                }
            }
            if (typeRacine.equals(TypeRacine.GENRES)) {
                Set<Entry<Long, Genre>> genres = bdd.getGenres().entrySet();
                for (Entry<Long, Genre> entryGenre : genres) {
                    Genre genre = entryGenre.getValue();
                    notifyGenre(genre);
                }
            }
            if (typeRacine.equals(TypeRacine.STUDIOS)){
                Set<Entry<Long, Studio>> studios = bdd.getStudios().entrySet();
                for (Entry<Long, Studio> entryStudio : studios) {
                    Studio studio = entryStudio.getValue();
                    notifyStudio(studio);
                }
            }
            Set<Entry<Long,Jeu>> set = bdd.getJeux().entrySet();
            for (Entry<Long,Jeu> entryJeu : set) {
                Jeu jeu = entryJeu.getValue();
                notifyJeu(jeu);
                Map<Long,Run> runsMap = jeu.getRuns();
                Set<Entry<Long,Run>> runsSet = runsMap.entrySet();
                for (Entry<Long,Run> runEntry : runsSet) {
                    Run run = runEntry.getValue();
                    notifyRun(run);
                    Map<Long,Live> livesMap = run.getLives();
                    Set<Entry<Long,Live>> livesSet = livesMap.entrySet();
                    for (Entry<Long,Live> liveEntry : livesSet) {
                        Live live = liveEntry.getValue();
                        notifyLive(live);
                    }
                }
            }
        }
    }
    
    public void fillPlateformePanel(long idPlateforme) {
        notifyFillPlateforme(idPlateforme);
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
            moyenne = sommeDureeVie / count;
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, timeUnit, false);
            dataset.addValue(moyenne,"Moyenne des durées de vie moyennes",live.getDateDebut());
        }
        if (livesForDataset.size() > 1) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie moyennes", "Total");
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
    public void setTypeRacine(TypeRacine type) {
        this.typeRacine = type;
        this.actualiser();
    }
    
    @Override
    public void notifyPlateforme(Plateforme plateforme) {
        if (observer != null) {
            observer.addPlateforme(plateforme);
        }
    }
    
    @Override
    public void notifyGenre(Genre genre) {
        if(observer != null) {
            observer.addGenre(genre);
        }
    }
    
    @Override
    public void notifyStudio(Studio studio) {
        if (observer != null) {
            observer.addStudio(studio);
        }
    }
    
    @Override
    public void notifyJeu(Jeu jeu) {
        if (observer != null) {
            observer.addJeu(jeu);
        }
    }
    
    @Override
    public void notifyRun(Run run) {
        if (observer != null) {
            observer.addRun(run.getJeu().getID(),run);
        }
    }
    
    @Override
    public void notifyLive(Live live) {
        if (observer != null) {
            Run run = live.getRun();
            observer.addLive(run.getID(), live);
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
    
}
