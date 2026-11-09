/**
 * Lenine Semedo 73327 & João Gonçalves 73439  daasaaddasda
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import transportNetwork.*;
import dataStructures.exceptions.*;
public class Main {

    private static String FILE_NAME = "data.txt";
    private static String TRANSPORTATION_RESTORED_SUCESS = "Transportation network restored successfully.";
    private static String TRANSPORTATION_NOT_FOUND = "Transportation network not found.";
    private static String NO_LINE = "No line definition.";
    private static String LOADDING_LINE = "Loading line definition. <%d> stations loaded\n.";
    private static String JOURNEY_ALREADY_EXISTS_MSG = "Journey already exists.";
    private static String JOURNEY_SUCCESSFULLY_ADDED_MSG = "Journey successfully added.";

    //fixme: ficheiro
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        Network tn = new NetworkClass();
        executeCommand(in, tn);
        in.close();
    /*
    File file = new File(FILE_NAME);

    if (file.exists()) {
     //   tn = new NetworkClass().load(FILE_NAME);
        System.out.println(TRANSPORTATION_RESTORED_SUCESS);

    }

    else if (args.length > 0) {
        tn = readFile(args[0]);
        // carregar do ficheiro

    } else if (tn == null) {
        System.out.println(NO_LINE);
        return;
    }
        executeCommand(in,tn);

     */
    }
/*
    //fixme: colequei algumas linhas como comentario somente para evitar erro, mas depois terminarei essa parte
    private static Network readFile(String nameFile) throws FileNotFoundException {

        File file = new File(nameFile);

        if (!file.exists()) {
            return null;
        }
        System.out.println(TRANSPORTATION_NOT_FOUND);

        try (Scanner leitor = new Scanner(new FileReader(nameFile))){
            Network tn = new NetworkClass();

            int size = leitor.nextInt();
           // leitor.nextLine();

            for (int i = 0; i < size;i++ ){
               String nameStation = leitor.nextLine().trim();
            //   tn.addStation(nameStation); // TODO: classe sistema, responsavel por adicionar estações na SepChainHashTable e  ArrayClass
            }

            System.out.printf(LOADDING_LINE,size);
             return  tn;

        }catch (FileNotFoundException e){
            return null;
        }
    }

 */


    /**
     * Contains all enum
     */
    private enum Command {
        addJourney("addJourney: Inserts a new journey into the transportation network.\n"),
        findJourney("findJourney: Finds the earliest journey that allows a passenger to travel from one station to another.\n"),
        findJourneyMinStops("findJourneyMinStops: Finds the direct journey with the minimum number of stops.\n"),
        removeJourney("removeJourney: Removes the given journey from the transportation network.\n"),
        help("help: Displays the list of available commands.\n"),
        quit("quit: Saves the transportation network and terminates the application.\n");

        private String msg;

        Command(String msg) {
            this.msg = msg;
        }

        String getMsg() {
            return this.msg;
        }

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
     *
     * @param in      Scanner input
     * @param tn      Transport Network App
     * @param command command
     */
    private static void processCommand(Scanner in, Network tn, Command command) {
        switch (command) {
            case addJourney -> addJourneyCmd(in, tn);
            case help -> command.executeHelp();
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
        String journeyId = in.nextLine();
        int nStops = in.nextInt();
        in.nextLine();
        boolean exists = tn.hasJourney(journeyId);
        if (exists) {
            for (int i = 0; i < nStops; i++) {
                in.nextLine();
                if (i == 0) {
                    in.nextLine();
                } else if (i == nStops - 1) {
                    in.nextLine();
                } else {
                    in.nextLine();
                    in.nextLine();
                }
                System.out.println(JOURNEY_ALREADY_EXISTS_MSG);
            }
        }
        else{
                tn.addJourney(journeyId);
                for (int i = 0; i < nStops; i++) {
                    String stationName = in.nextLine();
                    String departureTime = null;
                    String arrivalTime = null;
                    if (i == 0) {
                        departureTime = in.nextLine();
                    } else if (i == nStops - 1) {
                        arrivalTime = in.nextLine();
                    } else {
                        arrivalTime = in.nextLine();
                        departureTime = in.nextLine();
                    }
                    tn.addStopToJourney(journeyId, stationName, departureTime, arrivalTime);
                }
                System.out.println(JOURNEY_SUCCESSFULLY_ADDED_MSG);
            }
        }
    }

