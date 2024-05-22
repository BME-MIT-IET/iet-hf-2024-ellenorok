import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionPipeState {
	private Pipe pipe;
	private Pump pump1;
	private Pump pump2;
	private Saboteur player1;
	private Mechanic player2;
	private Game game;
	
	@Given("A player stands on a pipe and another player stands next to it")
	public void a_player_stands_on_a_pipe_and_another_player_stands_next_to_it() {
		pipe = new Pipe();
		pump1 = new Pump();
		pump2 = new Pump();
		player1 = new Saboteur();
		player2 = new Mechanic();
		
		pump1.addNeighbor(pipe);
		pipe.addNeighbor(pump1);
		pump2.addNeighbor(pipe);
		pipe.addNeighbor(pump2);
		
		game = Game.getInstance();
		game.addField(pipe);
		game.addField(pump1);
		game.addField(pump2);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		game.setActivePlayer(player1);
		game.setActionNumber(5);
		
		player1.setActiveField(pipe);
		pipe.addPlayer(player1);
		player2.setActiveField(pump1);
		pump1.addPlayer(player2);
		
		Timer.getInstance().addPeriodic(pump1);
		Timer.getInstance().addPeriodic(pump2);
		Timer.getInstance().addPeriodic(pipe);
		
		Timer.getInstance().addStateful(pipe);
	}

	@When("The player on the pipe makes it {string}")
	public void the_player_on_the_pipe_makes_it(String state) {
		if(state.equals("sticky")) {
			player1.makeSticky();
		}
		else {
			player1.makeSlippery();
		}
		player1.moveToField(pump2);
		game.endTurn();
	}
	
	@When("The other player steps on it")
	public void the_other_player_steps_on_it() {
		player2.moveToField(pipe);
	}

	@Then("The moving player {string} on the pipe")
	public void the_moving_player_on_the_pipe(String happening) {
		if(happening.equals("stucks")) {
			player2.moveToField(pump2);
			assertEquals(player2, pipe.getPlayers().get(0));
			assertEquals(true, pipe.isSticky());
		}
		else {
			assertEquals(0, pipe.getPlayers().size());
			assertEquals(true, pipe.isSlippery());
		}
	}
}
