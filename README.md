# Implementación de Estructuras de Datos: Matrices, Árboles y Grafos

## 📌 Información General
- **Título:** Implementación de Estructuras de Datos: Matrices, Árboles y Grafos  
- **Asignatura:** Estructura de Datos  
- **Carrera:** Computación  
- **Estudiantes:** Mateo Molina, Israel Orellana, Juan Ortiz y Einar Kaalhus
- **Fecha:** 5 de julio del 2025  
- **Profesor:** Ing. Pablo Torres  

## 🛠 Descripción

Este proyecto implementa y demuestra diferentes algoritmos fundamentales para la gestión y manipulación de estructuras de datos, incluyendo matrices, árboles binarios y grafos, utilizando el lenguaje Java. Entre los módulos cubiertos se encuentran:

- **Manejo de Matrices:** Operaciones fundamentales de matrices (transposición, recorrido, búsqueda).
- **Árboles Binarios y AVL:** Inserción, recorrido, inversión, cálculo de profundidad, auto-balanceo y visualización textual.
- **Grafos:** Representación mediante listas de adyacencia o matrices, recorrido BFS y DFS, búsqueda de caminos, detección de ciclos, entre otros.
- **Pruebas Unitarias:** Validación de la funcionalidad de cada estructura mediante JUnit.

El sistema incluye:

- Clases modelo para representar cada estructura de datos
- Controladores para manejo de operaciones específicas
- Métodos de recorrido y análisis para árboles y grafos
- Pruebas unitarias con JUnit para verificar la correcta implementación
- Visualización textual para facilitar el análisis de cada estructura

## 🚀 Ejecución

Para ejecutar el proyecto:

1. Asegúrate de tener **Java JDK 11** o superior instalado.
2. Compila el proyecto desde la raíz:
   ```sh
   javac -cp ".:lib/*" src/main/App.java
   ```
3. Ejecuta la aplicación principal:
   ```sh
   java -cp ".:lib/*:src" main.App
   ```

Para ejecutar las pruebas unitarias:
```sh
java -cp ".:lib/*:src" org.junit.runner.JUnitCore test.[NombreDelTest]
```
> Cambia `[NombreDelTest]` por el nombre de la clase de prueba correspondiente.

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── App.java                             # Clase principal
│   ├── Matrices/
│   │   └── MatrixOperations.java           # Operaciones sobre matrices
│   ├── Ejercicio_01_insert/
│   │   └── InsertBST.java                  # Inserción en BST
│   ├── Ejercicio_02_invert/
│   │   └── InvertBinaryTree.java           # Inversión de árbol
│   ├── Ejercicio_03_listLeves/
│   │   └── ListLevels.java                 # Listado por niveles
│   ├── Ejercicio_04_depth/
│   │   └── Depth.java                      # Cálculo de profundidad
│   ├── Grafos/
│   │   ├── Graph.java                      # Representación y operaciones de grafos
│   │   └── GraphAlgorithms.java            # BFS, DFS, búsqueda de caminos, ciclos
│   └── Materia/
│       ├── Controllers/
│       │   ├── AVLTree.java                # Árbol AVL auto-balanceado
│       │   ├── ArbolBinario.java           # Árbol binario básico
│       │   └── ArbolRecorridos.java        # Métodos de recorrido
│       └── Models/
│           └── Node.java                   # Clase nodo del árbol
└── test/
    └── [Pruebas unitarias para cada ejercicio]
```

## 🧪 Funcionalidades Implementadas

**1. Operaciones con Matrices**
- Transposición y recorrido de matrices
- Búsqueda de elementos
- Complejidad: O(n²) para operaciones básicas sobre matrices cuadradas

**2. Árboles Binarios y AVL**
- Inserción recursiva y balanceo automático (AVL)
- Recorridos in-order, pre-order, post-order y por niveles (BFS)
- Inversión de árbol y cálculo de profundidad
- Visualización textual de la estructura
- Complejidad: O(log n) promedio (AVL), O(n) peor caso (BST desbalanceado)

**3. Grafos**
- Implementación con lista de adyacencia o matriz de adyacencia
- Recorridos BFS y DFS
- Búsqueda de caminos y detección de ciclos
- Visualización y análisis de conectividad
- Complejidad: O(V+E) para recorridos, donde V = vértices, E = aristas

## 🧪 Ejemplo de Salida por Consola

```
👤 Estudiante: [Nombre]
✉️  Correo: [correo@ejemplo.com]

* Suma de Matrices:
[1 2]   [3 4]   [4 6]
[5 6] + [7 8] = [12 14]

* Nodo a insertar en árbol: 10
├── 10
│   ├── null
│   └── null
Nodo insertado: 10, Balance: 0

* Recorrido DFS de grafo desde nodo 1:
1 -> 2 -> 5 -> 3 -> 4
```

## 📊 Análisis de Complejidad

| Algoritmo              | Tiempo Promedio | Tiempo Peor Caso | Espacio      |
|------------------------|-----------------|------------------|--------------|
| Operaciones Matrices   | O(n²)           | O(n²)            | O(n²)        |
| Inserción BST/AVL      | O(log n)        | O(n)             | O(h)         |
| Inversión Árbol        | O(n)            | O(n)             | O(h)         |
| Listado por Niveles    | O(n)            | O(n)             | O(n)         |
| Profundidad Árbol      | O(n)            | O(n)             | O(h)         |
| BFS/DFS en Grafos      | O(V+E)          | O(V+E)           | O(V)         |

> n = dimensión de la matriz, h = altura del árbol, V = vértices, E = aristas

## ✅ Conclusiones

La implementación de estas estructuras y algoritmos demuestra la relevancia de las estructuras de datos fundamentales en el desarrollo de soluciones eficientes:

- Las matrices permiten modelar y resolver problemas matemáticos y gráficos.
- Los árboles binarios y AVL ofrecen almacenamiento jerárquico y búsquedas eficientes.
- Los grafos son esenciales para modelar relaciones entre entidades y resolver problemas de conectividad.
- La correcta elección de la estructura y el algoritmo impacta directamente en el rendimiento de las aplicaciones.

## 🔧 Dependencias

- Java JDK 11+
- JUnit 5 para pruebas unitarias
- No se requieren dependencias externas adicionales

## 📝 Notas Adicionales

- Todos los algoritmos incluyen validación de entrada
- Las pruebas unitarias cubren casos base y casos límite
- La visualización textual facilita la comprensión de las estructuras
- El código sigue principios de programación orientada a objetos
