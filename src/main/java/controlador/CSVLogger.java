// /src/main/java/util/CSVLogger.java
package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase utilitaria para registrar los resultados de las ejecuciones de los algoritmos
 * en un archivo CSV. Mantiene un historial de todas las soluciones intentadas.
 *
 * Características:
 * <ul>
 *   <li>Registra timestamp, algoritmo usado, éxito/fracaso y tiempo de ejecución</li>
 *   <li>Crea el archivo si no existe y añade encabezados</li>
 *   <li>Maneja escritura segura con manejo de recursos</li>
 *   <li>Permite análisis posterior de rendimiento</li>
 * </ul>
 *
 * El formato del CSV es:
 * Timestamp,Algorithm,PathFound,PathLength,ExecutionTime(ms)
 *
 */
public class CSVLogger {
    private final String filePath;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String CSV_HEADER = "Timestamp,Algorithm,PathFound,PathLength,ExecutionTime(ms)";

    public CSVLogger(String filePath) {
        this.filePath = filePath;
        initFile();
    }

    /**
     * Crea el archivo CSV con su encabezado si no existe.
     */
    private void initFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println(CSV_HEADER);
            } catch (IOException e) {
                System.err.println("Error al inicializar el archivo CSV: " + e.getMessage());
            }
        }
    }

    /**
     * Añade una nueva fila de resultados al archivo CSV.
     *
     * @param algorithm El nombre del algoritmo usado.
     * @param success   Si se encontró una ruta o no.
     * @param pathLength La longitud de la ruta (0 si no se encontró).
     * @param timeMs    El tiempo de ejecución en milisegundos.
     */
    public void logResult(String algorithm, boolean success, int pathLength, double timeMs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            String timestamp = dtf.format(LocalDateTime.now());
            // Usamos Locale.US para asegurar que el punto decimal sea un punto.
            String record = String.format(Locale.US, "%s,%s,%b,%d,%.4f",
                    timestamp,
                    algorithm,
                    success,
                    pathLength,
                    timeMs
            );
            writer.println(record);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }
}