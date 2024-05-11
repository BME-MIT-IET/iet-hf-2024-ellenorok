package iet.hf.ellenorok;

import java.io.Serializable;

/**
 * Vegtelen vizforrasnak szamit. A kimeneten mindig folyik a viz
 */
public class Source extends Field implements Periodic, Serializable {
    /**
     * A kimeneti csove
     */
    private Pipe output;

    public Source(){}

    public Source(Pipe out){ output = out; }

    /**
     * Eltavolitja a parameterul kapott mezot a szomszedok kozul
     * @param f Az eltavolitando mezo
     */
    @Override
    public void removeNeighbor(Field f) {
        super.removeNeighbor(f);
        output = null;
    }

    /**
     * Hozaadja a parameterul kapott mezot a szomszedokhoz, tovabba beallitja kimenetnek
     * @param p A hozzaadando mezo
     */
    @Override
    public void addNeighbor(Field p) {
        super.addNeighbor(p);
        try {
            output = (Pipe) p;
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * A kimeneti csovere rak vizet
     */
    public void step() {
        if (output != null)
            output.setNewWaterState(true);
    }
}
