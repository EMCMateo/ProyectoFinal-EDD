# Solucionador de Laberintos con Algoritmos de Búsqueda

📌 **Información General**
*   **Título**: Implementación y Comparación Visual de Algoritmos de Búsqueda en Laberintos
*   **Asignatura**: Estructura de Datos
*   **Carrera**: Computación
*   **Estudiantes**: Einar Kaalhus, Israel Orellana, Juan Pablo Ortiz y Mateo Molina
*   **Fecha**: 28 de Julio del 2024
*   **Profesor**: Ing. Pablo Torres

---

🛠️ **Descripción**
Este proyecto implementa y demuestra visualmente diferentes algoritmos de estructra de datos para la búsqueda y resolución de laberintos. La aplicación permite a los usuarios crear laberintos de forma interactiva, poner el inicio, fin y muros, seleccionar un algoritmo de resolución y observar su comportamiento en tiempo real.

Los algoritmos implementados son:
*   **Búsqueda en Amplitud (BFS)**: Encuentra la ruta más corta.
*   **Búsqueda en Profundidad (DFS)**: Encuentra una ruta, no necesariamente la más corta.
*   **Backtracking Recursivo(2 direcciones y 4 direcciones)**: Un enfoque recursivo que explora todas las posibilidades para encontrar la ruta óptima.

El sistema incluye:
*   Una interfaz gráfica de usuario (GUI) construida con **Java Swing**.
*   Funcionalidad para dibujar muros y establecer puntos de inicio y fin.
*   Visualización diferenciada para las **celdas exploradas** (gris) y la **ruta final** (azul/naranja).
*   Modo de ejecución **paso a paso** para analizar el comportamiento del algoritmo.
*   Registro de resultados (tiempo, longitud de la ruta) en un archivo **CSV**.
*   Una gráfica de comparación de tiempos de ejecución generada con **JFreeChart**.

---

### 📖 **Descripción de Clases del Paquete `modelo`**

La lógica de proyecto y las estructuras de datos centrales del mismo.

*   **`Celda.java`**: Representa una celda individual en la cuadrícula del laberinto. Cada objeto `Celda` almacena sus coordenadas (fila y columna) y un estado booleano (`transitable`) que indica si es un camino libre o un muro. Sobrescribe los métodos `equals()` y `hashCode()` para permitir su uso eficiente en colecciones como `HashSet` o como claves en un `HashMap`.

*   **`Laberinto.java`**: Modela la estructura completa del laberinto como una matriz bidimensional de objetos `Celda`. Esta clase se encarga de inicializar la cuadrícula, gestionar sus dimensiones y proporcionar métodos para establecer qué celdas son muros y cuáles son transitables.

*   **`SolverBFS.java`**: Implementa el algoritmo de **Búsqueda en Amplitud (BFS)**. Utiliza una cola (`Queue`) para explorar el laberinto nivel por nivel desde el punto de inicio. Este enfoque garantiza que la primera ruta encontrada hacia el destino sea la más corta en términos de número de celdas. La clase también registra todas las celdas visitadas en el proceso para su posterior visualización.

*   **`SolverDFS.java`**: Implementa el algoritmo de **Búsqueda en Profundidad (DFS)** de forma iterativa. Utiliza una pila (`Stack`) para explorar una rama del laberinto tan profundamente como sea posible antes de retroceder (backtracking). Generalmente encuentra una solución más rápido que BFS en laberintos con rutas largas, pero no garantiza que sea la más corta. Al igual que BFS, almacena el recorrido de exploración.

*   **`SolverRecursivo.java`**: Implementa la resolución de laberintos mediante **backtracking recursivo**. Esta clase explora sistemáticamente todas las rutas posibles desde el punto de inicio. A través de la recursión, avanza por un camino y, si llega a un punto muerto o al final, retrocede para probar otras alternativas. Está diseñado para encontrar la ruta más corta explorando todas las opciones y guardando la mejor encontrada. También registra todas las celdas que visita.


---


📁 Estructura del Proyecto
.
├── pom.xml # Archivo de configuración de Maven con dependencias
├── resultados_laberinto.csv # Archivo de salida con los resultados de las ejecuciones
└── src/
└── main/
└── java/
├── App.java # Clase principal que inicia la aplicación
├── controlador/
│ └── Controller.java # Conecta la vista con el modelo (lógica de control)
├── modelo/
│ ├── Celda.java # Representa una celda individual del laberinto
│ ├── Laberinto.java # Representa la estructura completa del laberinto
│ ├── SolverBFS.java # Implementación del algoritmo BFS
│ ├── SolverDFS.java # Implementación del algoritmo DFS
│ └── SolverRecursivo.java # Implementación de backtracking recursivo
├── util/
│ └── CSVLogger.java # Utilidad para escribir resultados en un archivo CSV
└── vista/
├── ControlPanel.java # Panel con botones, selectores y área de resultados
├── MazePanel.java # Panel donde se dibuja interactivamente el laberinto
├── MazeView.java # Ventana principal de la aplicación (JFrame)
└── TimesChartView.java # Ventana para mostrar la gráfica de tiempos

---


📊 Tabla Comparativa de Complejidad

| Algoritmo                        | Tiempo Peor Caso     | Espacio Peor Caso    | ¿Garantiza la Ruta más Corta? |
| :------------------------------ | :------------------: | :------------------: | :----------------------------: |
| BFS (Búsqueda en Amplitud)      | O(R * C)             | O(R * C)             | Sí                            |
| DFS (Búsqueda en Profundidad)   | O(R * C)             | O(R * C)             | No                            |
| Backtracking (Exhaustivo)       | O(4<sup>R×C</sup>)*  | O(R * C)             | Sí                            |

---

🚀 **Ejecución**
Para ejecutar el proyecto, asegúrate de tener el siguiente software instalado:
*   Java JDK 11 o superior
*   Apache Maven
*   
---
### Funcionalidades Implementadas

#### Búsqueda en Amplitud (BFS)

- Utiliza una `Queue` para explorar los nodos vecinos.
- Garantiza la ruta más corta en un laberinto sin pesos.
- **Complejidad:** `O(V + E)`, donde `V` es el número de celdas y `E` el número de conexiones.

#### Búsqueda en Profundidad (DFS)

- Utiliza una `Stack` para profundizar en los caminos.
- Encuentra una ruta rápidamente, pero no necesariamente la óptima.
- **Complejidad:** `O(V + E)`.

#### Backtracking Recursivo

- Explora todas las rutas posibles de forma recursiva.
- Encuentra la ruta más corta mediante exploración exhaustiva.
- **Complejidad:** puede ser exponencial en el peor de los casos, pero se optimiza con técnicas de "poda" (*pruning*).

#### Visualización Interactiva

- Permite al usuario ver cómo cada algoritmo procesa el laberinto coloreando las celdas exploradas.
- El modo paso a paso ofrece un control detallado sobre la animación de la ruta.

---

### Conclusiones

La implementación de estos algoritmos en un entorno visual ofrece una comprensión profunda de sus diferencias fundamentales:

- **BFS** es ideal cuando el objetivo es encontrar la ruta más corta garantizada, como en un sistema de GPS.
- **DFS** es útil cuando cualquier ruta es aceptable y la memoria es una limitación (su versión recursiva utiliza menos memoria que BFS en laberintos con muchas ramas).
- **Backtracking** es un ejemplo clásico de la potencia de la recursión para resolver problemas de exploración exhaustiva, siendo la base de muchos algoritmos de inteligencia artificial.
- La **visualización interactiva** demuestra que no hay un "mejor" algoritmo universal; la elección depende de los requisitos específicos del problema: optimalidad, velocidad o uso de memoria.

---

### Dependencias

- Java JDK 11 o superior
- Apache Maven
- JFreeChart: librería para la generación de gráficos (gestionada a través de `pom.xml`)

---

### Notas Adicionales

- El `Controller` sigue el patrón **Modelo-Vista-Controlador (MVC)** para separar la lógica de negocio de la interfaz gráfica.
- La aplicación es robusta y maneja correctamente casos donde no existe una ruta posible.
- El sistema de **logging en CSV** permite un análisis persistente y cuantitativo del rendimiento de cada algoritmo.

    
