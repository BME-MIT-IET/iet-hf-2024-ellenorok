import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionPutDownNewPump {
	private Pipe pipe;
	private Cistern cistern;
	private Source source;
	private Pump pump;
	private Mechanic player;
	private Game game;
	
	@Given("A mechanic player stands on a cistern and picks up a pump")
	public void a_mechanic_player_stands_on_a_cistern_and_picks_up_a_pump() {
		pipe = new Pipe();
		cistern = new Cistern(pipe);
		source = new Source(pipe);
		pump = new Pump();
		player = new Mechanic();
		
		pipe.addNeighbor(cistern);
		pipe.addNeighbor(source);
		
		cistern.addPlayer(player);
		player.setActiveField(cistern);
		
		game = Game.getInstance();
		game.addField(pipe);
		game.addField(cistern);
		game.addField(source);
		game.addPlayer(player);
		game.setActivePlayer(player);
		game.setActionNumber(3);
		
		Timer.getInstance().addPeriodic(pipe);
		Timer.getInstance().addPeriodic(cistern);
		Timer.getInstance().addPeriodic(source);
		
		Timer.getInstance().addStateful(pipe);
		
		player.doPickup(pump);
	}

	@When("The player steps on the pipe")
	public void the_player_steps_on_the_pipe() {
		player.moveToField(pipe);
	}

	@When("The player puts down the pump")
	public void the_player_puts_down_the_pump() {
		player.doPlace();
	}

	@Then("The pipe will become two new pipes")
	public void the_pipe_will_become_two_new_pipes() {
		for(Pipe inGamePipe : game.getPipes()) {
			assertNotEquals(pipe, inGamePipe);
		}
	}

	@Then("The pump will be between the two new pipes")
	public void the_pump_will_be_between_the_two_new_pipes() {
		assertEquals(pump.getNeighbours().get(0), game.getPipes().get(0));
		assertEquals(pump.getNeighbours().get(1), game.getPipes().get(1));
	}

	@Then("The player will stand on the pump")
	public void the_player_will_stand_on_the_pump() {
		assertEquals(player, pump.getPlayers().get(0));
	}
	
	@Then("The player wont has any action left")
	public void the_player_won_t_have_any_action_left() {
		assertEquals(0, game.getActionNumber());
	}
}
