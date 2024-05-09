import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

/**
 * Szamontartja, hogy van-e benne viz. Ezen kivul azt is jegyzi, hogy el van-e torve es ha igen es viz van benne,
 * akkor a kor vegen pontot ad a szabotoroknek
 */
public class Pipe extends Field implements Periodic, Stateful, Serializable {
    /**
     * Van-e benne viz, mindenki ezt a valtozot olvassa
     */
    private boolean oldWaterState;

    /**
     * A torhetetlensegbol hatralevo korok szama
     */
    private int breakProofCounter = 0;

    /**
     * A csuszossagbol hatralevo korok szama
     */
    private int slipperyCounter = 0;

    /**
     * A ragadossagbol hatralevo korok szama
     */
    private int stickyCounter = 0;

    /**
     * A kovetkezo korben lesz-e benne viz, mindenki ezt a valtozot irja.
     * Minden korben az updateState() hivasra ennek a valtozonak az erteket atmasolja az oldWaterState valtozoba
     */
    private boolean newWaterState = false;

    /**
     * A torottsegi allapot
     */
    private boolean isBroken;

    public Pipe(){}

    /**
     * Ha nem all rajta mas jatekos es a parameterben kapott jatekos egy mellette levo mezon all, akkor onmagara
     * lepteti ot es hozzaadja a players listához
     * Ha csuszos a cso, akkor a random faktortol fuggoen veletlenszeruen atleptetie egy szomszedos mezore, vagy megkerdezi
     * @param p A jatekos, aki a mezore lep
     * @see Player
     */
    @Override
    public void accept(Player p) {
        if(players.size() == 0) {
            Field oldfield = p.getActiveField();
            if(neighbors.contains(oldfield)) {
                if(slipperyCounter > 0 && !Game.getInstance().getRandom()){
                    System.out.println("Melyik oldalra csusszak? Regi vagy uj?");
                    Scanner scanner = new Scanner(System.in);
                    String valasz = scanner.nextLine();
                    if(valasz.equals("regi")){
                        p.swapField(oldfield);
                        oldfield.addPlayer(p);
                    }
                    else if(valasz.equals("uj")){
                        for(Field f : neighbors){
                            if(!f.equals(oldfield)){
                                p.swapField(f);
                                f.addPlayer(p);
                            }
                        }
                    }
                    else {
                        System.out.println("Helytelen valasz, ezert a regire csuszik.");
                        p.swapField(oldfield);
                        oldfield.addPlayer(p);
                    }
                } else if(slipperyCounter > 0 && Game.getInstance().getRandom()) {
                    Random random = new Random();
                    if(random.nextBoolean()){
                        for(Field f : neighbors){
                            if(!f.equals(oldfield)){
                                p.swapField(f);
                                f.addPlayer(p);
                            }
                        }
                    }
                    else if(!random.nextBoolean()){
                        p.swapField(oldfield);
                        oldfield.addPlayer(p);
                    }
                } else{
                    p.swapField(this);
                    players.add(p);
                }
            }
        }
    }

    /**
     * A hatralevo korok szamait csokkenti 1-gyel
     */
    public void step() {
        if(breakProofCounter > 0) breakProofCounter--;
        if(slipperyCounter > 0) slipperyCounter--;
        if(stickyCounter > 0) stickyCounter--;
    }

    /**
     * Ha kulonbozik a ket allapot (newWaterState) es (oldWaterState), akkor az oldWaterState megkapja a newWaterState
     * erteket. Ezutan, ha az oldWaterState igaz es a cso lyukas, a szabotorok kapnak pontot
     */
    public void changeState() {
        oldWaterState = newWaterState;
        if(isBroken && oldWaterState) {
            Game.getInstance().addSaboteurPoints();
        }
    }

    /**
     * Az isBroken erteke hamis lesz es amennyiben igaz volt, csokkenti a hatralevo akciok szamat 1-el majd
     * torhetetlenne teszi a csovet egy bizonyos random idore
     */
    @Override
    public void repair() {
        if(isBroken) {
            Game.getInstance().actionTaken();
            isBroken = false;
            breakProofCounter = 3;
            if(Game.getInstance().getRandom()) {
                Random random = new Random();
                breakProofCounter +=random.nextInt(2);
            }
        }
    }

    /**
     * Ha a cso mar regen volt megjavitva es nem torott, akkor eltori a csovet es levon egy akciot
     */
    @Override
    public void _break() {
        if(!isBroken && breakProofCounter == 0) {
            Game.getInstance().actionTaken();

            //If it breaks than water doesn't flow
            //BUT can contain water
            isBroken = true;
        }
    }

    /**
     * A parameterul kapott szerelo jatekoson meghivja a doPlace() metodust (lerakatja vele a pumpat)
     * @param m A szerelo jatekos
     * @see Mechanic
     */
    @Override
    public void placePump(Mechanic m) {
        m.doPlace();
    }

    /**
     * A parameterul kapott ciszternan meghivja az addWater() fuggvenyt, ha az oldWaterState igaz
     * ("folyatja bele a vizet")
     * @param c
     * @see Cistern
     */
    public void flowWater(Cistern c) {
        if(!isBroken && oldWaterState) {
            c.addWater();
        }
    }

    /**
     * Ad a parameterul kapott pumpa tartalyaba vizet, ha az oldWaterState igaz es eltunteti onnan a vizet, ha az
     * oldWaterState hamis
     * @param p
     * @see Pump
     */
    public void giveWater(Pump p) {
        //Ha el van torve akkor ne kapjon vizet
        //Azert van mert meg lehet viz egy csoben akkor is ha el van torve
        if(isBroken)
            p.setWater(false);
        else
            p.setWater(oldWaterState);
    }

    /**
     * Beallitja a newWaterState erteket a parameterben kapott ertekre
     * @param newState
     */
    public void setNewWaterState(boolean newState) {
        newWaterState = newState;
    }

    /**
     * Beallitja az oldWaterState erteket a parameterben kapott ertekre
     * @param oldState
     */
    public void setOldWaterState(boolean oldState){
        oldWaterState = oldState;
    }

    public boolean getOldWaterState() {
        return oldWaterState;
    }
    /**
     * Beallitja a slipperyCounter erteket a parameterben kapott ertekre
     * @param number
     */
    public void setSlipperyCounter(int number){
        slipperyCounter = number;
    }

    /**
     * Beallitja a breakProofCounter erteket a parameterben kapott ertekre
     * @param number
     */
    public void setBreakProofCounter(int number) { breakProofCounter = number; }

    /**
     * Beallitja a torottseg allapotot a parameterben kapott ertekre
     * @param b
     */
    public void setBroken(boolean b) { isBroken = b; }

    /**
     * A slipperyCounter értékét 5-re állítja és meghívja a Game osztály actionTaken() metódusát
     */
    @Override
    public void makeSlippery() {
        slipperyCounter = 5;
        Game.getInstance().actionTaken();
    }

    /**
     * A stickyCounter értékét 5-re állítja és meghívja a Game osztály actionTaken() metódusát
     */
    @Override
    public void makeSticky() {
        stickyCounter = 5 ;
        Game.getInstance().actionTaken();
    }

    /**
     * Visszaadja, hogy ragados-e a cso
     */
    @Override
    public boolean
    isSticky() {
        //If sticky counter is 0 then the stickiness expired
        //Fi sticky counter is 5 then was freshly applied.
        return stickyCounter > 0 && stickyCounter < 5;
    }

    public boolean isSlippery() { return slipperyCounter > 0;}

    public boolean isBroken() {
        return isBroken;
    }
}
