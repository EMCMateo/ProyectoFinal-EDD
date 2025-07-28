// /src/main/java/vista/MazeView.java
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * MazeView es la ventana principal (JFrame) de la aplicación de laberintos.
 * Ensambla el MazePanel (lienzo), el ControlPanel (botones) y la barra de menú.
 * Permite la interacción entre el usuario y el controlador siguiendo el patrón MVC.
 *
 * Funcionalidades principales:
 * <ul>
 *   <li>Contiene y organiza los paneles de laberinto y control.</li>
 *   <li>Proporciona métodos para mostrar resultados, errores y gráficas.</li>
 *   <li>Permite al controlador añadir listeners a los botones y menús.</li>
 *   <li>Gestiona la visualización de rutas y resultados.</li>
 * </ul>
 *
 */
public class MazeView extends JFrame {

    private MazePanel mazePanel;
    private ControlPanel controlPanel;

    // --- Atributos para los items del menú ---
    private JMenuItem listResultsMenuItem;
    private JMenuItem showTimesChartMenuItem;

    /**
     * Construye la ventana principal, inicializando y organizando todos los
     * componentes de la interfaz de usuario.
     */
    public MazeView() {
        setTitle("Solucionador de Laberintos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Crear la barra de menú
        createMenuBar();

        // 2. Crear instancias de los paneles
        mazePanel = new MazePanel(5, 5);
        controlPanel = new ControlPanel();

        // Panel contenedor con scroll para el laberinto
        JScrollPane scrollPane = new JScrollPane(mazePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 3. Añadir los paneles a la ventana
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        // Establecer un tamaño mínimo para la ventana
        setMinimumSize(new Dimension(600, 400));
        pack();
        setLocationRelativeTo(null);
        // NOTA: setVisible(true) es llamado por la clase App, no aquí.
    }

    /**
     * Crea y configura la barra de menú de la aplicación.
     * Este es un método de ayuda privado para mantener el constructor limpio.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu reportsMenu = new JMenu("Reportes");
        menuBar.add(reportsMenu);

        listResultsMenuItem = new JMenuItem("Listar Historial de Resultados");
        showTimesChartMenuItem = new JMenuItem("Mostrar Gráfica de Tiempos");

        reportsMenu.add(listResultsMenuItem);
        reportsMenu.add(showTimesChartMenuItem);

        setJMenuBar(menuBar);
    }

    // --- MÉTODOS PARA QUE EL CONTROLADOR MUESTRE VENTANAS SECUNDARIAS ---
    // --- Métodos públicos para que el Controlador actualice la vista --

    /**
     * Crea y muestra una ventana de diálogo modal con la tabla de resultados.
     * Esta acción es invocada por el Controlador.
     */
    public void showResultsTable() {
        ResultsTableView resultsDialog = new ResultsTableView(this);
        resultsDialog.setVisible(true);
    }

    /**
     * Crea y muestra una ventana de diálogo modal para la gráfica de tiempos.
     * Esta acción es invocada por el Controlador.
     */
    public void showTimesChart() {
        TimesChartView chartDialog = new TimesChartView(this);
        chartDialog.setVisible(true);
    }

    // --- MÉTODOS PARA QUE EL CONTROLADOR AÑADA LISTENERS ---

    /**
     * Añade un listener para el evento de clic en el item de menú "Listar Historial de Resultados".
     * @param listener El ActionListener a ejecutar, típicamente proporcionado por el Controlador.
     */
    public void addListResultsListener(ActionListener listener) {
        listResultsMenuItem.addActionListener(listener);
    }

    /**
     * Añade un listener para el evento de clic en el item de menú "Mostrar Gráfica de Tiempos".
     * @param listener El ActionListener a ejecutar, típicamente proporcionado por el Controlador.
     */
    public void addShowTimesChartListener(ActionListener listener) {
        showTimesChartMenuItem.addActionListener(listener);
    }

    // --- MÉTODOS DE ACCESO Y UTILIDAD ---

    /**
     * Devuelve la instancia del panel del laberinto.
     * @return El componente MazePanel.
     */
    public MazePanel getMazePanel() {
        return mazePanel;
    }

    /**
     * Devuelve la instancia del panel de control.
     * @return El componente ControlPanel.
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    /**
     * Muestra un mensaje de error genérico en un diálogo.
     * @param message El mensaje de error a mostrar.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra los resultados de texto en el área de resultados del panel de control.
     * @param results El texto con los resultados a mostrar.
     */
    public void setResults(String results) {
        controlPanel.setResultsText(results);
    }

    /**
     * Dibuja una ruta simple en el panel del laberinto.
     * Este método delega la llamada al MazePanel.
     * @param path La lista de coordenadas que componen la ruta.
     */
    public void drawPath(List<int[]> path) {
        // Delega la acción al método correspondiente en MazePanel
        mazePanel.drawSimplePath(path);
    }

    /**
     * Limpia cualquier elemento visual de la solución (rutas, nodos visitados)
     * en el panel del laberinto.
     */
    public void clearPath() {
        // Delega la acción al método de limpieza visual en MazePanel
        mazePanel.clearVisuals();
    }
}