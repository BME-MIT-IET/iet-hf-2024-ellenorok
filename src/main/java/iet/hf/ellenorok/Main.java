package iet.hf.ellenorok;
public class Main {
    public static void main(String[] args) {
        defaultMap(); // load initial map
        //Test18();
        Graphics graphics = new Graphics();
    }

    /**
     * the code under is not used, but it is not deleted because we might need some of it to generate the map
     */
    public static void defaultMap() {
        //6 pipes
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        Pipe pipe3 = new Pipe();
        Pipe pipe4 = new Pipe();
        Pipe pipe5 = new Pipe();
        Pipe pipe6 = new Pipe();
        Pipe pipe7 = new Pipe();

        //2 sources
        Source source1 = new Source(pipe1);
        pipe1.addNeighbor(source1);

        Source source2 = new Source(pipe2);
        pipe2.addNeighbor(source2);

        Source source3 = new Source(pipe7);
        pipe7.addNeighbor(source3);

        //3 pumps
        Pump pump1 = new Pump(pipe1, pipe5);
        pipe1.addNeighbor(pump1);
        pipe5.addNeighbor(pump1);

        Pump pump2 = new Pump(pipe2, pipe6);
        pipe2.addNeighbor(pump2);
        pipe6.addNeighbor(pump2);

        Pump pump3 = new Pump(pipe7, pipe4);
        pipe7.addNeighbor(pump3);
        pipe4.addNeighbor(pump3);

        //2 cisterns
        Cistern cistern1 = new Cistern(pipe5);
        pipe5.addNeighbor(cistern1);

        Cistern cistern2 = new Cistern(pipe6);
        pipe6.addNeighbor(cistern2);

        //2 saboteurs
        Saboteur saboteur1 = new Saboteur(cistern1);
        cistern1.addPlayer(saboteur1);

        Saboteur saboteur2 = new Saboteur(cistern2);
        cistern2.addPlayer(saboteur2);

        //2 mechanics
        Mechanic mechanic1 = new Mechanic(source1);
        source1.addPlayer(mechanic1);

        Mechanic mechanic2 = new Mechanic(source2);
        source2.addPlayer(mechanic2);

        // connect components
        pipe3.addNeighbor(pump1);
        pump1.addNeighbor(pipe3);

        pipe3.addNeighbor(pump2);
        pump2.addNeighbor(pipe3);

        pipe4.addNeighbor(pump2);
        pump2.addNeighbor(pipe4);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur1);

        //add fields to game
        g.addField(source1);
        g.addField(source2);
        g.addField(source3);
        g.addField(pump1);
        g.addField(pump2);
        g.addField(pump3);
        g.addField(cistern1);
        g.addField(cistern2);
        g.addField(pipe1);
        g.addField(pipe2);
        g.addField(pipe3);
        g.addField(pipe4);
        g.addField(pipe5);
        g.addField(pipe6);
        g.addField(pipe7);

        //add players to game
        g.addPlayer(saboteur1);
        g.addPlayer(saboteur2);

        g.addPlayer(mechanic1);
        g.addPlayer(mechanic2);

        // add periodics to timer
        Timer.getInstance().addPeriodic(source1);
        Timer.getInstance().addPeriodic(source2);
        Timer.getInstance().addPeriodic(source3);

        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pipe2);
        Timer.getInstance().addPeriodic(pipe3);
        Timer.getInstance().addPeriodic(pipe4);
        Timer.getInstance().addPeriodic(pipe5);
        Timer.getInstance().addPeriodic(pipe6);
        Timer.getInstance().addPeriodic(pipe7);

        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pump2);
        Timer.getInstance().addPeriodic(pump3);

        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(cistern2);

        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
        Timer.getInstance().addStateful(pipe2);
        Timer.getInstance().addStateful(pipe3);
        Timer.getInstance().addStateful(pipe4);
        Timer.getInstance().addStateful(pipe5);
        Timer.getInstance().addStateful(pipe6);
        Timer.getInstance().addStateful(pipe7);
    }

    
}