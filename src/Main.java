
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import transportNetwork.*;
import dataStructures.exceptions.*;
import java.io.*;
import dataStructures.*;

/**
 * Main class of the Transportation Network application.
 * Handles command-line input/output, file loading, and command execution.
 * Space Complexity:
 * O(1) - Main itself stores only a fixed set of static String constants and local references
 * without dynamic collections
 *
 * @author Lenine Semedo 73327 & Joao Goncalves 73439
 */

public class Main {

    private static String SERIALIZED_FILE_NAME = "file.dat";
    private static String TRANSPORTATION_RESTORED_SUCESS = "Transportation network restored successfully.";
    private static String TRANSPORTATION_NOT_FOUND = "Transportation network not found.";
    private static String NO_LINE_MSG = "No line definition.";
    private static String LOADING_LINE = "Loading line definition. %d stations loaded.\n";
    private static String JOURNEY_ALREADY_EXISTS_MSG = "Journey already exists.";
    private static String JOURNEY_SUCCESSFULLY_ADDED_MSG = "Journey successfully added.";
    private static String JOURNEY_PRINT_MSG = "Journey: %s\n";
    private static String DEPARTURE_STATION_PRINT_MSG = "Departure station: %s\n";
    private static String DEPARTURE_TIME_PRINT_MSG = "Departure time: %s\n";
    private static String ARRIVAL_STATION_PRINT_MSG = "Arrival station: %s\n";
    private static String ARRIVEL_TIME_PRINT_MSG = "Arrival time: %s\n";
    private static String NO_SUITABLE_JOURNEY_MSG = "No direct journey is available.";
    private static String INVALID_STATION_MSG = "Invalid station.";
    private static String INVALID_TIME_FORMAT_MSG = "Invalid time.";
    private static String INVALID_STATION_ORDER_MSG = "Stations are not in the correct order.";
    private static String STOPS_JOURNEY_PRINT_MSG = "Stops: %d\n";
    private static String INVALIDE_INTERVAL_TIME ="Invalid interval time.";
    private static String JOURNEY_SUCCESSFULY_REMOVE = "Journey successfully removed.";
    private static String INVALID_JOURNEY ="Invalid journey.";
    private static String SAVED_TRANSPORTATION_APP_MSG = "Transportation network saved.";
    private static String QUIT_MSG = "Goodbye.";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        Network tn = NetworkClass.load(SERIALIZED_FILE_NAME);

        if (tn!=null) {
            System.out.println(TRANSPORTATION_RESTORED_SUCESS);
        }
        else if (args.length > 0) {
            tn = readFile(args[0]);
        }
        if (tn == null) {
            System.out.println(NO_LINE_MSG);
            in.close();
            return;
        }
        executeCommand(in, tn);
        in.close();
    }

    /**
     * Time Complexity:
     * Best case: O(1) - Constant if the file does not exist //fixme
     * - Worst case: O(n) - Linear because it uses a loop that reads n elements.
     *
     * Reads the line definition from a text file and registers all stations in the network.
     * @param fileName path to the line definition file
     * @return initialized with loaded stations, or null if the file is not found
     * @throws FileNotFoundException if the file cannot be opened
     */
    private static Network readFile(String fileName) throws FileNotFoundException {
        try (Scanner reader = new Scanner(new File(fileName))){
            Network tn = new NetworkClass();

            int size = Integer.parseInt(reader.nextLine().trim());

            for (int i = 0; i < size;i++){
                String stationName = reader.nextLine().trim();
                tn.addStation(stationName);
            }
            System.out.println(TRANSPORTATION_NOT_FOUND);
            System.out.printf(LOADING_LINE,size);
            return tn;

        }catch (FileNotFoundException e){
            return null;
        }
    }


    /**
     * Contains all enum
     */
    private enum Command {
        addJourney("addJourney: Inserts a new journey into the transportation network."),
        findJourney("findJourney: Finds the earliest journey that allows a passenger to travel from one station to another."),
        findJourneyMinStops("findJourneyMinStops: Finds the direct journey with the minimum number of stops."),
        removeJourney("removeJourney: Removes the given journey from the transportation network."),
        help("help: Displays the list of available commands."),
        quit("quit: Saves the transportation network and terminates the application.");

        private String msg;

        Command(String msg) {
            this.msg = msg;
        }

        String getMsg() {
            return this.msg;
        }

        /**
         * Prints the description of every available command.
         */
        private void executeHelp() {
            for (int i = 0; i < Command.values().length; i++) {
                System.out.println(Command.values()[i].getMsg());
            }
        }
    }

    /**
     * Returns command
     *
     * @param in Scanner input
     * @return Command
     */
    private static Command getCommand(Scanner in) {
        String cmd = in.nextLine().trim();
        Command finalCmd = null;
        for (int i = 0; i < Command.values().length; i++) {
            if (Command.values()[i].toString().equalsIgnoreCase(cmd))
                finalCmd = Command.values()[i];
        }
        return finalCmd;
    }


    /**
     * Processes command interpreter.
     * @param in Scanner input
     * @param tn Transport Network App
     * @param command command
     */
    private static void processCommand(Scanner in, Network tn, Command command) {
        switch (command) {
            case help -> command.executeHelp();
            case addJourney -> addJourneyCmd(in, tn);
            case findJourney -> findJourneyCmd(in,tn);
            case findJourneyMinStops -> findJourneyMinStopsCmd(in, tn);
            case removeJourney -> removeCmd(in,tn);
            case quit -> quitCmd(tn);
        }
    }

    /**
     * Executes command interpreter.
     *
     * @param in Scanner input
     * @param tn Transport Network
     */
    private static void executeCommand(Scanner in, Network tn) {
        Command command;
        do {
            command = getCommand(in);
            processCommand(in, tn, command);
        } while (!command.equals(Command.quit));
    }

    /**
     * Time Complexity:
     * Best Case: O(K) - Reads all K stops from input in a loop, checks hasJourney in O(1) (no hash collisions), and either skips insertion if the journey already exists or inserts all K stops in O(1) each via addJourney.
     * Worst Case: O(n^2) - Reads all n stops, incurs O(n) hash collisions in hasJourney, and executes addJourney where each of the n stops may face O(n) collisions in line, O(n) traversal in a skewed station departure tree, and O(n^2) if journeys triggers a rehash with collisions.
     * Executes the addJourney command, reading journey details and inserting the new journey into the network.
     *
     * @param in Scanner reading user input
     * @param tn Transport Network instance
     */
    private static void addJourneyCmd(Scanner in, Network tn) {
        String journeyId = in.nextLine().trim();
        int nStops = in.nextInt();
        in.nextLine();
        boolean exists = tn.hasJourney(journeyId);

        String[] stationsName = new String[nStops];
        String[] arrivals = new String[nStops];
        String[] departures = new String[nStops];

        for(int i = 0; i<nStops;i++){
            String stationName = in.nextLine().trim();
            String arrivalTime = null;
            String departureTime = null;

            if(i==0){
                departureTime = in.nextLine().trim();
            } else if (i== nStops-1) {
                arrivalTime = in.nextLine().trim();
            }else{
                arrivalTime = in.nextLine().trim();
                departureTime = in.nextLine().trim();
            }
            stationsName[i] = stationName;
            arrivals[i] = arrivalTime;
            departures[i] = departureTime;
        }

        if (exists) {
            System.out.println(JOURNEY_ALREADY_EXISTS_MSG);
        }
        else{
            tn.addJourney(journeyId,stationsName,arrivals,departures);
            System.out.println(JOURNEY_SUCCESSFULLY_ADDED_MSG);
        }
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Validation fails immediately, or hash lookups in line resolve in O(1) and the first departure at the root of the station's schedule reaches arrivalStation at the first stop checked.
     * Worst Case: O(n^2) - All n stations collide in the hash table (O(n)) and all n departures at the departure station must be checked in a skewed tree while scanning up to n stops per journey.
     * Executes the findJourney command, finding and printing the earliest direct journey from the origin station to the destination station.
     *
     * @param in Scanner reading user input
     * @param tn Transport Network instance
     */
    private static void findJourneyCmd(Scanner in, Network tn) {
        try {
            String departureStation = in.nextLine().trim();
            String arrivalStation = in.nextLine().trim();
            String passengerTime = in.nextLine().trim();
            Ticket suitableJourney = tn.findJourney(departureStation,arrivalStation, passengerTime);
            if(suitableJourney!=null){
                System.out.printf(JOURNEY_PRINT_MSG, suitableJourney.journey().getId());
                System.out.printf(DEPARTURE_STATION_PRINT_MSG,suitableJourney.departureStation().getName());
                System.out.printf(DEPARTURE_TIME_PRINT_MSG, suitableJourney.departureTime().toString());

                System.out.printf(ARRIVAL_STATION_PRINT_MSG,suitableJourney.arrivalStation().getName());
                System.out.printf(ARRIVEL_TIME_PRINT_MSG,suitableJourney.arrivalTime().toString());
            }else{
                System.out.println(NO_SUITABLE_JOURNEY_MSG);
            }
        }catch (InvalidStationException e){
            System.out.println(INVALID_STATION_MSG);
        }catch(InvalidTimeFormatException e){
            System.out.println(INVALID_TIME_FORMAT_MSG);
        }catch(InvalidStationOrderException e){
            System.out.println(INVALID_STATION_ORDER_MSG);
        }
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Validation fails early in constant time, or hash lookups resolve in O(1) with no departures falling within the specified time interval.
     * Worst Case: O(n^2) - All n stations collide in the hash table (O(n)) and all n departures fall within the time window, scanning up to n stops per journey to find the minimum stop count.
     * Executes the findJourneyMinStops command, finding and printing the direct journey with the fewest intermediate stops within a time interval.
     *
     * @param in Scanner reading user input
     * @param tn Transport Network instance
     */
    private static void findJourneyMinStopsCmd(Scanner in, Network tn){
        try {
            String departureStation = in.nextLine().trim();
            String arrivalStation = in.nextLine().trim();
            String miniumTime = in.next().trim();
            String maximumTime = in.next().trim();
            in.nextLine();
            Ticket suitableJourney = tn.findJourneyMinStops(departureStation,arrivalStation,miniumTime,maximumTime);

            if (suitableJourney != null){
                System.out.printf(JOURNEY_PRINT_MSG, suitableJourney.journey().getId());
                System.out.printf(DEPARTURE_STATION_PRINT_MSG,suitableJourney.departureStation().getName());
                System.out.printf(DEPARTURE_TIME_PRINT_MSG, suitableJourney.departureTime().toString());
                System.out.printf(ARRIVAL_STATION_PRINT_MSG,suitableJourney.arrivalStation().getName());
                System.out.printf(ARRIVEL_TIME_PRINT_MSG,suitableJourney.arrivalTime().toString());
                System.out.printf(STOPS_JOURNEY_PRINT_MSG,suitableJourney.stop());
            }else{
                System.out.println(NO_SUITABLE_JOURNEY_MSG);
            }
            
        }catch (InvalidStationException e){
            System.out.println(INVALID_STATION_MSG);
        }catch(InvalidTimeFormatException e){
            System.out.println(INVALID_TIME_FORMAT_MSG);
        }catch (InvalideTimeException e ){
            System.out.println(INVALIDE_INTERVAL_TIME);
        } catch(InvalidStationOrderException e){
            System.out.println(INVALID_STATION_ORDER_MSG);
        }

    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Journey identifier is not found in the hash table (with no collisions), returning false immediately in constant time.
     * Worst Case: O(n^2) - Hash bucket has n collisions when removing the journey O(n), and iterating through all n stops requires O(n) traversal in each station's departure tree and departure list to remove the journey.
     * Executes the removeJourney command, removing the specified journey from the transportation network.
     *
     * @param in Scanner reading user input
     * @param tn Transport Network instance
     */
    private static void removeCmd(Scanner in, Network tn) {

        String journeyId = in.nextLine().trim();

        if (tn.removeJourney(journeyId)) {
            System.out.println(JOURNEY_SUCCESSFULY_REMOVE);

        } else
            System.out.println(INVALID_JOURNEY);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Network is empty or opening the output stream fails immediately in constant time.
     * Worst Case: O(n^2) - Serializes the entire network state to the file, traversing all n stations, n journeys, and their n stops and departures.
     * Executes the quit command, saving the transportation network state and terminating execution.
     *
     * @param tn Transport Network instance
     */
    private static void quitCmd(Network tn) {
            tn.storeCities(SERIALIZED_FILE_NAME);
        System.out.println(SAVED_TRANSPORTATION_APP_MSG);
        System.out.println(QUIT_MSG);
    }
}

