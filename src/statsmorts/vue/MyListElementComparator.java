/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.util.Comparator;

/**
 *
 * @author Robin
 */
public class MyListElementComparator implements Comparator<MyListElement> {
    
    @Override
    public int compare(MyListElement o1, MyListElement o2) {
        return o1.getTitre().compareTo(o2.getTitre());
    }
    
}
