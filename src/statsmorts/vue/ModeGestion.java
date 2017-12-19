/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;

/**
 *
 * @author robin
 */
public enum ModeGestion {
    
    //ÉNUMÉRATION
    AJOUTER(TexteConstantes.AJOUTER),
    MODIFIER(TexteConstantes.MODIFIER),
    SUPPRIMER(TexteConstantes.SUPPRIMER);
    
    
    //ATTRIBUTS
    private final String action;
    
    
    //CONSTRUCTEUR
    private ModeGestion(String action) {
        this.action = action;
    }
    
    
    //ACCESSEURS
    public String getAction() {
        return action;
    }
}
