# MiniLiga de Criaturas (Java, consola)

Proyecto de práctica/examen para **1º DAW** centrado en **Programación Orientada a Objetos (POO)**.

Simula un pequeño juego de criaturas que luchan por turnos con tipos elementales:

- `FUEGO`
- `AGUA`
- `PLANTA`
- `ELECTRICO`

> **Nota:** Aunque está inspirado en juegos de criaturas (tipo Pokémon), está planteado de forma genérica para que cualquier persona pueda implementarlo sin conocer esa temática.

---

## Objetivo didáctico

Este proyecto está diseñado para practicar y evaluar:

- **Encapsulación**
- **Clases abstractas**
- **Herencia**
- **Interfaces**
- **Polimorfismo**
- **Sobreescritura de métodos (override)**
- **Uso de colecciones (`ArrayList`)**
- **Menús por consola y validación básica de entradas**

---

## Funcionalidades

La aplicación permite:

1. **Crear criaturas**
2. **Listar criaturas**
3. **Combatir por turnos**
4. **Entrenar criaturas**
5. **Evolucionar criaturas**
6. **Curar criaturas**
7. **Salir del programa**

---

## Reglas del juego (resumen)

Cada criatura tiene:

- `nombre`
- `nivel`
- `psMax` (puntos de vida máximos)
- `psActual`
- `ataque`
- `defensa`
- `tipo` (como `String`)

### Tipos válidos (String)
- `"FUEGO"`
- `"AGUA"`
- `"PLANTA"`
- `"ELECTRICO"`

### Efectividad de tipos
- `FUEGO` > `PLANTA` (x2)
- `AGUA` > `FUEGO` (x2)
- `PLANTA` > `AGUA` (x2)
- `ELECTRICO` > `AGUA` (x2)

Y algunas debilidades:
- `FUEGO` < `AGUA` (x0.5)
- `AGUA` < `PLANTA` (x0.5)
- `PLANTA` < `FUEGO` (x0.5)
- `ELECTRICO` < `PLANTA` (x0.5)

En los demás casos, multiplicador **x1**.

---

## Fórmula de daño

Al atacar:

1. `dañoBase = ataque - (defensaObjetivo / 2)`
2. Si `dañoBase < 1`, entonces `dañoBase = 1`
3. Se aplica multiplicador por tipo (`0.5`, `1`, `2`)
4. `dañoFinal = round(dañoBase * multiplicador)`
5. Se resta al `psActual` del objetivo (sin bajar de 0)

---

## Estructura del proyecto

Ejemplo de archivos:

- `Main.java`
- `Criatura.java` *(clase abstracta)*
- `Entrenable.java` *(interfaz)*
- `Evolucionable.java` *(interfaz)*
- `Efectividad.java` *(utilidad para calcular multiplicador)*
- `FuegoMon.java`
- `AguaMon.java`
- `PlantaMon.java`
- `ElectricoMon.java`

---

## Diseño orientado a objetos

### 1. Clase abstracta `Criatura`
Contiene atributos y comportamiento común a todas las criaturas:
- nombre, nivel, vida, ataque, defensa
- métodos como:
  - `estaDebilitada()`
  - `curarCompleto()`
  - `recibirDanio(int)`
  - `estado()`

Además define métodos abstractos que las subclases deben implementar:
- `String getTipo()`
- `int atacar(Criatura objetivo)`

### 2. Interfaces
- `Entrenable`
  - `void entrenar()`
- `Evolucionable`
  - `boolean puedeEvolucionar()`
  - `Criatura evolucionar()`

### 3. Subclases
Cada subclase representa un tipo de criatura:
- `FuegoMon`
- `AguaMon`
- `PlantaMon`
- `ElectricoMon`

Cada una:
- devuelve su tipo con `getTipo()`
- implementa la lógica de ataque
- puede entrenar y evolucionar

---

## Cómo ejecutar

### Requisitos
- Java JDK 17+ (o versión usada en clase, por ejemplo JDK 11)

### Compilación (terminal)
```bash
javac *.java
