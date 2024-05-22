package model;

import interfaces.*;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A jatekszabalyok szerinti pumpa funkcioit valositja meg
 */
public class Pump extends Field implements Periodic, Serializable {
    private final SecureRandom random = new SecureRandom();
    private transient Logger logger;
    /**
     * A bemeneti csove
     */
    private Pipe input;

    /**
     * A kimeneti csove
     */
    private Pipe output;

    /**
     * Szamontartja, hogy van-e a viztartalyban viz
     */
    private boolean hasStoredWater;

    /**
     * Szamontartja, hogy a pumpa elromlott-e
     */
    private boolean isBroken;

    public Pump() {}

    public Pump(Pipe in, Pipe out){
        input = in; output = out;
        if (in != null) neighbors.add(in);
        if (out != null) neighbors.add(out);
    }

    public Pipe getInput() {
        return input;
    }

    public Pipe getOutput() {
        return output;
    }

    /**
     * Kis esellyel elromlik (isBroken true lesz). Ha nem romlott el, a kovetkezo viselkedes definialt:
     * 1A. Ha a tartalyaban van viz, a kimeneti csovere tesz vizet (a tartalybol nem tunik el a viz)
     * 1B. Ha a tartalyaban nincs viz, a kimeneti csoverol eltunteti a vizet
     * 2A. Ha a bemeneti csoven van viz, a tartalyaban is lesz
     * 2B. Ha a bemeneti csoven nincs viz, a tartalyaban sem lesz
     */
    public void step() {
        if(Game.getInstance().getRandom()) {
            if(!isBroken) {
                isBroken = random.nextBoolean() && random.nextBoolean() && random.nextBoolean() && random.nextBoolean() && random.nextBoolean();
            }
        } else {
            logger.log(Level.INFO, "Eltorjon a pumpa?");
            Scanner scanner = new Scanner(System.in);
            String valasz = scanner.nextLine();
            if(valasz.equals("Igen")){
                isBroken = true;
            } else if(!valasz.equals("Nem")){
                logger.log(Level.WARNING, "Helytelen valasz, ezert nem torik el");
            }
        }
        if(!isBroken && input != null && output != null) {
            output.setNewWaterState(hasStoredWater);
            input.giveWater(this);
        } else if(!isBroken && input != null) {
            input.giveWater(this);
        } else if(!isBroken && output != null) {
            output.setNewWaterState(hasStoredWater);
        } else if(isBroken && output != null) {
            //Ha elromlik akkor a tartalybol sem tud adni vizet
            output.setNewWaterState(false);
        }
    }

    public boolean isHasStoredWater() {
        return hasStoredWater;
    }

    public boolean isBroken() {
        return isBroken;
    }

    /**
     * Beallitja a bemeneti csovet (input) az in parameter altal kapott csore, a kimeneti csovet (output)
     * az out parameter altal kapott csore, amennyiben ez a ket parameter nem egyezik meg
     * @param in A beallitando bemeneti cso
     * @param out A beallitando kimeneti cso
     * @see Pipe
     */
    @Override
    public void changePumpDirection(Pipe in, Pipe out){
        if(!in.equals(out) && neighbors.contains(in) && neighbors.contains(out)) {
            //Ujjonan létrehozott pumpa eseten
            if(output != null) {
                //By removing the output we have to
                output.setNewWaterState(false);
            }
            input = in; output = out;
            Game.getInstance().actionTaken();
        }
    }

    /**
     * Ha eddig el volt romolva, akkor megjavul es csokkenti a korben levo akciok szamat 1-el
     */
    @Override
    public void repair(){
        if(isBroken && Game.getInstance().getActionNumber() > 0) {
            Game.getInstance().actionTaken();
            isBroken = false;
        }
    }

    /**
     * Eltavolitja a parameterben kapott mezot a szomszedjai kozul
     * @param f A szomszed, amelyet torolni kell
     */
    @Override
    public void removeNeighbor(Field f) {
        super.removeNeighbor(f);
        if(input == f) input = null;
        else if(output == f) output = null;
    }

    /**
     * A viztartaly toltottseget (hasStoredWater) a parameterkent kapott ertekre beallitja
     * @param b
     */
    public void setWater(boolean b) {
        hasStoredWater = b;
    }

    /**
     * Beallitja a bemeneti es kimeneti csoveket (input, output) a parameterkent kapott ertekekre
     * @param in A beallitando bemeneti cso
     * @param out A beallitando kimeneti cso
     */
    public void setInputOutput(Pipe in, Pipe out) { input = in; output = out; }

    /**
     * Beallitja a pumpa allapotat (isBroken) a parameterkent kapott ertekre
     * @param b A beallitando allapot
     */
    public void setBroken(boolean b) { isBroken = b; }

}
