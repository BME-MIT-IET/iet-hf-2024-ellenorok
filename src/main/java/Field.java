package iet.hf.ellenorok;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt osztaly, melynek funkcioja, hogy osszegyujtse azokat az objektumokat, amire a jatekosok ra tudnak lepni
 */
public abstract class Field implements Serializable {
    /**
     * Szamontartja a szomszedos mezoket
     */
    protected List<Field> neighbors;

    /**
     * Szamontartja, hogy milyen jatekosok allnak rajta
     */
    protected List<Player> players;

    /**
     * Visszaadja a szomszedos mezoket
     * @return A szomszedos mezok listaja
     */
    public List<Field> getNeighbours(){
        return neighbors;
    }

    public List<Player> getPlayers() {
        return  players;
    }

    public Field(){
        neighbors = new ArrayList<Field>();
        players = new ArrayList<Player>();
    }

    /**
     * Amennyiben lephet ra a parameterul kapott jatekos, lepteti ot es hozzaadja a players listahoz
     * @param p A jatekos, aki ra akar lepni
     * @see Player
     */
    public void accept(Player p) {
        Field oldfield = p.getActiveField();
        if(neighbors.contains(oldfield)) {
            p.swapField(this);
            players.add(p);
        }
    }

    /**
     * Felveszi a parameterkent kapott jatekost a players listaba
     * @param p A hozaadni kivant jatekos
     * @see Player
     */
    public void addPlayer(Player p) {
        if(!players.contains(p)) {
            players.add(p);
        }
    }

    /**
     * A parameterul kapott jatekost kiveszi a players listabol
     * @param p Az elvenni kivant jatekos
     * @see Player
     */
    public void removePlayer(Player p) {
        players.remove(p);
    }

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     * @param in A beallitando bemenet
     * @param out A beallitando kimenet
     * @see Pipe
     */
    public void changePumpDirection(Pipe in, Pipe out) {}

    /**
     * A parameterul kapott mezot hozzaveszi a szomszedok listajahoz
     * @param f A hozzaadni kivant mezo
     */
    public void addNeighbor(Field f) {
        if(!neighbors.contains(f)) {
            neighbors.add(f);
        }
    }

    /**
     * A parameterkent kapott mezot eltavolitja a szomszedok listajabol
     * @param f Az eltavolitani kivant szomszed
     */
    public void removeNeighbor(Field f) {
        neighbors.remove(f);
    }

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     * @param m A jatekos, aki lerakna a pumpat
     * @return
     * @see Mechanic
     */
    public void placePump(Mechanic m) {}

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     * @param m A jatekos, aki felvenne a pumpat
     * @see Mechanic
     */
    public void pickupPump(Mechanic m) {}

    /**
     * A mezot nem lehet megjavitani, nem tortenik semmi
     */
    public void repair() {}

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void _break() {}

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void makeSlippery() {}

    /**
     * Ures implementacio, a megfelelo leszarmazottakban van implementalva
     */
    public void makeSticky() {}

    /**
     * A metodus implementacioja ebben az osztalyban mindig False-szal ter vissza
     * @return A mezo ragadossaga
     */
    public boolean isSticky() {
        return false;
    }
}
