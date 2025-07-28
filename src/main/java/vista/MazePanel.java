package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel que se encarga de dibujar el laberinto, incluyendo celdas, muros,
 * puntos de inicio/fin y la ruta de la solución. También gestiona la
 * interacción del usuario con el ratón.
 *
 * @author Israel Orellana
 * @version 1.0
 */
public class MazePanel extends JPanel {
    private int rows;
    private int cols;
    private int[][] mazeData; // 1 para transitable, 0 para muro
    private List<int[]> path; // La ruta a dibujar
    private Point startPoint;
    private Point endPoint;

    /** El tamaño en píxeles de cada celda del laberinto. */
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

                // Lógica de Clic Corregida: Se comprueba la acción más específica primero.

                // 1. Shift + Clic Izquierdo para el punto de FIN (B)
                if (e.isShiftDown() && SwingUtilities.isLeftMouseButton(e)) {
                    endPoint = new Point(col, row);
                }
                // 2. Clic Derecho para el punto de INICIO (A)
                else if (SwingUtilities.isRightMouseButton(e)) {
                    startPoint = new Point(col, row);
                }
                // 3. Clic Izquierdo (normal) para poner/quitar MUROS
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    // Alterna el estado de la celda (muro/transitable)
                    mazeData[row][col] = (mazeData[row][col] == 1) ? 0 : 1;
                }
                // 4. (Opcional) Mantenemos el clic central como alternativa para el punto de FIN
                else if (SwingUtilities.isMiddleMouseButton(e)) {
                    endPoint = new Point(col, row);
                }

                repaint(); // Vuelve a dibujar el panel para reflejar los cambios
            }
        });
    }

    /**
     * Dibuja todos los componentes del laberinto: celdas, muros, cuadrícula,
     * ruta de solución y puntos de inicio/fin.
     *
     * @param g el contexto gráfico en el que se va a dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja el fondo y la cuadrícula
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.setColor(mazeData[row][col] == 0 ? Color.BLACK : Color.WHITE);
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        // Dibuja la ruta encontrada
        if (path != null && !path.isEmpty()) {
            g.setColor(new Color(50, 200, 255, 200)); // Cyan semi-transparente
            for (int[] step : path) {
                int row = step[0];
                int col = step[1];
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        // Dibuja el punto de inicio
        if (startPoint != null) {
            g.setColor(Color.GREEN);
            g.fillRect(startPoint.x * CELL_SIZE, startPoint.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.BLACK);
            g.drawString("A", startPoint.x * CELL_SIZE + (CELL_SIZE / 2) - 4, startPoint.y * CELL_SIZE + (CELL_SIZE / 2) + 5);
        }
        // Dibuja el punto de fin
        if (endPoint != null) {
            g.setColor(Color.RED);
            g.fillRect(endPoint.x * CELL_SIZE, endPoint.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.WHITE);
            g.drawString("B", endPoint.x * CELL_SIZE + (CELL_SIZE / 2) - 4, endPoint.y * CELL_SIZE + (CELL_SIZE / 2) + 5);
        }
    }

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
     * Establece la ruta de la solución para ser dibujada.
     * @param path una lista de coordenadas [fila, columna].
     */
    public void setPath(List<int[]> path) {
        this.path = path;
        repaint();
    }

    /**
     * Limpia cualquier ruta previamente dibujada en el panel.
     */
    public void clearPath() {
        this.path = null;
        repaint();
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
        path = null;
        repaint();
    }

    // --- Métodos para que el Controlador obtenga información ---
    public int[][] getMazeData() { return mazeData; }
    public Point getStartPoint() { return startPoint; }
    public Point getEndPoint() { return endPoint; }
}