// /src/main/java/vista/TimesChartView.java
package vista;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Una ventana de diálogo (JDialog) para mostrar la gráfica de comparación de tiempos.
 * <p>
 * Actualmente, esta clase actúa como un <b>placeholder</b>, mostrando un mensaje
 * de texto. En una futura implementación, podría integrarse con una librería
 * de gráficos como JFreeChart para visualizar los datos de rendimiento.
 *
 * @author Israel Orellana
 * @version 1.1
 */
public class TimesChartView extends JDialog {

    /**
     * Construye la ventana de diálogo para la gráfica de tiempos.
     *
     * @param owner La ventana principal (JFrame) que es dueña de este diálogo,
     *              asegurando que el diálogo se comporte de manera modal.
     */
    public TimesChartView(Frame owner) {
        super(owner, "Gráfica de Tiempos de Ejecución", true);
        setSize(800, 600);
        setLocationRelativeTo(owner);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        boolean tieneDatos = false;
        try (BufferedReader br = new BufferedReader(new FileReader("resultados_laberinto.csv"))) {
            String header = br.readLine(); // Leer encabezado
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String timestamp = parts[0].trim();
                    String algoritmo = parts[1].trim();
                    double tiempo = Double.parseDouble(parts[4].trim());
                    dataset.addValue(tiempo, algoritmo, timestamp);
                    tieneDatos = true;
                }
            }
        } catch (IOException | NumberFormatException ex) {
            JLabel errorLabel = new JLabel("Error al leer el archivo CSV o formato incorrecto.", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(errorLabel, BorderLayout.CENTER);
            return;
        }
        if (!tieneDatos) {
            JLabel vacioLabel = new JLabel("No hay datos de tiempos para mostrar.", SwingConstants.CENTER);
            vacioLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(vacioLabel, BorderLayout.CENTER);
            return;
        }
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Comparación de Tiempos de Algoritmos",
                "Timestamp",
                "Tiempo (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        // Personalización de colores y diseño
        lineChart.setBackgroundPaint(Color.WHITE);
        lineChart.getPlot().setBackgroundPaint(new Color(240, 240, 255));

        ChartPanel chartPanel = new ChartPanel(lineChart);
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
}