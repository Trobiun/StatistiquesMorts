/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Robin
 */
public class ScrollableJList extends JPanel {
    
    //ATTRIBUTS
    private JScrollPane scrollPane;
    private MyListModel listModel;
    private JList list;
    
    
    //CONSTRUCTEUR
    public ScrollableJList() {
        super(new BorderLayout());
        init();
        setComponents();
    }
    
    
    //ACCESSEURS
    public List<MyListElement> getSelection() {
        return list.getSelectedValuesList();
    }
    
    
    //MUTATEURS
    private void init() {
        listModel = new MyListModel();
        list = new JList(listModel);
        list.setVisibleRowCount(4);
        scrollPane = new JScrollPane(list);
    }
    
    private void setComponents() {
        this.add(scrollPane);
    }
    
    public void removeAllElements() {
        listModel.removeAllElements();
    }
    
    public void clearSelection() {
        list.clearSelection();
    }
    
    public void setSelectionMode(int selectionMode) {
        list.setSelectionMode(selectionMode);
    }
    
    public void addElement(MyListElement element) {
        listModel.addElement(element);
    }
    
    public void removeElement(MyListElement element) {
        listModel.removeElement(element);
    }
    
}