// /src/main/java/vista/TimesChartView.java
package vista;

import javax.swing.*;
import java.awt.*;

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

        // Placeholder: En una implementación real, aquí se añadiría un panel con la gráfica
        // (por ejemplo, usando una librería como JFreeChart).
        JLabel placeholderLabel = new JLabel("Aquí se mostrará la gráfica de comparación de tiempos.", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(placeholderLabel, BorderLayout.CENTER);
    }
}