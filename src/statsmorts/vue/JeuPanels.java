/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import statsmorts.classes.Genre;
import statsmorts.classes.Plateforme;
import statsmorts.classes.Studio;
import statsmorts.constantes.TexteConstantesFormatDate;
import statsmorts.controler.StatsMortsControler;

/**
 *
 * @author Robin
 */
public class JeuPanels extends ObjectDatabasePanels {
    
    //ATTRIBUTS
    //JPANElS
    private JPanel plateformesPanel;
    private JPanel genresPanel;
    private JPanel studioPanel;
    private JPanel anneeSortiePanel;
    //ENTREES UTILISATEUR
    private ScrollableJList listPlateformes;
    private ScrollableJList listGenres;
    private ScrollableJList listStudios;
    private JSpinner anneeSortieSpinner;
    
    private SimpleDateFormat dateFormat;
    
    
    //CONSTRUCTEURS
    public JeuPanels(StatsMortsControler controler) {
        super(controler,TexteConstantes.JEU);
        this.init();
        this.setComponents();
    }
    
    
    //ACCESSEURS
    public int getAnneeSortie() {
        return Integer.parseInt(dateFormat.format(anneeSortieSpinner.getValue()));
    }
    
    public List<Long> getPlateformes() {
        return listPlateformes.getSelectionID();
    }
    
    public List<Long> getGenres() {
        return listGenres.getSelectionID();
    }
    
    public long getStudioID() {
        List<Long> selectionList = listStudios.getSelectionID();
        long res = -1;
        if (selectionList.size() > 0) {
            res = selectionList.get(0);
        }
        return res;
    }
    
    
    //MUTATEURS
    private void init() {
        initPanels();
        initFields();
    }
    private void initPanels() {
        plateformesPanel = new JPanel(new BorderLayout());
        plateformesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.PLATEFORMES));
        
        genresPanel = new JPanel(new BorderLayout());
        genresPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.GENRES));
        
        studioPanel = new JPanel(new BorderLayout());
        studioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), TexteConstantes.STUDIO));
    }
    private void initFields() {
        dateFormat = new SimpleDateFormat(TexteConstantesFormatDate.YEAR_ONLY);
        SpinnerDateModel model = new SpinnerDateModel();
        anneeSortieSpinner = new JSpinner(model);
        anneeSortieSpinner.setEditor(new JSpinner.DateEditor(anneeSortieSpinner,dateFormat.toPattern()));
        anneeSortiePanel = new JPanel(new BorderLayout());
        anneeSortiePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),TexteConstantes.ANNEE_SORTIE));
        
        listPlateformes = new ScrollableJList();
        listPlateformes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listGenres = new ScrollableJList();
        listGenres.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listStudios = new ScrollableJList();
        listStudios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void setComponents() {
        //ajout des champs de saisie dans leur panel respectif
        anneeSortiePanel.add(anneeSortieSpinner);
        plateformesPanel.add(listPlateformes);
        genresPanel.add(listGenres);
        studioPanel.add(listStudios);
        
        GridBagConstraints gbc = new GridBagConstraints();
        //contraintes pour tous les éléments
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        gbc.gridx = 0;
        gbc.gridy = 2 ;
        gbc.gridheight = 1;
        saisiesPanel.add(anneeSortiePanel,gbc);
        
        gbc.gridy = 3;
        gbc.gridheight = 4;
        saisiesPanel.add(plateformesPanel,gbc);
        
        gbc.gridy = 7;
        saisiesPanel.add(genresPanel,gbc);
        
        gbc.gridy = 11;
        saisiesPanel.add(studioPanel,gbc);
    }
    
    @Override
    public void setResetButtonVisible(boolean visible) {
        if (visible){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor  = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 15;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridheight = 1;
            this.add(resetPanel,gbc);
        }
        else {
            this.remove(resetPanel);
        }
    }
    
    public void clearLists() {
        listPlateformes.removeAllElements();
        listGenres.removeAllElements();
        listStudios.removeAllElements();
    }
    
    public void addPlateforme(Plateforme plateforme) {
        listPlateformes.addElement(new MyListElement(plateforme.getID(),plateforme));
    }
    
    public void addGenre(Genre genre) {
        listGenres.addElement(new MyListElement(genre.getID(),genre));
    }
    
    public void addStudio(Studio studio) {
        listStudios.addElement(new MyListElement(studio.getID(),studio));
    }
    
    public void removePlateforme(long idPlateforme) {
        listPlateformes.removeElement(new MyListElement(idPlateforme,null));
    }
    
    public void removeGenre(long idGenre) {
        listGenres.removeElement(new MyListElement(idGenre,null));
    }
    
    public void removeStudio(long idStudio) {
        listStudios.removeElement(new MyListElement(idStudio,null));
    }
    
    @Override
    public void clearFields(boolean editable, boolean empty) {
        super.clearFields(editable, empty);
        if (editable) {
            if (empty) {
                listPlateformes.clearSelection();
                listGenres.clearSelection();
                listStudios.clearSelection();
                anneeSortieSpinner.setValue(new Date(System.currentTimeMillis()));
            }
        }
        anneeSortieSpinner.setEnabled(editable);
        listPlateformes.setEditable(editable);
        listGenres.setEditable(editable);
        listStudios.setEditable(editable);
    }
    
    public void setAnneeSortie(int anneeSortie) {
        try {
            anneeSortieSpinner.setValue(dateFormat.parse(TexteConstantes.EMPTY + anneeSortie));
        } catch (ParseException ex) {
            Logger.getLogger(JeuPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setPlateformesSelection(Long[] plateformesID) {
        listPlateformes.setSelection(plateformesID);
    }
    
    public void setGenresSelection(Long[] genreID) {
        listGenres.setSelection(genreID);
    }
    
    public void setStudioSelection(long idStudio) {
        Long[] studios = { idStudio };
        listStudios.setSelection(studios);
    }
    
    @Override
    public void fillItem(long idItem) {
        controler.fillJeuPanel(idItem);
    }
    
}
