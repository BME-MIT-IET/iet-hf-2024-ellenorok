package model;

import interfaces.Periodic;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Felelossege, hogy eltarolja folyt-e bele viz a korben (mert ha igen, akkor pontot kapnak a szerelok). Emellett
 * tarolja, hogy van-e rajta meg fel nem vett pumpa, melyet a szerelok felvehetnek
 */
public class Cistern extends Field implements Periodic {
    private static final Logger logger = Logger.getLogger("logger");
    private final SecureRandom random = new SecureRandom();
    /**
     * A bemeneti csove
     */
    private Pipe input;

    /**
     * Ha van rajta pumpa, annak referenciaja
     */
    private Pump spawnedPump;
    public boolean hasPump() {
        return spawnedPump != null;
    }

    public Cistern() {}

    public Cistern(Pipe in) {
        input = in;
        neighbors.add(in);
    }

    public Cistern(Pump p) { spawnedPump = p; }

    /**
     * Eltavolitja a parameterul kapott mezot a szomszedok kozul
     * @param f Az eltavolitando mezo
     */
    @Override
    public void removeNeighbor(Field f) {
        super.removeNeighbor(f);
        input = null;
    }

    /**
     * Tovabba beallitja a bemeneti csovet a parameterul kapott csore
     * @param p A cso, amit beallit
     */
    @Override
    public void addNeighbor(Field p) {
        super.addNeighbor(p);
        try {
            if(input != null) input.setNewWaterState(false);
            input = (Pipe) p;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Error casting field to pipe.");
        }
    }

    /**
     * Ha van rajta pumpa, akkor azt a parameterul kapott szerelonek odaadja
     * @param m A szerelo, aki fel akarja venni a pumpat
     * @see Mechanic
     */
    @Override
    public void pickupPump(Mechanic m) {
        if(spawnedPump != null) {
            m.doPickup(spawnedPump);
            spawnedPump = null;
        }
    }

    /**
     * Minden korben, ha a bemeneti csoven van viz, akkor "beszivja"
     * Tovabba veletlenszeruen letrehoz egy pumpat, ha nincs rajta
     */
    public void step() {
        if (spawnedPump == null) {
            if (Game.getInstance().getRandom()) {
                boolean b = random.nextBoolean() && random.nextBoolean();
                if (b) {
                    spawnedPump = new Pump();
                }
            } else {
                logger.log(Level.INFO, "Spawnoljon pumpa?");
                Scanner scanner = new Scanner(System.in);
                String valasz = scanner.nextLine();
                if (valasz.equals("Igen")) {
                    spawnedPump = new Pump();
                }
                if (!valasz.equals("Nem")) {
                    logger.log(Level.WARNING, "Helytelen valasz, ezert nem spawnolodik pumpa.");
                }
            }
        }
        if(input != null) {
            input.flowWater(this);
        }
    }

    /**
     * Viz kerul a ciszternaba, pontot kapnak a szerelok
     */
    public void addWater() {
        Game.getInstance().addMechanicPoints();
    }

    public void setInput(Pipe in){ input = in; }
}
