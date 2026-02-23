import clases.*;
import interfaces.Entrenable;
import interfaces.Evolucionable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Criatura> criaturas = new ArrayList<>();

        // Demo inicial (opcional)
        criaturas.add(new ElectricoMon("Pika", 5, calcPs(5), calcAtk(5), calcDef(5)));
        criaturas.add(new Aguamon("Aqua", 4, calcPs(4), calcAtk(4), calcDef(4)));

        int opcion;
        do {
            System.out.println("\n=== MiniLiga de Criaturas ===");
            System.out.println("1. Crear criatura");
            System.out.println("2. Listar criaturas");
            System.out.println("3. Combatir");
            System.out.println("4. Entrenar criatura");
            System.out.println("5. Evolucionar criatura");
            System.out.println("6. Curar criatura");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = leerEntero(sc, -1);

            switch (opcion) {
                case 1 -> crearCriatura(sc, criaturas);
                case 2 -> listarCriaturas(criaturas);
                case 3 -> combatir(sc, criaturas);
                case 4 -> entrenar(sc, criaturas);
                case 5 -> evolucionar(sc, criaturas);
                case 6 -> curar(sc, criaturas);
                case 0 -> System.out.println("Adiós.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    private static int calcPs(int nivel) { return 12 + nivel * 2; }
    private static int calcAtk(int nivel) { return 4 + nivel; }
    private static int calcDef(int nivel) { return 2 + (nivel / 2); }

    private static void crearCriatura(Scanner sc, List<Criatura> criaturas) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isBlank()) {
            System.out.println("Nombre vacío. Cancelado.");
            return;
        }

        System.out.println("Tipo (escribe exactamente): FUEGO / AGUA / PLANTA / ELECTRICO");
        System.out.print("Tipo: ");
        String tipo = sc.nextLine().trim().toUpperCase();

        if (!tipo.equals("FUEGO") && !tipo.equals("AGUA") && !tipo.equals("PLANTA") && !tipo.equals("ELECTRICO")) {
            System.out.println("Tipo inválido. Cancelado.");
            return;
        }

        System.out.print("Nivel inicial (1-10): ");
        int nivel = leerEntero(sc, 1);
        if (nivel < 1) nivel = 1;
        if (nivel > 10) nivel = 10;

        int ps = calcPs(nivel);
        int atk = calcAtk(nivel);
        int def = calcDef(nivel);

        Criatura c = switch (tipo) {
            case "FUEGO" -> new FuegoMon(nombre, nivel, ps, atk, def);
            case "AGUA" -> new Aguamon(nombre, nivel, ps, atk, def);
            case "PLANTA" -> new PlantaMon(nombre, nivel, ps, atk, def);
            case "ELECTRICO" -> new ElectricoMon(nombre, nivel, ps, atk, def);
            default -> null;
        };

        criaturas.add(c);
        System.out.println("Creada: " + c.estado());
    }

    private static void listarCriaturas(List<Criatura> criaturas) {
        if (criaturas.isEmpty()) {
            System.out.println("No hay criaturas.");
            return;
        }
        System.out.println("\n--- Lista ---");
        for (int i = 0; i < criaturas.size(); i++) {
            System.out.printf("%d) %s%n", i, criaturas.get(i).estado());
        }
    }

    private static void combatir(Scanner sc, List<Criatura> criaturas) {
        if (criaturas.size() < 2) {
            System.out.println("Necesitas al menos 2 criaturas.");
            return;
        }
        listarCriaturas(criaturas);

        System.out.print("Índice atacante: ");
        int iAt = leerEntero(sc, -1);
        System.out.print("Índice defensor: ");
        int iDef = leerEntero(sc, -1);

        if (iAt < 0 || iAt >= criaturas.size() || iDef < 0 || iDef >= criaturas.size() || iAt == iDef) {
            System.out.println("Índices inválidos.");
            return;
        }

        Criatura a = criaturas.get(iAt);
        Criatura d = criaturas.get(iDef);

        if (a.estaDebilitada() || d.estaDebilitada()) {
            System.out.println("Una de las criaturas está debilitada. Cura antes de combatir.");
            return;
        }

        System.out.println("\n=== COMBATE ===");
        System.out.println("A: " + a.estado());
        System.out.println("D: " + d.estado());

        boolean turnoA = true;
        while (!a.estaDebilitada() && !d.estaDebilitada()) {
            if (turnoA) {
                int dano = a.atacar(d);
                System.out.printf("%s ataca a %s e inflige %d daño.%n", a.getNombre(), d.getNombre(), dano);
                System.out.println("Defensor -> " + d.estado());
            } else {
                int dano = d.atacar(a);
                System.out.printf("%s ataca a %s e inflige %d daño.%n", d.getNombre(), a.getNombre(), dano);
                System.out.println("Atacante -> " + a.estado());
            }
            turnoA = !turnoA;
        }

        System.out.println(a.estaDebilitada() ? "Gana " + d.getNombre() + "!" : "Gana " + a.getNombre() + "!");
    }

    private static void entrenar(Scanner sc, List<Criatura> criaturas) {
        if (criaturas.isEmpty()) { System.out.println("No hay criaturas."); return; }
        listarCriaturas(criaturas);

        System.out.print("Índice a entrenar: ");
        int idx = leerEntero(sc, -1);
        if (idx < 0 || idx >= criaturas.size()) { System.out.println("Índice inválido."); return; }

        Criatura c = criaturas.get(idx);
        if (c instanceof Entrenable e) {
            e.entrenar();
            System.out.println("Entrenada: " + c.estado());
        }
    }

    private static void evolucionar(Scanner sc, List<Criatura> criaturas) {
        if (criaturas.isEmpty()) { System.out.println("No hay criaturas."); return; }
        listarCriaturas(criaturas);

        System.out.print("Índice a evolucionar: ");
        int idx = leerEntero(sc, -1);
        if (idx < 0 || idx >= criaturas.size()) { System.out.println("Índice inválido."); return; }

        Criatura c = criaturas.get(idx);
        if (c instanceof Evolucionable e) {
            if (!e.puedeEvolucionar()) {
                System.out.println("No puede evolucionar aún (nivel insuficiente).");
                return;
            }
            Criatura evo = e.evolucionar();
            criaturas.set(idx, evo);
            System.out.println("Evolución completada: " + evo.estado());
        }
    }

    private static void curar(Scanner sc, List<Criatura> criaturas) {
        if (criaturas.isEmpty()) { System.out.println("No hay criaturas."); return; }
        listarCriaturas(criaturas);

        System.out.print("Índice a curar: ");
        int idx = leerEntero(sc, -1);
        if (idx < 0 || idx >= criaturas.size()) { System.out.println("Índice inválido."); return; }

        Criatura c = criaturas.get(idx);
        c.curarCompleto();
        System.out.println("Curada: " + c.estado());
    }

    private static int leerEntero(Scanner sc, int porDefecto) {
        String s = sc.nextLine().trim();
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return porDefecto; }
    }
}