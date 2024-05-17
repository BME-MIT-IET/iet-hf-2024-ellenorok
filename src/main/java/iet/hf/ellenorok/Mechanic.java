package iet.hf.ellenorok;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

/**
 * A Player osztalyhoz kepest plusz felelossege, hogy szamontartja, hogy van-e a karakternel pumpa es ezt le tudja
 * rakni. Emellett javitani is tud mezoket
 */
public class Mechanic extends Player implements Serializable {
    /**
     * A szerelonel levo pumpa
     */
    private Pump carriedPump;

    public Mechanic(){}

    public Mechanic(Field field){ activeField = field; }

    public Mechanic(Field field, Pump pump){ activeField = field; carriedPump = pump; }

    public boolean HasCarriedPump(){
        if(carriedPump != null){
            return true;
        }
        return false;
    }

    /**
     * Megprobalja megjavitani azt a mezot, amin eppen all
     */
    @Override
    public void repairField() {
        activeField.repair();
    }

    /**
     * A Mechanic jatekosok nem tudnak csovet kilyukasztani, csak placeholder
     */
    @Override
    public void breakField() {
    }

    /**
     * Ha eddig volt pumpaja, most mar nem lesz, lerakja a jelenlegi mezojere ugy, hogy amin all, azt
     * 2 csovel es kozottuk 1 pumpaval helyettesiti es csokkenti a korben hatralevo akciok szamat 1-el
     */
    public void doPlace(){
        List<Field> neighbours = activeField.getNeighbours();

        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        carriedPump.addNeighbor(pipe1);
        carriedPump.addNeighbor(pipe2);

        pipe1.addNeighbor(carriedPump);
        pipe2.addNeighbor(carriedPump);

        //the action take is imbeded here
        carriedPump.changePumpDirection(pipe1, pipe2);

        neighbours.get(0).removeNeighbor(activeField);
        neighbours.get(1).removeNeighbor(activeField);

        neighbours.get(0).addNeighbor(pipe1);
        pipe1.addNeighbor(neighbours.get(0));

        neighbours.get(1).addNeighbor(pipe2);
        pipe2.addNeighbor(neighbours.get(1));

        Game.getInstance().addField(pipe1);
        Game.getInstance().addField(pipe2);
        Game.getInstance().addField(carriedPump);
        Game.getInstance().removeField(activeField);

        Timer.getInstance().getPeriodics().remove(activeField);
        Timer.getInstance().getStatefuls().remove(activeField);

        Timer.getInstance().getPeriodics().add(carriedPump);
        Timer.getInstance().getPeriodics().add(pipe1);
        Timer.getInstance().getPeriodics().add(pipe2);

        Timer.getInstance().getStatefuls().add(pipe1);
        Timer.getInstance().getStatefuls().add(pipe2);

        this.setActiveField(carriedPump);
        carriedPump.addPlayer(this);
        carriedPump = null;
    }

    /**
     * A parameterkent kapott pumpat veglegesen felveszi es csokkenti a korben hatralevo akciok szamat 1-gyel
     * @param p A felvenni kivant pumpa
     * @see Pump
     */
    public void doPickup(Pump p){
        carriedPump = p;
        Game.getInstance().actionTaken();
    }

    /**
     * Ha van nala pumpa, megprobalja letenni az aktualis mezojere
     */
    @Override
    public void place(){
        if(carriedPump != null) {
            activeField.placePump(this);
        }
    }

    /**
     * Ha nincs nala pumpa, megprobal felvenni egyet a mezorol, amin eppen all
     */
    @Override
    public void pickup(){
        if(carriedPump == null) {
            activeField.pickupPump(this);
        }
    }

    /**
     * Viszaadja az aktualis mezojet
     * @return Az aktualis mezo
     */
    @Override
    public Field getActiveField() {
        return activeField;
    }

    /**
     * Megprobal atlepni a parameterkent kapott mezore
     * @param f A mezo, amire at szeretne lepni
     */
    @Override
    public void moveToField(Field f) {
        if(Game.getInstance().getActionNumber() > 0)
            if(!activeField.isSticky())
                f.accept(this);
    }
}
