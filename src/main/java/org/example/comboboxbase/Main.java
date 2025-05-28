package org.example.comboboxbase;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private ComboBox<String> comboBox;
    private ColorPicker colorPicker;
    private DatePicker datePicker;
    private DatePicker fechaInicialPicker, fechaFinalPicker;
    private Text eventLog;
    private Rectangle colorDisplay;
    private Label comboBoxLabel, dateLabel, resumenLabel;
    private Label fechaInicialLabel, fechaFinalLabel;
    private Button guardarBtn, limpiarBtn;
    private ListView<String> historialListView;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        Text title = new Text("Formulario de Cita");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Médico", "Odontología", "Psicología");
        comboBox.setPrefWidth(200);
        comboBoxLabel = new Label("Seleccionado: ");

        colorPicker = new ColorPicker();
        colorPicker.setPrefWidth(200);
        colorDisplay = new Rectangle(50, 50, Color.WHITE);
        colorDisplay.setStroke(Color.BLACK);

        datePicker = new DatePicker();
        datePicker.setPrefWidth(200);
        dateLabel = new Label("Seleccionado: ");

        // Nuevos campos para fecha inicial y final
        fechaInicialPicker = new DatePicker();
        fechaInicialPicker.setPrefWidth(200);
        fechaInicialLabel = new Label("Seleccionado: ");

        fechaFinalPicker = new DatePicker();
        fechaFinalPicker.setPrefWidth(200);
        fechaFinalLabel = new Label("Seleccionado: ");

        guardarBtn = new Button("Guardar");
        limpiarBtn = new Button("Limpiar");
        resumenLabel = new Label("Resumen: ");
        historialListView = new ListView<>();
        historialListView.setPrefHeight(100);

        grid.add(new Label("Categoría:"), 0, 0);
        grid.add(comboBox, 1, 0);
        grid.add(comboBoxLabel, 2, 0);

        grid.add(new Label("Color identificador:"), 0, 1);
        grid.add(colorPicker, 1, 1);
        grid.add(colorDisplay, 2, 1);

        grid.add(new Label("Fecha de cita:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(dateLabel, 2, 2);

        // Agrega las nuevas filas para fecha inicial y final
        grid.add(new Label("Fecha inicial:"), 0, 3);
        grid.add(fechaInicialPicker, 1, 3);
        grid.add(fechaInicialLabel, 2, 3);

        grid.add(new Label("Fecha final:"), 0, 4);
        grid.add(fechaFinalPicker, 1, 4);
        grid.add(fechaFinalLabel, 2, 4);

        grid.add(guardarBtn, 1, 5);
        grid.add(limpiarBtn, 1, 6);
        grid.add(resumenLabel, 2, 5);
        grid.add(new Label("Historial:"), 0, 7);
        grid.add(historialListView, 1, 7, 2, 1);

        eventLog = new Text();
        eventLog.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
        ScrollPane scrollPane = new ScrollPane(eventLog);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(120);

        root.getChildren().addAll(title, grid, new Label("Eventos:"), scrollPane);

        new Controlador(this);

        Scene scene = new Scene(root, 650, 600);
        stage.setTitle("Formulario de Cita");
        stage.setScene(scene);
        stage.show();
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public DatePicker getFechaInicialPicker() {
        return fechaInicialPicker;
    }

    public DatePicker getFechaFinalPicker() {
        return fechaFinalPicker;
    }

    public Text getEventLog() {
        return eventLog;
    }

    public Rectangle getColorDisplay() {
        return colorDisplay;
    }

    public Label getComboBoxLabel() {
        return comboBoxLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public Label getFechaInicialLabel() {
        return fechaInicialLabel;
    }

    public Label getFechaFinalLabel() {
        return fechaFinalLabel;
    }

    public Button getGuardarBtn() {
        return guardarBtn;
    }

    public Button getLimpiarBtn() {
        return limpiarBtn;
    }

    public ListView<String> getHistorialListView() {
        return historialListView;
    }

    public Label getResumenLabel() {
        return resumenLabel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}