package iet.hf.ellenorok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

 

    @Test
         void Test() {
        // create fields
        Pipe pipe1 = new Pipe();
        //create characters
        Saboteur saboteur1 = new Saboteur();

        // connect components
        //add players to a field
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur1);
        //add fields to game
        g.addField(pipe1);
        //add players to game
        g.addPlayer(saboteur1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        saboteur1.breakField();
        
        assertEquals(true, pipe1.isBroken());
        
    }
}
