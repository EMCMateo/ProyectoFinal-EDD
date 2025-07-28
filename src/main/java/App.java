import controlador.Controller;
import vista.MazeView;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación del solucionador de laberintos.
 * Su única responsabilidad es crear la Vista y el Controlador, enlazarlos,
 * y hacer visible la interfaz gráfica de usuario de una manera segura para Swing.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class App {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater asegura que la creación de la GUI
        // se ejecute en el Event Dispatch Thread (EDT), lo cual es
        // la práctica recomendada para evitar problemas de concurrencia en Swing.
        SwingUtilities.invokeLater(() -> {
            // 1. Se crea la instancia de la vista (la ventana principal).
            MazeView view = new MazeView();

            // 2. Se crea la instancia del controlador, pasándole la vista.
            // El constructor del controlador se encargará de "conectar" los botones
            // y la lógica.
            new Controller(view);

            // 3. Se hace visible la ventana.
            // Esto se hace al final para que el usuario vea la aplicación
            // completamente inicializada.
            view.setVisible(true);
        });
    }
}