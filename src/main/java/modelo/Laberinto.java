package modelo;

/**
 * Representa la estructura completa del laberinto.
 * Mantiene una matriz de celdas y proporciona métodos para manipular
 * y consultar el estado del laberinto.
 *
 * Características:
 * <ul>
 *   <li>Gestiona una matriz bidimensional de celdas</li>
 *   <li>Permite colocar y quitar muros</li>
 *   <li>Provee acceso a celdas individuales</li>
 *   <li>Permite convertir el laberinto a formato de matriz de enteros</li>
 * </ul>
 *
 */
public class Laberinto {
    private Celda[][] matriz;
    private int filas;
    private int columnas;

    /**
     * Constructor para crear un nuevo laberinto de un tamaño específico.
     * Por defecto, todas las celdas son transitables.
     *
     * @param filas El número de filas del laberinto.
     * @param columnas El número de columnas del laberinto.
     */
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

    /**
     * Establece una celda específica como un muro (no transitable).
     *
     * @param fila La coordenada de la fila de la celda.
     * @param columna La coordenada de la columna de la celda.
     */
    public void setMuro(int fila, int columna) {
        matriz[fila][columna].setTransitable(false);
    }

    /**
     * Establece una celda específica como transitable.
     *
     * @param fila La coordenada de la fila de la celda.
     * @param columna La coordenada de la columna de la celda.
     */
    public void setTransitable(int fila, int columna) {
        matriz[fila][columna].setTransitable(true);
    }

    /**
     * Obtiene el objeto Celda en las coordenadas especificadas.
     *
     * @param fila La coordenada de la fila.
     * @param columna La coordenada de la columna.
     * @return El objeto Celda en la posición (fila, columna).
     */
    public Celda getCelda(int fila, int columna) {
        return matriz[fila][columna];
    }

    /**
     * Obtiene el número total de filas del laberinto.
     *
     * @return El número de filas.
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Obtiene el número total de columnas del laberinto.
     *
     * @return El número de columnas.
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Devuelve el laberinto como una matriz de enteros, donde 1 representa una
     * celda transitable y 0 representa un muro.
     *
     * @return Una matriz bidimensional de enteros que representa el laberinto.
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