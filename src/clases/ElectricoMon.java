package clases;

import interfaces.Entrenable;
import interfaces.Evolucionable;

public class ElectricoMon extends Criatura implements Entrenable, Evolucionable {

    public ElectricoMon(String nombre, int nivel, int psMax, int ataque, int defensa) {
        super(nombre, nivel, psMax, ataque, defensa);
    }

    @Override
    public String getTipo() { return "ELECTRICO"; }

    @Override
    public int atacar(Criatura objetivo) {
        if (objetivo == null) throw new IllegalArgumentException("Objetivo null");
        if (estaDebilitada()) return 0;

        int danoBase = getAtaque() - (objetivo.getDefensa() / 2);
        if (danoBase < 1) danoBase = 1;

        double mult = Efectividad.multiplicador(getTipo(), objetivo.getTipo());
        int danoFinal = (int) Math.round(danoBase * mult);

        objetivo.recibirDanio(danoFinal);
        return danoFinal;
    }

    @Override
    public void entrenar() {
        setNivel(getNivel() + 1);
        setAtaque(getAtaque() + 2);
        setDefensa(getDefensa() + 1);
        setPsActual(getPsActual() + 2);
    }

    @Override
    public boolean puedeEvolucionar() { return getNivel() >= 8; }

    @Override
    public Criatura evolucionar() {
        if (!puedeEvolucionar()) return this;

        int nuevoPs = (int) Math.round(getPsMax() * 1.15);
        int nuevoAtk = getAtaque() + 4;
        int nuevoDef = getDefensa() + 1;

        ElectricoMon evo = new ElectricoMon(getNombre() + " Evo", getNivel(), nuevoPs, nuevoAtk, nuevoDef);
        evo.curarCompleto();
        return evo;
    }
}