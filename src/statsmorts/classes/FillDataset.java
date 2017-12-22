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
 * Une interface pour remplit le Dataset d'un graphique (charrt) pour l'interface
 * graphique
 * @author Robin
 */
public interface FillDataset {
    
    /**
     * Retourne le titre de l'objet qui implémente cette interface.
     * @return une chaîne de caractères égale au titre de l'objet qui implémente cette interface.
     */
    String getTitre();
    /**
     * Retourne le titre à affciher du Dataset pour le graphique dans l'interface
     * graphique.
     * @return une chaîne de caractères
     */
    String getTitreDataset();
    /**
     * Retourne une arraylist composée de tous les lives contenus dans l'objet qui
     * implémente cette interface.
     * @return une arraylist qui contient les lives
     */
    ArrayList<Live> getLivesList();
    /**
     * Remplit le dataset 
     * @param dataset le dataset à remplir.
     * @param unit l'unité de temps utilisée pour les calculs de durée totale
     *             ou pour la durée de vie moyenne.
     * @param total booléen qui sert à déterminer si l'objet qui implémente cette
     *              interface doit donner le total des statistiques (faire la somme
     *              des morts, des durées, des durées de vie etc).
     */
    void fillDataset(DefaultCategoryDataset dataset, TimeUnit unit, boolean total);
    
}
