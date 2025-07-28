package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que dibuja el laberinto y gestiona múltiples modos de visualización.
 *
 * @author Israel Orellana
 * @version 1.2 - Añadida lógica para visualización completa y paso a paso manual.
 */
public class MazePanel extends JPanel {
    private int rows;
    private int cols;
    private int[][] mazeData;
    private Point startPoint;
    private Point endPoint;

    // --- Atributos para la visualización ---
    private List<int[]> visitedNodes; // Nodos visitados (gris)
    private List<int[]> finalPath;    // Camino final (azul)

    // --- NUEVO: Atributos para la animación paso a paso ---
    private List<int[]> stepByStepPathToAnimate; // La ruta completa a animar
    private int stepByStepCurrentIndex;      // El índice del paso actual

    private final int CELL_SIZE = 25;

    public MazePanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mazeData = new int[rows][cols];
        this.visitedNodes = new ArrayList<>();
        this.finalPath = new ArrayList<>();
        clearMaze();
        setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
        addMouseListeners();
    }

    private void addMouseListeners() {
        // ... (tu código de MouseListeners no necesita cambios)
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Dibuja las celdas base (muros y caminos)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g2d.setColor(mazeData[row][col] == 0 ? Color.BLACK : Color.WHITE);
                g2d.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // 2. Dibuja los nodos visitados (en gris claro)
        g2d.setColor(new Color(220, 220, 220)); // Un gris más claro
        for (int[] node : visitedNodes) {
            g2d.fillRect(node[1] * CELL_SIZE, node[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // 3. Dibuja el camino final completo (si está activo)
        g2d.setColor(new Color(66, 135, 245)); // Un azul claro
        for (int[] step : finalPath) {
            g2d.fillRect(step[1] * CELL_SIZE, step[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // 4. Dibuja la animación paso a paso (si está activa)
        if (stepByStepPathToAnimate != null && !stepByStepPathToAnimate.isEmpty()) {
            g2d.setColor(new Color(255, 165, 0)); // Naranja para destacar el paso a paso
            // Dibuja solo los pasos hasta el índice actual
            for (int i = 0; i <= stepByStepCurrentIndex && i < stepByStepPathToAnimate.size(); i++) {
                int[] step = stepByStepPathToAnimate.get(i);
                g2d.fillRect(step[1] * CELL_SIZE, step[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // 5. Dibuja la cuadrícula
        g2d.setColor(Color.GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g2d.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // 6. Dibuja los puntos de inicio y fin encima de todo
        if (startPoint != null) {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(startPoint.x * CELL_SIZE, startPoint.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawString("A", startPoint.x * CELL_SIZE + 8, startPoint.y * CELL_SIZE + 18);
        }
        if (endPoint != null) {
            g2d.setColor(Color.RED);
            g2d.fillRect(endPoint.x * CELL_SIZE, endPoint.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g2d.setColor(Color.WHITE);
            g2d.drawString("B", endPoint.x * CELL_SIZE + 8, endPoint.y * CELL_SIZE + 18);
        }
    }

    public void clearMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeData[i][j] = 1;
            }
        }
        startPoint = null;
        endPoint = null;
        clearVisuals();
        repaint();
    }

    public void clearVisuals() {
        visitedNodes.clear();
        finalPath.clear();
        // --- NUEVO: Limpiar estado de paso a paso ---
        stepByStepPathToAnimate = null;
        stepByStepCurrentIndex = -1;
        repaint();
    }

    // --- MÉTODOS PÚBLICOS PARA LOS MODOS DE DIBUJO ---

    /**
     * Dibuja instantáneamente solo la ruta de la solución final.
     * Usado por el botón "¡Resolver!".
     */
    public void drawSimplePath(List<int[]> path) {
        clearVisuals();
        if (path != null) {
            this.finalPath = new ArrayList<>(path);
        }
        repaint();
    }

    /**
     * Dibuja instantáneamente todos los nodos visitados y la ruta final.
     * Usado por el botón "Mostrar Camino Completo".
     */
    public void drawFullPath(List<int[]> visited, List<int[]> path) {
        clearVisuals();
        if (visited != null) {
            this.visitedNodes = new ArrayList<>(visited);
        }
        if (path != null) {
            this.finalPath = new ArrayList<>(path);
        }
        repaint();
    }

    /**
     * Prepara el panel para la animación paso a paso, pero no la inicia.
     * Guarda la ruta y reinicia el contador.
     */
    public void prepareForStepByStep(List<int[]> path) {
        clearVisuals(); // Limpia cualquier dibujo anterior
        if (path != null && !path.isEmpty()) {
            this.stepByStepPathToAnimate = new ArrayList<>(path);
            this.stepByStepCurrentIndex = -1; // Empezamos antes del primer paso
        }
    }

    /**
     * Avanza un paso en la animación y redibuja el panel.
     * Usado por el botón "Resolver Paso a Paso".
     */
    public void nextStep() {
        if (stepByStepPathToAnimate != null && stepByStepCurrentIndex < stepByStepPathToAnimate.size() - 1) {
            stepByStepCurrentIndex++;
            repaint();
        }
    }

    // --- Getters y Setters (sin cambios) ---
    public void setMazeData(int[][] mazeData) {
        if (mazeData == null || mazeData.length == 0) return;
        this.rows = mazeData.length;
        this.cols = mazeData[0].length;
        this.mazeData = mazeData;
        setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
        revalidate();
        repaint();
    }

    public int[][] getMazeData() { return mazeData; }
    public Point getStartPoint() { return startPoint; }
    public Point getEndPoint() { return endPoint; }
}