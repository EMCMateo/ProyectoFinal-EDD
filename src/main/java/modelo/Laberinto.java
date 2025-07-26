package modelo;

/**
 * Representa el laberinto como una matriz de celdas.
 */
public class Laberinto {
    private Celda[][] matriz;
    private int filas;
    private int columnas;

    public Laberinto(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        matriz = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new Celda(i, j, true); // Por defecto, todo transitable
            }
        }
    }

    public void setMuro(int fila, int columna) {
        matriz[fila][columna].setTransitable(false);
    }

    public void setTransitable(int fila, int columna) {
        matriz[fila][columna].setTransitable(true);
    }

    public Celda getCelda(int fila, int columna) {
        return matriz[fila][columna];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    /**
     * Devuelve el laberinto como matriz de enteros (1 = transitable, 0 = muro)
     */
    public int[][] aMatrizEnteros() {
        int[][] mat = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                mat[i][j] = matriz[i][j].esTransitable() ? 1 : 0;
            }
        }
        return mat;
    }
}
