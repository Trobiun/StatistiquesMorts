/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.util.Comparator;

/**
 * Une classe pour comparer deux MyListElement.
 * @author Robin
 */
public class MyListElementComparator implements Comparator<MyListElement> {
    
    /**
     * Compare deux MyListElement en fonction de leur titre.
     * @param o1 le premier paramètre
     * @param o2 le deuxième paramètre à comparer au premier
     * @return un entier qui désigne la comparaison entre le premier et le
     * deuxième paramètree
     */
    @Override
    public int compare(MyListElement o1, MyListElement o2) {
        return o1.getTitre().compareTo(o2.getTitre());
    }
    
}
