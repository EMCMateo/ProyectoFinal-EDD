package vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
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

    /**
     * Construye el panel de control, inicializando y organizando todos
     * sus componentes de interfaz.
     */
    public ControlPanel() {
        // Configuración del layout principal del panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(200, 0)); // Ancho preferido

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

        // --- Panel de selección de algoritmo ---
        JPanel algoPanel = new JPanel(new BorderLayout(5, 5));
        algoPanel.setBorder(new TitledBorder("Algoritmo"));
        String[] algorithms = {"BFS", "DFS", "Recursivo (2 dir)", "Recursivo (4 dir)", "Backtracking"};
        algorithmSelector = new JComboBox<>(algorithms);
        solveButton = new JButton("¡Resolver!");

        algoPanel.add(algorithmSelector, BorderLayout.NORTH);
        algoPanel.add(solveButton, BorderLayout.SOUTH);
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
     * Obtiene el nombre del algoritmo seleccionado por el usuario.
     * @return El algoritmo seleccionado como un String.
     */
    public String getSelectedAlgorithm() {
        return (String) algorithmSelector.getSelectedItem();
    }

    /**
     * Establece el texto que se mostrará en el área de resultados.
     * @param text El texto a mostrar.
     */
    public void setResultsText(String text) {
        resultsArea.setText(text);
    }

    /**
     * Añade un listener para el evento de clic en el botón "Generar/Limpiar".
     * @param listener El ActionListener a ejecutar.
     */
    public void addGenerateListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    /**
     * Añade un listener para el evento de clic en el botón "¡Resolver!".
     * @param listener El ActionListener a ejecutar.
     */
    public void addSolveListener(ActionListener listener) {
        solveButton.addActionListener(listener);
    }
}