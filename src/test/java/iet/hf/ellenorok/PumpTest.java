package iet.hf.ellenorok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PumpTest {

    private Pipe pipe1;
    private Pipe pipe2;
    private Pipe pipe3;
    private Pump pump;
    private Mechanic mechanic1;
    private Game g;

    @BeforeEach
    void CreateGame(){
        pipe1 = new Pipe();
        pipe2 = new Pipe();
        pipe3 = new Pipe(); 
        pump = new Pump(pipe1, pipe2);
        mechanic1 = new Mechanic(pump);
        pump.addNeighbor(pipe1); pipe1.addNeighbor(pump);
        pump.addNeighbor(pipe2); pipe2.addNeighbor(pump);
        pump.addNeighbor(pipe3); pipe3.addNeighbor(pump);
        g = Game.getInstance();

        g.addPlayer(mechanic1);
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pump);
        g.addField(pipe1);
        g.addField(pipe2);
        g.addField(pipe3);
        //add players to game

        // add periodics to timer
        Timer.getInstance().addPeriodic(pump);
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pipe2);
        Timer.getInstance().addPeriodic(pipe3);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
        Timer.getInstance().addStateful(pipe2);
        Timer.getInstance().addStateful(pipe3);
    }

    @Test
    void ChangePumpFlowDirectionTest() {

        mechanic1.setActiveField(pump); pump.addPlayer(mechanic1);

        assertEquals(pump.getInput(), pipe1);
        assertEquals(pump.getOutput(), pipe2);

        g.changePumpDirection(pipe1, pipe3);

        assertEquals(pump.getInput(), pipe1);
        assertEquals(pump.getOutput(), pipe3);
    }
    
}
