/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import javax.swing.tree.DefaultMutableTreeNode;
import statsmorts.classes.FillDataset;

/**
 *
 * @author Robin
 */
public class SortableTreeNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final FillDataset objet;
    private final boolean isInformations;
    
    
    //CONSTRUCTEUR
    public SortableTreeNode(FillDataset userObject, boolean isInformations) {
        super(userObject);
        this.objet = userObject;
        this.isInformations = isInformations;
    }
    public SortableTreeNode(String title, boolean isInformations) {
        super(title);
        this.objet = null;
        this.isInformations = isInformations;
    }
    
    
    //ACCESSEURS
    public boolean isInformations() {
        return isInformations;
    }
    
    @Override
    public String toString() {
        return isInformations ? objet.getTitre() : super.toString();
    }

    
    //MUTATEURS
    public void add(SortableTreeNode newChild) {
        int cc = getChildCount();
        Comparable newObject = (Comparable) newChild.getUserObject();
        for (int i = 0; i < cc; i++) {
            SortableTreeNode child = (SortableTreeNode) getChildAt(i);
            Comparable childObject = (Comparable) child.getUserObject();
            if (newObject.compareTo(childObject) < 0) {
                super.insert(newChild, i);
                return;
            }
        }
        super.add(newChild);
    }

    public void insert(SortableTreeNode newChild, int childIndex) {
        add(newChild);
    }

    public void remove() {
        SortableTreeNode parentNode = (SortableTreeNode)getParent();
        super.removeFromParent();
    }
    
    public void sort() {
        int cc = getChildCount();
        for (int i = 0; i < cc - 1; i++) {
            for (int j = i + 1; j <= cc - 1; j++) {
                SortableTreeNode here = (SortableTreeNode) getChildAt(i);
                SortableTreeNode there = (SortableTreeNode) getChildAt(j);
                Comparable hereObject = (Comparable) here.getUserObject();
                Comparable thereObject = (Comparable) there.getUserObject();
                if (hereObject.compareTo(thereObject) > 0) {
                    super.remove(here);
                    super.remove(there);
                    super.insert(there, i);
                    super.insert(here, j);
                }
            }
        }
    }
    
    
    //INTERFACE INFORMATIONS
    @Override
    public String getInformations() {
        return isInformations ? objet.toString() : "";
    }
    
    @Override
    public FillDataset getObjectFillDataset() {
        return objet;
    }
}
