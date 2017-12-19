/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.AbstractListModel;

/**
 *
 * @author Robin
 */
public class MyListModel extends AbstractListModel {
    
    //ATTRIBUTS
    private final ArrayList<MyListElement> list;
    private final MyListElementComparator comparator;
    
    
    //CONSTRUCTEUR
    public MyListModel() {
        super();
        list = new ArrayList();
        comparator = new MyListElementComparator();
    }
    
    
    //ACCESSEURS
    @Override
    public int getSize() {
        return list.size();
    }
    
    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }
    
    
    //MUTATEURS
    public void removeAllElements() {
        list.clear();
        fireContentsChanged(this, 0, 0);
    }
    
    public void addElement(MyListElement element) {
        list.add(element);
        this.sort();
    }
    
    public void removeElement(MyListElement element) {
        list.remove(element);
    }
    
    public void sort() {
        Collections.sort(list,comparator);
        fireContentsChanged(this, 0, list.size());
    }
    
}