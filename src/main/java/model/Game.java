package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tarolja a jatek mindenkori allapotat, iranyitja azt. Ebbe bele kell erteni a jatekosok listajanak jegyzeset,
 * a mezok szamontartasat, az eppen soron levo jatekos es a csapatok pontjainak szamontartasat
 */
public class Game implements Serializable {
    final int ROUND_BEGIN_ACTION_NUMBER = 3;
    /**
     * Az osztaly egyetlen peldanya
     */
    private static Game instance;

    /**
     * A mezoket szamontarto lista
     */
    private ArrayList<Field> fields = new ArrayList<>();

    /**
     * A jatekosokat szamontarto lista
     */
    private ArrayList<Player> players = new ArrayList<>();

    /**
     * A játékban szereplő szabotőrök
     */
    private List<Saboteur> saboteurs = new ArrayList<>();
    /**
     * A játékban szereplő szerelők
     */
    private List<Mechanic> mechanics = new ArrayList<>();

    private List<Pump> pumps = new ArrayList<>();
    private List<Cistern> cisterns = new ArrayList<>();
    private List<Source> sources = new ArrayList<>();
    private List<Pipe> pipes = new ArrayList<>();

    /**
     * Az aktiv jatekos forgatasahoz kell
     */
    private int currentIndex = 0;

    /**
     * A jelenleg aktiv jatekos referenciaja
     */
    private Player activePlayer;

    /**
     * A szabotor jatekosok pontjai
     */
    private int saboteurPoints;

    /**
     * A szerelo jatekosok pontjai
     */
    private int mechanicPoints;

    /**
     * Ebben a korben meg hany akciot tud vegrehajtani az aktualis jatekos
     */
    private int actionNumber;

    /**
     * A random faktor állapota, ki-be lehet kapcsolgatni
     */
    private boolean random = true;

    private void init() {
        actionNumber = ROUND_BEGIN_ACTION_NUMBER;
    }

    /**
     * Visszaadja az osztaly egyetlen peldanyat
     * @return Az osztaly egyetlen peldanya
     */
    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
            instance.init();
        }
        return instance;
    }
    public int getMechanicPoints() {
        return mechanicPoints;
    }

    public int getSaboteurPoints() {
        return saboteurPoints;
    }

    /**
     * Visszaadja az aktív játékos referenciáját
     * @return
     */
    public Player getActivePlayer() {
        return activePlayer;
    }
    /**
     * A jatekot indito metodus
     */
    public void play() {
        currentIndex = (currentIndex + 1) % players.size();
        activePlayer = players.get(currentIndex);
        actionNumber = ROUND_BEGIN_ACTION_NUMBER;
    }

    /**
     * Megallitja a jatekot
     */
    public void end() {
    }

    /**
     * A kor valtast megkezdo metodus. Kor valtaskor a periodikus palyaelemek lepnek egyet, az allapotos metodusok
     * allapotot valtoztatnak, majd a soron kovetkezo jatekos lesz aktiv
     */
    public void endTurn() {
        Timer.getInstance().turn();
        currentIndex = (currentIndex + 1) % players.size();
        activePlayer = players.get(currentIndex);
        actionNumber = ROUND_BEGIN_ACTION_NUMBER;
    }

    /**
     * Ezt a metodust meghivva a jelenlegi aktiv jatekos tonkreteszi az eppen aktualis helyzetenek megfelelo mezot
     * (amennyiben ez lehetseges)
     */
    public void breakField() {
        if(actionNumber <= 0) return;
        activePlayer.breakField();
    }

    /**
     * Ezt a metodust meghivva a jelenlegi aktiv jatekos megjavitja az eppen aktualis helyzetenek megfelelo mezot
     * (amennyiben ez lehetseges)
     */
    public void repairField() {
        if(actionNumber <= 0) return;
        activePlayer.repairField();
    }

    /**
     * Az aktiv jatekos lehelyezi a nala levo pumpat (ha ez lehetseges)
     */
    public void place() {
        if(actionNumber <= 0) return;
        activePlayer.place();
    }

    /**
     * Az aktiv karakter felvesz egy pumpat (ha ez lehetseges)
     */
    public void pickup() {
        if(actionNumber <= 0) return;
        activePlayer.pickup();
    }

    /**
     * Csokkenti a korben hatralevo akciok szamat
     */
    public void actionTaken() {
        actionNumber--;
    }

    /**
     * Pontot ad a szereloknek
     */
    public void addMechanicPoints() {
        mechanicPoints++;
    }

    /**
     * Pontot ad a szabotoroknek
     */
    public void addSaboteurPoints() {
        saboteurPoints++;
    }

    /**
     * Megvhivja az eppen aktiv jatekos changePumpDirection(Pipe in, Pipe out) fuggvenyet a ket kapott parameterrel
     * @param in A kivant bemeneti cso
     * @param out A kivant kimeneti cso
     * @see Pipe
     */
    public void changePumpDirection(Pipe in, Pipe out) {
        if(actionNumber <= 0) return;
        activePlayer.changePumpDirection(in, out);
    }

    /**
     * Atallitja a kivalasztott cso (p) egyik veget (oldEnd) egy masik helyre (newEnd)
     * Ha a newEnd parameter null, a cso veget sehova sem koti
     * @param p
     * @param oldEnd
     * @param newEnd
     * @see Pipe
     * @see Field
     */
    public void movePipe(Pipe p, Field oldEnd, Field newEnd) {
        if(actionNumber <= 0) return;

        //if old end is null and one edge is hanging than put this in new end
        if(oldEnd == null && p.getNeighbours().size() < 2) {
            p.addNeighbor(newEnd);
            newEnd.addNeighbor(p);
            this.actionTaken();
            return;
        }

        //the field that needs to be changed is not neighbouring
        if(!p.getNeighbours().contains(oldEnd)) {
            System.out.println("Old end is not neighbour!!!!!!");
            return;
        }

        //the field that needs to be plugged is already a neighbour
        if(p.getNeighbours().contains(newEnd)) {
            System.out.println("New end is neighbour!!!!!!");
            return;
        }

        if(!oldEnd.equals(newEnd)) {
            oldEnd.removeNeighbor(p);
            p.removeNeighbor(oldEnd);
            //if new end is null then the player unplugs the pipe
            if(newEnd != null) {
                p.addNeighbor(newEnd);
                newEnd.addNeighbor(p);
            }
            this.actionTaken();
        }
        else System.out.println("New end is same as old end!!!!!!");
    }

    /**
     * Meghívja az activePlayer tagváltozón a makeSlippery() metódust
     */
    public void makeSlippery() {
        if(actionNumber <= 0) return;
        activePlayer.makeSlippery();
    }

    /**
     * Meghívja az activePlayer tagváltozón a makeSticky() metódust
     */
    public void makeSticky() {
        if(actionNumber <= 0) return;
        activePlayer.makeSticky();
    }

    /**
     * Beallitja az aktiv jatekost
     * @param p A beallitando jatekos
     */
    public void setActivePlayer(Player p) {
        activePlayer = p;
    }

    /**
     * Beallitja a hatralevo akciok szamat
     * @param number A beallitando szam
     */
    public void setActionNumber(int number) {
        actionNumber = number;
    }

    /**
     * Visszaadja a hatralevo akciok szamat
     * @return A hatralevo akciok szama
     */
    public int getActionNumber() { return actionNumber; }

    /**
     * Visszaadja a random faktort
     * @return A random faktor erteke
     */
    public boolean getRandom() { return random; }

    /**
     * Beallitja a random faktort
     * @param b A beallitando ertek
     */
    public void setRandom(boolean b){ random = b; }

    public final List<Saboteur> getSaboteurs() {
        return saboteurs;
    }
    public final List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void addPlayer(Mechanic m) {
        players.add(m);
        mechanics.add(m);
    }

    public void addPlayer(Saboteur s) {
        players.add(s);
        saboteurs.add(s);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        mechanics.remove(p);
        saboteurs.remove(p);
    }

    public final List<Cistern> getCisterns() {
        return cisterns;
    }
    public final List<Pump> getPumps() {
        return pumps;
    }
    public final List<Pipe> getPipes() {
        return pipes;
    }
    public final List<Source> getSources() {
        return sources;
    }
    public void addField(Field f) {
        fields.add(f);
    }
    public void addField(Pump p) {
        pumps.add(p);
        fields.add(p);
    }
    public void addField(Cistern c) {
        cisterns.add(c);
        fields.add(c);
    }
    public void addField(Source s) {
        sources.add(s);
        fields.add(s);
    }
    public void addField(Pipe p) {
        pipes.add(p);
        fields.add(p);
    }
    public void removeField(Field f) {
        fields.remove(f);
        pipes.remove(f);
        sources.remove(f);
        cisterns.remove(f);
        pumps.remove(f);
    }
}
