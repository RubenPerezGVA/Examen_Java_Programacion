package clases;

public final class Efectividad {
    private Efectividad() {}

    public static double multiplicador(String atacante, String defensor) {
        // Normalizamos por seguridad
        atacante = atacante.toUpperCase();
        defensor = defensor.toUpperCase();

        // Fuertes (x2)
        if (atacante.equals("FUEGO") && defensor.equals("PLANTA")) return 2.0;
        if (atacante.equals("AGUA") && defensor.equals("FUEGO")) return 2.0;
        if (atacante.equals("PLANTA") && defensor.equals("AGUA")) return 2.0;
        if (atacante.equals("ELECTRICO") && defensor.equals("AGUA")) return 2.0;

        // Débiles (x0.5)
        if (atacante.equals("FUEGO") && defensor.equals("AGUA")) return 0.5;
        if (atacante.equals("AGUA") && defensor.equals("PLANTA")) return 0.5;
        if (atacante.equals("PLANTA") && defensor.equals("FUEGO")) return 0.5;
        if (atacante.equals("ELECTRICO") && defensor.equals("PLANTA")) return 0.5;

        return 1.0;
    }
}