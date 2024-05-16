package iet.hf.ellenorok;

import java.io.Serializable;

/**
 * Az allapottal rendelkezo objektumok allapotvaltasaert felel
 */
public interface Stateful extends Serializable {
    /**
     * Megvaltoztatja az objektum allapotat
     */
    public void changeState();
}
