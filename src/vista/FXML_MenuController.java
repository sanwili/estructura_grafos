/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controladores.AppContext;
import controladores.Nodos;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_MenuController implements Initializable {

    @FXML
    private Button btnDiskstra;
    @FXML
    private Button btnFloyd;
    @FXML
    private Button btnCamLargoAleatorio;
    @FXML
    private Button btnArbolAbarcador;
    @FXML
    private RadioButton rdbDirigido;
    @FXML
    private ToggleGroup tipoGrafo;
    @FXML
    private RadioButton rdbNoDirigido;
    @FXML
    private GridPane gridInformación;
    @FXML
    private TabPane tabPantallas;
    @FXML
    private Tab tabGrafo;
    @FXML
    private AnchorPane AncPanel;
    @FXML
    private AnchorPane AncMatriz;
    @FXML
    private Tab tabMatriz;
    @FXML
    private ToggleButton btnNuevaConexion;
    @FXML
    private ToggleGroup tgControles;
    @FXML
    private ToggleButton btnNuevoNodo;
    @FXML
    private ToggleButton btnEliminar;
    @FXML
    private ToggleButton btnMover;
    @FXML
    private ToggleGroup tgControles1;
    @FXML
    private TextField txtIdentificador;
    @FXML
    private TextField txtGradoNodo;
    @FXML
    private TextField txtDistanciaEntreNodos;
    @FXML
    private TextField txtNodoConectados;

    double xMouse;
    double yMouse;
    boolean seleccionado = false, mover = false;// seleccionado es para determinar si se selecciono un nodo, mover es el permiso para moverlo
    boolean dobleLinea1 = false, dobleLinea2 = false;
    Node CirculoSeleccionado = null, CirculoSeleccionado2 = null;// representan los circulos para la creación de lineas
    Node NuevoCirculo = null; // nuevo ciculo que se crea
    Integer num = 0, num2 = 0, cantidadNodosSeleccionados = 0, cantidadLineas = 0;
    Line NuevaLinea = null;// nueva linea que se crea
    List<Nodos> ListaDeConexiones = new ArrayList<>();
    int mat[][] = new int[15][15];
    char[] Letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ'};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                mat[i][j] = 0;
            }
        }
    }

    @FXML
    private void activarDiskstra(ActionEvent event) {
    }

    @FXML
    private void activarFloyd(ActionEvent event) {
    }

    @FXML
    private void activarCaminaLargoAleatorio(ActionEvent event) {
    }

    @FXML
    private void activarArbolAbarcador(ActionEvent event) {
    }

    @FXML
    private void movercirculo(MouseEvent event) {

    }

    void moverCirculo(Node cir) {
        if (mover) {
            cir.setLayoutX(xMouse);
            cir.setLayoutY(yMouse);
        }
    }

    @FXML
    private void obtenerPosicionMouse(MouseEvent event) {
        xMouse = event.getX() - 25;
        yMouse = event.getY() - 25;

        if (CirculoSeleccionado != null) {

            moverCirculo(CirculoSeleccionado);

        }

        if (NuevoCirculo != null) {
            NuevoCirculo.setVisible(true);
            mover = true;
            CirculoSeleccionado = NuevoCirculo;
            NuevoCirculo = null;
            CirculoSeleccionado.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        }

        if (btnNuevaConexion.isSelected() && cantidadNodosSeleccionados < 3 && CirculoSeleccionado != null && rdbDirigido.isSelected()) {
            if (cantidadNodosSeleccionados == 1) {
                NuevaLinea = new Line();
                NuevaLinea.setFill(Paint.valueOf("#000000"));
                NuevaLinea.setStrokeWidth(4);

                NuevaLinea.startXProperty().bind(CirculoSeleccionado.layoutXProperty().add(25));
                NuevaLinea.startYProperty().bind(CirculoSeleccionado.layoutYProperty().add(25));
                CirculoSeleccionado2 = CirculoSeleccionado;
            }
            if (cantidadNodosSeleccionados == 2) {

                NuevaLinea.endXProperty().bind(CirculoSeleccionado.layoutXProperty().add(25));
                NuevaLinea.endYProperty().bind(CirculoSeleccionado.layoutYProperty().add(25));
                AncPanel.getChildren().remove(CirculoSeleccionado2);
                AncPanel.getChildren().remove(CirculoSeleccionado);
                if (CirculoSeleccionado.equals(CirculoSeleccionado2)) {

                    for (int i = 0; i < ListaDeConexiones.size(); i++) {
                        if (ListaDeConexiones.get(i).getNodo().equals(CirculoSeleccionado)) {
                            if (!ListaDeConexiones.get(i).isLazo()) {
                                ListaDeConexiones.get(i).setLazo(true);
                                CirculoSeleccionado.setStyle(
                                        "-fx-background-radius: 50em; "
                                        + "-fx-min-width: 50px; "
                                        + "-fx-min-height: 50px; "
                                        + "-fx-max-width: 50px; "
                                        + "-fx-max-height: 50px;"
                                        + "-fx-background-color:#91b13c;"
                                        + "-fx-font: 150% sans-serif;"
                                );
                            }
                        }
                    }
                    AncPanel.getChildren().add(CirculoSeleccionado);
                } else {
                    Nodos n1 = new Nodos();
                    Nodos n2 = new Nodos();
                    NuevaLinea.setId(String.valueOf(cantidadLineas));
                    for (int i = 0; i < ListaDeConexiones.size(); i++) {
                        if (ListaDeConexiones.get(i).getNodo().equals(CirculoSeleccionado)) {
                            ListaDeConexiones.get(i).getListaConexiones().add(NuevaLinea);
                            n1 = ListaDeConexiones.get(i);
                        }
                        if (ListaDeConexiones.get(i).getNodo().equals(CirculoSeleccionado2)) {
                            ListaDeConexiones.get(i).getListaConexiones().add(NuevaLinea);
                            n2 = ListaDeConexiones.get(i);
                        }
                    }

                    for (int i = 0; i < n1.getListaConexiones().size(); i++) {
                        for (int j = 0; j < n2.getListaConexiones().size(); j++) {
                            if (n1.getListaConexiones().get(i).equals(n2.getListaConexiones().get(j))) {
                                if (dobleLinea1 == false) {
                                    dobleLinea1 = true;
                                    continue;
                                }
                                if (dobleLinea1 == true && dobleLinea2 == false) {
                                    dobleLinea2 = true;
                                }
                            }
                        }
                    }

                    if (dobleLinea1 == true && dobleLinea2 == true) {
                        NuevaLinea.setStroke(Paint.valueOf("#ff4444"));
                        NuevaLinea.setStrokeWidth(5);

                    }

                    AncPanel.getChildren().add(NuevaLinea);
                    AncPanel.getChildren().add(CirculoSeleccionado2);
                    AncPanel.getChildren().add(CirculoSeleccionado);
                }
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/FXML_Peso.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage(StageStyle.UTILITY);
                    stage.initOwner(btnNuevaConexion.getScene().getWindow());
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.showAndWait();

                } catch (Exception ex) {

                    System.err.println(ex);
                }
                cantidadNodosSeleccionados = 0;
                btnNuevaConexion.setSelected(false);
                if (!AppContext.getInstance().get("peso").equals("")) {
                    int n = Integer.valueOf(AppContext.getInstance().get("peso").toString());
                    if (mat[Integer.valueOf(CirculoSeleccionado.getId())][Integer.valueOf(CirculoSeleccionado2.getId())] == 0) {
                        mat[Integer.valueOf(CirculoSeleccionado.getId())][Integer.valueOf(CirculoSeleccionado2.getId())] = n;
                    } else {
                        mat[Integer.valueOf(CirculoSeleccionado2.getId())][Integer.valueOf(CirculoSeleccionado.getId())] = n;
                    }
                }
            }
            dobleLinea1 = false;
            dobleLinea2 = false;
            CirculoSeleccionado = null;
        }
        // termina grafos dirigidos
        //empieza grafos No dirigidos
    }

    @FXML
    private void crearConexion(ActionEvent event) {
        btnMover.setSelected(false);

    }

    @FXML
    private void moverNodos(ActionEvent event) {

        CirculoSeleccionado = null;
        mover = btnMover.isSelected();
    }

    @FXML
    private void CambiarPantalla(MouseEvent event) {
        if (tabMatriz.isSelected()) {
            AncMatriz.getChildren().clear();
            gridInformación.setVisible(false);

            GridPane matriz = new GridPane();

            for (int i = 0; i <= num; i++) {
                for (int j = 0; j <= num; j++) {
                    if (i >= 1 && j == 0) {
                        TextField t = new TextField(String.valueOf(Letras[i - 1]));
                        t.setMaxWidth(54);
                        t.setEditable(false);
                        matriz.add(t, i, j);
                    } else if (i == 0 && j >= 1) {
                        TextField t = new TextField(String.valueOf(Letras[j - 1]));
                        t.setMaxWidth(54);
                        t.setEditable(false);
                        matriz.add(t, i, j);
                    } else if (i >= 1 && j >= 1) {
                        TextField t = new TextField(String.valueOf(mat[i - 1][j - 1]));
                        t.setMaxWidth(54);
                        matriz.add(t, i, j);
                    } else {
                        TextField t = new TextField("I/D");
                        t.setMaxWidth(54);
                        matriz.add(t, i, j);
                    }
                }
            }
            AncMatriz.getChildren().add(matriz);
        } else {
            gridInformación.setVisible(true);
        }

    }

    @FXML
    private void crearNodo(ActionEvent event) {
        btnMover.setSelected(false);
        mover = false;
        if (num < 15) {
            Button roundButton = new Button();

            roundButton.setStyle(
                    "-fx-background-radius: 50em; "
                    + "-fx-min-width: 50px; "
                    + "-fx-min-height: 50px; "
                    + "-fx-max-width: 50px; "
                    + "-fx-max-height: 50px;"
                    + "-fx-background-color:#3c7fb1;"
                    + "-fx-font: 250% sans-serif;"
            );
            roundButton.setText(String.valueOf(Letras[num]));
            roundButton.setId(String.valueOf(num));
            num++;

            roundButton.setVisible(false);
            roundButton.setLayoutX(0);
            roundButton.setLayoutY(AncPanel.getWidth());
            ListaDeConexiones.add(new Nodos(roundButton, false));
            AncPanel.getChildren().add(roundButton);
            NuevoCirculo = roundButton;
        } else {
            btnNuevoNodo.setDisable(true);
            new Alert(Alert.AlertType.WARNING, "Se supero el número de nodos permitidos.", ButtonType.OK).showAndWait();
        }

    }

    @FXML
    private void eiminarObjeto(ActionEvent event) {

    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            if (seleccionado == false && !btnNuevoNodo.isSelected()) {
                while (CirculoSeleccionado == null) {
                    CirculoSeleccionado = e.getPickResult().getIntersectedNode();
                    if (CirculoSeleccionado.getStyleClass().toString().equals("text")) {
                        CirculoSeleccionado = CirculoSeleccionado.getParent();
                    }

                    seleccionado = true;
                    if (btnNuevaConexion.isSelected()) {

                        CirculoSeleccionado = e.getPickResult().getIntersectedNode();
                        if (CirculoSeleccionado.getStyleClass().toString().equals("text")) {
                            CirculoSeleccionado = CirculoSeleccionado.getParent();
                        }
                        cantidadNodosSeleccionados++;

                        seleccionado = false;
                    }
                }
                // el siguiente bloque es para visualizar la info de los nodos
                txtIdentificador.setText("Nodo-> " + Letras[Integer.valueOf(CirculoSeleccionado.getId())]);
                for (int i = 0; i < ListaDeConexiones.size(); i++) {
                    if (ListaDeConexiones.get(i).getNodo().equals(CirculoSeleccionado)) {
                        txtGradoNodo.setText(String.valueOf("Grado-> " + ListaDeConexiones.get(i).getListaConexiones().size()));
                    }
                }
                txtNodoConectados.clear();
                txtNodoConectados.setText("Nodos Conectados ");
                txtDistanciaEntreNodos.clear();
                txtDistanciaEntreNodos.setText("Distancia entre Nodos ");
                for (int i = 0; i < 15; i++) {
                    if (mat[i][Integer.valueOf(CirculoSeleccionado.getId())] != 0) {
                        txtNodoConectados.setText(txtNodoConectados.getText() + " -> " + String.valueOf(Letras[i]));
                        txtDistanciaEntreNodos.setText(txtDistanciaEntreNodos.getText() + " -> " + String.valueOf(mat[i][Integer.valueOf(CirculoSeleccionado.getId())]));
                    }
                }
                if (btnEliminar.isSelected()) {
                    boolean elimino = false;
                    for (int i = 0; i < ListaDeConexiones.size(); i++) {
                        if (ListaDeConexiones.get(i).getNodo().equals(CirculoSeleccionado)) {
                            if (i != ListaDeConexiones.size() - 1) {
                                elimino = true;
                            }
                            for (int j = 0; j < ListaDeConexiones.get(i).getListaConexiones().size(); j++) {
                                AncPanel.getChildren().remove(ListaDeConexiones.get(i).getListaConexiones().get(j));

                            }
                            ListaDeConexiones.remove(i);

                        }
                        if (elimino) {
                            Button.class.cast(ListaDeConexiones.get(i).getNodo()).setText(String.valueOf(Letras[i]));
                            for (int x = 0; x <= num; x++) {
                                for (int j = 0; j <= num; j++) {
                                // correr la fila y la columna
                                }
                            }
                        }
                    }
                    AncPanel.getChildren().remove(CirculoSeleccionado);
                    num--;

                }
            } else {
                CirculoSeleccionado = null;
                seleccionado = false;
                btnNuevoNodo.setSelected(false);
                if (!btnMover.isSelected()) {
                    mover = false;
                }

            }

        }
    };

}
