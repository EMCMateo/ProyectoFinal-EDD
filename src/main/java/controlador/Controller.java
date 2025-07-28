// /src/main/java/controlador/Controller.java
package controlador;

import modelo.*;
import vista.MazeView;
import util.CSVLogger; // Crearemos esta clase de utilidad

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * El controlador principal de la aplicación. Conecta la Vista (MazeView)
 * con el Modelo (los algoritmos de resolución de laberintos).
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Controller {
    private final MazeView view;
    private final CSVLogger csvLogger;

    public Controller(MazeView view) {
        this.view = view;
        this.csvLogger = new CSVLogger("resultados_laberinto.csv");
        initController();
    }

    /**
     * Inicializa el controlador asignando listeners a los componentes de la vista.
     */
    private void initController() {
        // La vista ya no maneja la lógica, solo notifica al controlador.
        view.getControlPanel().addGenerateListener(e -> generateNewMaze());
        view.getControlPanel().addSolveListener(e -> solveMaze());
        view.getControlPanel().addClearAllListener(e -> clearAll());

        view.addListResultsListener(e -> view.showResultsTable());
        view.addShowTimesChartListener(e -> view.showTimesChart());
    }

    /**
     * Orquesta la limpieza y generación de un nuevo laberinto en la vista.
     */
    private void generateNewMaze() {
        int rows = view.getControlPanel().getRows();
        int cols = view.getControlPanel().getCols();
        view.getMazePanel().setMazeData(new int[rows][cols]); // Crea una nueva matriz
        view.getMazePanel().clearMaze(); // Limpia estado (puntos, ruta)
        view.pack(); // Reajusta el tamaño de la ventana
    }

    /**
     * Orquesta todo el proceso de resolución del laberinto.
     */
    private void solveMaze() {
        // Limpiar visualización antes de resolver con otro método
        view.getMazePanel().clearVisuals();
        // 1. Recoger datos de la Vista
        int[][] mazeData = view.getMazePanel().getMazeData();
        Point startPoint = view.getMazePanel().getStartPoint();
        Point endPoint = view.getMazePanel().getEndPoint();
        String algorithm = view.getControlPanel().getSelectedAlgorithm();

        // 2. Validar datos de entrada
        if (startPoint == null || endPoint == null) {
            view.showError("Por favor, defina un punto de inicio (clic derecho) y un punto de fin (Shift + clic izquierdo).");
            return;
        }

        if (mazeData[startPoint.y][startPoint.x] == 0 || mazeData[endPoint.y][endPoint.x] == 0) {
            view.showError("El punto de inicio o fin está en un muro.");
            return;
        }

        // Validación especial para recursivo 2 direcciones
        if (algorithm != null && algorithm.contains("2 dir")) {
            if (endPoint.y < startPoint.y || endPoint.x < startPoint.x) {
                view.showError("El método recursivo de 2 direcciones solo funciona si el destino está a la derecha y abajo del inicio.");
                return;
            }
        }

        // 3. Preparar datos, ejecutar el algoritmo y medir el tiempo
        long startTime = System.nanoTime();
        List<int[]> path = findPath(mazeData, startPoint, endPoint, algorithm);
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;

        // 4. Formatear y mostrar resultados en la Vista
        boolean success = !path.isEmpty();
        String resultText;
        if (success) {
            resultText = String.format("Algoritmo: %s\nRuta encontrada.\nPasos: %d\nTiempo: %.4f ms",
                    algorithm, path.size(), durationMs);
            view.drawPath(path);
        } else {
            resultText = String.format("Algoritmo: %s\nNo se encontró ruta.\nTiempo: %.4f ms",
                    algorithm, durationMs);
            view.clearPath();
        }
        view.setResults(resultText);

        // 5. Guardar resultados en CSV
        csvLogger.logResult(algorithm, success, path.size(), durationMs);
    }

    /**
     * Selecciona y ejecuta el algoritmo de resolución apropiado.
     *
     * @return La lista de celdas del camino encontrado.
     */
    private List<int[]> findPath(int[][] mazeData, Point startPoint, Point endPoint, String algorithm) {
        // Para BFS y DFS, necesitamos convertir la matriz a nuestro modelo de objetos
        Laberinto laberinto = new Laberinto(mazeData.length, mazeData[0].length);
        for (int i = 0; i < mazeData.length; i++) {
            for (int j = 0; j < mazeData[0].length; j++) {
                if (mazeData[i][j] == 0) {
                    laberinto.setMuro(i, j);
                }
            }
        }
        Celda inicio = laberinto.getCelda(startPoint.y, startPoint.x);
        Celda fin = laberinto.getCelda(endPoint.y, endPoint.x);

        switch (algorithm) {
            case "BFS":
                SolverBFS bfs = new SolverBFS();
                List<Celda> bfsPath = bfs.buscarRuta(laberinto, inicio, fin);
                return convertCeldaPathToIntArrayPath(bfsPath);

            case "DFS":
                SolverDFS dfs = new SolverDFS();
                List<Celda> dfsPath = dfs.buscarRuta(laberinto, inicio, fin);
                return convertCeldaPathToIntArrayPath(dfsPath);

            // En método findPath(), actualiza así las llamadas a SolverRecursivo:
            case "Recursivo (2 dir)":
                SolverRecursivo rec2 = new SolverRecursivo();
                List<int[]> res2 = rec2.resolver2Direcciones(mazeData, startPoint.y, startPoint.x, endPoint.y, endPoint.x);
                return res2 != null ? res2 : new ArrayList<>();

            case "Recursivo (4 dir)":
                SolverRecursivo rec4 = new SolverRecursivo();
                List<int[]> res4 = rec4.resolver4Direcciones(mazeData, startPoint.y, startPoint.x, endPoint.y, endPoint.x);
                return res4 != null ? res4 : new ArrayList<>();

            case "Backtracking":
                SolverRecursivo backtrack = new SolverRecursivo();
                List<int[]> bt = backtrack.resolverBacktracking(mazeData, startPoint.y, startPoint.x, endPoint.y, endPoint.x);
                return bt != null ? bt : new ArrayList<>();


            default:
                return new ArrayList<>();
        }
    }

    /**
     * Convierte una lista de objetos Celda a una lista de arrays de enteros [fila, columna].
     */
    private List<int[]> convertCeldaPathToIntArrayPath(List<Celda> celdaPath) {
        if (celdaPath == null) {
            return new ArrayList<>();
        }
        return celdaPath.stream()
                .map(c -> new int[]{c.getFila(), c.getColumna()})
                .collect(Collectors.toList());
    }

    private void clearAll() {
        view.getMazePanel().clearMaze();
        view.getControlPanel().setResultsText("");
    }
}