/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import statsmorts.classes.Genre;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Studio;
import statsmorts.constantes.TexteConstantesFormatDate;
import statsmorts.controler.StatsMortsControler;

/**
 * Une classe  gérer les entrées utilisateur pour gérer les jeux de la base de
 * données.
 * @author Robin
 */
public class JeuPanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    //JPANElS
    private JPanel plateformesPanel;
    private JPanel genresPanel;
    private JPanel studioPanel;
    private JPanel dateSortiePanel;
    //ENTREES UTILISATEUR
    /**
     * La ScrollableJList des plateformes.
     */
    private ScrollableJList listPlateformes;
    /**
     * La ScrollableJList des genres.
     */
    private ScrollableJList listGenres;
    /**
     * La ScrollableJList des studios.
     */
    private ScrollableJList listStudios;
    /**
     * Le JSpinner pour saisir la date de sortie du jeu.
     */
    private DateSpinner dateSortieSpinner;
    /**
     * Un SimpleDateFormat pour l'année de sortie.
     */
    private SimpleDateFormat dateFormat;
    
    
    //CONSTRUCTEURS
    public JeuPanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.JEU);
        this.init();
        this.setComponents();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne l'année de sortie d'un jeu en entier.
     * @return 
     */
    public String getDateSortie() {
//        return dat.parseInt(dateFormat.format(dateSortieSpinner.getValue()));
        return dateFormat.format(dateSortieSpinner.getDate());
    }
    
    /**
     * Retourne une liste des identifiants des plateformes sélectionnées.
     * @return une liste des identifiants des plateformes sélectionnées
     */
    public List<Long> getPlateformes() {
        return listPlateformes.getSelectionID();
    }
    
    /**
     * Retourne une liste des identifiants des genres sélectionnés.
     * @return une liste des identifiants des genres sélectionnés
     */
    public List<Long> getGenres() {
        return listGenres.getSelectionID();
    }
    
    /**
     * Retourne l'identifiant du studio sélectionné.
     * @return l'identifiant du studio sélectionné,
     *         -1 s'il n'y en a pas
     */
    public long getStudioID() {
        List<Long> selectionList = listStudios.getSelectionID();
        long res = -1;
        if (selectionList.size() > 0) {
            res = selectionList.get(0);
        }
        return res;
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init() {
        initPanels();
        initFields();
    }
    /**
     * Initialise les JPanels.
     */
    private void initPanels() {
        plateformesPanel = new JPanel(new BorderLayout());
        plateformesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.PLATEFORMES));
        
        genresPanel = new JPanel(new BorderLayout());
        genresPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.GENRES));
        
        studioPanel = new JPanel(new BorderLayout());
        studioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.STUDIO));
    }
    /**
     * Initialise les champs de saisie.
     */
    private void initFields() {
        dateFormat = new SimpleDateFormat(TexteConstantesFormatDate.SHORT);
        SpinnerDateModel model = new SpinnerDateModel();
        dateSortieSpinner = new DateSpinner(TexteConstantesFormatDate.SHORT);
        dateSortiePanel = new JPanel(new BorderLayout());
        dateSortiePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.DATE_SORTIE));
        
        listPlateformes = new ScrollableJList();
        listPlateformes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listPlateformes.addActionListener(idComboBox);
        listGenres = new ScrollableJList();
        listGenres.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listStudios = new ScrollableJList();
        listStudios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * Met les composants les uns dans les autres.
     */
    private void setComponents() {
        //ajout des champs de saisie dans leur panel respectif
        dateSortiePanel.add(dateSortieSpinner);
        plateformesPanel.add(listPlateformes);
        genresPanel.add(listGenres);
        studioPanel.add(listStudios);
        
        GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 2 ;
        gbc.gridheight = 1;
        saisiesPanel.add(dateSortiePanel,gbc);
        
        gbc.gridy = 3;
        gbc.gridheight = 4;
        saisiesPanel.add(plateformesPanel,gbc);
        
        gbc.gridy = 7;
        saisiesPanel.add(genresPanel,gbc);
        
        gbc.gridy = 11;
        saisiesPanel.add(studioPanel,gbc);
    }
    
    /**
     * Change la visibilité du bouton de réinitialisation.
     * @param visible vrai pour rendre visible, faux sinon
     */
//    @Override
//    public void setResetButtonVisible(boolean visible) {
//        if (visible){
//            GridBagConstraints gbc = GridBagConstraintsSimpleFactory.getNewGridBagConstraints();
//            gbc.gridy = 15;
//            this.add(resetPanel,gbc);
//        }
//        else {
//            this.remove(resetPanel);
//        }
//    }
    
    /**
     * Vide les listes des plateformes, genres et du studio.
     */
    public void clearLists() {
        listPlateformes.removeAllElements();
        listGenres.removeAllElements();
        listStudios.removeAllElements();
    }
    
    /**
     * Ajoute une plateforme dans la liste de sélection possible des plateformes.
     * @param plateforme la plateforme à ajouter à la liste de sélection des plateformes
     */
    public void addPlateforme(Plateforme plateforme) {
        listPlateformes.addElement(new MyListElement(plateforme.getID(),plateforme));
    }
    
    /**
     * Ajoute un genre dans la liste de sélection possible des genres.
     * @param genre le genre à ajouter à la liste de sélection des genres
     */
    public void addGenre(Genre genre) {
        listGenres.addElement(new MyListElement(genre.getID(),genre));
    }
    
    /**
     * Ajout un studio dans la liste de sélection possible des studios.
     * @param studio le studio à ajouter dans la liste de sélection des studios
     */
    public void addStudio(Studio studio) {
        listStudios.addElement(new MyListElement(studio.getID(),studio));
    }
    
    /**
     * Supprime une plateforme de la liste de sélection possible des plateformes.
     * @param idPlateforme l'identifiant de la plateforme à supprimer
     */
    public void removePlateforme(long idPlateforme) {
        listPlateformes.removeElement(new MyListElement(idPlateforme,null));
    }
    
    /**
     * Supprime un genre de la liste de sélection possible des genres.
     * @param idGenre l'identifiant du genre à supprimer
     */
    public void removeGenre(long idGenre) {
        listGenres.removeElement(new MyListElement(idGenre,null));
    }
    
    /**
     * Supprime un studio de la liste de sélection possible des studios.
     * @param idStudio l'identifiant du studio à supprimer
     */
    public void removeStudio(long idStudio) {
        listStudios.removeElement(new MyListElement(idStudio,null));
    }
    
    /**
     * Vide les champs de saisie et la sélection.
     * @param editable permet de rendre éditable ou non les champs de saisie
     *                  (pour suppression ou non)
     * @param empty booléen pour vider ou non les champs de saisie (vrai pour
     *              les vider, faux sinon)
     */
    @Override
    public void clearFields(boolean editable, boolean empty) {
        super.clearFields(editable, empty);
        if (editable) {
            if (empty) {
                listPlateformes.clearSelection();
                listGenres.clearSelection();
                listStudios.clearSelection();
                dateSortieSpinner.setDate(new Date(System.currentTimeMillis()));
            }
        }
        dateSortieSpinner.setEnabled(editable);
        listPlateformes.setEditable(editable);
        listGenres.setEditable(editable);
        listStudios.setEditable(editable);
    }
    
    /**
     * Change l'année de sortie dans le champ de saisie concerné.
     * @param dateSortieString l'année de sortie à mettre
     */
    public void setDateSortie(String dateSortieString) {
        try {
            dateSortieSpinner.setDate(dateFormat.parse(/*TexteConstantes.EMPTY + */dateSortieString));
        } catch (ParseException ex) {
            Logger.getLogger(JeuPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Sélectionne les plateformes en fonction de leur identifiant.
     * @param plateformesID le tableau des identifiants des plateformes à sélectionner
     */
    public void setPlateformesSelection(Long[] plateformesID) {
        listPlateformes.setSelection(plateformesID);
    }
    
    /**
     * Sélectionne les genres en fonction de leur identifiant.
     * @param genreID le tableau des identifiants des genres à sélectionner
     */
    public void setGenresSelection(Long[] genreID) {
        listGenres.setSelection(genreID);
    }
    
    /**
     * Sélectionne les studios en fonction de leur identifiant.
     * @param idStudio le tableau des identifiants des studios à sélectionner
     */
    public void setStudioSelection(long idStudio) {
        Long[] studios = { idStudio };
        listStudios.setSelection(studios);
    }
    
    /**
     * Demande au contrôleur de remplir les champs de saisie avec les données
     * du jeu dont l'identifiant est passé en paramète
     * @param idItem l'identifiant du jeu auquel chercher les données
     */
    @Override
    public void fillItem(long idItem) {
        controler.fillJeuPanel(idItem);
    }
    
    
    //LISTENER
    public void addPlateformesActionListener(ActionListener listener) {
        listPlateformes.addActionListener(listener);
    }
    
    public void addGenresActionListener(ActionListener listener) {
        listGenres.addActionListener(listener);
    }
    
    public void addStudiosActionListener(ActionListener listener) {
        listStudios.addActionListener(listener);
    }
}
