/**
 * Lenine Semedo 73327 & João Gonçalves 73439
 */

import java.util.Scanner;
import transportNetwork.*;
public class Main {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    Network tn = new NetworkClass();
    executeCommand(in,tn);
    in.close();
    }

    private enum Command {
        addJourney("addJourney: Inserts a new journey into the transportation network.\n"),
        findJourney("findJourney: Finds the earliest journey that allows a passenger to travel from one station to another.\n"),
        findJourneyMinStops("findJourneyMinStops: Finds the direct journey with the minimum number of stops.\n"),
        removeJourney("removeJourney: Removes the given journey from the transportation network.\n"),
        help("help: Displays the list of available commands.\n"),
        quit("quit: Saves the transportation network and terminates the application.\n");

        private String msg;

        Command (String msg){
            this.msg = msg;
        }

        String getMsg(){
            return this.msg;
        }
        private void executeHelp(){
            for(int i = 0 ; i<Command.values().length;i++){
                System.out.println(Command.values()[i].getMsg());
            }
        }
    }

    /**
     * Returns command
     * @param in Scanner input
     * @return Command
     */
    private static Command getCommand (Scanner in){
        String cmd = in.nextLine().trim();
        Command finalCmd = null;
        for(int i = 0;i<Command.values().length;i++){
            if(Command.values()[i].toString().equalsIgnoreCase(cmd))
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
    private static void processCommand(Scanner in, Network tn, Command command){
        switch(command){
            case help -> command.executeHelp();
        }

    }

    /**
     * Executes command interpreter.
     * @param in Scanner input
     * @param tn Transport Network
     */
    private static void executeCommand(Scanner in, Network tn){
        Command command;
        do{
            command = getCommand(in);
            processCommand(in,tn,command);
        }while(!command.equals(Command.quit));
    }
}