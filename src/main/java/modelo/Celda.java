package modelo;

/**
 * Representa una celda del laberinto.
 * Puede ser transitable o un muro.
 */
public class Celda {
    private int fila;
    private int columna;
    private boolean transitable;

    public Celda(int fila, int columna, boolean transitable) {
        this.fila = fila;
        this.columna = columna;
        this.transitable = transitable;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean esTransitable() {
        return transitable;
    }

    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }
}
