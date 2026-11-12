package transportNetwork;

import dataStructures.exceptions.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;
import java.time.*;

import java.io.*;
/**
 *  Represents trains transportations
 */
public class NetworkClass implements Network{
    private static final long serialVersionUID = 1L;
  //  private final DateTimeFormatter TIME_FORMAT  = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);//todo: Pesquisar sobre o método
    /**
     * Sequence of stations
     */
     private Map<String,Station> line;
    /**
     * Collection of journeys
     */
    private Map<String,Journey> journeys;//todo: HashMap vai devolver um iterador que percorre por ordem(para serialização);
    /**
     * Structure to serialize the train station.
     */
    private List<Station> serializedLine;


    /**
     * Train Network constructor
     */
    public NetworkClass(){
     journeys = new HashMap<>(20);
     line = new HashMap<>(20);
     serializedLine = new LinkedList<>();

    }

    @Override
    public boolean hasJourney(String id){
        return journeys.containsKey(id.toLowerCase());
    }


    @Override
    public void addJourney(String journeyId, String[] stationsName, String[] arrivals, String[] departures) {
        Journey journey = new JourneyClass(journeyId);
        journeys.put(journeyId.toLowerCase(),journey);

        for(int i = 0; i< stationsName.length;i++){
            Station station = line.get(stationsName[i].toLowerCase());

            if(station==null) {
                station = new StationClass(stationsName[i], i);
                line.put(stationsName[i].toLowerCase(), station);
            }

            LocalTime departureTime = getLocalTime(departures[i]);
            LocalTime arrivalTime = getLocalTime(arrivals[i]);

            if(departureTime!= null)
                station.addDeparture(departureTime,journey);

            Stops stop = new StopClass(station, journey,arrivalTime,departureTime);
            journey.addStop(stop);
        }

    }


    @Override
    public Ticket findJourney(String departureStation, String arrivalStation, String passengerTime) throws InvalidStationException,
            InvalidTimeFormatException, InvalidStationOrderException {
        if(!line.containsKey(departureStation.toLowerCase())||!line.containsKey(arrivalStation.toLowerCase()))
            throw new InvalidStationException();

        LocalTime time = getLocalTime(passengerTime);
        if(time == null)
            throw new InvalidTimeFormatException();

        if(line.get(departureStation.toLowerCase()).getLinePosition()>=line.get(arrivalStation.toLowerCase()).getLinePosition())
            throw new InvalidStationOrderException();

        Station station = line.get(departureStation.toLowerCase());

        return station.getSuitableJourney(arrivalStation,time);
    }

    @Override
    public Ticket findJourneyMinStops(String departureStation, String arrivalStation, String miniumTime, String maximumTime)throws InvalidStationException,
            InvalidTimeFormatException, InvalidStationOrderException, InvalideTimeException {

        if (!line.containsKey(departureStation.toLowerCase()) || !line.containsKey(arrivalStation.toLowerCase())){
            throw  new InvalidStationException();
        }
        LocalTime minTime = getLocalTime(miniumTime);
        LocalTime maxTime = getLocalTime(maximumTime);

        if (minTime == null || maxTime == null){
            throw new InvalidTimeFormatException();
        }
        if (minTime.isAfter(maxTime)){
            throw new InvalideTimeException();
        }

        if (line.get(departureStation.toLowerCase()).getLinePosition() > line.get(arrivalStation.toLowerCase()).getLinePosition() ){
            throw new InvalidStationOrderException();
        }

        Station station = line.get(departureStation.toLowerCase());
        return station.getSuitableJourneyMinStops(arrivalStation,minTime,maxTime);
    }

    @Override
    public boolean removeJourney(String journeyId) {
        Journey journey = journeys.remove(journeyId.toLowerCase());
        if (journey == null){
            return false;
        }
        for (Stops stop : journey.getStops()) {
            stop.getStation().removeJourney(journey);
        }
        return true;
    }


    @Override
    public void addStation(String nameStation) {
        String key = nameStation.toLowerCase();
        if (!line.containsKey(key)) {
            int position = line.size(); // Atribui pos pela ordem de leitura
            Station station = new StationClass(nameStation, position);
            line.put(key, station);
        }
    }
@Override
public void storeCities(String fileName){
        try{
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(fileName));
            file.writeObject(this);
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new OutputWriteException();
        }
    }

    @Override
    public String getNormalizedString(String input) {
        String finalString = input.substring(0,1).toUpperCase()+ input.substring(1).toLowerCase();

        return finalString   ;
    }


    /**
     *Loads and deserializes a saved Network object from a file.
     * Time Complexity:
     * Best Case: O(1) - File is missing or empty, failing in constant time.
     * Worst Case: O(N) - Reads and deserializes N saved cities into memory.
     * @param name Serialization file1
     * @return Loaded Network object, or null if loading fails
     */
    public static Network load(String name) {
        Network tn = null ;
        try{
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream(name) );
            tn = (Network) file.readObject();
            file.close();
        }
        catch (IOException e ) {
            tn = null;
        }
        catch (ClassNotFoundException e) {
            tn = null;
        }
        return tn;
    }

    /**
     * Converts string to LocalTime object
     * @param time time
     * @return LocalTime time
     */
    private LocalTime getLocalTime(String time){
        if (time == null)
            return null;
        try{
            return LocalTime.parse(time);
        }catch(DateTimeParseException e) {
            return null;
        }
    }

}
