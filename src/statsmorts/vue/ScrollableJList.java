/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Une classe pour une JList dans un JScrollPane pour prendre moins de place, et
 * gérer la sélection.
 * @author Robin
 */
public class ScrollableJList extends JPanel {
    
    //ATTRIBUTS
    /**
     * Le JScrolLPane dans lequel mettre la JList.
     */
    private JScrollPane scrollPane;
    /**
     * Le modèle de la JList pour la gérer.
     */
    private MyListModel listModel;
    /**
     * La JList utilisée.
     */
    private JList list;
    
    
    //CONSTRUCTEUR
    /**
     * Crée une ScrollableJList.
     */
    public ScrollableJList() {
        super(new BorderLayout());
        init();
        setComponents();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne la sélection avec une listes d'objets.
     * @return la sélection avec une listes d'objets.
     */
    public List<MyListElement> getSelection() {
        return list.getSelectedValuesList();
    }
    
    /**
     * Retourne la sélection avec une liste de long qui représentent les identifiants
     * des objets sélectionnés.
     * @return une liste de long qui représentent les identifiants
     * des objets sélectionnés
     */
    public List<Long> getSelectionID() {
        List<Long> listID = new ArrayList();
        List<MyListElement> selection = getSelection();
        for (MyListElement element : selection) {
            listID.add(element.getID());
        }
        return listID;
    }
    
    
    //MUTATEURS
    /**
     * Initialise tous les composants graphiques.
     */
    private void init() {
        listModel = new MyListModel();
        list = new JList(listModel);
        list.setVisibleRowCount(4);
        scrollPane = new JScrollPane(list);
    }
    
    /**
     * Met les composants les uns dans les autres. (ajoute le JScrollPane à ce panel)
     */
    private void setComponents() {
        this.add(scrollPane);
    }
    
    /**
     * Met la sélection en fonction du tableau d'identifiants en paramètre de sorte
     * à sélectionner les bons indices en fonction des identifiants.
     * @param selection le tableau d'identifiants à sélectionner
     */
    public void setSelection(Long[] selection) {
        if (selection.length > 0) {
            int[] indices = new int[selection.length];
            Map<Long, Integer> map = listModel.getIDToIndices();
            if (map.size() > 0) {
                int i = 0;
                for (Long id : selection) {
                    indices[i] = map.get(id);
                    i++;
                }
                list.setSelectedIndices(indices);
            }
        }
        else {
            clearSelection();
        }
    }
    
    /**
     * Met si oui ou non la ScrollableJList est éditable (désactive ou non la JList).
     * @param editable vrai si le ScrollableJList doit être éditable, faux sinon
     */
    public void setEditable(boolean editable) {
        list.setEnabled(editable);
    }
    
    /**
     * Supprime tous les éléments.
     */
    public void removeAllElements() {
        listModel.removeAllElements();
    }
    
    /**
     * Supprime la sélection.
     */
    public void clearSelection() {
        list.clearSelection();
    }
    
    /**
     * Change le mode de sélection de la JList. Doit être : ListSelectionModel.SINGLE_SELECTION
     * , ListSelectionModel.SINGLE_INTERVAL_SELECTION ou ListSelectionModel.MULTIPLE_INTERVAL_SELECTION.
     * @param selectionMode le mode de sélection
     */
    public void setSelectionMode(int selectionMode) {
        list.setSelectionMode(selectionMode);
    }
    
    /**
     * Ajoute un élément à la liste.
     * @param element l'élément à ajouter
     */
    public void addElement(MyListElement element) {
        listModel.addElement(element);
    }
    
    /**
     * Supprime un élément à la liste.
     * @param element l'élément à supprimer
     */
    public void removeElement(MyListElement element) {
        listModel.removeElement(element);
    }
    
}
