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
    private static void defaultMap() {
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

    private static void Test1_BUILD() { 
        // create fields
        Pipe pipe1 = new Pipe();
        Cistern cistern1 = new Cistern(pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        //add players to a field
        mechanic1.setActiveField(pipe1); pipe1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        g.addField(cistern1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
    }

    private static void Test1() {
        // create fields
        Pipe pipe1 = new Pipe();
        Cistern cistern1 = new Cistern(pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        //add players to a field
        mechanic1.setActiveField(pipe1); pipe1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        g.addField(cistern1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.moveToField(cistern1);
    }

    private static void Test2() {
        // create fields
        Pipe pipe1 = new Pipe(); pipe1.setSlipperyCounter(4);
        Cistern cistern1 = new Cistern(pipe1);
        Pump pump1 = new Pump(null, pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        g.addField(cistern1);
        g.addField(pump1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.moveToField(pipe1);
    }

    private static void Test3() {
        // create fields
        Pipe pipe1 = new Pipe();
        Cistern cistern1 = new Cistern(pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic();
        Saboteur saboteur1 = new Saboteur();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        g.addField(cistern1);
        //add players to game
        g.addPlayer(mechanic1);
        g.addPlayer(saboteur1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.moveToField(pipe1);
    }

    private static void Test4() {
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
    }

    private static void Test5() {
        // create fields
        Pipe pipe1 = new Pipe(); pipe1.setBreakProofCounter(1);
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
    }

    private static void Test6() {
        // create fields
        Pipe pipe1 = new Pipe(); pipe1.setBroken(true);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        //add players to a field
        mechanic1.setActiveField(pipe1); pipe1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.repairField();
    }

    private static void Test7() {
        // create fields
        Pipe pipe1 = new Pipe();
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        //add players to a field
        mechanic1.setActiveField(pipe1); pipe1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.repairField();
    }

    private static void Test8() {
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
        saboteur1.makeSlippery();
    }

    private static void Test9() {
        // create fields
        Pipe pipe = new Pipe();
        Cistern cistern1 = new Cistern(pipe);
        Pump pump1 = new Pump(null, pipe);
        //create characters
        Saboteur saboteur1 = new Saboteur();

        // connect components
        cistern1.addNeighbor(pipe); pipe.addNeighbor(cistern1);
        pump1.addNeighbor(pipe); pipe.addNeighbor(pump1);
        //add players to a field
        saboteur1.setActiveField(pipe); pipe.addPlayer(saboteur1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur1);
        //add fields to game
        g.addField(pipe);
        g.addField(cistern1);
        g.addField(pump1);
        //add players to game
        g.addPlayer(saboteur1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pipe);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe);

        //kimenethez ezt ki kell kommentelni
        //;
        saboteur1.makeSticky();
    }

    private static void Test10() {
        // create fields
        Pipe pipe1 = new Pipe(); pipe1.setSlipperyCounter(4); pipe1.setOldWaterState(true);
        Cistern cistern1 = new Cistern(pipe1);
        Pump pump1 = new Pump(null, pipe1); pump1.setWater(true);
        //create characters
        Mechanic mechanic1 = new Mechanic();
        Saboteur saboteur1 = new Saboteur();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);
        saboteur1.setActiveField(pump1); pump1.addPlayer(saboteur1);

        // create game
        Game g = Game.getInstance();
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

        //kimenethez ezt ki kell kommentelni
        //
        Timer.getInstance().turn();
        g.setActivePlayer(saboteur1);
    }

    private static void Test14() {
        // create fields
        Pipe pipe1 = new Pipe();
        Cistern cistern1 = new Cistern(pipe1);
        Pump pump1 = new Pump(null, pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic(pipe1, new Pump());

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
        //add players to a field
        pipe1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(cistern1);
        g.addField(pump1);
        g.addField(pipe1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pump1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.place();
    }

    private static void Test15() {
        // create fields
        Pipe pipe1 = new Pipe();
        Cistern cistern1 = new Cistern(pipe1);
        Pump pump1 = new Pump(null, pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic(pipe1);

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        pump1.addNeighbor(pipe1); pipe1.addNeighbor(pump1);
        //add players to a field
        pipe1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        g.setActionNumber(0);
        //add fields to game
        g.addField(cistern1);
        g.addField(pump1);
        g.addField(pipe1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pump1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.place();
    }

    private static void Test16() {
        // create fields
        Cistern cistern1 = new Cistern(new Pump());
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        //add players to a field and set active field of player
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(cistern1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        //add statefuls to timer

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.pickup();
    }

    private static void Test17() {
        // create fields
        Cistern cistern = new Cistern(new Pump());
        //create characters
        Mechanic mechanic1 = new Mechanic(cistern, new Pump());

        // connect components


        //add players to a field and set active field of player
        cistern.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(cistern);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern);
        //add statefuls to time

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.pickup();
    }

    private static void Test18() {
        // create fields
        Pipe pipe = new Pipe();
        Pump pump1 = new Pump(pipe, null);
        Pump pump2 = new Pump(null, pipe);
        Pump pump3 = new Pump();

        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        pump1.addNeighbor(pipe); pipe.addNeighbor(pump1);
        pump2.addNeighbor(pipe); pipe.addNeighbor(pump2);

        //add players to a field and set active field of player
        //in this test case it doesn't matter on which field does the player stand.
        mechanic1.setActiveField(pump1); pump1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pump1);
        g.addField(pump2);
        g.addField(pump3);
        g.addField(pipe);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pump2);
        Timer.getInstance().addPeriodic(pump3);
        Timer.getInstance().addPeriodic(pipe);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe);

        //kimenethez ezt ki kell kommentelni
        //
        g.movePipe(pipe, pump2, pump3);
    }

    private static void Test19() {
        // create fields
        Pipe pipe = new Pipe();
        Pump pump = new Pump(pipe, null);
        Cistern cistern = new Cistern();
        Source source = new Source();

        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        pipe.addNeighbor(pump);
        pipe.addNeighbor(cistern);
        cistern.addNeighbor(pipe);

        //add players to a field and set active field of player
        //in this test case it doesn't matter on which field does the player stand.
        mechanic1.setActiveField(pump);
        pump.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pump);
        g.addField(cistern);
        g.addField(source);
        g.addField(pipe);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pump);
        Timer.getInstance().addPeriodic(cistern);
        Timer.getInstance().addPeriodic(source);
        Timer.getInstance().addPeriodic(pipe);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe);

        //kimenethez ezt ki kell kommentelni
        //
        g.movePipe(pipe, cistern, source);
    }

    private static void Test20() {
        // create fields
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        Pipe pipe3 = new Pipe();
        //the first two pumps are the input and output
        Pump pump = new Pump(pipe1, pipe2);

        //create characters
        Mechanic mechanic1 = new Mechanic(pump);

        // connect components
        pump.addNeighbor(pipe1); pipe1.addNeighbor(pump);
        pump.addNeighbor(pipe2); pipe2.addNeighbor(pump);
        pump.addNeighbor(pipe3); pipe3.addNeighbor(pump);

        //add players to a field and set active field of player
        //in this test case it doesn't matter on which field does the player stand.
        mechanic1.setActiveField(pump); pump.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.addPlayer(mechanic1);
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pump);
        g.addField(pipe1);
        g.addField(pipe2);
        g.addField(pipe3);
        //add players to game

        // add periodics to timer
        Timer.getInstance().addPeriodic(pump);
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pipe2);
        Timer.getInstance().addPeriodic(pipe3);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
        Timer.getInstance().addStateful(pipe2);
        Timer.getInstance().addStateful(pipe3);

        //kimenethez ezt ki kell kommentelni
        //
        //first pipe remains the input, third pipe become the output
        g.changePumpDirection(pipe1, pipe3);
    }

    private static void Test21() {
        // create fields
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        Pipe pipe3 = new Pipe();
        //the first two pumps are the input and output
        Pump pump = new Pump(pipe1, pipe2);

        //create characters
        Mechanic mechanic1 = new Mechanic(pump);

        // connect components
        pump.addNeighbor(pipe1); pipe1.addNeighbor(pump);
        pump.addNeighbor(pipe2); pipe2.addNeighbor(pump);
        pump.addNeighbor(pipe3); pipe3.addNeighbor(pump);

        //add players to a field and set active field of player
        //in this test case it doesn't matter on which field does the player stand.
        mechanic1.setActiveField(pump); pump.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pump);
        g.addField(pipe1);
        g.addField(pipe2);
        g.addField(pipe3);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pump);
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pipe2);
        Timer.getInstance().addPeriodic(pipe3);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
        Timer.getInstance().addStateful(pipe2);
        Timer.getInstance().addStateful(pipe3);

        //kimenethez ezt ki kell kommentelni
        //
        //first pipe remains the input, third pipe become the output
        g.changePumpDirection(pipe1, pipe1);
    }

    public static void Test22(){
        // create fields
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe(); pipe2.setSlipperyCounter(4);
        Pipe pipe3 = new Pipe();
        Pump pump1 = new Pump(pipe1, pipe2); pump1.setWater(true);
        Pump pump2 = new Pump(pipe2, pipe3);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        pipe1.addNeighbor(pump1);
        pipe2.addNeighbor(pump1); pipe2.addNeighbor(pump2);
        pipe3.addNeighbor(pump2);
        //add players to a field
        mechanic1.setActiveField(pump1); pump1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        g.setRandom(false);
        //add fields to game
        g.addField(pipe1);
        g.addField(pipe2);
        g.addField(pipe3);
        g.addField(pump1);
        g.addField(pump2);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pipe2);
        Timer.getInstance().addPeriodic(pipe3);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(pump2);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
        Timer.getInstance().addStateful(pipe2);
        Timer.getInstance().addStateful(pipe3);


        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.moveToField(pipe2);
        mechanic1.changePumpDirection(pipe3, pipe2);
    }

    public static void Test23(){
        // create fields
        Pipe pipe1 = new Pipe();
        Pump pump1 = new Pump(pipe1, null);
        //create characters
        Saboteur saboteur1 = new Saboteur();

        // connect components
        pipe1.addNeighbor(pump1);
        //add players to a field
        saboteur1.setActiveField(pipe1); pipe1.addPlayer(saboteur1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur1);
        //add fields to game
        g.addField(pipe1);
        g.addField(pump1);
        //add players to game
        g.addPlayer(saboteur1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pump1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        saboteur1.breakField();
        saboteur1.makeSlippery();
        saboteur1.moveToField(pump1);
    }

    public static void Test24(){
        // create fields
        Pipe pipe1 = new Pipe();
        Cistern cistern1 = new Cistern(new Pump()); cistern1.setInput(pipe1);
        Pump pump1 = new Pump(null, pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic();

        // connect components
        cistern1.addNeighbor(pipe1); pipe1.addNeighbor(cistern1);
        pipe1.addNeighbor(pump1);
        //add players to a field
        mechanic1.setActiveField(cistern1); cistern1.addPlayer(mechanic1);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(pipe1);
        g.addField(pump1);
        g.addField(cistern1);
        //add players to game
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(cistern1);
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pump1);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);

        //kimenethez ezt ki kell kommentelni
        //
        mechanic1.pickup();
        mechanic1.moveToField(pipe1);
        mechanic1.place();
    }

    public static void Test25(){
        // create fields
        Pipe pipeBreak = new Pipe();
        Pipe inputOfMechStartingPump = new Pipe();
        Pipe outOfSecondPump = new Pipe();
        Pipe mechChangesToThis = new Pipe();
        Pump mechStartingPump = new Pump(inputOfMechStartingPump, pipeBreak);
        Pump sabStartingPump = new Pump(pipeBreak, outOfSecondPump);

        //create characters
        Mechanic mechanic1 = new Mechanic(mechStartingPump);
        Saboteur saboteur1 = new Saboteur(pipeBreak);
        Saboteur saboteur2 = new Saboteur(sabStartingPump);

        // connect components
        inputOfMechStartingPump.addNeighbor(mechStartingPump);
        pipeBreak.addNeighbor(mechStartingPump); pipeBreak.addNeighbor(sabStartingPump);
        outOfSecondPump.addNeighbor(sabStartingPump);
        mechChangesToThis.addNeighbor(mechStartingPump); mechStartingPump.addNeighbor(mechChangesToThis);
        //add players to a field
        pipeBreak.addPlayer(saboteur1);
        mechStartingPump.addPlayer(mechanic1);
        sabStartingPump.addPlayer(saboteur2);
        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur1);
        //add fields to game
        g.addField(pipeBreak);
        g.addField(mechStartingPump);
        g.addField(outOfSecondPump);
        g.addField(sabStartingPump);
        g.addField(inputOfMechStartingPump);
        g.addField(mechChangesToThis);
        //add players to game
        g.addPlayer(saboteur1);
        g.addPlayer(mechanic1);
        g.addPlayer(saboteur2);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipeBreak);
        Timer.getInstance().addPeriodic(outOfSecondPump);
        Timer.getInstance().addPeriodic(inputOfMechStartingPump);
        Timer.getInstance().addPeriodic(mechChangesToThis);
        Timer.getInstance().addPeriodic(mechStartingPump);
        Timer.getInstance().addPeriodic(sabStartingPump);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipeBreak);
        Timer.getInstance().addStateful(outOfSecondPump);
        Timer.getInstance().addStateful(inputOfMechStartingPump);
        Timer.getInstance().addStateful(mechChangesToThis);

        //kimenethez ezt ki kell kommentelni
        //
        saboteur1.breakField();
        g.endTurn();
        mechanic1.changePumpDirection(inputOfMechStartingPump, mechChangesToThis);
        g.endTurn();
        saboteur2.changePumpDirection(outOfSecondPump, pipeBreak);
        saboteur2.moveToField(outOfSecondPump);
    }

    public static void Test26(){
        // create fields
        Pipe pipeBreak = new Pipe();
        Pipe mechStartingPipe = new Pipe();
        Pipe pipe = new Pipe();
        Pump sabStartingPump = new Pump(null, pipeBreak);
        Pump pumpBroken = new Pump(pipeBreak, mechStartingPipe); pumpBroken.setBroken(true);
        Pump pumpOneEnd = new Pump(mechStartingPipe, null);
        Pump pumpTwoEnd = new Pump(pipe, null);
        //create characters
        Saboteur saboteur1 = new Saboteur(sabStartingPump);
        Mechanic mechanicRepair = new Mechanic(mechStartingPipe);
        Mechanic mechanicTransfer = new Mechanic(pumpTwoEnd);
        // connect components
        pipeBreak.addNeighbor(sabStartingPump); pipeBreak.addNeighbor(pumpBroken);
        mechStartingPipe.addNeighbor(pumpBroken); mechStartingPipe.addNeighbor(pumpOneEnd);
        pipe.addNeighbor(pumpBroken); pipe.addNeighbor(pumpTwoEnd);
        pumpBroken.addNeighbor(pipe);
        //add players to a field
        sabStartingPump.addPlayer(saboteur1);
        mechStartingPipe.addPlayer(mechanicRepair);
        pumpTwoEnd.addPlayer(mechanicTransfer);
        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur1);
        //add fields to game
        g.addField(pipeBreak);
        g.addField(sabStartingPump);
        g.addField(mechStartingPipe);
        g.addField(pipe);
        g.addField(pumpBroken);
        g.addField(pumpOneEnd);
        g.addField(pumpTwoEnd);
        //add players to game
        g.addPlayer(saboteur1);
        g.addPlayer(mechanicRepair);
        g.addPlayer(mechanicTransfer);
        // add periodics to timer
        Timer.getInstance().addPeriodic(pipeBreak);
        Timer.getInstance().addPeriodic(sabStartingPump);
        Timer.getInstance().addPeriodic(mechStartingPipe);
        Timer.getInstance().addPeriodic(pipe);
        Timer.getInstance().addPeriodic(pumpBroken);
        Timer.getInstance().addPeriodic(pumpOneEnd);
        Timer.getInstance().addPeriodic(pumpTwoEnd);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipeBreak);
        Timer.getInstance().addStateful(pipe);
        Timer.getInstance().addStateful(mechStartingPipe);

        //kimenethez ezt ki kell kommentelni
        //
        saboteur1.moveToField(pipeBreak);
        saboteur1.breakField();
        g.endTurn();
        mechanicRepair.moveToField(pumpBroken);
        mechanicRepair.repairField();
        g.endTurn();
        g.movePipe(mechStartingPipe, pumpBroken, pumpTwoEnd);
        mechanicTransfer.moveToField(mechStartingPipe);
    }

    public static void Test27(){
        // create fields
        Pipe mechStartPipe = new Pipe();
        Pipe pipeOut = new Pipe();
        Pipe pipeTransfer = new Pipe();
        Pump mechStartPump = new Pump(mechStartPipe, pipeOut);
        Pump pump = new Pump(pipeOut, pipeTransfer);
        Cistern mechStartCistern = new Cistern(pipeTransfer);
        //create characters
        Mechanic mechanic1 = new Mechanic(mechStartPump);
        Mechanic mechanic2 = new Mechanic(mechStartPipe);
        Mechanic mechanic3 = new Mechanic(mechStartCistern);
        // connect components
        mechStartPipe.addNeighbor(mechStartPump);
        pipeOut.addNeighbor(mechStartPump); pipeOut.addNeighbor(pump);
        pipeTransfer.addNeighbor(pump); pipeTransfer.addNeighbor(mechStartCistern);
        mechStartCistern.addNeighbor(pipeTransfer);
        //add players to a field
        mechStartPump.addPlayer(mechanic1);
        mechStartPipe.addPlayer(mechanic2);
        mechStartCistern.addPlayer(mechanic3);
        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(mechanic1);
        //add fields to game
        g.addField(mechStartPipe);
        g.addField(pipeOut);
        g.addField(pipeTransfer);
        g.addField(mechStartPump);
        g.addField(pump);
        g.addField(mechStartCistern);
        //add players to game
        g.addPlayer(mechanic1);
        g.addPlayer(mechanic2);
        g.addPlayer(mechanic3);
        // add periodics to timer
        Timer.getInstance().addPeriodic(mechStartPipe);
        Timer.getInstance().addPeriodic(pipeOut);
        Timer.getInstance().addPeriodic(pipeTransfer);
        Timer.getInstance().addPeriodic(mechStartPump);
        Timer.getInstance().addPeriodic(pump);
        Timer.getInstance().addPeriodic(mechStartCistern);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipeOut);
        Timer.getInstance().addStateful(mechStartPipe);
        Timer.getInstance().addStateful(pipeTransfer);

        //kimenethez ezt ki kell kommentelni
        //
        g.movePipe(pipeTransfer, pump, mechStartPump);
        g.endTurn();
        mechanic2.moveToField(mechStartPump);
        mechanic2.changePumpDirection(mechStartPipe, pipeTransfer);
        g.endTurn();
        g.movePipe(pipeTransfer, mechStartPump, pump);
    }

    public static void Test28() {
        // create fields
        Pipe slimyPipe = new Pipe();
        Pump pump1 = new Pump(slimyPipe, null);
        Cistern cistern = new Cistern(slimyPipe);
        //create characters
        Mechanic mechanic1 = new Mechanic(pump1);
        Saboteur saboteur = new Saboteur(cistern);

        // connect components
        slimyPipe.addNeighbor(pump1);   pump1.addNeighbor(slimyPipe);
        slimyPipe.addNeighbor(cistern);

        //add players to a field
        pump1.addPlayer(mechanic1);
        cistern.addPlayer(saboteur);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur);
        g.setRandom(false);

        //add fields to game
        g.addField(slimyPipe);
        g.addField(pump1);
        g.addField(cistern);

        //add players to game
        g.addPlayer(mechanic1);
        g.addPlayer(saboteur);

        // add periodics to timer
        Timer.getInstance().addPeriodic(slimyPipe);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(cistern);
        //add statefuls to timer
        Timer.getInstance().addStateful(slimyPipe);

        //kimenethez ezt ki kell kommenteln
        saboteur.moveToField(slimyPipe);
        saboteur.makeSlippery();
        saboteur.moveToField(pump1);
        g.endTurn();
        mechanic1.moveToField(slimyPipe);
    }

    public static void Test29() {
        // create fields
        Pipe pipe = new Pipe();
        Pump pump1 = new Pump(pipe, null);
        Cistern cistern = new Cistern(pipe);
        //create characters
        Mechanic mechanic1 = new Mechanic(pump1);
        Saboteur saboteur = new Saboteur(cistern);

        // connect components
        pipe.addNeighbor(pump1);
        pump1.addNeighbor(pipe);
        pipe.addNeighbor(cistern);

        //add players to a field
        pump1.addPlayer(mechanic1);
        cistern.addPlayer(saboteur);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur);
        g.setRandom(false);

        //add fields to game
        g.addField(pipe);
        g.addField(pump1);
        g.addField(cistern);

        //add players to game
        g.addPlayer(saboteur);
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(cistern);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe);

        //kimenethez ezt ki kell kommente
        saboteur.moveToField(pipe);
        saboteur.breakField();
        saboteur.moveToField(pump1);
        g.endTurn();
        mechanic1.moveToField(pipe);
        mechanic1.repairField();
    }

    public static void Test30() {
        // create fields
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        Pump pump1 = new Pump(pipe1, null);
        pump1.setBroken(true);
        Cistern cistern = new Cistern(pipe1);
        //create characters
        Mechanic mechanic1 = new Mechanic(pump1);
        Mechanic saboteur = new Mechanic(cistern);

        // connect components
        pipe1.addNeighbor(pump1);
        pump1.addNeighbor(pipe1);
        pipe1.addNeighbor(cistern);
        cistern.addNeighbor(pipe1);
        pump1.addNeighbor(pipe2);
        pipe2.addNeighbor(pump1);

        //add players to a field
        pump1.addPlayer(mechanic1);
        cistern.addPlayer(saboteur);

        // create game
        Game g = Game.getInstance();
        g.setActivePlayer(saboteur);
        g.setRandom(false);

        //add fields to game
        g.addField(pipe1);
        g.addField(pipe2);
        g.addField(pump1);
        g.addField(cistern);

        //add players to game
        g.addPlayer(saboteur);
        g.addPlayer(mechanic1);

        // add periodics to timer
        Timer.getInstance().addPeriodic(pipe1);
        Timer.getInstance().addPeriodic(pipe2);
        Timer.getInstance().addPeriodic(pump1);
        Timer.getInstance().addPeriodic(cistern);
        //add statefuls to timer
        Timer.getInstance().addStateful(pipe1);
        Timer.getInstance().addStateful(pipe2);

        //kimenethez ezt ki kell kommente
//        saboteur.moveToField(pipe1);
//        saboteur.breakField();
//        saboteur.moveToField(pump1);
//        g.endTurn();
//        mechanic1.moveToField(pipe);
//        mechanic1.repairField();
    }
}