/**
 * Lenine Semedo 73327 & João Gonçalves 73439
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import transportNetwork.*;
import dataStructures.exceptions.*;
import java.io.*;
import java.util.*;
public class Main {

    private static String FILE_NAME = "data.txt";
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
    private static String INVALID_JOURNEY_MSG = "Invalid journey.";
    private static String REMOVED_JOURNEY_MSG = "Journey successfully removed.";
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
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
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
         *
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
     * Inserts a new journey into the transportation network.
     *
     * @param in Scanner input
     * @param tn Transport Network
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
     * Finds the earliest journey that allows a passenger to travel from the origin station to the destination station
     * w/o any transfers.
     * @param in Scanner in
     * @param tn Transport network app
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
     * Finds the journey with the minimum number of stops that allows a passenger to travel from the
     * origin station to the destination station without making any transfers within a specified time
     * interval.
     * @param in Scanner input
     * @param tn Transport Network app
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
     * Removes the journey with the given identifier from the transportation network.
     * @param in Scanner input
     * @param tn Transport Network app
     */
    private static void removeCmd(Scanner in, Network tn) {

        String journeyId = in.nextLine().trim();

        if (tn.removeJourney(journeyId)) {
            System.out.println(JOURNEY_SUCCESSFULY_REMOVE);

        } else
            System.out.println(INVALID_JOURNEY);
    }

    /**
     * Terminates program execution
     * @param tn Transport Network app
     */
    private static void quitCmd(Network tn) {
        try{
            tn.storeCities(SERIALIZED_FILE_NAME);
        } catch (RuntimeException e) {
        }
        System.out.println(SAVED_TRANSPORTATION_APP_MSG);
        System.out.println(QUIT_MSG);
    }
}

