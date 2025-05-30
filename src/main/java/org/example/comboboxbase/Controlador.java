package org.example.comboboxbase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        vista.getComboBox().valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String oldVal, String newVal) {
                actualizarEstadoGuardar();
                limpiarEstiloCampo(vista.getComboBox());
            }
        });
//Poner en la tablita
        vista.getColorPicker().setOnAction(this);
        vista.getColorPicker().showingProperty().addListener(this);
        vista.getColorPicker().valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> obs, Color oldVal, Color newVal) {
                actualizarEstadoGuardar();
                limpiarEstiloCampo(vista.getColorPicker());
            }
        });

        vista.getDatePicker().setOnAction(this);
        vista.getDatePicker().showingProperty().addListener(this);//Iria en la segunda columna ligar el escuchador
        vista.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> obs, LocalDate oldVal, LocalDate newVal) {
                actualizarEstadoGuardar();
                limpiarEstiloCampo(vista.getDatePicker());
            }
        });

        vista.getFechaInicialPicker().setOnAction(this);
        vista.getFechaInicialPicker().showingProperty().addListener(this);
        vista.getFechaInicialPicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> obs, LocalDate oldVal, LocalDate newVal) {
                actualizarEstadoGuardar();
                limpiarEstiloCampo(vista.getFechaInicialPicker());
            }
        });

        vista.getFechaFinalPicker().setOnAction(this);
        vista.getFechaFinalPicker().showingProperty().addListener(this);
        vista.getFechaFinalPicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> obs, LocalDate oldVal, LocalDate newVal) {
                actualizarEstadoGuardar();
                limpiarEstiloCampo(vista.getFechaFinalPicker());
            }
        });

        vista.getGuardarBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                guardarDatos();
            }
        });

        vista.getLimpiarBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                limpiarCampos();
            }
        });
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
        } else if (source == vista.getFechaInicialPicker()) {
            LocalDate fecha = vista.getFechaInicialPicker().getValue();
            if (fecha != null) {
                vista.getFechaInicialLabel().setText("Seleccionado: " + fecha.format(formato));
                registrarEvento("Fecha inicial seleccionada: " + fecha.format(formato));
            }
        } else if (source == vista.getFechaFinalPicker()) {
            LocalDate fecha = vista.getFechaFinalPicker().getValue();
            if (fecha != null) {
                vista.getFechaFinalLabel().setText("Seleccionado: " + fecha.format(formato));
                registrarEvento("Fecha final seleccionada: " + fecha.format(formato));
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
        } else if (obs == vista.getFechaInicialPicker().showingProperty()) {
            registrarEvento("Fecha inicial calendario " + (newVal ? "abierto" : "cerrado"));
        } else if (obs == vista.getFechaFinalPicker().showingProperty()) {
            registrarEvento("Fecha final calendario " + (newVal ? "abierto" : "cerrado"));
        }
    }

    private void guardarDatos() {
        String categoria = vista.getComboBox().getValue();
        Color color = vista.getColorPicker().getValue();
        LocalDate fecha = vista.getDatePicker().getValue();
        LocalDate fechaInicial = vista.getFechaInicialPicker().getValue();
        LocalDate fechaFinal = vista.getFechaFinalPicker().getValue();
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
        if (fechaInicial == null) {
            resaltarCampo(vista.getFechaInicialPicker());
            completo = false;
        }
        if (fechaFinal == null) {
            resaltarCampo(vista.getFechaFinalPicker());
            completo = false;
        }

        if (!completo) {
            vista.getResumenLabel().setText("Resumen: ¡Completa todos los campos!");
            registrarEvento("Intento de guardar con campos incompletos.");
            return;
        }

        if (fechaFinal.isBefore(fechaInicial)) {
            vista.getResumenLabel().setText("Resumen: La fecha final debe ser posterior a la inicial.");
            registrarEvento("Error: Fecha final anterior a la inicial.");
            resaltarCampo(vista.getFechaFinalPicker());
            return;
        }

        long dias = ChronoUnit.DAYS.between(fechaInicial, fechaFinal);
        String resumen = String.format("Resumen: %s, %s, %s, %d días entre %s y %s",
                categoria,
                color.toString(),
                fecha.format(formato),
                dias,
                fechaInicial.format(formato),
                fechaFinal.format(formato)
        );
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
        vista.getFechaInicialPicker().setValue(null);
        vista.getFechaFinalPicker().setValue(null);
        vista.getComboBoxLabel().setText("Seleccionado: ");
        vista.getDateLabel().setText("Seleccionado: ");
        vista.getFechaInicialLabel().setText("Seleccionado: ");
        vista.getFechaFinalLabel().setText("Seleccionado: ");
        vista.getColorDisplay().setFill(Color.WHITE);
        vista.getResumenLabel().setText("Resumen: ");
        limpiarEstiloCampo(vista.getComboBox());
        limpiarEstiloCampo(vista.getColorPicker());
        limpiarEstiloCampo(vista.getDatePicker());
        limpiarEstiloCampo(vista.getFechaInicialPicker());
        limpiarEstiloCampo(vista.getFechaFinalPicker());
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
                vista.getDatePicker().getValue() != null &&
                vista.getFechaInicialPicker().getValue() != null &&
                vista.getFechaFinalPicker().getValue() != null;
        vista.getGuardarBtn().setDisable(!habilitar);
    }

    private void mostrarConfirmacion(String mensaje) {
        registrarEvento(mensaje);
    }

    private void registrarEvento(String mensaje) {
        vista.getEventLog().setText(vista.getEventLog().getText() + "\n" + mensaje);
    }
}