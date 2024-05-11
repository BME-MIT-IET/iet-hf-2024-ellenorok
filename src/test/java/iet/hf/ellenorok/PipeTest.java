package iet.hf.ellenorok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PipeTest {

    private Pipe pipe1;
    private Cistern cistern1;
    private Pump pump1;
    private Mechanic mechanic1;
    private Saboteur saboteur1;
    private Game g;

    @BeforeEach
    void CreateGame(){
        pipe1 = new Pipe();
        cistern1 = new Cistern(pipe1);
        pump1 = new Pump(null, pipe1);
        mechanic1 = new Mechanic();
        saboteur1 = new Saboteur();
       // connect components
       cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
       pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
       g = Game.getInstance();
       g.setActivePlayer(mechanic1);
       //add fields to game
       g.addField(pipe1);
       g.addField(cistern1);
       g.addField(pump1);
       //add players to game
       g.addPlayer(mechanic1);
       g.addPlayer(saboteur1);
       // add periodics to timer
       Timer.getInstance().addPeriodic(cistern1);
       Timer.getInstance().addPeriodic(pump1);
       Timer.getInstance().addPeriodic(pipe1);
       //add statefuls to timer
       Timer.getInstance().addStateful(pipe1);
    }



    

    @Test
    void TestSlipperyPipeTest() {
        pipe1.setSlipperyCounter(4);        
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        mechanic1.moveToField(pipe1);
        assertNotEquals(pipe1, mechanic1.getActiveField());
    }

    @Test
    void CantStayTwoPlayerOnTheSamePipeTest() {

        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        mechanic1.moveToField(pipe1);
        assertEquals(cistern1, mechanic1.getActiveField());
    }

    @Test
    void UnbreakablePipeTest() {
        pipe1.setBreakProofCounter(1);  //Set pipe unbreakable for 1 round
        
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        saboteur1.breakField();     //Saboteur trys to break pipe1

        assertEquals(false, pipe1.isBroken());
    }
}
