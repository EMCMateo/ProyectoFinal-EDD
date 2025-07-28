// /src/main/java/vista/ResultsTableView.java
package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Una ventana de diálogo (JDialog) que muestra los resultados de los laberintos
 * leídos desde un archivo CSV en una tabla (JTable).
 *
 * @author Israel Orellana
 * @version 1.1
 */
public class ResultsTableView extends JDialog {

    /**
     * Construye la ventana de diálogo, lee el archivo CSV y puebla la tabla.
     * Si el archivo no se encuentra, muestra un diálogo de error.
     *
     * @param owner La ventana principal (JFrame) que es dueña de este diálogo.
     *              Esto asegura que el diálogo se comporte de manera modal.
     */
    public ResultsTableView(Frame owner) {
        super(owner, "Historial de Resultados", true); // true para hacerlo modal
        setSize(800, 400);
        setLocationRelativeTo(owner);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true); // La tabla ocupa todo el alto

        try (BufferedReader br = new BufferedReader(new FileReader("resultados_laberinto.csv"))) {
            // Leer cabeceras
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                for (String header : headers) {
                    tableModel.addColumn(header);
                }
            }

            // Leer filas de datos
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo leer el archivo 'resultados_laberinto.csv'.\nError: " + e.getMessage(),
                    "Error de Archivo",
                    JOptionPane.ERROR_MESSAGE);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}