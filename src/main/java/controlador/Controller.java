// /src/main/java/controlador/Controller.java
package controlador;

import modelo.*;
import vista.MazeView;
import util.CSVLogger;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador principal de la aplicación de laberintos.
 * Implementa el patrón MVC (Modelo-Vista-Controlador) gestionando la interacción
 * entre la interfaz gráfica y los algoritmos de resolución.
 *
 * Responsabilidades:
 * <ul>
 *   <li>Gestiona eventos de la interfaz de usuario</li>
 *   <li>Coordina la ejecución de algoritmos de búsqueda</li>
 *   <li>Registra resultados en CSV para análisis</li>
 *   <li>Maneja la validación de datos y errores</li>
 *   <li>Actualiza la vista con los resultados</li>
 * </ul>
 *
 */
public class Controller {
    private final MazeView view;
    private final CSVLogger csvLogger;

    // --- NUEVO: Atributos para guardar el último resultado ---
    private List<int[]> lastFinalPath;
    private List<int[]> lastVisitedCells;

    public Controller(MazeView view) {
        this.view = view;
        this.csvLogger = new CSVLogger("resultados_laberinto.csv");
        this.lastFinalPath = null;
        this.lastVisitedCells = null;
        initController();
    }

    private void initController() {
        view.getControlPanel().addGenerateListener(e -> generateNewMaze());
        view.getControlPanel().addSolveListener(e -> solveMaze());
        view.getControlPanel().addClearAllListener(e -> clearAll());

        // --- NUEVO: Listeners para los nuevos botones ---
        view.getControlPanel().addShowFullPathListener(e -> showFullPath());
        view.getControlPanel().addStepByStepListener(e -> showNextStep());

        // Listeners existentes para reportes
        view.addListResultsListener(e -> view.showResultsTable());
        view.addShowTimesChartListener(e -> view.showTimesChart());
    }

    private void generateNewMaze() {
        int rows = view.getControlPanel().getRows();
        int cols = view.getControlPanel().getCols();
        view.getMazePanel().setMazeData(new int[rows][cols]);
        view.getMazePanel().clearMaze();
        view.pack();
        // Limpiar resultados al generar nuevo laberinto
        lastFinalPath = null;
        lastVisitedCells = null;
    }

    private void solveMaze() {
        view.getMazePanel().clearVisuals(); // Limpia dibujos anteriores

        int[][] mazeData = view.getMazePanel().getMazeData();
        Point startPoint = view.getMazePanel().getStartPoint();
        Point endPoint = view.getMazePanel().getEndPoint();
        String algorithm = view.getControlPanel().getSelectedAlgorithm();

        if (startPoint == null || endPoint == null) {
            view.showError("Por favor, defina un punto de inicio y fin.");
            return;
        }
        // ... (otras validaciones que ya tenías)

        List<int[]> path;
        List<int[]> visitedCells;

        long startTime = System.nanoTime();

        // --- Lógica de ejecución movida aquí ---
        Laberinto laberinto = createLaberintoFromData(mazeData);
        Celda inicio = laberinto.getCelda(startPoint.y, startPoint.x);
        Celda fin = laberinto.getCelda(endPoint.y, endPoint.x);

        switch (algorithm) {
            case "BFS":
                SolverBFS bfs = new SolverBFS();
                path = convertCeldaPathToIntArrayPath(bfs.buscarRuta(laberinto, inicio, fin));
                visitedCells = convertCeldaPathToIntArrayPath(bfs.getRecorrido());
                break;

            case "Recursivo (2 dir)":
                if (endPoint.y < startPoint.y || endPoint.x < startPoint.x) {
                    path = new ArrayList<>();
                    visitedCells = new ArrayList<>();
                    view.showError("El método recursivo de 2 direcciones solo funciona si el destino está a la derecha y abajo del inicio.");
                } else {
                    SolverRecursivo rec2 = new SolverRecursivo();
                    path = rec2.resolver2Direcciones(mazeData, startPoint.y, startPoint.x, endPoint.y, endPoint.x);
                    visitedCells = rec2.getCeldasVisitadas();
                }
                break;

            case "DFS":
                SolverDFS dfs = new SolverDFS();
                path = convertCeldaPathToIntArrayPath(dfs.buscarRuta(laberinto, inicio, fin));
                visitedCells = convertCeldaPathToIntArrayPath(dfs.getRecorrido());
                break;

            case "Backtracking":
            case "Recursivo (4 dir)": // Asumimos que ambos usan la misma lógica de backtracking
                SolverRecursivo backtrack = new SolverRecursivo();
                path = backtrack.resolverBacktracking(mazeData, startPoint.y, startPoint.x, endPoint.y, endPoint.x);
                visitedCells = backtrack.getCeldasVisitadas();
                break;

            // Puedes añadir casos para los otros algoritmos recursivos si lo deseas

            default:
                path = new ArrayList<>();
                visitedCells = new ArrayList<>();
                view.showError("Algoritmo no reconocido o implementado.");
                break;
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;

        // Guardar los resultados para los otros botones
        this.lastFinalPath = path;
        this.lastVisitedCells = visitedCells;

        boolean success = !path.isEmpty();
        String resultText;
        if (success) {
            resultText = String.format("Algoritmo: %s\nRuta encontrada.\nPasos: %d\nCeldas exploradas: %d\nTiempo: %.4f ms",
                    algorithm, path.size(), visitedCells.size(), durationMs);
            // Por defecto, al resolver, mostramos el camino completo
            view.getMazePanel().drawFullPath(visitedCells, path);
            // Preparamos el panel para la animación paso a paso
            view.getMazePanel().prepareForStepByStep(path);
        } else {
            resultText = String.format("Algoritmo: %s\nNo se encontró ruta.\nCeldas exploradas: %d\nTiempo: %.4f ms",
                    algorithm, visitedCells.size(), durationMs);
            // Si no hay ruta, mostramos todas las celdas visitadas en el intento
            view.getMazePanel().drawFullPath(visitedCells, null);
        }
        view.setResults(resultText);
        csvLogger.logResult(algorithm, success, path.size(), durationMs);
    }

    // Método para el botón "Mostrar Camino Completo" ---
    private void showFullPath() {
        if (lastVisitedCells == null) {
            view.showError("Primero debes resolver el laberinto con el botón '¡Resolver!'.");
            return;
        }
        view.getMazePanel().drawFullPath(lastVisitedCells, lastFinalPath);
    }

    //  Método para el botón "Resolver Paso a Paso" ---
    private void showNextStep() {
        if (lastFinalPath == null) {
            view.showError("Primero debes resolver el laberinto con el botón '¡Resolver!'.");
            return;
        }
        if (lastFinalPath.isEmpty()) {
            view.showError("No se encontró una ruta para mostrar paso a paso.");
            return;
        }
        view.getMazePanel().nextStep();
    }

    private void clearAll() {
        view.getMazePanel().clearMaze();
        view.getControlPanel().setResultsText("");
        lastFinalPath = null;
        lastVisitedCells = null;
    }

    // --- Métodos de utilidad ---
    private Laberinto createLaberintoFromData(int[][] mazeData) {
        Laberinto laberinto = new Laberinto(mazeData.length, mazeData[0].length);
        for (int i = 0; i < mazeData.length; i++) {
            for (int j = 0; j < mazeData[0].length; j++) {
                if (mazeData[i][j] == 0) {
                    laberinto.setMuro(i, j);
                }
            }
        }
        return laberinto;
    }

    private List<int[]> convertCeldaPathToIntArrayPath(List<Celda> celdaPath) {
        if (celdaPath == null) {
            return new ArrayList<>();
        }
        return celdaPath.stream()
                .map(c -> new int[]{c.getFila(), c.getColumna()})
                .collect(Collectors.toList());
    }
}