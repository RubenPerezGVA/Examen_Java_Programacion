package clases;

public abstract class Criatura {
    private final String nombre;
    private int nivel;
    private final int psMax;
    private int psActual;
    private int ataque;
    private int defensa;

    protected Criatura(String nombre, int nivel, int psMax, int ataque, int defensa) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        if (nivel < 1) throw new IllegalArgumentException("Nivel mínimo 1");
        if (psMax < 1) throw new IllegalArgumentException("PS max debe ser >= 1");

        this.nombre = nombre.trim();
        this.nivel = nivel;
        this.psMax = psMax;
        this.psActual = psMax;
        this.ataque = Math.max(1, ataque);
        this.defensa = Math.max(0, defensa);
    }

    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getPsMax() { return psMax; }
    public int getPsActual() { return psActual; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }

    protected void setNivel(int nivel) { this.nivel = Math.max(1, nivel); }
    protected void setAtaque(int ataque) { this.ataque = Math.max(1, ataque); }
    protected void setDefensa(int defensa) { this.defensa = Math.max(0, defensa); }
    protected void setPsActual(int psActual) {
        this.psActual = Math.max(0, Math.min(psMax, psActual));
    }

    public boolean estaDebilitada() {
        return psActual <= 0;
    }

    public void curarCompleto() {
        this.psActual = psMax;
    }

    public void recibirDanio(int cantidad) {
        if (cantidad < 0) cantidad = 0;
        setPsActual(psActual - cantidad);
    }

    public String estado() {
        return String.format(
                "%s [%s] Nv.%d PS %d/%d ATK %d DEF %d",
                nombre, getTipo(), nivel, psActual, psMax, ataque, defensa
        );
    }

    public abstract String getTipo();          // "FUEGO", "AGUA", "PLANTA", "ELECTRICO"
    public abstract int atacar(Criatura obj);  // devuelve daño infligido
}