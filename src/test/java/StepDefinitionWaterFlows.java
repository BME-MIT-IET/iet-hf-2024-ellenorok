import model.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionWaterFlows {
	private Pipe pipe1;
	private Pipe pipe2;
	private Pipe pipe3;
	private Pump pump;
	private Mechanic player;
	private Game game;
	
	@Given("A player stands on a working pump")
	public void a_player_stands_on_a_working_pump() {
		pipe1 = new Pipe();
		pipe2 = new Pipe();
		pipe3 = new Pipe();
		pump = new Pump();
		player = new Mechanic();
		
		pipe1.addNeighbor(pump);
		pump.addNeighbor(pipe1);
		pipe2.addNeighbor(pump);
		pump.addNeighbor(pipe2);
		pipe3.addNeighbor(pump);
		pump.addNeighbor(pipe3);
		pump.changePumpDirection(pipe1, pipe2);
		
		pump.addPlayer(player);
		player.setActiveField(pump);
		
		pipe1.setNewWaterState(true);
		pipe1.setOldWaterState(true);
		pump.setWater(true);
		pipe2.setOldWaterState(true);
		pipe3.setOldWaterState(false);
		
		game = Game.getInstance();
		game.addField(pipe1);
		game.addField(pipe2);
		game.addField(pipe3);
		game.addField(pump);
		game.addPlayer(player);
		game.setActivePlayer(player);
		game.setActionNumber(3);
		
		Timer.getInstance().addPeriodic(pipe1);
		Timer.getInstance().addPeriodic(pipe2);
		Timer.getInstance().addPeriodic(pipe3);
		Timer.getInstance().addPeriodic(pump);
		
		Timer.getInstance().addStateful(pipe1);
		Timer.getInstance().addStateful(pipe2);
		Timer.getInstance().addStateful(pipe3);
	}

	@When("The player changes the pumps flow direction")
	public void the_player_changes_the_pumps_flow_direction() {
		player.changePumpDirection(pipe1, pipe3);
		game.endTurn();
		if(pump.isBroken())
			pump.repair();
	}

	@Then("The old pipe output wont have water")
	public void the_old_pipe_output_wont_have_water() {
		assertFalse(pipe2.getOldWaterState());
	}

	@Then("The new pipe output will have water")
	public void the_new_pipe_output_will_have_water() {
		assertTrue(pipe3.getOldWaterState());
	}
}
