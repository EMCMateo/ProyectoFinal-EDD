package vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel que contiene todos los controles de la aplicación: configuración del
 * tamaño, selección de algoritmo, botones de acción y área de resultados.
 *
 * @author Israel Orellana
 * @version 1.1
 * Panel que contiene todos los controles de la aplicación: configuración del tamaño,
 * selección de algoritmo, botón para resolver y área de resultados.
 *
 * @author Israel Orellana
 * @version 1.0
 */
public class ControlPanel extends JPanel {
    private JComboBox<String> algorithmSelector;
    private JTextArea resultsArea;
    private JTextField rowsField;
    private JTextField colsField;
    private JButton generateButton;

    private JButton solveButton;
    private JButton showFullPathButton;
    private JButton stepByStepButton;

    /**
     * Construye el panel de control, inicializando y organizando todos
     * sus componentes de interfaz.
     */
    public ControlPanel() {
        // Configuración del layout principal del panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(250, 0)); // Un poco más ancho para el texto de los botones

        // --- Panel de configuración ---
        JPanel configPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        configPanel.setBorder(new TitledBorder("Configuración"));

        rowsField = new JTextField("20");
        colsField = new JTextField("20");
        generateButton = new JButton("Generar/Limpiar");

        configPanel.add(new JLabel("Filas:"));
        configPanel.add(rowsField);
        configPanel.add(new JLabel("Columnas:"));
        configPanel.add(colsField);
        configPanel.add(generateButton);
        configPanel.add(new JLabel()); // Espacio vacío para alinear
        add(configPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Panel de selección de algoritmo y acciones ---
        JPanel algoPanel = new JPanel(new BorderLayout(5, 5));
        algoPanel.setBorder(new TitledBorder("Algoritmo y Acciones"));
        String[] algorithms = {"BFS", "DFS", "Recursivo (2 dir)", "Recursivo (4 dir)", "Backtracking"};
        algorithmSelector = new JComboBox<>(algorithms);
        algoPanel.add(algorithmSelector, BorderLayout.NORTH);

        // --- Panel para agrupar los tres botones de acción ---
        JPanel actionButtonsPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // Layout para apilar botones
        solveButton = new JButton("¡Resolver!"); // Botón original
        showFullPathButton = new JButton("Mostrar Camino Completo");
        stepByStepButton = new JButton("Resolver Paso a Paso");

        actionButtonsPanel.add(solveButton);
        actionButtonsPanel.add(showFullPathButton);
        actionButtonsPanel.add(stepByStepButton);

        algoPanel.add(actionButtonsPanel, BorderLayout.CENTER); // Se añaden al centro

        add(algoPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Panel de resultados ---
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Resultados"));
        resultsArea = new JTextArea(10, 15);
        resultsArea.setEditable(false);
        resultsPanel.add(new JScrollPane(resultsArea));
        add(resultsPanel);
    }

    // --- Métodos públicos para que otros (la Vista o el Controlador) interactúen con este panel ---

    /**
     * Obtiene el número de filas introducido por el usuario.
     *
     * @return El número de filas como un entero. Devuelve 20 si la entrada es inválida.
     * @return El número de filas como un entero.
     */
    public int getRows() {
        try {
            return Integer.parseInt(rowsField.getText());
        } catch (NumberFormatException e) {
            return 20; // Valor por defecto si la entrada es inválida
        }
    }

    /**
     * Obtiene el número de columnas introducido por el usuario.
     *
     * @return El número de columnas como un entero. Devuelve 20 si la entrada es inválida.
     * @return El número de columnas como un entero.
     */
    public int getCols() {
        try {
            return Integer.parseInt(colsField.getText());
        } catch (NumberFormatException e) {
            return 20; // Valor por defecto
        }
    }

    /**
     * Obtiene el nombre del algoritmo seleccionado por el usuario en el JComboBox.
     *
     * Obtiene el nombre del algoritmo seleccionado por el usuario.
     * @return El algoritmo seleccionado como un String.
     */
    public String getSelectedAlgorithm() {
        return (String) algorithmSelector.getSelectedItem();
    }

    /**
     * Establece el texto que se mostrará en el área de resultados.
     *
     * @param text El texto a mostrar.
     */
    public void setResultsText(String text) {
        resultsArea.setText(text);
    }

    /**
     * Añade un listener para el evento de clic en el botón "Generar/Limpiar".
     *
     * @param listener El ActionListener a ejecutar cuando se presiona el botón.
     * @param listener El ActionListener a ejecutar.
     */
    public void addGenerateListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    /**
     * Añade un listener para el evento de clic en el botón "¡Resolver!".
     *
     * @param listener El ActionListener a ejecutar.
     */
    public void addSolveListener(ActionListener listener) {
        solveButton.addActionListener(listener);
    }

    /**
     * Añade un listener para el evento de clic en el botón "Mostrar Camino Completo".
     *
     * @param listener El ActionListener a ejecutar.
     */
    public void addShowFullPathListener(ActionListener listener) {
        showFullPathButton.addActionListener(listener);
    }

    /**
     * Añade un listener para el evento de clic en el botón "Resolver Paso a Paso".
     *
     * @param listener El ActionListener a ejecutar.
     */
    public void addStepByStepListener(ActionListener listener) {
        stepByStepButton.addActionListener(listener);
    }
}