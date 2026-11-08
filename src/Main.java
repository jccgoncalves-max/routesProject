/**
 * Lenine Semedo 73327 & João Gonçalves 73439
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.PrimitiveIterator;
import java.util.Scanner;
import transportNetwork.*;
public class Main {

    private static String FILE_NAME = "data.txt";
    private static String TRANSPORTATION_RESTORED_SUCESS = "Transportation network restored successfully.";
    private static String TRANSPORTATION_NOT_FOUND = "Transportation network not found.";
    private static String NO_LINE = "No line definition.";
    private static String LOADDING_LINE=  "Loading line definition. <%d> stations loaded\n.";

    //fixme: ficheiro
    public static void main(String[] args) throws FileNotFoundException {
    Scanner in = new Scanner(System.in);
    Network tn = null;

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
    in.close();
    }

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

    private static void addJourney(){

    }







}