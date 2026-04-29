# Sudoku Studio

Sudoku Studio es una aplicación de escritorio desarrollada en **Java Swing** que permite jugar una partida de Sudoku desde una interfaz gráfica. El proyecto maneja un tablero `9x9`, diferencia entre celdas fijas y celdas editables, valida el estado del juego y permite reiniciar o limpiar los valores ingresados por el jugador.

## Características

- Interfaz gráfica construida con **Swing**.
- Tablero de Sudoku `9x9` con separación visual por subcuadrículas `3x3`.
- Índices visibles para filas y columnas.
- Celdas fijas protegidas para evitar modificaciones.
- Ingreso de números usando el teclado numérico o las teclas `1` a `9`.
- Eliminación de números con `Backspace` o desde el panel de controles.
- Validación del estado del juego:
  - No iniciado.
  - Incompleto.
  - Completo.
- Detección visual de errores cuando un número ingresado no coincide con la solución esperada.
- Opción para limpiar el tablero conservando los números iniciales.
- Opción para finalizar el juego solo cuando el Sudoku está correctamente resuelto.

## Tecnologías utilizadas

- **Java 21**
- **Java Swing**
- **AWT**

## Estructura del proyecto

```text
src/
├── Main.java
├── model/
│   ├── Board.java
│   ├── GameStatusEnum.java
│   └── Space.java
├── service/
│   └── GameService.java
├── ui/
│   └── custom/
│       ├── frame/
│       │   └── MainFrame.java
│       ├── input/
│       │   ├── NumberText.java
│       │   └── NumberTextLimit.java
│       ├── panel/
│       │   ├── BoardPanel.java
│       │   └── ControlPanel.java
│       └── screen/
│           └── MainScreen.java
└── util/
    └── BoardTemplate.java
```

## Descripción de paquetes

### `model`

Contiene las clases principales del dominio del juego.

- `Board`: representa el tablero completo y contiene la lógica para cambiar valores, limpiar celdas, reiniciar el tablero, consultar el estado y verificar si el juego terminó correctamente.
- `Space`: representa una celda del Sudoku, indicando su valor actual, el valor esperado y si es una celda fija.
- `GameStatusEnum`: define los posibles estados del juego: `NON_STARTED`, `INCOMPLETE` y `COMPLETE`.

### `service`

Contiene la capa de servicio del juego.

- `GameService`: coordina las operaciones principales entre la interfaz gráfica y el modelo, como iniciar una partida, colocar números, remover números, limpiar el tablero y validar el estado.

### `ui.custom`

Contiene los componentes gráficos personalizados.

- `MainFrame`: ventana principal de la aplicación.
- `MainScreen`: pantalla principal donde se conectan el tablero, los controles y la lógica del juego.
- `BoardPanel`: panel encargado de renderizar el tablero `9x9`.
- `ControlPanel`: panel lateral con botones de control.
- `NumberText` y `NumberTextLimit`: componentes para limitar entradas numéricas.

### `util`

Contiene clases auxiliares.

- `BoardTemplate`: define la solución base del Sudoku y las posiciones fijas iniciales.

## Cómo ejecutar el proyecto

### 1. Clonar o descargar el proyecto

```bash
git clone <url-del-repositorio>
cd <nombre-del-proyecto>
```

Si descargaste el proyecto como `.zip`, descomprímelo y entra a la carpeta raíz del proyecto.

### 2. Compilar

Desde la raíz del proyecto, ejecuta:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

### 3. Ejecutar

```bash
java -cp out Main
```

## Ejecución en Windows

En PowerShell puedes compilar así:

```powershell
New-Item -ItemType Directory -Force out
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
java -cp out Main
```

## Cómo jugar

1. Haz clic en **Iniciar / reiniciar** para cargar el tablero base.
2. Selecciona una celda editable del tablero.
3. Presiona una tecla del `1` al `9` para colocar un número.
4. Usa **Backspace** o el botón **Remover número** para borrar un número ingresado.
5. Usa **Limpiar tablero** para eliminar todos los números agregados por el jugador y conservar los números fijos.
6. Haz clic en **Finalizar juego** cuando el tablero esté completo.

## Reglas implementadas

- Solo se permiten números del `1` al `9`.
- Las celdas fijas no se pueden modificar ni eliminar.
- El tablero se considera completo cuando todas las celdas tienen un valor.
- El juego solo puede finalizar si el tablero está completo y no contiene errores.
- Un error ocurre cuando una celda editable tiene un valor distinto al valor esperado definido en la solución.

## Tablero base

El tablero se genera desde la clase `BoardTemplate`, donde se definen:

- `SOLUTION`: matriz con la solución completa del Sudoku.
- `FIXED`: matriz booleana que indica cuáles posiciones aparecen como números iniciales.

Ejemplo conceptual:

```java
private static final int[][] SOLUTION = {
    {5, 3, 4, 6, 7, 8, 9, 1, 2},
    {6, 7, 2, 1, 9, 5, 3, 4, 8},
    {1, 9, 8, 3, 4, 2, 5, 6, 7},
    // ...
};
```

## Posibles mejoras futuras

- Agregar generación aleatoria de tableros.
- Permitir seleccionar niveles de dificultad.
- Validar conflictos por fila, columna y subcuadrícula en lugar de comparar únicamente contra la solución esperada.
- Agregar modo de notas o números candidatos.
- Guardar y cargar partidas.
- Añadir contador de tiempo.
- Añadir pruebas unitarias para la lógica del tablero.

## Autor

Proyecto desarrollado como práctica de programación en Java.
