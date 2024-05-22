import static org.junit.jupiter.api.Assertions.assertEquals;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Pipe pipe1;
    private Cistern cistern1;
    private Source source1;
    private Mechanic mechanic1;
    private Saboteur saboteur1;
    private Game g;

    @BeforeEach
    void CreateGame(){
        pipe1 = new Pipe();
        cistern1 = new Cistern(pipe1);
        source1 = new Source(pipe1);
        mechanic1 = new Mechanic();
        saboteur1 = new Saboteur();
       // connect components
       cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
       source1.addNeighbor(pipe1); pipe1.addNeighbor(source1);
       g = Game.getInstance();
       g.setActivePlayer(mechanic1);
       //add fields to game
       g.addField(pipe1);
       g.addField(cistern1);
       g.addField(source1);
       //add players to game
       g.addPlayer(mechanic1);
       g.addPlayer(saboteur1);
       // add periodics to timer
       Timer.getInstance().addPeriodic(cistern1);
       Timer.getInstance().addPeriodic(source1);
       Timer.getInstance().addPeriodic(pipe1);
       //add statefuls to timer
       Timer.getInstance().addStateful(pipe1);
       g.setActionNumber(5);

    }

    @Test
    void WaterFlowsFromPumpToCisternTest() {
        pipe1.setOldWaterState(false);
        
        assertEquals(0, g.getMechanicPoints());

        Timer.getInstance().turn();
        Timer.getInstance().turn();

        assertEquals(1, g.getMechanicPoints());
    }

    @Test
    void WaterFlowsOutOfPipeTest(){
        pipe1.setOldWaterState(false);
        pipe1.setBroken(true);
        
        assertEquals(0, g.getSaboteurPoints());

        Timer.getInstance().turn();

        assertEquals(1, g.getSaboteurPoints());
    }
    
}
