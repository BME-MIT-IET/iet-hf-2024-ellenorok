package iet.hf.ellenorok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    
    private Pipe pipe1;
    private Cistern cistern1;
    private Pump pump1;
    private Mechanic mechanic1;
    private Saboteur saboteur1;
    private Game g;

    @BeforeEach
    void CreateGame(){
        pipe1 = new Pipe();
        cistern1 = new Cistern(new Pump());
        pump1 = new Pump(null, pipe1);
        mechanic1 = new Mechanic();
        saboteur1 = new Saboteur();
       // connect components
       cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
       pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
       cistern1.setInput(pipe1);
       g = Game.getInstance();
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
    void SaboteurBreaksPipeTest() {
        //add players to a field
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        assertEquals(false, ((Pipe)saboteur1.getActiveField()).isBroken());
        saboteur1.breakField();
        assertEquals(true, ((Pipe)saboteur1.getActiveField()).isBroken());
    }

    @Test
    void MechanicRepairsPipeTest() {
        pipe1.setBroken(true);
        
        mechanic1.setActiveField(pipe1); pipe1.addPlayer(mechanic1);
        assertEquals(true, ((Pipe)mechanic1.getActiveField()).isBroken());
        mechanic1.repairField();
        assertEquals(false, ((Pipe)mechanic1.getActiveField()).isBroken());
    }

    @Test
    void SaboteurMakesPipeSlipperyTest() {
       
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);        
        assertEquals(false, ((Pipe)saboteur1.getActiveField()).isSlippery());
        saboteur1.makeSlippery();
        assertEquals(true, ((Pipe)saboteur1.getActiveField()).isSlippery());

    }
    
    @Test
    void MakesStickyWasFreshlyAppliedTest() {
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        assertEquals(false, pipe1.isSticky());
        saboteur1.makeSticky();
        assertEquals(false, pipe1.isSticky());      //Pipe is not sticky, because makeSticky was freshly applied, and the player needs to step off.
    }

    @Test
    void MechanicPlacesPumpTest() {

        Mechanic mechanic1 = new Mechanic(pipe1, new Pump());

        pipe1.addPlayer(mechanic1);

        g.setActivePlayer(mechanic1);

        assertNotEquals(Pump.class, mechanic1.activeField.getClass());

        mechanic1.place();          //Mechanic trys to place a pump

        assertEquals(Pump.class, mechanic1.activeField.getClass());
    }

    @Test
    void MechanicPicksUpPumpFromCisternTest() {
        

        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        assertEquals(false, mechanic1.HasCarriedPump());

        mechanic1.pickup();

        assertEquals(true, mechanic1.HasCarriedPump());
    }

    @Test
    void PickUpPumpAndPlaceItTest(){
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);
        g.setActivePlayer(mechanic1);

        assertTrue(cistern1.hasPump());
        assertFalse(mechanic1.HasCarriedPump());
        assertEquals(Cistern.class, mechanic1.getActiveField().getClass());

        mechanic1.pickup();

        assertTrue(mechanic1.HasCarriedPump());

        mechanic1.moveToField(pipe1);

        assertEquals(Pipe.class, mechanic1.getActiveField().getClass());
        
        mechanic1.place();

        assertEquals(Pump.class, mechanic1.getActiveField().getClass());
        assertFalse(mechanic1.HasCarriedPump());
        assertFalse(cistern1.hasPump());
    }

    @Test
    void BreakAndRepairPipeTest() {


        //add players to a field
        pump1.addPlayer(mechanic1); mechanic1.setActiveField(pump1);
        cistern1.addPlayer(saboteur1); saboteur1.setActiveField(cistern1);

        g.setActivePlayer(saboteur1);


        //kimenethez ezt ki kell kommente
        saboteur1.moveToField(pipe1);
        assertEquals(pipe1, saboteur1.getActiveField());

        saboteur1.breakField();
        assertTrue(((Pipe) saboteur1.getActiveField()).isBroken());

        saboteur1.moveToField(pump1);
        assertEquals(pump1, saboteur1.getActiveField());

        g.endTurn();
        mechanic1.moveToField(pipe1);
        assertEquals(pipe1, mechanic1.getActiveField());

        mechanic1.repairField();
        assertFalse(((Pipe) mechanic1.getActiveField()).isBroken());


    }

}
