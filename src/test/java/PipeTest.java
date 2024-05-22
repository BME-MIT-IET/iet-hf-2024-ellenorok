import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PipeTest {

    private Pipe pipe1;
    private Cistern cistern1;
    private Mechanic mechanic1;
    private Saboteur saboteur1;
    private Game g;

    @BeforeEach
    void CreateGame(){
        pipe1 = new Pipe();
        cistern1 = new Cistern(pipe1);
        Pump pump1 = new Pump(null, pipe1);
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
       g.setActionNumber(5);

    }



    

    @Test
    void TestSlipperyPipeTest() {
        pipe1.setSlipperyCounter(4);
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        mechanic1.moveToField(pipe1);                           //Mechanic trys to step on the slippery pipe. Should slip of.
        assertNotEquals(pipe1, mechanic1.getActiveField());
    }

    @Test
    void CantStayTwoPlayerOnTheSamePipeTest() {

        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        mechanic1.moveToField(pipe1);   //Mechanic trys to step on the pipe, that has already a player on.
        assertEquals(cistern1, mechanic1.getActiveField());
    }

    @Test
    void UnbreakablePipeTest() {
        pipe1.setBreakProofCounter(1);  //Set pipe unbreakable for 1 round
        
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        saboteur1.breakField();     //Saboteur trys to break pipe1

        assertFalse(pipe1.isBroken());
    }

    

    @Test
    void ChangePipesNeighbourTest() {
        // create fields
        Pipe pipe = new Pipe();
        Pump pump1 = new Pump(pipe, null);
        Pump pump2 = new Pump(null, pipe);
        Pump pump3 = new Pump();

        pump1.addNeighbor(pipe); pipe.addNeighbor(pump1);
        pump2.addNeighbor(pipe); pipe.addNeighbor(pump2);

        mechanic1.setActiveField(pump1); pump1.addPlayer(mechanic1);


        g.addField(pump1);
        g.addField(pump2);
        g.addField(pump3);
        g.addField(pipe);


        // add periodics to timer
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pump2);
        Timer.getInstance().addPeriodic(pump3);
        Timer.getInstance().addPeriodic(pipe);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe);

        assertFalse(pump3.getNeighbours().contains(pipe));

        g.movePipe(pipe, pump2, pump3);     //Change one of pipe's end from pump2 to pump3

        assertTrue(pump3.getNeighbours().contains(pipe));
    }
}
