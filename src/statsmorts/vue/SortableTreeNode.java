/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import javax.swing.tree.DefaultMutableTreeNode;
import statsmorts.classes.FillDataset;

/**
 * Une classe pour un nœud d'arbre trié.
 * @author Robin
 */
public class SortableTreeNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    /**
     * L'objet à désigner dans l'arbre.
     */
    private final FillDataset objet;
    /**
     * Booléen qui détermine si l'objet à désigner "possède" des informations ou non.
     */
    private final boolean isInformations;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un SortbleTreeNode avec un objet à désigner et avec le booléen
     * isInformations à vrai.
     * @param userObject l'objet à désigner par le nœud
     */
    public SortableTreeNode(FillDataset userObject) {
        super(userObject);
        this.objet = userObject;
        this.isInformations = true;
    }
    /**
     * Crée un SortableTreeNode avec un titre en chaîne de caractèrees et avec le
     * booléen isInformations à faux.
     * @param title 
     */
    public SortableTreeNode(String title) {
        super(title);
        this.objet = null;
        this.isInformations = false;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne vrai si l'objet à désigner "possède" des informations, faux sinon.
     * @return un booléen qui détermine si l'objet à désigner "possède" des
     * informations ou non.
     */
    public boolean isInformations() {
        return isInformations;
    }
    
    /**
     * Retourne le titre de l'objet si l'objet "possède" dez informations, sinon
     * retourne super.toString().
     * @return une chaîne de caractère qui représente le SortableTreeNode dans
     * l'arbre
     */
    @Override
    public String toString() {
        return isInformations ? objet.getTitre() : super.toString();
    }

    
    //MUTATEURS
    /**
     * Ajoute de façon triée un enfant à ce nœud.
     * @param newChild le nouvel enfant à ajouter
     */
    public void add(SortableTreeNode newChild) {
        int cc = getChildCount();
        Comparable newObject = (Comparable)newChild.getUserObject();
        for (int i = 0; i < cc; i++) {
            SortableTreeNode child = (SortableTreeNode)getChildAt(i);
            Comparable childObject = (Comparable)child.getUserObject();
            if (newObject.compareTo(childObject) < 0) {
                super.insert(newChild, i);
                return;
            }
        }
        super.add(newChild);
    }
    
    /**
     * Supprime ce nœud de son parent.
     */
    public void remove() {
//        SortableTreeNode parentNode = (SortableTreeNode)getParent();
        super.removeFromParent();
    }
    
    /**
     * Trie les enfants de ce nœud.
     */
    public void sort() {
        int cc = getChildCount();
        for (int i = 0; i < cc - 1; i++) {
            for (int j = i + 1; j <= cc - 1; j++) {
                SortableTreeNode here = (SortableTreeNode)getChildAt(i);
                SortableTreeNode there = (SortableTreeNode)getChildAt(j);
                Comparable hereObject = (Comparable)here.getUserObject();
                Comparable thereObject = (Comparable)there.getUserObject();
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
    /**
     * Retourne les informations, si possible, de l'objet du nœud.
     * @return les informations, si possible, de l'objet du nœud
     */
    @Override
    public String getInformations() {
        return isInformations ? objet.toString() : TexteConstantes.EMPTY;
    }
    
    /**
     * Retourne l'objet FillDataset désigné par ce nœud.
     * @return l'objet FillDataset désigné par ce nœud
     */
    @Override
    public FillDataset getObjectFillDataset() {
        return objet;
    }
}
