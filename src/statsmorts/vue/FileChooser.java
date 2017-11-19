/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmorts.vue;

import java.awt.BorderLayout;
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
    private JFileChooser fileChooser;
    private final int typeDialog;
    private JTextField textField;
    private String text;
    private JButton open;
    
    //CONSTRUCTEUR
    public FileChooser(String title, String text, int mode, int typeDialog) {
        super(new BorderLayout(5,5));
        this.typeDialog = typeDialog;
        
        init(text,mode);
        
        setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), title));
        
        add(textField, BorderLayout.CENTER);
        add(open, BorderLayout.EAST);
    }
    
    private void init(String text, int mode) {
        this.text = text;
        textField = new JTextField(text);
        
        fileChooser = new JFileChooser(new File(text));
        fileChooser.setFileSelectionMode(mode);
        fileChooser.setSelectedFile(new File(text));
        
        open = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        open.addActionListener(new ButtonListener());
    }
    
    
    //ACCESSEURS
    public String getPath() {
        File file = new File(textField.getText());
        
        boolean correctFile = (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile() || fileChooser.getDialogType() == JFileChooser.SAVE_DIALOG);
        boolean correctDir = (fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory() || fileChooser.getDialogType() == JFileChooser.SAVE_DIALOG);
        
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
    
    public void setFilter(FileNameExtensionFilter filter) {
        fileChooser.setFileFilter(filter);
    }
    
    
    //LISTENERS
    class ButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == open) {
                fileChooser.setCurrentDirectory(new File(textField.getText()));
                int returnVal;
                if (typeDialog == JFileChooser.OPEN_DIALOG) {
                    returnVal = fileChooser.showOpenDialog(open.getParent());
                }
                else {
                    returnVal = fileChooser.showSaveDialog(open.getParent());
                }
                
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    /*boolean correctDir = (fileChooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY && file.isDirectory());
                    boolean correctFile = (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY && file.isFile());
                    if (correctDir || correctFile) {*/
                        textField.setText(file.getAbsolutePath());
                    //}
                }
            }
        }
        
    }
}
