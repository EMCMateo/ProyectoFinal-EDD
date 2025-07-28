package modelo;

/**
 * Representa una celda individual dentro del laberinto.
 * Cada celda se define por sus coordenadas (fila y columna) y un estado que
 * indica si es transitable (parte del camino) o un muro.
 *
 * @author Juan Pablo Ortiz
 * @version 1.0
 */
public class Celda {
    private int fila;
    private int columna;
    private boolean transitable;

    /**
     * Constructor para crear una nueva celda.
     *
     *
     * @param fila La coordenada de la fila de la celda.
     * @param columna La coordenada de la columna de la celda.
     * @param transitable Un valor booleano que indica si la celda es transitable ({@code true}) o un muro ({@code false}).
     */
    public Celda(int fila, int columna, boolean transitable) {
        this.fila = fila;
        this.columna = columna;
        this.transitable = transitable;
    }

    /**
     * Obtiene la coordenada de la fila de la celda.
     *
     * @return El número de la fila.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la coordenada de la columna de la celda.
     *
     * @return El número de la columna.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Verifica si la celda es transitable.
     *
     * @return {@code true} si la celda es transitable, {@code false} si es un muro.
     */
    public boolean esTransitable() {
        return transitable;
    }

    /**
     * Establece el estado de transitabilidad de la celda.
     *
     * @param transitable Un valor booleano que indica si la celda debe ser transitable.
     */
    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }

    /**
     * Devuelve una representación en cadena de la celda, mostrando sus coordenadas.
     *
     * @return Una cadena con el formato "[row=fila, col=columna]".
     */
    @Override
    public String toString() {
        return "[row=" + fila + ", col=" + columna + "]";
    }

    /**
     * Genera un valor de código hash para la celda. Este metodo es útil para
     * almacenar objetos Celda en estructuras de datos como {@code HashMap} o {@code HashSet}.
     *
     * @return El valor del código hash.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fila;
        result = prime * result + columna;
        return result;
    }

    /**
     * Compara esta celda con otro objeto para determinar si son iguales.
     * Dos celdas se consideran iguales si tienen las mismas coordenadas de fila y columna.
     *
     * @param obj El objeto con el que se va a comparar.
     * @return {@code true} si el objeto es una Celda con las mismas coordenadas, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Celda other = (Celda) obj;
        if (fila != other.fila)
            return false;
        if (columna != other.columna)
            return false;
        return true;
    }
}