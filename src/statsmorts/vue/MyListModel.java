/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 * Une classe pour un modèle de JList trié et retournant les identifiants des
 * items sélectionnés avec une map d'association ID <-> index.
 * @author Robin
 */
public class MyListModel extends AbstractListModel {
    
    //ATTRIBUTS
    /**
     * La liste des éléments.
     */
    private final ArrayList<MyListElement> list;
    /**
     * La map d'association ID <-> index.
     */
    private final HashMap<Long,Integer> idToIndex;
    /**
     * Le comparateur d'éléments.
     */
    private final MyListElementComparator comparator;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un modèle de liste.
     */
    public MyListModel() {
        super();
        list = new ArrayList();
        idToIndex = new HashMap();
        comparator = new MyListElementComparator();
    }
    
    
    //ACCESSEURS
    /**
     * Retourne la taille de la liste.
     * @return la taille de la liste
     */
    @Override
    public int getSize() {
        return list.size();
    }
    
    /**
     * Retourne l'élément à un certain index.
     * @param index l'index auquel chercher l'élément
     * @return l'objet à l'index indiqué
     */
    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }
    
    
    //MUTATEURS
    /**
     * Supprime tous les éléments.
     */
    public void removeAllElements() {
        list.clear();
        idToIndex.clear();
        fireContentsChanged(this, 0, 0);
    }
    
    /**
     * Ajoute un élément, puis trie la liste.
     * @param element l'élément à ajouter
     */
    public void addElement(MyListElement element) {
        list.add(element);
        this.sort();
    }
    
    /**
     * Supprime un élément, puis trie la liste.
     * @param element l'élément à supprimer
     */
    public void removeElement(MyListElement element) {
        list.remove(element);
        this.sort();
    }
    
    /**
     * Retourne la map d'association ID <-> index.
     * @return la map d'association ID <-> index
     */
    public Map<Long,Integer> getIDToIndices() {
        return idToIndex;
    }
    
    /**
     * Trie la liste puis remet la map d'association ID <-> index à jour.
     */
    public void sort() {
        Collections.sort(list,comparator);
        idToIndex.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            idToIndex.put(list.get(i).getID(), i);
        }
        fireContentsChanged(this, 0, size);
    }
    
}
