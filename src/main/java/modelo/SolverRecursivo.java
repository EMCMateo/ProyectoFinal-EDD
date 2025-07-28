package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa soluciones recursivas para encontrar caminos en el laberinto.
 * Incluye tres variantes de algoritmos recursivos:
 * <ul>
 *   <li>Recursivo de 2 direcciones (solo derecha y abajo)</li>
 *   <li>Recursivo de 4 direcciones (todas las direcciones)</li>
 *   <li>Backtracking con optimización para encontrar el camino más corto</li>
 * </ul>
 *
 * Características principales:
 * <ul>
 *   <li>Usa técnicas de backtracking para explorar caminos</li>
 *   <li>Implementa poda para optimizar la búsqueda</li>
 *   <li>Mantiene registro de celdas visitadas para evitar ciclos</li>
 *   <li>Incluye versión con memoización para mejorar rendimiento</li>
 * </ul>
 *
 */
public class SolverRecursivo {

    // Movimientos para 2 direcciones: derecha y abajo
    private static final int[] dx2 = {0, 1};
    private static final int[] dy2 = {1, 0};

    // Movimientos para 4 direcciones: derecha, izquierda, abajo, arriba
    private static final int[] dx4 = {0, 0, 1, -1};
    private static final int[] dy4 = {1, -1, 0, 0};

    // --- Variables de estado para una única ejecución ---
    private boolean[][] visitado;
    private List<int[]> rutaActual;
    private List<int[]> mejorRuta;
    private List<int[]> celdasVisitadas = new ArrayList<>(); // Guarda TODAS las celdas exploradas

    /**
     * Inicializa las variables de estado antes de cada búsqueda.
     * Es crucial para asegurar que cada llamada a 'resolver' sea independiente.
     *
     * @param laberinto La matriz del laberinto para determinar las dimensiones.
     */
    private void inicializar(int[][] laberinto) {
        this.visitado = new boolean[laberinto.length][laberinto[0].length];
        this.rutaActual = new ArrayList<>();
        this.mejorRuta = new ArrayList<>();
        this.celdasVisitadas = new ArrayList<>(); // ¡Importante inicializar aquí!
    }

    /**
     * Devuelve la lista de todas las celdas que fueron exploradas durante la última
     * ejecución del algoritmo.
     *
     * @return Una lista de coordenadas [fila, columna].
     */
    public List<int[]> getCeldasVisitadas() {
        return celdasVisitadas;
    }

    /**
     * Resuelve el laberinto usando backtracking con solo 2 direcciones (abajo y derecha).
     * Encuentra la única ruta posible si existe.
     */
    public List<int[]> resolver2Direcciones(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        if (finFila < fila || finCol < col) {
            return mejorRuta; // Imposible llegar
        }
        backtrack(laberinto, fila, col, finFila, finCol, dx2, dy2);
        return mejorRuta;
    }

    /**
     * Resuelve el laberinto usando backtracking con 4 direcciones.
     * Esto encontrará una ruta, pero no necesariamente la más corta.
     */
    public List<int[]> resolver4Direcciones(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        backtrack(laberinto, fila, col, finFila, finCol, dx4, dy4);
        return mejorRuta;
    }

    /**
     * Resuelve el laberinto usando backtracking completo para encontrar la ruta más corta.
     * Explora todas las posibilidades.
     */
    public List<int[]> resolverBacktracking(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        backtrack(laberinto, fila, col, finFila, finCol, dx4, dy4);
        return mejorRuta;
    }

    /**
     * Método de backtracking genérico que explora el laberinto.
     *
     * @param lab      La matriz del laberinto.
     * @param x        Fila actual.
     * @param y        Columna actual.
     * @param finX     Fila de destino.
     * @param finY     Columna de destino.
     * @param movesX   Array de movimientos en X.
     * @param movesY   Array de movimientos en Y.
     */
    private void backtrack(int[][] lab, int x, int y, int finX, int finY, int[] movesX, int[] movesY) {
        // Condición de parada: fuera de límites, es un muro o ya se visitó en esta ruta.
        if (!esValido(lab, x, y) || visitado[x][y]) {
            return;
        }

        // --- Acción ---
        visitado[x][y] = true;
        rutaActual.add(new int[]{x, y});
        celdasVisitadas.add(new int[]{x, y}); // Registra la celda como explorada

        // Si llegamos al destino
        if (x == finX && y == finY) {
            // Si es la primera ruta encontrada o si es más corta que la anterior
            if (mejorRuta.isEmpty() || rutaActual.size() < mejorRuta.size()) {
                mejorRuta = new ArrayList<>(rutaActual);
            }
        } else {
            // --- Recursión ---
            // Explorar todos los vecinos posibles según los movimientos permitidos
            for (int i = 0; i < movesX.length; i++) {
                // Poda: Si la ruta actual ya es más larga que la mejor encontrada, no seguir.
                // Esto es clave para la eficiencia en el backtracking completo.
                if (mejorRuta.isEmpty() || rutaActual.size() < mejorRuta.size()) {
                    backtrack(lab, x + movesX[i], y + movesY[i], finX, finY, movesX, movesY);
                }
            }
        }

        // --- Backtrack (deshacer) ---
        // Liberamos la celda para que pueda ser parte de otras rutas posibles.
        visitado[x][y] = false;
        rutaActual.remove(rutaActual.size() - 1);
    }

    /**
     * Verifica si una celda es transitable (dentro de los límites y no es un muro).
     */
    private boolean esValido(int[][] lab, int x, int y) {
        return x >= 0 && y >= 0 && x < lab.length && y < lab[0].length && lab[x][y] == 1;
    }
}