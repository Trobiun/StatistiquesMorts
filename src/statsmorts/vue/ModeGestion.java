/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;

/**
 * Enumération qui permet de déterminer l'action/mode de gestion de la base de
 * données.
 * @author robin
 */
public enum ModeGestion {
    
    //ÉNUMÉRATION
    /**
     * Désigne l'action/mode d'ajouter un objet dans la base de données.
     */
    AJOUTER(TexteConstantes.AJOUTER),
    /**
     * Désigne l'action/mode de modifier un objet dans la base de données.
     */
    MODIFIER(TexteConstantes.MODIFIER),
    /**
     * Désigne l'action/mode de supprimer un objet dans la base de données.
     */
    SUPPRIMER(TexteConstantes.SUPPRIMER);
    
    
    //ATTRIBUTS
    /**
     * Le texte à afficher.
     */
    private final String texte;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un mode de gestion, avec un texte pour l'affichage
     * @param texte le texte à afficher
     */
    private ModeGestion(String texte) {
        this.texte = texte;
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le texte en chaîne de caractères pour l'affichage.
     * @return une chaîne de caractères de le texte pour l'affichage
     */
    public String getAction() {
        return texte;
    }
}
