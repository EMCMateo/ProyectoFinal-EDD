package modelo;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implementa el algoritmo de búsqueda en profundidad (Depth-First Search)
 * para encontrar un camino en el laberinto.
 *
 * Características:
 * <ul>
 *   <li>Explora el laberinto siguiendo una rama hasta el final antes de retroceder</li>
 *   <li>Utiliza una pila (implícita en la recursión) para la exploración</li>
 *   <li>Mantiene un registro de celdas visitadas</li>
 *   <li>No garantiza encontrar el camino más corto</li>
 * </ul>
 *
 */
public class SolverDFS {

    // Movimientos para 4 direcciones: arriba, abajo, izquierda, derecha
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    /** Lista que almacena el orden en que las celdas fueron visitadas para el recorrido. */
    private List<Celda> recorrido;

    /**
     * Devuelve la lista de celdas visitadas en el orden de exploración.
     * Esta lista puede ser utilizada por el controlador para animar el proceso de búsqueda.
     *
     * @return Una lista de celdas que representa el recorrido completo del algoritmo.
     */
    public List<Celda> getRecorrido() {
        return recorrido;
    }

    /**
     * Busca una ruta desde un punto de inicio hasta un punto de destino
     * en un laberinto usando el algoritmo DFS (iterativo).
     * @param laberinto El objeto Laberinto que contiene la matriz de celdas.
     * @param inicio La celda de inicio.
     * @param fin La celda de destino.
     * @return Una lista de celdas que representa una ruta, o una lista vacía si no hay ruta.
     */
    public List<Celda> buscarRuta(Laberinto laberinto, Celda inicio, Celda fin) {
        if (laberinto == null || inicio == null || fin == null) {
            return Collections.emptyList();
        }

        Stack<Celda> pila = new Stack<>();
        boolean[][] visitado = new boolean[laberinto.getFilas()][laberinto.getColumnas()];
        Celda[][] predecesor = new Celda[laberinto.getFilas()][laberinto.getColumnas()];
        this.recorrido = new ArrayList<>(); // Inicializar la lista de recorrido al inicio de la búsqueda

        pila.push(inicio);
        visitado[inicio.getFila()][inicio.getColumna()] = true;
        this.recorrido.add(inicio); // Añadir el inicio al recorrido

        while (!pila.isEmpty()) {
            Celda actual = pila.pop();

            // Usamos las coordenadas para la comparación de celdas
            if (actual.getFila() == fin.getFila() && actual.getColumna() == fin.getColumna()) {
                // Al encontrar la solución, añadimos el destino al recorrido
                this.recorrido.add(fin);
                return reconstruirRuta(predecesor, inicio, fin);
            }

            for (int i = 0; i < 4; i++) {
                int nuevaFila = actual.getFila() + dx[i];
                int nuevaColumna = actual.getColumna() + dy[i];

                if (esValido(laberinto, nuevaFila, nuevaColumna, visitado)) {
                    Celda vecino = laberinto.getCelda(nuevaFila, nuevaColumna);
                    visitado[nuevaFila][nuevaColumna] = true;
                    predecesor[nuevaFila][nuevaColumna] = actual;
                    pila.push(vecino);
                    this.recorrido.add(vecino); // Añadir la celda visitada al recorrido
                }
            }
        }
        return Collections.emptyList(); // No se encontró ruta
    }

    /**
     * Reconstruye la ruta desde el destino hasta el inicio usando el arreglo de predecesores.
     */
    private List<Celda> reconstruirRuta(Celda[][] predecesor, Celda inicio, Celda fin) {
        List<Celda> ruta = new ArrayList<>();
        for (Celda at = fin; at != null; at = predecesor[at.getFila()][at.getColumna()]) {
            ruta.add(at);
        }
        Collections.reverse(ruta);
        return ruta;
    }

    /**
     * Verifica si una celda es válida para el movimiento.
     */
    private boolean esValido(Laberinto laberinto, int fila, int columna, boolean[][] visitado) {
        int filas = laberinto.getFilas();
        int columnas = laberinto.getColumnas();
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false;
        }
        if (visitado[fila][columna] || !laberinto.getCelda(fila, columna).esTransitable()) {
            return false;
        }
        return true;
    }
}