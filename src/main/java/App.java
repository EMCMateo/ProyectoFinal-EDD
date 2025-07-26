import modelo.SolverRecursivo;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Laberinto de ejemplo (1 = libre, 0 = muro)
        int[][] laberinto = {
            {1, 1, 0, 1},
            {0, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 1, 1}
        };
        int inicioX = 0, inicioY = 0, finX = 3, finY = 3;
        SolverRecursivo solver = new SolverRecursivo();

        System.out.println("Prueba: 2 direcciones (derecha, abajo)");
        List<int[]> ruta2 = solver.resolver2Direcciones(laberinto, inicioX, inicioY, finX, finY);
        imprimirRuta(ruta2);

        System.out.println("\nPrueba: 4 direcciones (sin backtracking completo)");
        List<int[]> ruta4 = solver.resolver4Direcciones(laberinto, inicioX, inicioY, finX, finY);
        imprimirRuta(ruta4);

        System.out.println("\nPrueba: 4 direcciones + backtracking completo");
        List<int[]> rutaBack = solver.resolverBacktracking(laberinto, inicioX, inicioY, finX, finY);
        imprimirRuta(rutaBack);
    }

    private static void imprimirRuta(List<int[]> ruta) {
        if (ruta == null || ruta.isEmpty()) {
            System.out.println("No se encontró ruta.");
            return;
        }
        for (int[] paso : ruta) {
            System.out.print("[" + paso[0] + "," + paso[1] + "] ");
        }
        System.out.println();
    }
}
