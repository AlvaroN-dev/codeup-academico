/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package RegistroAcademico.codeup.academico.ui;

import RegistroAcademico.codeup.academico.domain.Estudiante;
import RegistroAcademico.codeup.academico.domain.Nota;
import RegistroAcademico.codeup.academico.service.ArchivoService;
import RegistroAcademico.codeup.academico.service.CalculoService;
import RegistroAcademico.codeup.academico.service.RegistroEstudiantesService;
import java.io.File;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author anonimo
 */
public class JFrameRegistroEstudiante extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFrameRegistroEstudiante.class.getName());
    RegistroEstudiantesService registroSt = new RegistroEstudiantesService();

    private CalculoService calculo = new CalculoService();
    private ArchivoService archivoService = new ArchivoService();

    /**
     * Creates new form JFrameRegistroEstudiante
     */
    public JFrameRegistroEstudiante() {
        initComponents();
    }

    private boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            jTextName.requestFocus();
            return false;
        }

        if (nombre.trim().length() < 2) {
            mostrarError("El nombre debe tener al menos 2 caracteres.");
            jTextName.requestFocus();
            return false;
        }

        if (nombre.trim().length() > 50) {
            mostrarError("El nombre no puede tener más de 50 caracteres.");
            jTextName.requestFocus();
            return false;
        }

        if (!nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarError("El nombre solo puede contener letras y espacios.");
            jTextName.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validarEdad(int edad) {
        if (edad < 15) {
            mostrarError("La edad mínima es 15 años.");
            jSpnAge.requestFocus();
            return false;
        }

        if (edad > 100) {
            mostrarError("La edad máxima es 100 años.");
            jSpnAge.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validarNota(String notaTexto, String numeroNota, javax.swing.JTextField campo) {
        if (notaTexto == null || notaTexto.trim().isEmpty()) {
            mostrarError("La " + numeroNota + " es obligatoria.");
            campo.requestFocus();
            return false;
        }

        try {
            double nota = Double.parseDouble(notaTexto.trim());

            if (nota < 0) {
                mostrarError("La " + numeroNota + " no puede ser negativa.");
                campo.requestFocus();
                return false;
            }

            if (nota > 100) {
                mostrarError("La " + numeroNota + " no puede ser mayor a 100.");
                campo.requestFocus();
                return false;
            }

            if (notaTexto.contains(".")) {
                String[] partes = notaTexto.split("\\.");
                if (partes.length > 1 && partes[1].length() > 2) {
                    mostrarError("La " + numeroNota + " puede tener máximo 2 decimales.");
                    campo.requestFocus();
                    return false;
                }
            }

            return true;

        } catch (NumberFormatException e) {
            mostrarError("La " + numeroNota + " debe ser un número válido.");
            campo.requestFocus();
            return false;
        }
    }

    private boolean validarTodosLosCampos() {

        String nombre = jTextName.getText().trim();
        int edad = (Integer) jSpnAge.getValue();
        String nota1 = jTextGrade1.getText().trim();
        String nota2 = jTextGrade2.getText().trim();
        String nota3 = jTextGrade3.getText().trim();

        if (!validarNombre(nombre)) {
            return false;
        }
        if (!validarEdad(edad)) {
            return false;
        }
        if (!validarNota(nota1, "Nota 1", jTextGrade1)) {
            return false;
        }
        if (!validarNota(nota2, "Nota 2", jTextGrade2)) {
            return false;
        }
        if (!validarNota(nota3, "Nota 3", jTextGrade3)) {
            return false;
        }

        return true;
    }

    private boolean validarEstudianteUnico(String nombre) {
        var estudiantes = registroSt.listarEstudiantes();

        for (var estudiante : estudiantes) {
            if (estudiante.getName().trim().equalsIgnoreCase(nombre.trim())) {
                int opcion = JOptionPane.showConfirmDialog(
                        this,
                        "Ya existe un estudiante con el nombre '" + nombre + "'.\n"
                        + "¿Desea agregarlo de todos modos?",
                        "Estudiante duplicado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                return opcion == JOptionPane.YES_OPTION;
            }
        }

        return true;
    }

    private boolean validarCamposParaCalculo() {
        String nombre = jTextName.getText().trim();
        String nota1 = jTextGrade1.getText().trim();
        String nota2 = jTextGrade2.getText().trim();
        String nota3 = jTextGrade3.getText().trim();

        if (nombre.isEmpty() || nota1.isEmpty() || nota2.isEmpty() || nota3.isEmpty()) {
            mostrarError("Debe completar todos los campos antes de calcular.");
            return false;
        }

        return validarTodosLosCampos();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void limpiarErrores() {

        jTextName.setBackground(java.awt.Color.WHITE);
        jTextGrade1.setBackground(java.awt.Color.WHITE);
        jTextGrade2.setBackground(java.awt.Color.WHITE);
        jTextGrade3.setBackground(java.awt.Color.WHITE);
    }

    private void resaltarCampoError(javax.swing.JTextField campo) {
        campo.setBackground(new java.awt.Color(255, 200, 200)); // Fondo rojizo
        campo.requestFocus();
    }

    private Estudiante studentsInfoValidado() {
        if (!validarTodosLosCampos()) {
            return null;
        }

        String name = jTextName.getText().trim();

        if (!validarEstudianteUnico(name)) {
            return null;
        }

        int age = (Integer) jSpnAge.getValue();
        double grade1 = Double.parseDouble(jTextGrade1.getText().trim());
        double grade2 = Double.parseDouble(jTextGrade2.getText().trim());
        double grade3 = Double.parseDouble(jTextGrade3.getText().trim());

        Estudiante student = new Estudiante(name, age);

        Nota nota1 = new Nota(grade1);
        Nota nota2 = new Nota(grade2);
        Nota nota3 = new Nota(grade3);

        student.agregarNota(nota1);
        student.agregarNota(nota2);
        student.agregarNota(nota3);

        return student;
    }

    private void guardarCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setSelectedFile(new File("estudiantes.csv"));

        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }

            if (archivo.exists()) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "El archivo ya existe. ¿Desea sobrescribirlo?",
                        "Confirmar sobrescritura",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            try {
                var estudiantes = registroSt.obtenerTodos();

                if (estudiantes.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No hay estudiantes para guardar.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }

                archivoService.guardarCSV(archivo, estudiantes);

                JOptionPane.showMessageDialog(
                        this,
                        "Datos guardados correctamente en:\n" + archivo.getAbsolutePath(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al guardar archivo: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }

    private void cargarCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargar archivo CSV");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!ArchivoService.esArchivoCSV(archivo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor seleccione un archivo CSV válido.",
                        "Archivo inválido",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String[] opciones = {"Reemplazar datos actuales", "Fusionar por ID", "Cancelar"};
            int opcion = JOptionPane.showOptionDialog(
                    this,
                    "¿Cómo desea cargar los datos?\n\n"
                    + "Reemplazar: Elimina todos los estudiantes actuales\n"
                    + "Fusionar: Mantiene los existentes y agrega los nuevos",
                    "Opciones de carga",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (opcion == 2) {
                return;
            }

            try {
                var estudiantes = archivoService.cargarCSV(archivo);

                if (opcion == 0) {
                    registroSt.reemplazar(estudiantes);
                } else {
                    registroSt.fusionar(estudiantes);
                }

                actualizarListaEstudiantes();

                String mensaje = String.format(
                        "Datos cargados correctamente.\n"
                        + "Estudiantes procesados: %d\n"
                        + "Total de estudiantes: %d",
                        estudiantes.size(),
                        registroSt.obtenerTodos().size()
                );

                JOptionPane.showMessageDialog(
                        this,
                        mensaje,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al cargar archivo: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }

    private void actualizarListaEstudiantes() {
        var modelo = (javax.swing.table.DefaultTableModel) jTableData.getModel();

        modelo.setRowCount(0);

        for (var est : registroSt.listarEstudiantes()) {
            modelo.addRow(new Object[]{
                est.getName(),
                est.getAge(),
                est.getNotas().get(0).getValor(),
                est.getNotas().get(1).getValor(),
                est.getNotas().get(2).getValor()

            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSpnAge = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextGrade1 = new javax.swing.JTextField();
        jTextGrade2 = new javax.swing.JTextField();
        jTextGrade3 = new javax.swing.JTextField();
        jLbTitleResult = new javax.swing.JLabel();
        JbtnCalculate = new javax.swing.JButton();
        jLblTitleAverage = new javax.swing.JLabel();
        jLblTitleNote = new javax.swing.JLabel();
        jLblTitleResul = new javax.swing.JLabel();
        jLabelAverage = new javax.swing.JLabel();
        jLabelGradeMax = new javax.swing.JLabel();
        jLabelResult = new javax.swing.JLabel();
        jBtnClear = new javax.swing.JButton();
        jBtnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableData = new javax.swing.JTable();
        jBtnSave = new javax.swing.JButton();
        jBttnStadistic = new javax.swing.JButton();
        jLblTituloPromedio = new javax.swing.JLabel();
        jLblPromedioGlobal = new javax.swing.JLabel();
        jLblTituleMejorStudent = new javax.swing.JLabel();
        jLblResultMejorStudent = new javax.swing.JLabel();
        jLblTituleCantidadAprobado = new javax.swing.JLabel();
        jLblCantidadAprovado = new javax.swing.JLabel();
        jLblCantidadReprobado = new javax.swing.JLabel();
        jLblResultReprobado = new javax.swing.JLabel();
        jBtnGuardarCsv = new javax.swing.JButton();
        jBttnCargarCsv = new javax.swing.JButton();
        jBttnCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("          Registro Estudiantes");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 358, 36));

        jLabel2.setText("Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 44, 25));
        getContentPane().add(jTextName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 138, -1));

        jLabel3.setText("Age:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 36, 25));
        getContentPane().add(jSpnAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 79, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("  Registro de Notas ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 235, -1));

        jLabel5.setText("Nota 1:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        jLabel6.setText("Nota 2:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, -1, -1));

        jLabel7.setText("Nota 3:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, -1, -1));
        getContentPane().add(jTextGrade1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 89, -1));
        getContentPane().add(jTextGrade2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 96, -1));
        getContentPane().add(jTextGrade3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 92, -1));

        jLbTitleResult.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        getContentPane().add(jLbTitleResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 143, 30));

        JbtnCalculate.setBackground(new java.awt.Color(51, 255, 51));
        JbtnCalculate.setForeground(new java.awt.Color(30, 30, 30));
        JbtnCalculate.setText("Calcular");
        JbtnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnCalculateActionPerformed(evt);
            }
        });
        getContentPane().add(JbtnCalculate, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 96, -1));
        getContentPane().add(jLblTitleAverage, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 70, 20));
        getContentPane().add(jLblTitleNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 530, 100, 20));
        getContentPane().add(jLblTitleResul, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 610, 80, 20));
        getContentPane().add(jLabelAverage, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 105, 27));
        getContentPane().add(jLabelGradeMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 135, 25));
        getContentPane().add(jLabelResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 640, 110, 25));

        jBtnClear.setBackground(new java.awt.Color(51, 255, 255));
        jBtnClear.setForeground(new java.awt.Color(30, 30, 30));
        jBtnClear.setText("Limpiar");
        jBtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnClearActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, 94, -1));

        jBtnExit.setBackground(new java.awt.Color(255, 51, 51));
        jBtnExit.setForeground(new java.awt.Color(30, 30, 30));
        jBtnExit.setText("Salir");
        jBtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExitActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1158, 6, -1, -1));

        jTableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Age", "Note 1", "Note 2", "Note 3"
            }
        ));
        jScrollPane1.setViewportView(jTableData);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 640, 580));

        jBtnSave.setBackground(new java.awt.Color(51, 255, 51));
        jBtnSave.setForeground(new java.awt.Color(30, 30, 30));
        jBtnSave.setText("Guardar");
        jBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 92, -1));

        jBttnStadistic.setBackground(new java.awt.Color(51, 255, 204));
        jBttnStadistic.setForeground(new java.awt.Color(30, 30, 30));
        jBttnStadistic.setText("Estadistica");
        jBttnStadistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnStadisticActionPerformed(evt);
            }
        });
        getContentPane().add(jBttnStadistic, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 93, -1));
        getContentPane().add(jLblTituloPromedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 460, 100, 20));
        getContentPane().add(jLblPromedioGlobal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 490, 70, 30));
        getContentPane().add(jLblTituleMejorStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, 100, 20));
        getContentPane().add(jLblResultMejorStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 560, 110, 30));
        getContentPane().add(jLblTituleCantidadAprobado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 600, 140, 20));
        getContentPane().add(jLblCantidadAprovado, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 630, 80, 30));
        getContentPane().add(jLblCantidadReprobado, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 600, 140, 20));
        getContentPane().add(jLblResultReprobado, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 630, 80, 30));

        jBtnGuardarCsv.setBackground(new java.awt.Color(51, 255, 51));
        jBtnGuardarCsv.setForeground(new java.awt.Color(30, 30, 30));
        jBtnGuardarCsv.setText("Guardar Csv");
        jBtnGuardarCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarCsvActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnGuardarCsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 390, -1, -1));

        jBttnCargarCsv.setBackground(new java.awt.Color(51, 255, 51));
        jBttnCargarCsv.setForeground(new java.awt.Color(30, 30, 30));
        jBttnCargarCsv.setText("Cargar Csv");
        jBttnCargarCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnCargarCsvActionPerformed(evt);
            }
        });
        getContentPane().add(jBttnCargarCsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 440, 100, -1));

        jBttnCerrarSesion.setBackground(new java.awt.Color(255, 51, 51));
        jBttnCerrarSesion.setForeground(new java.awt.Color(30, 30, 30));
        jBttnCerrarSesion.setText("Cerrar Sesion");
        jBttnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBttnCerrarSesionActionPerformed(evt);
            }
        });
        getContentPane().add(jBttnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JbtnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnCalculateActionPerformed
        // TODO add your handling code here:

        limpiarErrores();

        if (!validarCamposParaCalculo()) {
            return;
        }

        try {
            Estudiante student = studentsInfoValidado();

            jLabelAverage.setText("Average: " + String.format("%.2f", calculo.promedio(student.getNotas())));
            jLabelGradeMax.setText("Grade max: " + String.format("%.2f", calculo.notaMaxima(student.getNotas()).getValor()));

            if (calculo.aprovado(calculo.promedio(student.getNotas()))) {
                jLabelResult.setText("Approved");
                jLabelResult.setForeground(new java.awt.Color(0, 150, 0));
            } else {
                jLabelResult.setText("Reproved");
                jLabelResult.setForeground(new java.awt.Color(200, 0, 0));
            }

            jLbTitleResult.setText("Resultados");
            jLblTitleAverage.setText("Promedio:");
            jLblTitleNote.setText("Nota Maxima:");
            jLblTitleResul.setText("Resultado:");
            jLblTituloPromedio.setText("Promedio global:");
            jLblTituleMejorStudent.setText("Mejor Estudiante:");
            jLblTituleCantidadAprobado.setText("Cantidad de aprobados:");
            jLblCantidadReprobado.setText("Cantidad de Reprobados:");

        } catch (Exception e) {
            mostrarError("Error al calcular: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error en cálculo", e);
        }
    }//GEN-LAST:event_JbtnCalculateActionPerformed

    private void jBtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnClearActionPerformed
        // TODO add your handling code here:

        jTextName.setText("");
        jSpnAge.setValue(15); // Valor por defecto válido
        jTextGrade1.setText("");
        jTextGrade2.setText("");
        jTextGrade3.setText("");

        // Limpiar resultados
        jLabelAverage.setText("");
        jLabelGradeMax.setText("");
        jLabelResult.setText("");
        jLbTitleResult.setText("");
        jLblTitleAverage.setText("");
        jLblTitleNote.setText("");
        jLblTitleResul.setText("");
        jLblTituloPromedio.setText("");
        jLblTituleMejorStudent.setText("");
        jLblTituleCantidadAprobado.setText("");
        jLblCantidadReprobado.setText("");
        jLblPromedioGlobal.setText("");
        jLblResultMejorStudent.setText("");
        jLblCantidadAprovado.setText("");
        jLblResultReprobado.setText("");

        limpiarErrores();

        jTextName.requestFocus();
    }//GEN-LAST:event_jBtnClearActionPerformed

    private void jBtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExitActionPerformed
        // TODO add your handling code here:

        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar la aplicación completamente o regresar al login?",
                "Salir del sistema",
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {

            System.exit(0);
        } else if (confirmacion == javax.swing.JOptionPane.NO_OPTION) {

            java.awt.EventQueue.invokeLater(() -> {
                new JFrameLogin().setVisible(true);
            });
            this.dispose();
        }
    }//GEN-LAST:event_jBtnExitActionPerformed

    private void jBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSaveActionPerformed

        limpiarErrores();

        try {
            Estudiante student = studentsInfoValidado();

            if (student == null) {
                return;
            }

            registroSt.agregarEstudiante(student);
            actualizarListaEstudiantes();

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante '" + student.getName() + "' guardado correctamente.",
                    "Guardado exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea limpiar los campos para ingresar otro estudiante?",
                    "Limpiar campos",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                jBtnClearActionPerformed(null);
            }

        } catch (Exception e) {
            mostrarError("Error al guardar estudiante: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error al guardar estudiante", e);
        }

    }//GEN-LAST:event_jBtnSaveActionPerformed

    private void jBttnStadisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnStadisticActionPerformed
        // TODO add your handling code here:

        try {
            var estudiantes = registroSt.listarEstudiantes();

            if (estudiantes.isEmpty()) {
                mostrarAdvertencia("No hay estudiantes registrados para mostrar estadísticas.");
                return;
            }

            double promedioGlobal = registroSt.calcularPromedioGeneral();
            long aprobados = registroSt.contarAprovados();
            long reprobados = registroSt.contarReprobado();

            jLblPromedioGlobal.setText(String.format("%.2f", promedioGlobal));
            jLblCantidadAprovado.setText(String.valueOf(aprobados));
            jLblResultReprobado.setText(String.valueOf(reprobados));

            Optional<Estudiante> mejorSt = registroSt.mejorEstudiante();

            if (mejorSt.isPresent()) {
                Estudiante mejor = mejorSt.get();
                jLblResultMejorStudent.setText(mejor.getName());
            } else {
                jLblResultMejorStudent.setText("No disponible");
            }

            if (jLblTituloPromedio.getText().isEmpty()) {
                jLblTitleResul.setText("Resultado");
                jLblTituloPromedio.setText("Promedio global:");
                jLblTituleMejorStudent.setText("Mejor Estudiante:");
                jLblTituleCantidadAprobado.setText("Cantidad de aprobados:");
                jLblCantidadReprobado.setText("Cantidad de Reprobados:");
            }

            String resumen = String.format(
                    "Estadísticas generales:\n\n"
                    + "Total de estudiantes: %d\n"
                    + "Promedio global: %.2f\n"
                    + "Estudiantes aprobados: %d (%.1f%%)\n"
                    + "Estudiantes reprobados: %d (%.1f%%)\n"
                    + "Mejor estudiante: %s",
                    estudiantes.size(),
                    promedioGlobal,
                    aprobados, (aprobados * 100.0 / estudiantes.size()),
                    reprobados, (reprobados * 100.0 / estudiantes.size()),
                    mejorSt.isPresent() ? mejorSt.get().getName() : "N/A"
            );

            JOptionPane.showMessageDialog(
                    this,
                    resumen,
                    "Estadísticas del Sistema",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            mostrarError("Error al calcular estadísticas: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error en estadísticas", e);
        }

    }//GEN-LAST:event_jBttnStadisticActionPerformed

    private void jBtnGuardarCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarCsvActionPerformed
        // TODO add your handling code here:

        guardarCSV();
    }//GEN-LAST:event_jBtnGuardarCsvActionPerformed

    private void jBttnCargarCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnCargarCsvActionPerformed
        // TODO add your handling code here:

        cargarCSV();
    }//GEN-LAST:event_jBttnCargarCsvActionPerformed

    private void jBttnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBttnCerrarSesionActionPerformed
        // TODO add your handling code here:

        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cerrar la sesión?\n\nSe perderán los datos no guardados.",
                "Confirmar cierre de sesión",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Sesión cerrada. Regresando al login...",
                    "Sesión cerrada",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

           
            java.awt.EventQueue.invokeLater(() -> {
                new JFrameLogin().setVisible(true);
            });

            
            this.dispose();
        }

    }//GEN-LAST:event_jBttnCerrarSesionActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JbtnCalculate;
    private javax.swing.JButton jBtnClear;
    private javax.swing.JButton jBtnExit;
    private javax.swing.JButton jBtnGuardarCsv;
    private javax.swing.JButton jBtnSave;
    private javax.swing.JButton jBttnCargarCsv;
    private javax.swing.JButton jBttnCerrarSesion;
    private javax.swing.JButton jBttnStadistic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAverage;
    private javax.swing.JLabel jLabelGradeMax;
    private javax.swing.JLabel jLabelResult;
    private javax.swing.JLabel jLbTitleResult;
    private javax.swing.JLabel jLblCantidadAprovado;
    private javax.swing.JLabel jLblCantidadReprobado;
    private javax.swing.JLabel jLblPromedioGlobal;
    private javax.swing.JLabel jLblResultMejorStudent;
    private javax.swing.JLabel jLblResultReprobado;
    private javax.swing.JLabel jLblTitleAverage;
    private javax.swing.JLabel jLblTitleNote;
    private javax.swing.JLabel jLblTitleResul;
    private javax.swing.JLabel jLblTituleCantidadAprobado;
    private javax.swing.JLabel jLblTituleMejorStudent;
    private javax.swing.JLabel jLblTituloPromedio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSpinner jSpnAge;
    private javax.swing.JTable jTableData;
    private javax.swing.JTextField jTextGrade1;
    private javax.swing.JTextField jTextGrade2;
    private javax.swing.JTextField jTextGrade3;
    private javax.swing.JTextField jTextName;
    // End of variables declaration//GEN-END:variables
}
