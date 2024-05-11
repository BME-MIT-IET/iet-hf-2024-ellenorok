package iet.hf.ellenorok;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PipeTest {
    @Test
    void TestSlipperyPipe() {
        // create fields
        Pipe pipe1 = new Pipe();
        pipe1.setSlipperyCounter(4);
        Cistern cistern1 = new Cistern(pipe1);
        Pump pump1 = new Pump(null, pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        g.addField(cistern1);
        g.addField(pump1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.moveToField(pipe1);
        assertNotEquals(pipe1, mechanic1.getActiveField());
    }
}
