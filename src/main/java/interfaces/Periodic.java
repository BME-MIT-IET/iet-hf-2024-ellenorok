package interfaces;

import java.io.Serializable;

/**
 * Lehetove teszi, hogy a halozat objektumainak korvegi teendoi sorban meghivodjanak. Ezalatt mindegyik tipusatol
 * fuggoen definialt egy viselkedest
 */
public interface Periodic extends Serializable {
    /**
     * Minden osztaly ami implementalja, ebbe irja le a (kor vegi) sajatos viselkedeset
     */
    public void step();
}
