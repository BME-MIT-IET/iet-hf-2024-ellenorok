package display;

import model.Field;
import model.Pipe;
import model.PlayerIcon;

import java.util.ArrayList;

/**
 * Absztrakt ősosztály a megjelenítendő Field leszármazott elemek megjelenített objektumaihoz.
 */
public abstract class DisplayField {
    /**
     * A megjelenített mezőn tartózkodó játékosok ikonjai.
     */
    protected java.util.List<PlayerIcon> playerIcons = new ArrayList<>();

    /**
     * Beállítja a rajta tartózkodó játékosok ikonjait (playerIcons).
     */
    protected abstract void setPlayerIcons();

    /**
     * Visszaadja azt a Field típusú objektumot, melyet reprezentál.
     * @return Field a reprezentált objektum
     */
    public abstract Field getGameReference();

    /**
     * Felrajzolja magát a paraméterként kapott grafikus objektumra.
     * @param g a grafikus objektum
     */
    public abstract void draw(java.awt.Graphics g);

    /**
     * true-val tér vissza, ha az (x,y) pont benne van abban a területben,
     * amire ő ki van rajzolva, egyébként false-al.
     * @param x a pont x koordinátája
     * @param y a pont y koordinátája
     * @return
     */
    public abstract boolean on(int x, int y);

    /**
     * Azt a szöveget adja vissza, amely megjelenik egy tooltip-ben, ha az objektum fölé visszük az egeret.
     * @return a kiírandó szöveg
     */
    public abstract String getTooltipText();

    /**
     * Azert kell mert van ahol nem eleg ha abstract osztalyt adunk hanem kell pipe osztaly
     * @return az objektum által reprezentált logika
     */
    public Pipe getPipe() {return null;}
}
