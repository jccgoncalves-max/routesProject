package transportNetwork;

import dataStructures.exceptions.*;
import dataStructures.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.time.*;

import java.io.*;
/**
 *  Represents trains transportations
 */
public class NetworkClass implements Network,Serializable{
    private static final long serialVersionUID = 1L;

    private final static DateTimeFormatter TIME_FORMAT  = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
    /**
     * Sequence of stations
     */
     private Map<String,Station> line;
    /**
     * Collection of journeys
     */
    private Map<String,Journey> journeys;


    /**
     * Train Network constructor
     */
    public NetworkClass(){
     journeys = new SepChainHashTable<>(100);
     line = new SepChainHashTable<>(100);

    }

    @Override
    public boolean hasJourney(String id){
        return journeys.get(id.toLowerCase()) != null;
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

        if(line.get(departureStation.toLowerCase()) == null || line.get(arrivalStation.toLowerCase()) == null)
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

        Station depStation = line.get(departureStation.toLowerCase());
        Station arrStation = line.get(arrivalStation.toLowerCase());

        if (depStation == null || arrStation== null){
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

        if (depStation.getLinePosition() >= arrStation.getLinePosition() ){
            throw new InvalidStationOrderException();
        }

        return depStation.getSuitableJourneyMinStops(arrivalStation,minTime,maxTime);
    }

    @Override
    public boolean removeJourney(String journeyId) {
        Journey journey = journeys.remove(journeyId.toLowerCase());
        if (journey == null){
            return false;
        }

        Iterator<Stops> it = journey.getStops().iterator();
        while (it.hasNext()) {
            it.next().getStation().removeJourney(journey);
        }
        return true;
    }


    @Override
    public void addStation(String nameStation) {
        String key = nameStation.toLowerCase();
        if (line.get(key) == null) {
            int position = line.size();
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
            return LocalTime.parse(time.trim(),TIME_FORMAT);
        }catch(DateTimeParseException e) {
            return null;
        }
    }

}
