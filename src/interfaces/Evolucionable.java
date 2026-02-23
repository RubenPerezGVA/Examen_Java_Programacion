package interfaces;

import clases.Criatura;

public interface Evolucionable {
    boolean puedeEvolucionar();
    Criatura evolucionar();
}