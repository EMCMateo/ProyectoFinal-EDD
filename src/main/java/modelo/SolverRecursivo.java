package modelo;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que implementa algoritmos recursivos para encontrar rutas en un laberinto.
 * Incluye versiones con 2 y 4 direcciones, y una variante con backtracking.
 * Se deja estructura para considerar programación dinámica (memoización/tabulación).
 */
public class SolverRecursivo {
    private Map<String, Boolean> memo = new HashMap<>();

    // Movimientos para 2 direcciones: derecha y abajo
    private static final int[] dx2 = {0, 1};
    private static final int[] dy2 = {1, 0};

    // Movimientos para 4 direcciones: derecha, izquierda, abajo, arriba
    private static final int[] dx4 = {0, 0, 1, -1};
    private static final int[] dy4 = {1, -1, 0, 0};

    private boolean[][] visitado;
    private List<int[]> rutaActual = new ArrayList<>();
    private List<int[]> mejorRuta = new ArrayList<>();

    /**
     * Busca una ruta del inicio al fin moviéndose solo a la derecha o abajo.
     * @param laberinto Matriz del laberinto (1 = libre, 0 = muro)
     * @param x Fila inicial
     * @param y Columna inicial
     * @param finX Fila destino
     * @param finY Columna destino
     * @return Lista con la mejor ruta encontrada (como pares [x, y])
     */
    public List<int[]> resolver2Direcciones(int[][] laberinto, int x, int y, int finX, int finY) {
        visitado = new boolean[laberinto.length][laberinto[0].length];
        rutaActual.clear();
        mejorRuta.clear();
        backtrack2(laberinto, x, y, finX, finY);
        return mejorRuta;
    }

    private void backtrack2(int[][] lab, int x, int y, int fx, int fy) {
        if (!esValido(lab, x, y) || visitado[x][y]) return;
        rutaActual.add(new int[]{x, y});
        if (x == fx && y == fy) {
            mejorRuta = new ArrayList<>(rutaActual);
        } else {
            visitado[x][y] = true;
            for (int i = 0; i < 2; i++) {
                backtrack2(lab, x + dx2[i], y + dy2[i], fx, fy);
            }
            visitado[x][y] = false;
        }
        rutaActual.remove(rutaActual.size() - 1);
    }

    /**
     * Busca una ruta del inicio al fin moviéndose en 4 direcciones (sin backtracking completo).
     * @param laberinto Matriz del laberinto (1 = libre, 0 = muro)
     * @param x Fila inicial
     * @param y Columna inicial
     * @param finX Fila destino
     * @param finY Columna destino
     * @return Lista con la mejor ruta encontrada (como pares [x, y])
     */
    public List<int[]> resolver4Direcciones(int[][] laberinto, int x, int y, int finX, int finY) {
        visitado = new boolean[laberinto.length][laberinto[0].length];
        rutaActual.clear();
        mejorRuta.clear();
        backtrack4(laberinto, x, y, finX, finY);
        return mejorRuta;
    }

    private void backtrack4(int[][] lab, int x, int y, int fx, int fy) {
        if (!esValido(lab, x, y) || visitado[x][y]) return;
        rutaActual.add(new int[]{x, y});
        if (x == fx && y == fy) {
            mejorRuta = new ArrayList<>(rutaActual);
        } else {
            visitado[x][y] = true;
            for (int i = 0; i < 4; i++) {
                backtrack4(lab, x + dx4[i], y + dy4[i], fx, fy);
            }
            visitado[x][y] = false;
        }
        rutaActual.remove(rutaActual.size() - 1);
    }

    /**
     * Busca la ruta más corta usando backtracking completo en 4 direcciones.
     * @param laberinto Matriz del laberinto (1 = libre, 0 = muro)
     * @param x Fila inicial
     * @param y Columna inicial
     * @param finX Fila destino
     * @param finY Columna destino
     * @return Lista con la mejor ruta encontrada (como pares [x, y])
     */
    public List<int[]> resolverBacktracking(int[][] laberinto, int x, int y, int finX, int finY) {
        visitado = new boolean[laberinto.length][laberinto[0].length];
        mejorRuta.clear();
        backtrackCompleto(laberinto, x, y, finX, finY, new ArrayList<>());
        return mejorRuta;
    }

    private void backtrackCompleto(int[][] lab, int x, int y, int fx, int fy, List<int[]> camino) {
        if (!esValido(lab, x, y) || visitado[x][y]) return;
        camino.add(new int[]{x, y});
        if (x == fx && y == fy) {
            if (mejorRuta.isEmpty() || camino.size() < mejorRuta.size()) {
                mejorRuta = new ArrayList<>(camino);
            }
        } else {
            visitado[x][y] = true;
            for (int i = 0; i < 4; i++) {
                backtrackCompleto(lab, x + dx4[i], y + dy4[i], fx, fy, camino);
            }
            visitado[x][y] = false;
        }
        camino.remove(camino.size() - 1);
    }

    /**
     * Método auxiliar para validar si una celda es transitable.
     */
    private boolean esValido(int[][] lab, int x, int y) {
        return x >= 0 && y >= 0 && x < lab.length && y < lab[0].length && lab[x][y] == 1;
    }

    /**
 * Variante de backtracking con memoización (programación dinámica).
 * Guarda los caminos que ya han sido intentados para no repetirlos.
 */
public List<int[]> resolverBacktrackingConMemo(int[][] laberinto, int x, int y, int finX, int finY) {
    visitado = new boolean[laberinto.length][laberinto[0].length];
    mejorRuta.clear();
    memo.clear(); // Limpiar caché antes de cada ejecución
    backtrackConMemo(laberinto, x, y, finX, finY, new ArrayList<>());
    return mejorRuta;
}

private boolean backtrackConMemo(int[][] lab, int x, int y, int fx, int fy, List<int[]> camino) {
    String key = x + "," + y;
    if (memo.containsKey(key)) return memo.get(key);
    if (!esValido(lab, x, y) || visitado[x][y]) return false;

    camino.add(new int[]{x, y});
    if (x == fx && y == fy) {
        if (mejorRuta.isEmpty() || camino.size() < mejorRuta.size()) {
            mejorRuta = new ArrayList<>(camino);
        }
        camino.remove(camino.size() - 1);
        return true;
    }

    visitado[x][y] = true;
    boolean encontrado = false;
    for (int i = 0; i < 4; i++) {
        if (backtrackConMemo(lab, x + dx4[i], y + dy4[i], fx, fy, camino)) {
            encontrado = true;
        }
    }
    visitado[x][y] = false;
    camino.remove(camino.size() - 1);
    memo.put(key, encontrado);
    return encontrado;
}
}
