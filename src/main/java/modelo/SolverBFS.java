package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que implementa el algoritmo de Búsqueda en Amplitud (BFS)
 * para encontrar la ruta más corta en un laberinto.
 *
 * @author Juan Pablo Ortiz
 * @version 1.0
 */
public class SolverBFS {

    // Movimientos para 4 direcciones: arriba, abajo, izquierda, derecha
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    /**
     * Busca la ruta más corta desde un punto de inicio hasta un punto de destino
     * en un laberinto usando el algoritmo BFS.
     * @param laberinto El objeto Laberinto que contiene la matriz de celdas.
     * @param inicio La celda de inicio.
     * @param fin La celda de destino.
     * @return Una lista de celdas que representa la ruta más corta, o una lista vacía si no hay ruta.
     */
    public List<Celda> buscarRuta(Laberinto laberinto, Celda inicio, Celda fin) {
        if (laberinto == null || inicio == null || fin == null) {
            return Collections.emptyList();
        }

        Queue<Celda> cola = new LinkedList<>();
        boolean[][] visitado = new boolean[laberinto.getFilas()][laberinto.getColumnas()];
        Celda[][] predecesor = new Celda[laberinto.getFilas()][laberinto.getColumnas()];

        cola.add(inicio);
        visitado[inicio.getFila()][inicio.getColumna()] = true;

        while (!cola.isEmpty()) {
            Celda actual = cola.poll();

            // Usamos las coordenadas para la comparación de celdas
            if (actual.getFila() == fin.getFila() && actual.getColumna() == fin.getColumna()) {
                return reconstruirRuta(predecesor, inicio, fin);
            }

            for (int i = 0; i < 4; i++) {
                int nuevaFila = actual.getFila() + dx[i];
                int nuevaColumna = actual.getColumna() + dy[i];

                if (esValido(laberinto, nuevaFila, nuevaColumna, visitado)) {
                    Celda vecino = laberinto.getCelda(nuevaFila, nuevaColumna);
                    visitado[nuevaFila][nuevaColumna] = true;
                    predecesor[nuevaFila][nuevaColumna] = actual;
                    cola.add(vecino);
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