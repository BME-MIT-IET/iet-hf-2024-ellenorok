package iet.hf.ellenorok;

import java.io.Serializable;

/**
 * A Player osztalyhoz kepest plusz felelossege, hogy ki tud lyukasztani csoveket
 */
public class Saboteur extends Player implements Serializable {
    public Saboteur(){}

    public Saboteur(Field field){ activeField = field; }

    /**
     * Megprobalja csuszossa tenni a mezot, amin all
     */
    @Override
    public void makeSlippery() {
        activeField.makeSlippery();
    }

    /**
     * Megprobal atlepni a parameterul kapott mezore
     * @param f A mezo, amire at probal lepni
     */
    @Override
    public void moveToField(Field f) {
        if(Game.getInstance().getActionNumber() > 0)
            if(!activeField.isSticky())
                f.accept(this);
    }

    /**
     * A Saboteur jatekosok nem tudnak csovet megjavitani, csak placeholder
     */
    @Override
    public void repairField() {}

}
