package iet.hf.ellenorok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionPlayerGetsPoint {
	private Pipe pipe;
	private Source source;
	private Cistern cistern;
	private Mechanic mechanic;
	private Saboteur saboteur;
	private Game game;
	
	@Given("On a pipe with water next to a cistern stands a {string}")
	public void on_a_pipe_with_water_next_to_a_cistern_stands_a(String name) {
	    pipe = new Pipe();
	    cistern = new Cistern(pipe);
	    source = new Source(pipe);
	    
	    cistern.addNeighbor(pipe);
	    pipe.addNeighbor(cistern);
	    source.addNeighbor(pipe);
	    pipe.addNeighbor(source);
	    
	    game = Game.getInstance();
	    game.addField(pipe);
	    game.addField(cistern);
	    game.addField(source);
	    
	    if(name.equals("mechanic")) {
	    	mechanic = new Mechanic();
	    	game.addPlayer(mechanic);
	    	game.setActivePlayer(mechanic);
		    mechanic.setActiveField(pipe);
		    pipe.addPlayer(mechanic);
		    pipe._break();
	    }
	    else {
	    	saboteur = new Saboteur();
	    	game.addPlayer(saboteur);
	    	game.setActivePlayer(saboteur);
		    saboteur.setActiveField(pipe);
		    pipe.addPlayer(saboteur);
	    }
	    
	    Timer.getInstance().addPeriodic(cistern);
	    Timer.getInstance().addPeriodic(source);
	    Timer.getInstance().addPeriodic(pipe);
	    
	    Timer.getInstance().addStateful(pipe);
	    
	    game.setActionNumber(1);
	    game.setActionNumber(1);
	    pipe.setOldWaterState(true);
	}

	@When("The player does {string}")
	public void the_player_does(String action) {
		if(action.equals("repairs")) {
			mechanic.repairField();
			game.endTurn();
		}
		else {
			saboteur.breakField();
			game.endTurn();
		}
	}

	@Then("The next turn the point increases for {string}")
	public void the_next_turn_the_point_increases_for(String name) {
		if(name.equals("mechanic")) {
			assertEquals(1, game.getMechanicPoints());
		}
		else {
			assertEquals(1, game.getSaboteurPoints());
		}
	}
}
