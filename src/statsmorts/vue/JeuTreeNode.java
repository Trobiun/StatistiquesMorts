/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import javax.swing.tree.DefaultMutableTreeNode;
import statsmorts.classes.Jeu;

/**
 *
 * @author Robin
 */
public class JeuTreeNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final Jeu jeu;
    
    
    //CONSTRUCTEUR
    public JeuTreeNode(Jeu jeu) {
        super(jeu);
        this.jeu = jeu;
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        return jeu.getTitre();
    }
    
    public Jeu getJeu() {
        return jeu;
    }
    
    //MUTATEURS
    
    
    //INFORMATIONS
    @Override
    public String getInformations() {
        return jeu.toString();
    }
    
}
