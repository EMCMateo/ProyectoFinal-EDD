
package vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * La ventana principal (JFrame) de la aplicación.
 * Ensambla el MazePanel (lienzo) y el ControlPanel (botones).
 * Actúa como intermediario inicial entre los componentes de la vista.
 */
public class MazeView extends JFrame {

    private MazePanel mazePanel;
    private ControlPanel controlPanel;


    public MazePanel getMazePanel() {
        return mazePanel;
    }

    public void setMazePanel(MazePanel mazePanel) {
        this.mazePanel = mazePanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public MazeView() {
        setTitle("Solucionador de Laberintos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Crear instancias de las clases separadas
        mazePanel = new MazePanel(20, 20);
        controlPanel = new ControlPanel();

        // 2. Añadirlas a la ventana
        add(mazePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        // 3. Conectar los botones del ControlPanel con acciones que afectan al MazePanel
        addControlListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Conecta los listeners de los botones del ControlPanel a la lógica.
     * Esta es la "magia" que une los dos paneles.
     * En el patrón MVC final, esta lógica se moverá al Controlador.
     */
    private void addControlListeners() {
        // Acción para el botón "Generar/Limpiar"
        controlPanel.addGenerateListener(e -> {
            int rows = controlPanel.getRows();
            int cols = controlPanel.getCols();
            mazePanel.setMazeData(new int[rows][cols]); // Crea una nueva matriz
            mazePanel.clearMaze(); // Limpia todo
            pack(); // Ajusta el tamaño de la ventana
        });

        // Acción para el botón "¡Resolver!"
        controlPanel.addSolveListener(e -> {
            Point start = mazePanel.getStartPoint();
            Point end = mazePanel.getEndPoint();

            if (start == null || end == null) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, defina un punto de inicio (clic derecho) y un punto de fin (Shift + click izquierdo).",
                        "Faltan Puntos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            mazePanel.clearPath(); // Limpia la ruta anterior

            // Placeholder para la llamada al controlador del Estudiante 4
            String algorithm = controlPanel.getSelectedAlgorithm();
            System.out.println("Llamada al controlador (pendiente) con algoritmo: " + algorithm);
            // controller.solve(mazePanel.getMazeData(), start, end, algorithm);
        });
    }

    // --- Métodos públicos para que el Controlador actualice la vista ---

    public void drawPath(List<int[]> path) {
        mazePanel.setPath(path);
    }

    public void setResults(String results) {
        controlPanel.setResultsText(results);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MazeView::new);
    }

    public void showError(String messaje){
        JOptionPane.showMessageDialog(this, messaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void clearPath(){
        mazePanel.clearPath();
    }
}