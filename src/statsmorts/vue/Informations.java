/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.classes.FillDataset;

/**
 * Une interface pour obtenir des informations des nœuds de l'arbre.
 * @author Robin
 */
public interface Informations {
    /**
     * Retourne des informations du nœud concerné.
     * @return une chaîne de caractères qui représente l'objet qui implémente
     *         cette interface.
     */
    String getInformations();
    /**
     * Retourne l'objet qui implémente l'interface FillDataset.
     * @return un objet FillDataset pour remplir le dataset.
     */
    FillDataset getObjectFillDataset();
    
}
