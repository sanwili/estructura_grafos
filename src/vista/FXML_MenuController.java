/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import com.sun.java.swing.plaf.windows.resources.windows;
import controladores.AppContext;
import controladores.Nodos;
import controladores.logica.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
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
    private GridPane gridAlgoritmos;
    @FXML
    private GridPane gridControles;
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
    private ToggleButton btnListaAdyacencia;
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
    boolean dijkstra = false, dijkstraLargo = false;
    boolean seleccionado = false, mover = false;// seleccionado es para determinar si se selecciono un nodo, mover es el permiso para moverlo
    boolean dobleLinea1 = false, dobleLinea2 = false;
    Node CirculoSeleccionado = null, CirculoSeleccionado2 = null, lineaSeleccionada = null;// representan los circulos para la creación de lineas
    Node NuevoCirculo = null; // nuevo ciculo que se crea
    Integer num = 0, num2 = 0, cantidadNodosSeleccionados = 0, cantidadLineas = 0;
    Line NuevaLinea = null;// nueva linea que se crea
    List<Nodos> ListaDeConexiones = new ArrayList<>();
    List<Node> ListaEliminar = new ArrayList<>();
    int mat[][] = new int[15][15];
    char[] Letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ'};
    Grafo grafo = new Grafo();
    Algoritmo algoritmo = new Algoritmo();
    int nodo1 = 0, nodo2 = 0;

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
        btnArbolAbarcador.disableProperty().bind(rdbDirigido.selectedProperty());
    }

    @FXML
    private void activarDiskstra(ActionEvent event) {
        algoritmo.limpiar();
        dijkstra = true;
        CirculoSeleccionado = null;
        CirculoSeleccionado2 = null;
        new Alert(Alert.AlertType.INFORMATION, "Seleccione el nodo de Inicio y de Fin.", ButtonType.OK).showAndWait();

    }

    private void CambiarNodosVerdes(Node n) throws InterruptedException {
        n.setStyle("-fx-background-radius: 50em; "
                + "-fx-min-width: 50px; "
                + "-fx-min-height: 50px; "
                + "-fx-max-width: 50px; "
                + "-fx-max-height: 50px;"
                + "-fx-background-color:#61b13c;"
                + "-fx-font: 150% sans-serif;"
        );

    }

    @FXML
    private void activarFloyd(ActionEvent event) {
        ObtenerListaFloyd();

    }

    @FXML
    private void activarCaminaLargoAleatorio(ActionEvent event) {
        algoritmo.limpiar();
        dijkstraLargo = true;
        CirculoSeleccionado = null;
        CirculoSeleccionado2 = null;
        obtenerDijkstraLargo();
    }

    @FXML
    private void activarArbolAbarcador(ActionEvent event) {
        algoritmo.limpiar();
        algoritmo.arbolMinimo(mat, num);
        CirculoSeleccionado = null;
        CirculoSeleccionado2 = null;
        nodo1 = 0;
        nodo2 = 0;
        NuevaLinea = null;
        for (int i = 1; i < num; i++) {

            System.out.println(algoritmo.getNodosArbolMinimo()[i] + " " + i);

        }
        for (int i = 1; i < num; i++) {

            CirculoSeleccionado = ListaDeConexiones.get(algoritmo.getNodosArbolMinimo()[i]).getNodo();
            nodo1 = algoritmo.getNodosArbolMinimo()[i];

            CirculoSeleccionado2 = ListaDeConexiones.get(i).getNodo();
            nodo2 = i;
            boolean salir = false;
            for (int a = 0; a < (ListaDeConexiones.get(nodo1).getListaConexiones().size()); a++) {
                for (int b = 0; b < (ListaDeConexiones.get(nodo2).getListaConexiones().size()); b++) {
                    if (!ListaDeConexiones.get(nodo1).getListaConexiones().isEmpty() && !ListaDeConexiones.get(nodo2).getListaConexiones().isEmpty()) {
                        if (ListaDeConexiones.get(nodo1).getListaConexiones().get(a).getId().equals(ListaDeConexiones.get(nodo2).getListaConexiones().get(b).getId())) {
                            NuevaLinea = (Line) ListaDeConexiones.get(nodo2).getListaConexiones().get(b);

                        }
                    }
                }
            }

            NuevaLinea.setStroke(Paint.valueOf("#29f2ab"));
            CirculoSeleccionado.setStyle("-fx-background-radius: 50em; "
                    + "-fx-min-width: 50px; "
                    + "-fx-min-height: 50px; "
                    + "-fx-max-width: 50px; "
                    + "-fx-max-height: 50px;"
                    + "-fx-background-color:#29f2ab;"
                    + "-fx-font: 150% sans-serif;"
            );
            CirculoSeleccionado2.setStyle("-fx-background-radius: 50em; "
                    + "-fx-min-width: 50px; "
                    + "-fx-min-height: 50px; "
                    + "-fx-max-width: 50px; "
                    + "-fx-max-height: 50px;"
                    + "-fx-background-color:#29f2ab;"
                    + "-fx-font: 150% sans-serif;"
            );

        }
        CirculoSeleccionado = null;
        CirculoSeleccionado2 = null;
        nodo1 = 0;
        nodo2 = 0;
        NuevaLinea = null;

    }

    @FXML
    private void hacerNoDirigido(ActionEvent event) throws InterruptedException {
        CirculoSeleccionado = null;
        CirculoSeleccionado2 = null;
        nodo1 = 0;
        nodo2 = 0;
        NuevaLinea = null;

        for (int i = 0; i < ListaDeConexiones.size(); i++) {
            for (int j = 1; j < ListaDeConexiones.size(); j++) {

                CirculoSeleccionado = ListaDeConexiones.get(i).getNodo();
                nodo1 = i;

                CirculoSeleccionado2 = ListaDeConexiones.get(j).getNodo();
                nodo2 = j;
                boolean salir = false;
                for (int a = 0; a < (ListaDeConexiones.get(nodo1).getListaConexiones().size()); a++) {
                    for (int b = 0; b < (ListaDeConexiones.get(nodo2).getListaConexiones().size()); b++) {
                        if (!ListaDeConexiones.get(nodo1).getListaConexiones().isEmpty() && !ListaDeConexiones.get(nodo2).getListaConexiones().isEmpty()) {
                            if (ListaDeConexiones.get(nodo1).getListaConexiones().get(a).getId().equals(ListaDeConexiones.get(nodo2).getListaConexiones().get(b).getId())) {
                                NuevaLinea = (Line) ListaDeConexiones.get(nodo2).getListaConexiones().get(b);
                                if (mat[nodo2][nodo1] == 0) {
                                    mat[nodo2][nodo1] = mat[nodo1][nodo2];
                                } else {
                                    mat[nodo1][nodo2] = mat[nodo2][nodo1];
                                }
                            }
                        }
                    }
                }

                NuevaLinea.setStroke(Paint.valueOf("#ff4444"));

            }
        }
        CirculoSeleccionado = null;
        CirculoSeleccionado2 = null;
        nodo1 = 0;
        nodo2 = 0;
        NuevaLinea = null;
    }

    void moverCirculo(Node cir) {
        if (mover) {
            cir.setLayoutX(xMouse);
            cir.setLayoutY(yMouse);
        }
    }

    void AbrirModal(String direccion, Window owner) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(direccion));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);

            stage.initOwner(owner);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {

            System.err.println(ex);
        }
    }

    @FXML
    private void obtenerPosicionMouse(MouseEvent event) throws InterruptedException {
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

        if (btnNuevaConexion.isSelected() && cantidadNodosSeleccionados < 3 && CirculoSeleccionado != null) {
            if (cantidadNodosSeleccionados == 1) {
                NuevaLinea = new Line();
                NuevaLinea.setFill(Paint.valueOf("#000000"));
                NuevaLinea.setStrokeWidth(4);
                NuevaLinea.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerLineas);

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
                    cantidadLineas++;
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
                    if (rdbNoDirigido.isSelected()) {
                        dobleLinea1 = true;
                        dobleLinea2 = true;
                    }

                    if (dobleLinea1 == true && dobleLinea2 == true) {
                        NuevaLinea.setStroke(Paint.valueOf("#ff4444"));
                        NuevaLinea.setStrokeWidth(5);

                    }
                    CirculoSeleccionado.setStyle(
                            "-fx-background-radius: 50em; "
                            + "-fx-min-width: 50px; "
                            + "-fx-min-height: 50px; "
                            + "-fx-max-width: 50px; "
                            + "-fx-max-height: 50px;"
                            + "-fx-background-color:#3c7fb1;"
                            + "-fx-font: 250% sans-serif;"
                    );
                    CirculoSeleccionado2.setStyle(
                            "-fx-background-radius: 50em; "
                            + "-fx-min-width: 50px; "
                            + "-fx-min-height: 50px; "
                            + "-fx-max-width: 50px; "
                            + "-fx-max-height: 50px;"
                            + "-fx-background-color:#3c7fb1;"
                            + "-fx-font: 250% sans-serif;"
                    );

                    AncPanel.getChildren().add(NuevaLinea);
                    AncPanel.getChildren().add(CirculoSeleccionado2);
                    AncPanel.getChildren().add(CirculoSeleccionado);
                }
                AbrirModal("/vista/FXML_Peso.fxml", btnNuevaConexion.getScene().getWindow());

                cantidadNodosSeleccionados = 0;
                btnNuevaConexion.setSelected(false);
                if (!AppContext.getInstance().get("peso").equals("")) {
                    int n = Integer.valueOf(AppContext.getInstance().get("peso").toString());
                    if (mat[Integer.valueOf(CirculoSeleccionado2.getId())][Integer.valueOf(CirculoSeleccionado.getId())] == 0 && rdbDirigido.isSelected()) {
                        mat[Integer.valueOf(CirculoSeleccionado2.getId())][Integer.valueOf(CirculoSeleccionado.getId())] = n;
                    } else {
                        mat[Integer.valueOf(CirculoSeleccionado.getId())][Integer.valueOf(CirculoSeleccionado2.getId())] = n;
                    }
                    if (mat[Integer.valueOf(CirculoSeleccionado2.getId())][Integer.valueOf(CirculoSeleccionado.getId())] == 0 && rdbNoDirigido.isSelected()) {
                        mat[Integer.valueOf(CirculoSeleccionado2.getId())][Integer.valueOf(CirculoSeleccionado.getId())] = n;
                        mat[Integer.valueOf(CirculoSeleccionado.getId())][Integer.valueOf(CirculoSeleccionado2.getId())] = n;
                    }
                }
            }

            dobleLinea1 = false;
            dobleLinea2 = false;
            CirculoSeleccionado = null;
            grafo.setMatrizAdyacencia(mat);
            grafo.actualizarListaAdyacencia();
        }
        // termina grafos dirigidos
        //empieza grafos No dirigidos
        if (dijkstra == true) {

            if (CirculoSeleccionado != null && CirculoSeleccionado2 == null) {
                CirculoSeleccionado2 = CirculoSeleccionado;
                CirculoSeleccionado = null;

            }
            if (CirculoSeleccionado != null && CirculoSeleccionado2 != null) {

                algoritmo.dijkstraCorto(mat, Integer.valueOf(CirculoSeleccionado2.getId()), Integer.valueOf(CirculoSeleccionado.getId()), Integer.MAX_VALUE, 0);
                CirculoSeleccionado = null;
                CirculoSeleccionado2 = null;
                nodo1 = 0;
                nodo2 = 0;
                NuevaLinea = null;

                new Thread(() -> {
                    for (int i = 0; i < algoritmo.getListaResultadoDijkstra().size(); i++) {

                        for (int x = 0; x < ListaDeConexiones.size(); x++) {
                            if (ListaDeConexiones.get(x).getNodo().getId().equals(String.valueOf(algoritmo.getListaResultadoDijkstra().get(i)))) {
                                CirculoSeleccionado = ListaDeConexiones.get(x).getNodo();
                                nodo1 = x;
                            }
                            if (i < algoritmo.getListaResultadoDijkstra().size() - 1 && ListaDeConexiones.get(x).getNodo().getId().equals(String.valueOf(algoritmo.getListaResultadoDijkstra().get(i + 1)))) {
                                CirculoSeleccionado2 = ListaDeConexiones.get(x).getNodo();
                                nodo2 = x;
                            }
                        }
                        for (int a = 0; a < (ListaDeConexiones.get(nodo1).getListaConexiones().size()); a++) {
                            for (int b = 0; b < (ListaDeConexiones.get(nodo2).getListaConexiones().size()); b++) {
                                if (ListaDeConexiones.get(nodo1).getListaConexiones().get(a).getId().equals(ListaDeConexiones.get(nodo2).getListaConexiones().get(b).getId())) {
                                    NuevaLinea = (Line) ListaDeConexiones.get(nodo2).getListaConexiones().get(b);
                                }
                            }
                        }

                        Platform.runLater(() -> {
                            try {
                                CambiarNodosVerdes(CirculoSeleccionado);

                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (i < algoritmo.getListaResultadoDijkstra().size() - 1) {
                            NuevaLinea.setStroke(Paint.valueOf("#61b13c"));
                        }
                        if (i == algoritmo.getListaResultadoDijkstra().size() - 1) {
                            try {
                                CambiarNodosVerdes(CirculoSeleccionado2);

                                break;
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();

                dijkstra = false;
                CirculoSeleccionado = null;
                CirculoSeleccionado2 = null;
            }
        }
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
            gridControles.setDisable(true);
            gridAlgoritmos.setDisable(true);

            GridPane matriz = new GridPane();

            for (int i = 0; i <= num; i++) {
                for (int j = 0; j <= num; j++) {
                    if (i >= 1 && j == 0) {
                        TextField t = new TextField(String.valueOf(Letras[i - 1]));
                        t.setMaxWidth(54);
                        t.setEditable(false);
                        matriz.add(t, j, i);
                    } else if (i == 0 && j >= 1) {
                        TextField t = new TextField(String.valueOf(Letras[j - 1]));
                        t.setMaxWidth(54);
                        t.setEditable(false);
                        matriz.add(t, j, i);
                    } else if (i >= 1 && j >= 1) {
                        TextField t = new TextField(String.valueOf(mat[i - 1][j - 1]));
                        t.setMaxWidth(54);
                        matriz.add(t, j, i);

                    } else {
                        TextField t = new TextField("I/D");
                        t.setMaxWidth(54);
                        matriz.add(t, j, i);
                    }
                }
            }
            AncMatriz.getChildren().add(matriz);
        } else {
            gridInformación.setVisible(true);
            gridControles.setDisable(false);
            gridAlgoritmos.setDisable(false);
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
        grafo.crearNodo();
        grafo.actualizarListaAdyacencia();
        btnNuevoNodo.setDisable(true);
    }

    @FXML
    private void DesSeleccionar(MouseEvent event) {
        if (CirculoSeleccionado != null) {
            for (int i = 0; i < ListaDeConexiones.size(); i++) {
                if (ListaDeConexiones.get(i).getNodo().getId().equals(CirculoSeleccionado.getId()) && ListaDeConexiones.get(i).isLazo()) {
                    CirculoSeleccionado.setStyle(
                            "-fx-background-radius: 50em; "
                            + "-fx-min-width: 50px; "
                            + "-fx-min-height: 50px; "
                            + "-fx-max-width: 50px; "
                            + "-fx-max-height: 50px;"
                            + "-fx-background-color:#91b13c;"
                            + "-fx-font: 250% sans-serif;"
                    );
                } else {
                    CirculoSeleccionado.setStyle(
                            "-fx-background-radius: 50em; "
                            + "-fx-min-width: 50px; "
                            + "-fx-min-height: 50px; "
                            + "-fx-max-width: 50px; "
                            + "-fx-max-height: 50px;"
                            + "-fx-background-color:#3c7fb1;"
                            + "-fx-font: 250% sans-serif;"
                    );
                }
            }

            txtDistanciaEntreNodos.clear();
            txtGradoNodo.clear();
            txtIdentificador.clear();
            txtNodoConectados.clear();
            CirculoSeleccionado = null;
            txtDistanciaEntreNodos.setPromptText("Distancia a los Nodos Conectados");
            txtGradoNodo.setPromptText("Grado del Nodo");
            txtIdentificador.setPromptText("Identificador");
            txtNodoConectados.setPromptText("Nodos Conectados");
        }
    }

    @FXML
    private void ObtenerListaAdyacencia(ActionEvent event) {
        List<String> adya = new ArrayList<>();
        adya.add("Lista de adyacencia");
        if (CirculoSeleccionado == null) {
            for (int i = 0; i < num; i++) {
                ListaAdyacencia aux = grafo.getNodos()[i].getListaAdyacencia();

                while (aux != null) {
                    adya.add("Nodo: " + String.valueOf(Letras[i]) + " -> "
                            + String.valueOf(Letras[aux.getArista()]) + " / "
                            + String.valueOf(aux.getCosto()));
                    aux = aux.getSig();
                }
            }
        } else {
            ListaAdyacencia aux = grafo.getNodos()[Integer.valueOf(CirculoSeleccionado.getId())].getListaAdyacencia();

            while (aux != null) {
                adya.add("Nodo: " + String.valueOf(Letras[Integer.valueOf(CirculoSeleccionado.getId())]) + " -> "
                        + String.valueOf(Letras[aux.getArista()]) + " / "
                        + String.valueOf(aux.getCosto()));
                aux = aux.getSig();
            }
        }
        if (!adya.isEmpty()) {
            AppContext.getInstance().set("lista", adya);

            AbrirModal("/vista/FXML_ListaAdyacencia.fxml", btnListaAdyacencia.getScene().getWindow());
        } else {

            new Alert(Alert.AlertType.ERROR, "No tiene nodos para poder realizar esta acción.", ButtonType.OK).showAndWait();
        }
        btnListaAdyacencia.setSelected(false);
    }

    private void ObtenerListaFloyd() {
        List<String> adya = new ArrayList<>();
        adya.add("Floyd (camino más corto)");
        algoritmo.limpiar();
        algoritmo.floyd(mat, num);
        for (int i = 0; i < num; i++) {
            adya.add("Nodo " + String.valueOf(Letras[i]));
            for (int j = 0; j < num; j++) {
                algoritmo.obtenerCaminoFloyd(i, j);
                List<Integer> resultado = algoritmo.getCaminoFloyd();
                String todoCamino = new String("");
                adya.add("   Inicio: " + String.valueOf(Letras[i]) + "  Final: " + String.valueOf(Letras[j]));
                todoCamino = todoCamino + "      "; //Tabulación
                for (int k = 0; k < resultado.size(); k++) {
                    if (k != resultado.size() - 1) {
                        todoCamino = todoCamino + Letras[resultado.get(k)] + "->";
                    } else {
                        todoCamino = todoCamino + Letras[resultado.get(k)] + ".";
                    }
                }
                adya.add(String.valueOf(todoCamino));
                algoritmo.limpiarCaminoFloyd();
            }
        }

        if (!adya.isEmpty()) {
            AppContext.getInstance().set("lista", adya);
            AbrirModal("/vista/FXML_ListaAdyacencia.fxml", btnListaAdyacencia.getScene().getWindow());
        } else {
            new Alert(Alert.AlertType.ERROR, "No tiene nodos para poder realizar esta acción.", ButtonType.OK).showAndWait();
        }
        //btnFloyd.setSelected(false);
    }

    private void obtenerDijkstraLargo() {
        if (dijkstraLargo == true) {
            Random aleatorio = new Random(System.currentTimeMillis());
            int intAleatorio = 0, intAleatorio2 = 0;

            while (intAleatorio == intAleatorio2) {//Que no sea el mismo nodo.
                intAleatorio = aleatorio.nextInt(num);
                aleatorio.setSeed(System.currentTimeMillis());

                intAleatorio2 = aleatorio.nextInt(num);
                aleatorio.setSeed(System.currentTimeMillis());
            }
            algoritmo.dijkstraLargo(mat, intAleatorio, intAleatorio2, 0, 0);

            CirculoSeleccionado = ListaDeConexiones.get(intAleatorio).getNodo();
            CirculoSeleccionado2 = ListaDeConexiones.get(intAleatorio2).getNodo();

            nodo1 = 0;
            nodo2 = 0;
            NuevaLinea = null;
            if (algoritmo.getListaResultadoDijkstra().isEmpty()) {
                obtenerDijkstraLargo();//Vuelve a buscar.
            } else {
                new Thread(() -> {
                    for (int i = 0; i < algoritmo.getListaResultadoDijkstra().size(); i++) {

                        for (int x = 0; x < ListaDeConexiones.size(); x++) {
                            if (ListaDeConexiones.get(x).getNodo().getId().equals(String.valueOf(algoritmo.getListaResultadoDijkstra().get(i)))) {
                                CirculoSeleccionado = ListaDeConexiones.get(x).getNodo();
                                nodo1 = x;
                            }
                            if (i < algoritmo.getListaResultadoDijkstra().size() - 1 && ListaDeConexiones.get(x).getNodo().getId().equals(String.valueOf(algoritmo.getListaResultadoDijkstra().get(i + 1)))) {
                                CirculoSeleccionado2 = ListaDeConexiones.get(x).getNodo();
                                nodo2 = x;
                            }
                        }
                        for (int a = 0; a < (ListaDeConexiones.get(nodo1).getListaConexiones().size()); a++) {
                            for (int b = 0; b < (ListaDeConexiones.get(nodo2).getListaConexiones().size()); b++) {
                                if (ListaDeConexiones.get(nodo1).getListaConexiones().get(a).getId().equals(ListaDeConexiones.get(nodo2).getListaConexiones().get(b).getId())) {
                                    NuevaLinea = (Line) ListaDeConexiones.get(nodo2).getListaConexiones().get(b);
                                }
                            }
                        }

                        Platform.runLater(() -> {
                            try {
                                CambiarNodosVerdes(CirculoSeleccionado);

                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (i < algoritmo.getListaResultadoDijkstra().size() - 1) {
                            NuevaLinea.setStroke(Paint.valueOf("#61b13c"));
                        }
                        if (i == algoritmo.getListaResultadoDijkstra().size() - 1) {
                            try {
                                CambiarNodosVerdes(CirculoSeleccionado2);
                                break;
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();
            }
            dijkstraLargo = false;
            CirculoSeleccionado = null;
            CirculoSeleccionado2 = null;
        }
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
                    CirculoSeleccionado.setStyle(
                            "-fx-background-radius: 50em; "
                            + "-fx-min-width: 50px; "
                            + "-fx-min-height: 50px; "
                            + "-fx-max-width: 50px; "
                            + "-fx-max-height: 50px;"
                            + "-fx-background-color:#79e2f7;"
                            + "-fx-font: 250% sans-serif;"
                    );
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
                        if (ListaDeConexiones.get(i).isLazo()) {
                            if (rdbDirigido.isSelected()) {
                                txtGradoNodo.setText(String.valueOf("Grado-> " + (ListaDeConexiones.get(i).getListaConexiones().size() + 2)));
                            }
                            if (rdbNoDirigido.isSelected()) {
                                txtGradoNodo.setText(String.valueOf("Grado-> " + ((ListaDeConexiones.get(i).getListaConexiones().size() * 2) + 2)));
                            }
                        } else {
                            if (rdbDirigido.isSelected()) {
                                txtGradoNodo.setText(String.valueOf("Grado-> " + ListaDeConexiones.get(i).getListaConexiones().size()));
                            }
                            if (rdbNoDirigido.isSelected()) {
                                txtGradoNodo.setText(String.valueOf("Grado-> " + ListaDeConexiones.get(i).getListaConexiones().size() * 2));
                            }

                        }
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
                            ListaEliminar = ListaDeConexiones.get(i).getListaConexiones();
                            for (int j = 0; j < ListaDeConexiones.get(i).getListaConexiones().size(); j++) {

                                AncPanel.getChildren().remove(ListaDeConexiones.get(i).getListaConexiones().get(j));

                            }
                            ListaDeConexiones.remove(i);
                            for (int y = 0; y < ListaDeConexiones.size(); y++) {
                                for (int a = 0; a < ListaDeConexiones.get(y).getListaConexiones().size(); a++) {
                                    if (ListaDeConexiones.get(y).getListaConexiones().get(a).getId().equals(ListaEliminar.get(a).getId())) {
                                        ListaDeConexiones.get(y).getListaConexiones().remove(a);
                                    }
                                }
                            }
                            ListaEliminar.clear();
                            // se convierte la fila y columna en 0
                            for (int x = 0; x <= num + 1; x++) {
                                mat[i][x] = 0;
                            }
                            for (int j = 0; j <= num + 1; j++) {
                                // correr la fila y la columna
                                mat[j][i] = 0;
                            }
                        }
                        if (elimino) {
                            ListaDeConexiones.get(i).getNodo().setId(String.valueOf(i));
                            Button.class.cast(ListaDeConexiones.get(i).getNodo()).setText(String.valueOf(Letras[i]));
                            for (int x = 0; x <= num + 1; x++) {
                                mat[i][x] = mat[i + 1][x];
                                mat[i + 1][x] = 0;
                            }
                            for (int j = 0; j <= num + 1; j++) {

                                mat[j][i] = mat[j][i + 1];
                                mat[j][i + 1] = 0;
                            }
                        }
                    }
                    AncPanel.getChildren().remove(CirculoSeleccionado);
                    num--;
                    elimino = false;
                    grafo.setMatrizAdyacencia(mat);
                    grafo.actualizarListaAdyacencia();
                }
            } else {
                if (CirculoSeleccionado != null) {
                    for (int i = 0; i < ListaDeConexiones.size(); i++) {
                        if (ListaDeConexiones.get(i).getNodo().getId().equals(CirculoSeleccionado.getId()) && ListaDeConexiones.get(i).isLazo()) {
                            CirculoSeleccionado.setStyle(
                                    "-fx-background-radius: 50em; "
                                    + "-fx-min-width: 50px; "
                                    + "-fx-min-height: 50px; "
                                    + "-fx-max-width: 50px; "
                                    + "-fx-max-height: 50px;"
                                    + "-fx-background-color:#91b13c;"
                                    + "-fx-font: 250% sans-serif;"
                            );
                        } else {
                            CirculoSeleccionado.setStyle(
                                    "-fx-background-radius: 50em; "
                                    + "-fx-min-width: 50px; "
                                    + "-fx-min-height: 50px; "
                                    + "-fx-max-width: 50px; "
                                    + "-fx-max-height: 50px;"
                                    + "-fx-background-color:#3c7fb1;"
                                    + "-fx-font: 250% sans-serif;"
                            );
                        }
                    }

                }
                CirculoSeleccionado = null;
                btnNuevoNodo.setDisable(false);
                txtDistanciaEntreNodos.setPromptText("Distancia a los Nodos Conectados");
                txtGradoNodo.setPromptText("Grado del Nodo");
                txtIdentificador.setPromptText("Identificador");
                txtNodoConectados.setPromptText("Nodos Conectados");
                seleccionado = false;
                btnNuevoNodo.setSelected(false);

                if (!btnMover.isSelected()) {
                    mover = false;
                }
            }
        }
    };
    EventHandler<MouseEvent> eventHandlerLineas = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (btnEliminar.isSelected()) {
                CirculoSeleccionado = null;
                CirculoSeleccionado2 = null;

                lineaSeleccionada = e.getPickResult().getIntersectedNode();
                for (int i = 0; i < ListaDeConexiones.size(); i++) {
                    for (int j = 0; j < ListaDeConexiones.get(i).getListaConexiones().size(); j++) {
                        if (lineaSeleccionada.getId().equals(ListaDeConexiones.get(i).getListaConexiones().get(j).getId())) {
                            if (CirculoSeleccionado == null) {
                                CirculoSeleccionado = ListaDeConexiones.get(i).getNodo();
                                ListaDeConexiones.get(i).getListaConexiones().remove(j);
                                continue;
                            }
                            if (CirculoSeleccionado2 == null && CirculoSeleccionado != null) {
                                CirculoSeleccionado2 = ListaDeConexiones.get(i).getNodo();
                                ListaDeConexiones.get(i).getListaConexiones().remove(j);
                            }

                        }
                    }
                }
                mat[Integer.valueOf(CirculoSeleccionado.getId())][Integer.valueOf(CirculoSeleccionado2.getId())] = 0;
                mat[Integer.valueOf(CirculoSeleccionado2.getId())][Integer.valueOf(CirculoSeleccionado.getId())] = 0;
                AncPanel.getChildren().remove(lineaSeleccionada);
            }
        }
    };
}
