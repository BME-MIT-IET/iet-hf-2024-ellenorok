package model;

import java.io.Serializable;

/**
 * Absztrakt osztaly, mely definialja a Saboteur es a Mechanic kozos viselkedeset: mozgas a halozaton es annak
 * nyilvantartasa, hogy milyen mezon all
 */
public abstract class Player implements Serializable {
    /**
     * Azt jelzi, hogy melyik mezon all a jatekos az adott pillanatban
     */
    protected Field activeField;

    protected Player(){}

    /**
     * Megprobalja eltorni azt a mezot, amin eppen all
     */
    public void breakField() {
        activeField.breakField();
    }

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void repairField(){}

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void pickup(){}

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void place(){}

    /**
     * A parameterkent kapott mezore atlep. Kiveszi magat (removePlayer()) a jelenlegi mezojenek listajabol
     * majd csokkenti a korben hatralevo akciok szamat 1-el
     * @param f A mezo, amire at szeretne lepni
     * @see Field
     */
    public void swapField(Field f){
        activeField.removePlayer(this);
        activeField = f;
        Game.getInstance().actionTaken();
    }

    /**
     * A parameterkent kapott mezore megprobal atlepni, ha nem ragados a csove
     * @param f A mezo, amire at szeretne lepni
     * @see Field
     */
    public void moveToField(Field f) {
        if(Game.getInstance().getActionNumber() > 0 && !activeField.isSticky())
                f.accept(this);
    }

    /**
     * Megprobalja atallitani a jelenlegi aktiv mezojen a bemeneti es kimeneti csoveket (ha ez lehetseges)
     * @param in A kivant bemeneti cso
     * @param out A kivant kimeneti cso
     * @see Pipe
     */
    public void changePumpDirection(Pipe in, Pipe out) {
        if(in != null && out != null)
            activeField.changePumpDirection(in, out);
    }

    /**
     * Visszaadja a jelenlegi aktiv mezojet
     * @return
     */
    public Field getActiveField() { return activeField; }

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void makeSlippery() {}

    /**
     * A jelenlegi aktiv mezo ragadossagat allitja be
     */
    public void makeSticky() {
        activeField.makeSticky();
    }

    /**
     * Beallitja az aktiv mezojet
     * @param f A beallitando mezo
     */
    public void  setActiveField(Field f) {
        activeField = f;
    }
}
