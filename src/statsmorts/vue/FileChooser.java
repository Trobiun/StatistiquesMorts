/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import constantes.TexteConstantes;
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
 *
 * @author Robin
 */
public class FileChooser extends JPanel{
    
    //ATTRIBUTS
    private JPanel panelButtons;
    private JFileChooser fileChooser;
    private int typeDialog;
    private JTextField textField;
    private String text;
    private JButton buttonOpen;
    private JButton buttonCreer;
    
    private final boolean create;
    private String defaultExtension;
    
    
    //CONSTRUCTEUR
    public FileChooser(String title, String text, int mode, int typeDialog, boolean create) {
        super(new BorderLayout(5,5));
        this.typeDialog = typeDialog;
        this.create = create;
        
        init(text,mode);
        
        setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), title));
        
        setComponents();
    }
    
    private void init(String text, int mode) {
        panelButtons = new JPanel(new GridLayout(1,2,5,5));
        
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
    
    private void setComponents() {
        panelButtons.add(buttonOpen);
        if (create) {
            panelButtons.add(buttonCreer);
        }
        add(textField, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.EAST);
    }
    
    
    //ACCESSEURS
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
        else {
            /*String message = "";
            if (!correctFile) {
                message = "Il faut un dossier";
            }
            if (!correctDir) {
                message = "Il faut un fichier";
            }*/
            /*JOptionPane optionPane = new JOptionPane();
            JOptionPane.showMessageDialog(optionPane, message, "Erreur", JOptionPane.ERROR_MESSAGE);
            */
            //System.out.println(message);
        }
        return text;
    }
    
    
    //MUTATEURS
    public void setText(String text) {
        this.text = text;
        textField.setText(text);
    }
    
    public void setFilter(FileNameExtensionFilter filter, String defaultExtension) {
        fileChooser.setFileFilter(filter);
        this.defaultExtension = defaultExtension;
    }
    
    
    //LISTENERS
    class ButtonsListener implements ActionListener {
        
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
                    /*boolean correctDir = (fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory());
                    boolean correctFile = (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile());
                    if (correctDir || correctFile) {*/
                    if (fileChooser.getFileFilter().accept(file)) {
                        setText(file.getAbsolutePath());
//                        textField.setText(file.getAbsolutePath());
                    }
                    else {
                        setText(file.getAbsolutePath() + TexteConstantes.DOT + defaultExtension);
//                        textField.setText();
                    }
                    //}
                }
            }
            if (e.getSource().equals(buttonCreer)) {
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                typeDialog = JFileChooser.SAVE_DIALOG;
                fileChooser.setCurrentDirectory(new File(textField.getText()).getParentFile());
                
                int returnVal = fileChooser.showSaveDialog(buttonCreer.getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    /*boolean correctDir = (fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory());
                    boolean correctFile = (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile());
                    if (correctDir || correctFile) {*/
//                        textField.setText(file.getAbsolutePath());
                    //}
                    if (fileChooser.getFileFilter().accept(file)) {
//                        textField.setText(file.getAbsolutePath());
                        setText(file.getAbsolutePath());
                    }
                    else {
//                        textField.setText(file.getAbsolutePath() + "." + defaultExtension);
                        setText(file.getAbsolutePath() + TexteConstantes.DOT + defaultExtension);
                    }
                }
            }
        }
        
    }
}
