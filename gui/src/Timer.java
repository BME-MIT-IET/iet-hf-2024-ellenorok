import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A jatek koreinek lepteteseert felelos. Ismerve az osszes olyan objektumot, amit leptetni vagy allapotat valtoztatni
 * kell, sorban az elobbieken meghivja a step() metodust. Ezutan meghivja a changeState() metodust az utobbiakon
 * @see Periodic
 * @see Stateful
 */
public class Timer implements Serializable {
    /**
     * Az osztaly egyetlen peldanya
     */
    private static Timer instance;

    /**
     * Szamontartja az osszes olyan objektumot, ami megvalositja a Periodic interfeszt.
     * @see Periodic
     */
    private List<Periodic> periodics;

    /**
     * Szamontartja az osszes olyan objektumot, ami megvalositja a Stateful interfeszt.
     * @see Stateful
     */
    private List<Stateful> statefuls;

    /**
     * Visszaadja az osztaly egyetlen peldanyat
     * @return Az osztaly egyetlen peldanya
     */
    public static Timer getInstance() {
        if(instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    /**
     * Beállítja az osztály példányát a paraméterben kapott példányra
     * @param t Az új példány
     */
    public static void setInstance(Timer t) {
        instance = t;
    }

    /**
     * Torli az osztaly egyetlen peldanyat
     */
    public static void removeInstance() {
        instance = null;
    }

    private Timer() {
        periodics = new ArrayList<Periodic>();
        statefuls = new ArrayList<Stateful>();
    }

    /**
     * Egy ciklussal vegigmeny a periodics listaban levo objektumokon es mindegyiken meghivja eloszor a step()
     * fuggvenyt. Ezutan megteszi ugyanezt a statefuls lista minden elemen: meghivja a changeState() metodust,
     * illetve az actionNumber visszaall 3-ra
     */
    public void turn() {
        for (Periodic p : periodics){
            p.step();
        }
        for (Stateful s : statefuls){
            s.changeState();
        }
    }

    /**
     * Visszaadja a periodics listat
     * @return A periodics lista
     */
    public List<Periodic> getPeriodics(){ return periodics; }

    /**
     * Visszaadja a statefuls listat
     * @return A statefuls lista
     */
    public List<Stateful> getStatefuls(){ return statefuls; }

    /**
     * Hozzaad egy Periodic objektumot a periodics listahoz
     * @param p A hozzaadando Periodic objektum
     */
    public void addPeriodic(Periodic p) { periodics.add(p); }

    /**
     * Hozzaad egy Stateful objektumot a statefuls listahoz
     * @param s
     */
    public void addStateful(Stateful s){ statefuls.add(s); }

}
