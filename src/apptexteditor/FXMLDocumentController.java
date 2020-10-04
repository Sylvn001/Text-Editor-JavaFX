/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptexteditor;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

/**
 *
 * @author junio
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btAumenta;
    @FXML
    private Button BtDiminuir;
    @FXML
    private Button btAbrir;
    @FXML
    private Button btSalvar;
    @FXML
    private TextArea btTextArea;
    
    //New atributes  
    private int tamanho;
    private boolean alterado = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btTextArea.setText("Documento em branco");
        tamanho = (int) btTextArea.getFont().getSize(); 
    }    

    @FXML
    private void evtAumentaFonte(ActionEvent event) {
        tamanho++;
        trocarTamanhoFonte();
    }

    @FXML
    private void evtDiminuiFonte(ActionEvent event) {
        tamanho--;
        trocarTamanhoFonte();
    }
    
    private void trocarTamanhoFonte()
    {
        btTextArea.setFont(new Font(tamanho));
    }

    @FXML
    private void evtNovo(ActionEvent event) {
         if(alterado){
            evtSalvar(event);
        }
        btTextArea.clear();
        alterado = false;
    }

    @FXML
    private void evtAbrir(ActionEvent event) {
        if(alterado){
            evtSalvar(event);
        }
        FileChooser fc = new FileChooser();
        File file =  fc.showOpenDialog(null);
        if (file != null){
            try{
                byte[] bytes; 
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                bytes = new byte[(int)file.length()];
                raf.readFully(bytes);
                btTextArea.setText(new String(bytes));
                raf.close();
            }
            catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao Abrir");
                alert.setHeaderText("Erro ao abrir o arquivo!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            alterado = false;
        }
    }

    @FXML
    private void evtSalvar(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file =  fc.showSaveDialog(null);
        if (file != null){
            try{
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.writeBytes(btTextArea.getText());
                raf.close();
            }
            catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao Salvar");
                alert.setHeaderText("Erro ao salvar o arquivo!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            alterado = false;
        }
        
    }

    @FXML
    private void evtAlterouTexto(KeyEvent event) {
        alterado = true;
    }
    
}
