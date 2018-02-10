 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import statsmorts.constantes.TexteConstantes;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Une classe pour gérer un file chooser avec un JTextField et des boutons.
 * @author Robin
 */
public class FileChooser extends JPanel{
    
    //ATTRIBUTS STATIC
    private static final int ROWS = 1;
    private static final int COLS = 2;
    private static final int HGAP = 5;
    private static final int VGAP = 5;
    //ATTRIBUTS
    /**
     * Le panel qui contient les boutons.
     */
    private JPanel panelButtons;
    /**
     * Le file chooser pour sélectionner un fichier.
     */
    private JFileChooser fileChooser;
    /**
     * 
     */
    private int typeDialog;
    /**
     * Le champ de saisie du nom de fichier.
     */
    private JTextField textField;
    /**
     * 
     */
    private String text;
    /**
     * Le bouton pour ouvrir le file chooser en mode "ouvrir fichier".
     */
    private JButton buttonOpen;
    /**
     * Le bouton pour ouvrir le file chooser en mdoe "sauvegarder fichier".
     */
    private JButton buttonCreer;
    
    /**
     * Booléen qui permet de déterminer si ce file chooser a le bouton "créer".
     */
    private final boolean create;
    /**
     * L'extension par défaut à mettre au fichier si l'utilisateur ne rentre pas
     * un fichier correct.
     */
    private String defaultExtension;
    
    
    //CONSTRUCTEUR
    /**
     * Crée un FileChooser.
     * @param title le titre du FileChooser
     * @param text le texte par défaut dans le jtextfield
     * @param mode le mode du JFileChooser {@link javax.swing.JFileChooser}
     * @param typeDialog le type du dialogue {@link javax.swing.JFileChooser}
     * @param create détermine si ce file chooser a le bouton "créer"
     */
    public FileChooser(String title, String text, int mode, int typeDialog, boolean create) {
        super(new BorderLayout(HGAP,VGAP));
        this.typeDialog = typeDialog;
        this.create = create;
        
        init(text,mode);
        
        setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), title));
        
        setComponents();
    }
    
    /**
     * Initialise tous les composants graphiques.
     * @param text le texte à afficher par défaut dans le JTextField
     * @param mode le mode 
     */
    private void init(String text, int mode) {
        panelButtons = new JPanel(new GridLayout(ROWS,COLS,HGAP,VGAP));
        
        this.text = text;
        textField = new JTextField(text);
        fileChooser = new JFileChooser(new File(text));
        
        fileChooser.setFileSelectionMode(mode);
        fileChooser.setSelectedFile(new File(text));
        
        ButtonsListener listener = new ButtonsListener();
        buttonOpen = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        buttonOpen.addActionListener(listener);
        
        buttonCreer = new JButton(TexteConstantes.CREER);
        buttonCreer.addActionListener(listener);
    }
    
    /**
     * Met les composantes les uns dans les autres puis dans ce panel.
     */
    private void setComponents() {
        panelButtons.add(buttonOpen);
        if (create) {
            panelButtons.add(buttonCreer);
        }
        add(textField, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.EAST);
    }
    
    
    //ACCESSEURS
    /**
     * Retourne le chemin du fichier sélectionné du FileChooser.
     * @return le chemin du fichier sélectionné du FileChooser
     */
    public String getPath() {
        File file = new File(textField.getText());
        
        boolean correctFile = ((fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile()) 
                || (fileChooser.getDialogType() == JFileChooser.SAVE_DIALOG) 
                || (typeDialog == JFileChooser.SAVE_DIALOG));
        boolean correctDir = ((fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory()) 
                || (fileChooser.getDialogType() == JFileChooser.SAVE_DIALOG));
        
        if (correctFile || correctDir) {
            setText(file.getAbsolutePath());
            return text;
        }
//        else {
//            String message = "";
//            if (!correctFile) {
//                message = "Il faut un dossier";
//            }
//            if (!correctDir) {
//                message = "Il faut un fichier";
//            }
//            JOptionPane optionPane = new JOptionPane();
//            JOptionPane.showMessageDialog(optionPane, message, "Erreur", JOptionPane.ERROR_MESSAGE);
//            
//            System.out.println(message);
//        }
        return text;
    }
    
    
    //MUTATEURS
    /**
     * Change le texte.
     * @param text le nouveau texte
     */
    public void setText(String text) {
        this.text = text;
        textField.setText(text);
    }
    
    /**
     * Met le filtre de fichiers et l'extension par défaut.
     * @param filter le filtre de fichiers acceptés
     * @param defaultExtension l'extension par défaut quand l'extension n'est pas précisée
     */
    public void setFilter(FileNameExtensionFilter filter, String defaultExtension) {
        fileChooser.setFileFilter(filter);
        this.defaultExtension = defaultExtension;
    }
    
    
    //LISTENERS
    /**
     * Classe pour écouter les boutons pour afficher le JFileChooser.
     */
    class ButtonsListener implements ActionListener {
        
        /**
         * Affiche le JFileChooser et change le texte par le fichier sélectionné.
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(buttonOpen)) {
                fileChooser.setDialogType(typeDialog);
                fileChooser.setCurrentDirectory(new File(textField.getText()));
                int returnVal;
                if (typeDialog == JFileChooser.OPEN_DIALOG) {
                    returnVal = fileChooser.showOpenDialog(buttonOpen.getParent());
                }
                else {
                    returnVal = fileChooser.showSaveDialog(buttonOpen.getParent());
                }
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
//                    boolean correctDir = (fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory());
//                    boolean correctFile = (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile());
//                    if (correctDir || correctFile) {
                    if (fileChooser.getFileFilter().accept(file)) {
                        setText(file.getAbsolutePath());
                    }
                    else {
                        setText(file.getAbsolutePath() + "." + defaultExtension);
                    }
//                    }
                }
            }
            if (e.getSource().equals(buttonCreer)) {
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                fileChooser.setCurrentDirectory(new File(textField.getText()).getParentFile());
                
                int returnVal = fileChooser.showSaveDialog(buttonCreer.getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
//                    boolean correctDir = (fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory());
//                    boolean correctFile = (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile());
//                    if (correctDir || correctFile) {
//                        textField.setText(file.getAbsolutePath());
//                    }
                    if (fileChooser.getFileFilter().accept(file)) {
                        setText(file.getAbsolutePath());
                    }
                    else {
                        setText(file.getAbsolutePath() + "." + defaultExtension);
                    }
                }
            }
        }
        
    }
}
