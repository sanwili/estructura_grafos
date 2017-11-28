/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controladores.AppContext;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_ListaAdyacenciaController implements Initializable {

    @FXML
    private Label messageLabel;
    @FXML
    private TextArea txtLista;
    @FXML
    private HBox actionParent;
    @FXML
    private HBox okParent;
    @FXML
    private Button btnCerrarVentana;
    
    char[] Letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ'};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
          List<String> l = (List<String>) AppContext.getInstance().get("lista");
          for(int i=1; i<l.size();i++){
              txtLista.setText(txtLista.getText()+l.get(i)+"\n");
          }
          messageLabel.setText(l.get(0));
    }    

    @FXML
    private void CerrarVentana(ActionEvent event) {
    Stage stage = (Stage) btnCerrarVentana.getScene().getWindow();
        stage.close();
    }
    
}
