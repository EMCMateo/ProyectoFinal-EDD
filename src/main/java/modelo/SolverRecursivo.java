package modelo;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class SolverRecursivo {
    private Map<String, Boolean> memo = new HashMap<>();

    // Movimientos para 2 direcciones: derecha y abajo
    private static final int[] dx2 = {0, 1};
    private static final int[] dy2 = {1, 0};

    // Movimientos para 4 direcciones
    private static final int[] dx4 = {0, 0, 1, -1};
    private static final int[] dy4 = {1, -1, 0, 0};

    private boolean[][] visitado;
    private List<int[]> rutaActual;
    private List<int[]> mejorRuta;

    private void inicializar(int[][] laberinto) {
        visitado = new boolean[laberinto.length][laberinto[0].length];
        rutaActual = new ArrayList<>();
        mejorRuta = new ArrayList<>();
    }

    public List<int[]> resolver2Direcciones(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        // Validación especial: solo puede ir a la derecha y abajo
        if (finFila < fila || finCol < col) return new ArrayList<>();
        backtrack2(laberinto, fila, col, finFila, finCol);
        return mejorRuta;
    }

    private void backtrack2(int[][] lab, int x, int y, int fx, int fy) {
        if (!esValido(lab, x, y) || visitado[x][y]) return;
        visitado[x][y] = true;
        rutaActual.add(new int[]{x, y});
        if (x == fx && y == fy) {
            if (mejorRuta.isEmpty() || rutaActual.size() < mejorRuta.size()) {
                mejorRuta = new ArrayList<>(rutaActual);
            }
        } else {
            for (int i = 0; i < 2; i++) {
                backtrack2(lab, x + dx2[i], y + dy2[i], fx, fy);
            }
        }
        rutaActual.remove(rutaActual.size() - 1);
        visitado[x][y] = false;
    }

    public List<int[]> resolver4Direcciones(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        backtrack4(laberinto, fila, col, finFila, finCol);
        return mejorRuta;
    }

    private void backtrack4(int[][] lab, int x, int y, int fx, int fy) {
        if (!esValido(lab, x, y) || visitado[x][y]) return;
        visitado[x][y] = true;
        rutaActual.add(new int[]{x, y});
        if (x == fx && y == fy) {
            if (mejorRuta.isEmpty() || rutaActual.size() < mejorRuta.size()) {
                mejorRuta = new ArrayList<>(rutaActual);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                backtrack4(lab, x + dx4[i], y + dy4[i], fx, fy);
            }
        }
        rutaActual.remove(rutaActual.size() - 1);
        visitado[x][y] = false;
    }

    public List<int[]> resolverBacktracking(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        int maxLargo = laberinto.length * laberinto[0].length + 1;
        backtrackCompletoPodado(laberinto, fila, col, finFila, finCol, new ArrayList<>(), maxLargo);
        return mejorRuta;
    }

    private void backtrackCompletoPodado(int[][] lab, int x, int y, int fx, int fy, List<int[]> camino, int maxLargo) {
        if (!esValido(lab, x, y) || visitado[x][y]) return;
        if (camino.size() >= maxLargo) return; // Poda: si ya es más largo que el mejor, no seguir
        visitado[x][y] = true;
        camino.add(new int[]{x, y});
        if (x == fx && y == fy) {
            if (mejorRuta.isEmpty() || camino.size() < mejorRuta.size()) {
                mejorRuta = new ArrayList<>(camino);
                // Actualiza el máximo permitido para podar ramas más largas
                maxLargo = mejorRuta.size();
            }
        } else {
            for (int i = 0; i < 4; i++) {
                backtrackCompletoPodado(lab, x + dx4[i], y + dy4[i], fx, fy, camino, maxLargo);
            }
        }
        camino.remove(camino.size() - 1);
        visitado[x][y] = false;
    }

    public List<int[]> resolverBacktrackingConMemo(int[][] laberinto, int fila, int col, int finFila, int finCol) {
        inicializar(laberinto);
        memo.clear();
        backtrackConMemo(laberinto, fila, col, finFila, finCol, new ArrayList<>());
        return mejorRuta;
    }

    private boolean backtrackConMemo(int[][] lab, int x, int y, int fx, int fy, List<int[]> camino) {
        String key = x + "," + y;
        if (memo.containsKey(key)) return memo.get(key);
        if (!esValido(lab, x, y) || visitado[x][y]) return false;

        visitado[x][y] = true;
        camino.add(new int[]{x, y});

        boolean found = false;
        if (x == fx && y == fy) {
            mejorRuta = new ArrayList<>(camino);
            found = true;
        } else {
            for (int i = 0; i < 4; i++) {
                if (backtrackConMemo(lab, x + dx4[i], y + dy4[i], fx, fy, camino)) {
                    found = true;
                }
            }
        }

        visitado[x][y] = false;
        camino.remove(camino.size() - 1);
        memo.put(key, found);
        return found;
    }

    private boolean esValido(int[][] lab, int x, int y) {
        return x >= 0 && y >= 0 && x < lab.length && y < lab[0].length && lab[x][y] == 1;
    }
}
