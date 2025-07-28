// /src/main/java/vista/MazePanel.java
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que dibuja el laberinto, incluyendo celdas, muros, puntos de
 * inicio/fin y la ruta de la solución. También gestiona la interacción
 * del usuario con el ratón y las diferentes formas de visualización.
 *
 * @author Israel Orellana
 * @version 1.1
 */
public class MazePanel extends JPanel {
    private int rows;
    private int cols;
    private int[][] mazeData; // 1 para transitable, 0 para muro
    private Point startPoint;
    private Point endPoint;

    // --- Atributos para la visualización avanzada ---
    private List<int[]> visitedNodes; // Nodos visitados por el algoritmo (gris)
    private List<int[]> finalPath;    // El camino correcto final (azul)
    private Timer animationTimer;     // Timer para la animación paso a paso

    private final int CELL_SIZE = 25;

    /**
     * Construye el panel del laberinto con un tamaño inicial.
     *
     * @param rows El número inicial de filas.
     * @param cols El número inicial de columnas.
     */
    public MazePanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mazeData = new int[rows][cols];
        this.visitedNodes = new ArrayList<>();
        this.finalPath = new ArrayList<>();

        clearMaze(); // Limpia y establece todo como transitable

        setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
        addMouseListeners();
    }

    /**
     * Añade los listeners del ratón para permitir la interacción del usuario
     * con el laberinto (dibujar muros, seleccionar inicio/fin).
     */
    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;

                if (row >= rows || col >= cols) return; // Fuera de los límites

                // Lógica de Clic: Se comprueba la acción más específica primero.
                if (e.isShiftDown() && SwingUtilities.isLeftMouseButton(e)) {
                    endPoint = new Point(col, row);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    startPoint = new Point(col, row);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    mazeData[row][col] = (mazeData[row][col] == 1) ? 0 : 1;
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    endPoint = new Point(col, row);
                }
                repaint(); // Vuelve a dibujar el panel para reflejar los cambios
            }
        });
    }

    /**
     * Dibuja todos los componentes del laberinto en capas.
     * El orden es importante para la correcta visualización.
     *
     * @param g el contexto gráfico en el que se va a dibujar.
     */
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

        // 2. Dibuja los nodos visitados (en gris)
        g2d.setColor(Color.LIGHT_GRAY);
        for (int[] node : visitedNodes) {
            g2d.fillRect(node[1] * CELL_SIZE, node[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // 3. Dibuja el camino final (en azul) sobre los nodos visitados
        g2d.setColor(new Color(66, 135, 245)); // Un azul claro
        for (int[] step : finalPath) {
            g2d.fillRect(step[1] * CELL_SIZE, step[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // 4. Dibuja la cuadrícula
        g2d.setColor(Color.GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g2d.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // 5. Dibuja los puntos de inicio y fin encima de todo
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

    /**
     * Restablece el laberinto a su estado inicial: todas las celdas transitables
     * y sin puntos de inicio, fin o ruta.
     */
    public void clearMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeData[i][j] = 1; // Todo transitable
            }
        }
        startPoint = null;
        endPoint = null;
        clearVisuals();
    }

    /**
     * Limpia solo los elementos visuales de la solución (caminos y visitados)
     * y detiene cualquier animación en curso.
     */
    public void clearVisuals() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        visitedNodes.clear();
        finalPath.clear();
        repaint();
    }

    // --- NUEVOS MÉTODOS PÚBLICOS PARA EL CONTROLADOR ---

    /**
     * Dibuja instantáneamente solo la ruta de la solución final.
     * Usado por el botón "¡Resolver!".
     * @param path La lista de coordenadas de la ruta final.
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
     * @param visited La lista de todos los nodos explorados por el algoritmo.
     * @param path La lista de coordenadas de la ruta final.
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
     * Inicia una animación paso a paso de la ruta de la solución.
     * Usado por el botón "Resolver Paso a Paso".
     * @param path La lista de coordenadas de la ruta final para animar.
     */
    public void startStepByStepAnimation(List<int[]> path) {
        clearVisuals();
        if (path == null || path.isEmpty()) return;

        final List<int[]> pathToAnimate = new ArrayList<>(path);
        finalPath.clear(); // Se construirá esta lista paso a paso

        animationTimer = new Timer(50, e -> {
            if (finalPath.size() < pathToAnimate.size()) {
                finalPath.add(pathToAnimate.get(finalPath.size()));
                repaint();
            } else {
                ((Timer) e.getSource()).stop(); // Detiene el timer al finalizar
            }
        });
        animationTimer.start();
    }


    // --- MÉTODOS EXISTENTES ---

    /**
     * Actualiza los datos del laberinto y redibuja el panel.
     * @param mazeData una matriz de enteros que representa el nuevo laberinto.
     */
    public void setMazeData(int[][] mazeData) {
        if (mazeData == null || mazeData.length == 0) return;
        this.rows = mazeData.length;
        this.cols = mazeData[0].length;
        this.mazeData = mazeData;
        setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
        revalidate();
        repaint();
    }

    /**
     * Obtiene la representación interna del laberinto.
     * @return Una matriz de enteros donde 1 es transitable y 0 es un muro.
     */
    public int[][] getMazeData() { return mazeData; }

    /**
     * Obtiene el punto de inicio seleccionado por el usuario.
     * @return Un objeto Point con las coordenadas (columna, fila) del inicio.
     */
    public Point getStartPoint() { return startPoint; }

    /**
     * Obtiene el punto final seleccionado por el usuario.
     * @return Un objeto Point con las coordenadas (columna, fila) del fin.
     */
    public Point getEndPoint() { return endPoint; }
}