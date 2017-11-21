/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.Comparator;

/**
 *
 * @author Robin
 */
public class LiveComparator implements Comparator<Live> {
    
    @Override
    public int compare(Live l1, Live l2) {
        return l1.getDateDebut().compareTo(l2.getDateDebut());
    }
    
}
