/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.modele;

import constantes.TexteConstantesSQL;
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
import statsmorts.classes.TypeBasicInputs;

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
        return null != observer;
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
    
    
    //MÉTHODES DE GESTION DE LA BASE DE DONNÉES
    //GESTION BASIC INPUTS (PLATEFORMES, GENRES, STUDIOS)
    public void ajouterBasicInputs(TypeBasicInputs typeBasicInputs, String nom) {
        String table = null;
        String table_nom = null;
        switch (typeBasicInputs) {
            case PLATEFORMES:
                table = TexteConstantesSQL.TABLE_PLATEFORMES;
                table_nom = TexteConstantesSQL.TABLE_PLATEFORMES_NOM;
                break;
            case GENRES:
                table = TexteConstantesSQL.TABLE_GENRES;
                table_nom = TexteConstantesSQL.TABLE_GENRES_NOM;
                break;
            case STUDIOS:
                table = TexteConstantesSQL.TABLE_STUDIOS;
                table_nom = TexteConstantesSQL.TABLE_STUDIOS_NOM;
                break;
            default:
        }
        if (null != table && null != table_nom) {
            final String requete = TexteConstantesSQL.INSERT + " " + table
                    + "(" + table_nom + ")" + TexteConstantesSQL.VALUES + "(?)";
            ResultSet result = connexion.executerPreparedUpdate(requete, nom);
            try {
                ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
                if (resultID.next()) {
                    switch (typeBasicInputs) {
                        case PLATEFORMES:
                            Plateforme plateforme = new Plateforme(resultID.getInt(1), nom);
                            bdd.ajouterPlateforme(plateforme);
                            notifyAddPlateforme(plateforme);
                            break;
                        case GENRES:
                            Genre genre = new Genre(resultID.getInt(1), nom);
                            bdd.ajouterGenre(genre);
                            notifyAddGenre(genre);
                            break;
                        case STUDIOS:
                            Studio studio = new Studio(resultID.getInt(1), nom);
                            bdd.ajouterStudio(studio);
                            notifyAddStudio(studio);
                            break;
                        default:
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void modifierBasicInputs(TypeBasicInputs typeBasicInputs, long id, String nom) {
        String table = null;
        String table_id = null;
        String table_nom = null;
        switch (typeBasicInputs) {
            case PLATEFORMES:
                table = TexteConstantesSQL.TABLE_PLATEFORMES;
                table_id = TexteConstantesSQL.TABLE_PLATEFORMES_ID;
                table_nom = TexteConstantesSQL.TABLE_PLATEFORMES_NOM;
                break;
            case GENRES:
                table = TexteConstantesSQL.TABLE_GENRES;
                table_id = TexteConstantesSQL.TABLE_GENRES_ID;
                table_nom = TexteConstantesSQL.TABLE_GENRES_NOM;
                break;
            case STUDIOS:
                table = TexteConstantesSQL.TABLE_STUDIOS;
                table_id = TexteConstantesSQL.TABLE_STUDIOS_ID;
                table_nom = TexteConstantesSQL.TABLE_STUDIOS_NOM;
                break;
            default:
        }
        if (null != table && null != table_nom) {
            final String requete = TexteConstantesSQL.UPDATE + " "
                + table + " " + TexteConstantesSQL.SET + " " + table_nom
                + " = ? " + TexteConstantesSQL.WHERE + " " + table_id + " = ?";
            ResultSet result = connexion.executerPreparedUpdate(requete, nom, "(long)" + id);
            try {
                ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
                if (resultID.next()) {
                    switch (typeBasicInputs) {
                        case PLATEFORMES:
                             bdd.getPlateformes().get(id).rename(nom);
                            break;
                        case GENRES:
                            bdd.getGenres().get(id).rename(nom);
                            break;
                        case STUDIOS:
                            bdd.getStudios().get(id).rename(nom);
                            break;
                        default:
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void supprimerBasicInputs(TypeBasicInputs typeBasicInputs, long id) {
        String table = null;
        String table_id = null;
        switch (typeBasicInputs) {
            case PLATEFORMES:
                table = TexteConstantesSQL.TABLE_PLATEFORMES;
                table_id = TexteConstantesSQL.TABLE_PLATEFORMES_ID;
                break;
            case GENRES:
                table = TexteConstantesSQL.TABLE_GENRES;
                table_id = TexteConstantesSQL.TABLE_GENRES_ID;
                break;
            case STUDIOS:
                table = TexteConstantesSQL.TABLE_STUDIOS;
                table_id = TexteConstantesSQL.TABLE_STUDIOS_ID;
                break;
            default:
        }
        if (null != table) {
            final String requete = TexteConstantesSQL.DELETE + " " + table
                + " " + TexteConstantesSQL.WHERE + " " + table_id + " = ?";
            ResultSet result = connexion.executerPreparedUpdate(requete,"(long)" + id);
            try {
                ResultSet resultID = connexion.getPreparedStatement().getGeneratedKeys();
                if (resultID.next()) {
                    switch (typeBasicInputs) {
                        case PLATEFORMES:
                             bdd.getPlateformes().remove(id);
                            notifyRemovePlateforme(id);
                            break;
                        case GENRES:
                            bdd.getGenres().remove(id);
                            notifyRemoveGenre(id);
                            break;
                        case STUDIOS:
                            bdd.getStudios().remove(id);
                            notifyRemoveStudio(id);
                            break;
                        default:
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(StatsMortsModele.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            Set<Entry<Long, Plateforme>> plateformes = bdd.getPlateformes().entrySet();
            for (Entry<Long, Plateforme> entryPlateforme : plateformes) {
                Plateforme plateforme = entryPlateforme.getValue();
                notifyAddPlateforme(plateforme);
            }
            Set<Entry<Long, Genre>> genres = bdd.getGenres().entrySet();
            for (Entry<Long, Genre> entryGenre : genres) {
                Genre genre = entryGenre.getValue();
                notifyAddGenre(genre);
            }
            Set<Entry<Long, Studio>> studios = bdd.getStudios().entrySet();
            for (Entry<Long, Studio> entryStudio : studios) {
                Studio studio = entryStudio.getValue();
                notifyAddStudio(studio);
            }
            Set<Entry<Long, Jeu>> set = bdd.getJeux().entrySet();
            for (Entry<Long, Jeu> entryJeu : set) {
                Jeu jeu = entryJeu.getValue();
                notifyAddJeu(jeu);
                Map<Long, Run> runsMap = jeu.getRuns();
                Set<Entry<Long, Run>> runsSet = runsMap.entrySet();
                for (Entry<Long, Run> runEntry : runsSet) {
                    Run run = runEntry.getValue();
                    notifyAddRun(run);
                    Map<Long, Live> livesMap = run.getLives();
                    Set<Entry<Long, Live>> livesSet = livesMap.entrySet();
                    for (Entry<Long, Live> liveEntry : livesSet) {
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
    
    public void fillStudiPanel(long idStudio) {
        notifyFillStudio(idStudio);
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
            observer.removeGenre(idGenre);
        }
    }
    
    @Override
    public void notifyRemoveStudio(long idStudio) {
        if (hasObserver()) {
            observer.removeStudio(idStudio);
        }
    }
    
    @Override
    public void notifyRemoveJeu(long idJeu) {
        if (hasObserver()) {
            observer.removeJeu(idJeu);
        }
    }
    
    @Override
    public void notifyRemoveRun(long idRun) {
        if (hasObserver()) {
            observer.removeRun(idRun);
        }
    }
    
    @Override
    public void notifyRemoveLive(long idLive) {
        if (hasObserver()) {
            observer.removeLive(idLive);
        }
    }
    
    @Override
    public void notifyDataset(String titre, DefaultCategoryDataset dataset) {
        if (hasObserver() && dataset != null) {
            observer.updateDataset(titre,dataset);
        }
    }
    
    @Override
    public void notifyFillPlateforme(long idPlateforme) {
        if (hasObserver()) {
            final Plateforme plateforme = bdd.getPlateformes().get(idPlateforme);
            if (null != plateforme) {
                observer.fillPlateforme(idPlateforme,plateforme.getTitre());
            }
        }
    }
    
    @Override
    public void notifyFillGenre(long idGenre) {
        if (hasObserver()) {
            final Genre genre = bdd.getGenres().get(idGenre);
            if (null != genre) {
                observer.fillGenre(idGenre,genre.getTitre());
            }
        }
    }
    
    @Override
    public void notifyFillStudio(long idStudio) {
        if (hasObserver()) {
            final Studio studio = bdd.getStudios().get(idStudio);
            if (null != studio) { 
                observer.fillStudio(idStudio,studio.getTitre());
            }
        }
    }
    
    @Override
    public void notifyFillJeu(long idJeu) {
        if (hasObserver()) {
            final Jeu jeu = bdd.getJeux().get(idJeu);
            if (null != jeu) {
                observer.fillJeu(idJeu, jeu.getTitre(),jeu.getAnneeSortie());
            }
        }
    }
    
    @Override
    public void notifyFillRun(long idRun) {
        if (hasObserver()) {
            final Run run = bdd.getRuns().get(idRun);
            if (null != run) {
//                observer.
            }
        }
    }
    
    @Override
    public void notifyFillLive(long idLive) {
        if (hasObserver()) {
            
        }
    }
    
}
