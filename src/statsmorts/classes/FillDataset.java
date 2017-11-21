/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.classes;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Robin
 */
public interface FillDataset {
    
    public ArrayList<Live> getLivesList();
    public void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total);
    
}
