// src/main/java/org/example/comboboxbase/Controlador.java
package org.example.comboboxbase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Control;

public class Controlador implements EventHandler<ActionEvent>, ChangeListener<Boolean> {
    private Main vista;
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Controlador(Main vista) {
        this.vista = vista;
        configurarEventos();
        actualizarEstadoGuardar();
    }

    private void configurarEventos() {
        vista.getComboBox().setOnAction(this);
        vista.getComboBox().showingProperty().addListener(this);
        vista.getComboBox().valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarEstadoGuardar();
            limpiarEstiloCampo(vista.getComboBox());
        });

        vista.getColorPicker().setOnAction(this);
        vista.getColorPicker().showingProperty().addListener(this);
        vista.getColorPicker().valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarEstadoGuardar();
            limpiarEstiloCampo(vista.getColorPicker());
        });

        vista.getDatePicker().setOnAction(this);
        vista.getDatePicker().showingProperty().addListener(this);
        vista.getDatePicker().valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarEstadoGuardar();
            limpiarEstiloCampo(vista.getDatePicker());
        });

        vista.getGuardarBtn().setOnAction(e -> guardarDatos());
        vista.getLimpiarBtn().setOnAction(e -> limpiarCampos());
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        if (source == vista.getComboBox()) {
            String seleccion = vista.getComboBox().getValue();
            vista.getComboBoxLabel().setText("Seleccionado: " + seleccion);
            registrarEvento("Categoría seleccionada: " + seleccion);
        } else if (source == vista.getColorPicker()) {
            Color color = vista.getColorPicker().getValue();
            vista.getColorDisplay().setFill(color);
            registrarEvento("Color seleccionado: " + color);
        } else if (source == vista.getDatePicker()) {
            LocalDate fecha = vista.getDatePicker().getValue();
            if (fecha != null) {
                vista.getDateLabel().setText("Seleccionado: " + fecha.format(formato));
                registrarEvento("Fecha seleccionada: " + fecha.format(formato));
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        if (obs == vista.getComboBox().showingProperty()) {
            registrarEvento("ComboBox popup " + (newVal ? "abierto" : "cerrado"));
        } else if (obs == vista.getColorPicker().showingProperty()) {
            registrarEvento("ColorPicker diálogo " + (newVal ? "abierto" : "cerrado"));
        } else if (obs == vista.getDatePicker().showingProperty()) {
            registrarEvento("DatePicker calendario " + (newVal ? "abierto" : "cerrado"));
        }
    }

    private void guardarDatos() {
        String categoria = vista.getComboBox().getValue();
        Color color = vista.getColorPicker().getValue();
        LocalDate fecha = vista.getDatePicker().getValue();
        boolean completo = true;
        if (categoria == null) {
            resaltarCampo(vista.getComboBox());
            completo = false;
        }
        if (color == null) {
            resaltarCampo(vista.getColorPicker());
            completo = false;
        }
        if (fecha == null) {
            resaltarCampo(vista.getDatePicker());
            completo = false;
        }
        if (!completo) {
            vista.getResumenLabel().setText("Resumen: ¡Completa todos los campos!");
            registrarEvento("Intento de guardar con campos incompletos.");
            return;
        }
        String resumen = String.format("Resumen: %s, %s, %s",
                categoria, color.toString(), fecha.format(formato));
        vista.getResumenLabel().setText(resumen);
        vista.getHistorialListView().getItems().add(resumen);
        registrarEvento("Datos guardados: " + resumen);
        mostrarConfirmacion("Cita guardada correctamente.");
        limpiarCampos();
    }

    private void limpiarCampos() {
        vista.getComboBox().setValue(null);
        vista.getColorPicker().setValue(null);
        vista.getDatePicker().setValue(null);
        vista.getComboBoxLabel().setText("Seleccionado: ");
        vista.getDateLabel().setText("Seleccionado: ");
        vista.getColorDisplay().setFill(Color.WHITE);
        vista.getResumenLabel().setText("Resumen: ");
        limpiarEstiloCampo(vista.getComboBox());
        limpiarEstiloCampo(vista.getColorPicker());
        limpiarEstiloCampo(vista.getDatePicker());
        actualizarEstadoGuardar();
        registrarEvento("Campos limpiados.");
    }

    private void resaltarCampo(Control campo) {
        campo.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void limpiarEstiloCampo(Control campo) {
        campo.setStyle("");
    }

    private void actualizarEstadoGuardar() {
        boolean habilitar = vista.getComboBox().getValue() != null &&
                vista.getColorPicker().getValue() != null &&
                vista.getDatePicker().getValue() != null;
        vista.getGuardarBtn().setDisable(!habilitar);
    }

    private void mostrarConfirmacion(String mensaje) {
        registrarEvento(mensaje);
    }

    private void registrarEvento(String mensaje) {
        vista.getEventLog().setText(vista.getEventLog().getText() + "\n" + mensaje);
    }
}

