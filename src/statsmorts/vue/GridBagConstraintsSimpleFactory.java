/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.GridBagConstraints;

/**
 *
 * @author robin
 */
public abstract class GridBagConstraintsSimpleFactory {
    
    private GridBagConstraintsSimpleFactory() { }
    
    public static GridBagConstraints getNewGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }
    
}
