/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.jfree.data.category.DefaultCategoryDataset;
import statsmorts.constantes.TexteConstantes;

/**
 * Une classe pour représenter un jeu dans la base de données.
 * @author Robin
 */
public class Jeu extends ObjectDatabaseWithTitle implements FillDataset, Comparable {
    
    //ATTRIBUTS
    /**
     * L'année de sortie du jeu.
     */
    private int anneeSortie;
    /**
     * Le studio qui a fait le jeu.
     */
    private Studio studio;
    /**
     * L'éditeur du jeu.
     */
    private Editeur editeur;
    /**
     * La collection des plateformes sur lesquelles est sorti le jeu.
     */
    private final HashMap<Long,Plateforme> plateformes;
    /**
     * La collection des genres du jeu.
     */
    private final HashMap<Long,Genre> genres;
    /**
     * La collection des runs/parties faites sur ce jeu.
     */
    private final HashMap<Long,Run> runs;
    
    
    //CONSTRUCTEURS
    /**
     * Crée un jeu sans les attributs studio, plateformes, genres et runs.
     * @param id l'identifiant du jeu dans la base de données
     * @param titre le titre du jeu
     * @param anneeSortie l'année de sortie du jeu
     * @param studio le studio de développement du jeu
     */
    public Jeu(final long id, final String titre, final int anneeSortie, final Studio studio, final Editeur editeur) {
        super(id,titre);
        this.anneeSortie = anneeSortie;
        this.studio = studio;
        this.editeur = editeur;
        plateformes = new HashMap();
        genres = new HashMap();
        runs = new HashMap();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'année de sortie du jeu.
     * @return l'année de sortie du jeu
     */
    public int getAnneeSortie() {
        return anneeSortie;
    }
    
    /**
     * Retourne le studio qui a développé le jeu.
     * @return le studio qui a développé le jeu
     */
    public Studio getStudio() {
        return studio;
    }
    
    /**
     * Retourne l'édteur qui a édité le jeu.
     * @return l'éditeur qui a édité le jeu
     */
    public Editeur getEditeur() {
        return editeur;
    }
    
    /**
     * Retourne la HashMap des plateformes sur lesquelles le jeu est sorti.
     * @return la HashMap des plateformes sur lesquelles le jeu est sorti
     */
    public HashMap<Long,Plateforme> getPlateformes() {
        return plateformes;
    }
    
    /**
     * Retourne un tableau de Long qui contient les identifiants des plateformes
     * sur lesquelles le jeu est sorti. Utilisé pour les présélectionner les plateformes
     * dans les saisies utilisateur.
     * @return un tableau de Long qui contient les identifiants des plateformes
     *         sur lesquelles le jeu est sorti
     */
    public Long[] getPlateformesID() {
        Long[] res = new Long[plateformes.keySet().size()];
        plateformes.keySet().toArray(res);
        return res;
    }
    
    /**
     * Retourne la HashMap des genres du jeu.
     * @return la HashMap des genres du jeu
     */
    public HashMap<Long,Genre> getGenres() {
        return genres;
    }
    
    /**
     * Retourne un tableau de Long qui contient les identifiants des genres
     * du jeu. Utilisé pour les présélectionner les genres
     * dans les saisies utilisateur.
     * @return un tableau de Long qui contient les identifiants des genres
     *         du jeu
     */
    public Long[] getGenresID() {
        Long[] res = new Long[genres.keySet().size()];
        genres.keySet().toArray(res);
        return res;
    }
    
    /**
     * Retourne la HashMap des runs/parties faites sur ce jeu.
     * @return la HashMap des runs/parties faites sur ce jeu
     */
    public HashMap<Long,Run> getRuns() {
        return runs;
    }
    
    /**
     * Retourne la durée totale passé sur ce jeu (fais la somme des durées totales
     * de toutes les runs/parties).
     * @param unit l'unité de temps dans laquelle calculer la durée totale
     * @return la durée totale passé sur ce jeu en float (float pour les heures)
     * @see statsmorts.classes.Run#getTotalDuration(TimeUnit) 
     */
    public float getTotalDuration(final TimeUnit unit) {
        float res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getTotalDuration(unit);
        }
        return res;
    }
    
    /**
     * Retourne le nombre total de lives passé sur ce jeu (fais la somme des lives
     * totaux de toutes les runs/parties).
     * @return le nombre total de lives passé sur ce jeu
     * @see statsmorts.classes.Run#lives
     */
    public int getTotalLives() {
        int res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getNombreLives();
        }
        return res;
    }
    
    /**
     * Retourne le nombre de morts sur ce jeu (fais la somme des morts dans toutes
     * les runs/parties).
     * @return le nombre de morts sur ce jeu
     * @see statsmorts.classes.Run#getTotalMorts()
     */
    public int getTotalMorts() {
        int res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getTotalMorts();
        }
        return res;
    }
    
    /**
     * 
     * @return 
     */
    public int getTotalBoss() {
        int res = 0;
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            res += runEntry.getValue().getTotalBoss();
        }
        return res;
    }
    
    /**
     * Retourne en float la moyenne des durées de vie sur ce jeu (fais la moyenne
     * des moyennes de durée de vie des runs de ce jeu) en unité de temps 'unit'
     * @param unit l'unité de temps dans laquelle calculer la moyenne
     * @return la moyenne des durées de vie sur ce jeu
     */
    public float getMoyenneDureeVie(final TimeUnit unit) {
        float moyenneDesMoyennes = 0, sommeDureeVie = 0, moyenne, sommeMoyennes = 0;
        int count = 0;
        TreeSet<Live> livesTreeSet = new TreeSet();
        Set<Entry<Long,Run>> runsSet = runs.entrySet();
        for (Entry<Long,Run> runEntry : runsSet) {
            livesTreeSet.addAll(runEntry.getValue().getLivesList());
        }
        for (Live live : livesTreeSet) {
            count++;
            sommeDureeVie += live.getDureeVieMoyenne(unit);
            moyenne = sommeDureeVie / (float)count;
            sommeMoyennes += moyenne;
        }
        moyenneDesMoyennes = sommeMoyennes / (float)count;
        return moyenneDesMoyennes;
    }
    
    /**
     * Retourne une chaîne de caractères qui liste les plateformes sur lesquelles
     * le jeu est sorti.
     * @return une chaîne de caractères qui liste les plateformes sur lesquelles
     * le jeu est sorti
     */
    private String plateformesToString() {
        String res = "Plateformes : ";
        Set<Entry<Long,Plateforme>> plateformesSet = plateformes.entrySet();
        Iterator<Entry<Long,Plateforme>> plateformesIterator = plateformesSet.iterator();
        while (plateformesIterator.hasNext()) {
            Entry<Long,Plateforme> plateformeEntry = plateformesIterator.next();
            res += plateformeEntry.getValue().toString();
            if (plateformesIterator.hasNext()) {
                res += ", ";
            }
        }
        res += TexteConstantes.NEW_LINE;
        return res;
    }
    
    /**
     * Retourne une chaîne de caractères qui liste les genres du jeu.
     * @return une chaîne de caractères qui liste les genres du jeu
     */
    private String genresToString() {
        String res = TexteConstantes.GENRES + " : ";
        Set<Entry<Long,Genre>> genresSet = genres.entrySet();
        Iterator<Entry<Long,Genre>> genresIterator = genresSet.iterator();
        while (genresIterator.hasNext()) {
            Entry<Long,Genre> genreEntry = genresIterator.next();
            res += genreEntry.getValue().toString();
            if (genresIterator.hasNext()) {
                res += ", ";
            }
        }
        res += TexteConstantes.NEW_LINE;
        return res;
    }
    
    /**
     * Retourne une chaîne de caractères représentant le jeu, avec ses attributs
     * et des variables calculées (les durées, durées de vie moyennes, morts etc).
     * Utilisée par la méthode getInformations de l'interface Informations.
     * @return une chaîne de caractères représentant le jeu
     * @see statsmorts.vue.Informations#getInformations()
     */
    @Override
    public String toString() {
        int totalMorts = getTotalMorts();
        int boss = getTotalBoss();
        float dureeTotaleHeures = getTotalDuration(TimeUnit.HOURS);
        float dureeTotaleMinutes = getTotalDuration(TimeUnit.MINUTES);
        float mortsParBoss = (boss == 0) ? 0 : (float)(totalMorts) / (float)(boss);
        float heuresParBoss = (boss == 0) ? 0 : dureeTotaleHeures / (float)boss;
        float minutesParBoss = (boss == 0) ? 0 : dureeTotaleMinutes / (float)boss;
        String res = TexteConstantes.TITRE + " : " + super.getTitre() + TexteConstantes.NEW_LINE
                + "" + studio.toString() + TexteConstantes.NEW_LINE
                + "Année de sortie : " + anneeSortie + TexteConstantes.NEW_LINE
                + plateformesToString()
                + genresToString() + TexteConstantes.NEW_LINE
                + "Total de runs : " + runs.size() + TexteConstantes.NEW_LINE
                + "Total de lives : " + getTotalLives() + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Durée totale : " + dureeTotaleHeures + " heures" + TexteConstantes.NEW_LINE
                + "Durée totale : " + (int) dureeTotaleHeures + "h" + (int) (dureeTotaleMinutes % 60) + "m" + TexteConstantes.NEW_LINE
                + "Durée totale : " + dureeTotaleMinutes + " minutes" + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Total de morts : " + totalMorts + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Durée de vie moyenne : " + (dureeTotaleHeures / ((float) totalMorts + 1)) + " heures" + TexteConstantes.NEW_LINE
                + "Durée de vie moyenne : " + (dureeTotaleMinutes / ((float) totalMorts + 1)) + " minutes" + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Moyenne des durées de vie : " + getMoyenneDureeVie(TimeUnit.HOURS) + " heures" + TexteConstantes.NEW_LINE
                + "Moyenne des durées de vie : " + getMoyenneDureeVie(TimeUnit.MINUTES) + " minutes" + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Boss vaincus : " + boss + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Moyenne de morts par boss : " + mortsParBoss + TexteConstantes.NEW_LINE + TexteConstantes.NEW_LINE
                + "Temps moyen par boss : " + heuresParBoss + " heures" + TexteConstantes.NEW_LINE
                + "Temps moyen par boss : " + minutesParBoss + " minutes" + TexteConstantes.NEW_LINE;
        return res;
    }
    
    
    //MUTATEURS
    /**
     * Change l'année de sortie du jeu.
     * @param anneeSortie la nouvelle année de sortie du jeu
     */
    public void setAnneeSortie(final int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }
    
    /**
     * Supprime les occurrences de ce jeu dans les plateformes sur lesquelles il
     * est sorti puis vide la map des plateformes du jeu.
     */
    public void clearPlateformes() {
        Set<Entry<Long, Plateforme>> setPlateformes = plateformes.entrySet();
        for (Entry<Long, Plateforme> entry : setPlateformes) {
            entry.getValue().supprimerJeu(super.getID());
        }
        plateformes.clear();
    }
    
    /**
     * Supprime lzs occurrences de ce jeu dans les genres du jeu puis vide la map
     * des genres du jeu.
     */
    public void clearGenres() {
        Set<Entry<Long, Genre>> setGenres = genres.entrySet();
        for (Entry<Long, Genre> entry : setGenres) {
            entry.getValue().supprimerJeu(super.getID());
        }
        genres.clear();
    }
    
    /**
     * Supprime l'occurrence de ce jeu dans le studio du jeu, puis change le studio
     * du jeu par le studio 'studio'.
     * @param studio le nouveau studio du jeu
     */
    public void setStudio(final Studio studio) {
        if(null != this.studio) {
            this.studio.supprimerJeu(super.getID());
        }
        this.studio = studio;
    }
    
    /**
     * Supprime l'occurrence de ce jeu dans l'éditeur du jeu, puis change l'éditeur
     * du jeu par l'éditeur 'editeur'.
     * @param editeur le nouvel éditeur du jeu
     */
    public void setEditeur(final Editeur editeur) {
        if(null != this.editeur) {
            this.editeur.supprimerJeu(super.getID());
        }
        this.editeur = editeur;
    }
    
    /**
     * Ajoute une plateforme à la map des plateformes de ce jeu.
     * @param plateforme la plateforme à ajouter
     */
    public void ajouterPlateforme(final Plateforme plateforme) {
        plateformes.put(plateforme.getID(),plateforme);
    }
    
    /**
     * Supprime l'occurrence de ce jeu dans la plateforme dont l'identifiant est 
     * 'idPlateforme' puis supprime cette plateforme de la map du jeu.
     * @param idPlateforme l'identifiant de la plateforme à supprimer
     */
    public void supprimerPlateforme(final long idPlateforme) {
        plateformes.get(idPlateforme).supprimerJeu(super.getID());
        plateformes.remove(idPlateforme);
    }
    
    /**
     * Ajout un genre à la map des genres de ce jeu.
     * @param genre le genre à ajouter
     */
    public void ajouterGenre(final Genre genre) {
        genres.put(genre.getID(),genre);
    }
    
    /**
     * Supprime l'occurrence de ce jeu dans le genre dont l'identifiant est
     * 'idGenre' puis supprime le genre de la map du jeu.
     * @param idGenre l'identifiant du genre à supprimer
     */
    public void supprimerGenre(final long idGenre) {
        genres.get(idGenre).supprimerJeu(super.getID());
        genres.remove(idGenre);
    }
    
    /**
     * Ajoute une run à la map des runs de ce jeu.
     * @param run la run à ajouter
     */
    public void ajouterRun(final Run run) {
        runs.put(run.getID(),run);
    }
    
    /**
     * Supprime la run dont l'identifiant est 'idRun'.
     * @param idRun l'identifiant de la run à supprimer
     */
    public void supprimerRun(final long idRun) {
        runs.remove(idRun);
    }
    
    
    /**
     * Supprime toutes les occurrences de ce jeu dans tous les plateformes, genres
     * et studio liés à ce jeu.
     */
    public void supprimerJeu() {
        clearPlateformes();
        clearGenres();
        if (null != studio) {
            studio.supprimerJeu(super.getID());
        }
    }
    
    
    //INTERFACE FILLDATASET
    /**
     * @inheritDoc
     */
    @Override
    public String getTitreDataset() {
        return super.getTitre();
    }
    
    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Live> getLivesList() {
        ArrayList<Live> livesList = new ArrayList();
        Set<Entry<Long,Run>> setRuns = runs.entrySet();
        for (Entry<Long,Run> entryRun : setRuns) {
            livesList.addAll(entryRun.getValue().getLivesList());
        }
        return livesList;
    }
    
    /**
     * @inheritDoc
     */
    @Override
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total) {
        ArrayList<Live> livesList = this.getLivesList();
        Collections.sort(livesList);
        
        float moyenne = 0, sommeDureeVie = 0, sommeMoyennes = 0;
        int count = 0, sommeMorts = 0;
//        long sommeMinutes = 0;
        float sommeTime = 0;
        for (Live live : livesList) {
            sommeTime += live.getDuration(unit);
            sommeMorts += live.getMorts();
            sommeDureeVie += live.getDureeVieMoyenne(unit);
            count++;
            moyenne = sommeDureeVie / count;
            sommeMoyennes += moyenne;
            live.fillDataset(dataset, unit, false);
            dataset.addValue(moyenne,"Moyenne des durées de vie",Live.DATE_FORMAT_SHORT.format(live.getDateDebut()));
        }
        if (total) {
            float dureeVieMoyenneTotale = (float)sommeTime / (float)(sommeMorts + 1);
            moyenne = sommeMoyennes / (float)count;
            dataset.addValue(sommeMorts, "Morts", "Total");
            dataset.addValue(dureeVieMoyenneTotale, "Durée de vie moyenne", "Total");
            dataset.addValue(sommeTime, "Durée du live", "Total");
            dataset.addValue(moyenne, "Moyenne des durées de vie", "Total");
        }
        
    }
    
    
    //INTERFACE COMPARABLE
    /**
     * @inheritDoc
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Jeu){
            return super.getTitre().compareTo(((Jeu)o).getTitre());
        }
        else {
            return super.getTitre().compareTo(o.toString());
        }
    }
    
}
