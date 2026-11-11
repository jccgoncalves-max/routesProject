package transportNetwork;

import dataStructures.exceptions.InvalidStationException;
import dataStructures.exceptions.InvalidStationOrderException;
import dataStructures.exceptions.InvalidTimeFormatException;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.*;
/**
 *  Represents trains transportations
 */
public class NetworkClass implements Network{
    private final DateTimeFormatter TIME_FORMAT  = DateTimeFormatter.ofPattern("HH:mm");
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
    public void addJourney(String journeyId){
            Journey newJourney = new JourneyClass(journeyId);
            journeys.put(journeyId.toLowerCase(),newJourney);
    }

    @Override
    public boolean hasJourney(String id){
        return journeys.containsKey(id.toLowerCase());
    }


    @Override
    public void addStopToJourney(String journeyId, String stationName, String departureTime, String arrivalTime,int position) {
        Journey journey = journeys.get(journeyId.toLowerCase());
        Station station = new StationClass(stationName,position);
        station.addDeparture(getLocalTime(departureTime),journey);
        line.put(stationName.toLowerCase(),station);
        Stops stop = new StopClass(station,journey,getLocalTime(departureTime),getLocalTime(arrivalTime));
        journey.addStop(stop);
    }


    @Override
    public Ticket findJourney(String departureStation, String arrivalStation, String passengerTime) throws InvalidStationException,
            InvalidTimeFormatException, InvalidStationOrderException {
        if(!line.containsKey(departureStation)||!line.containsKey(arrivalStation))
            throw new InvalidStationException();
        LocalTime time = getLocalTime(passengerTime);
        if(time == null)
            throw new InvalidTimeFormatException();
        if(line.get(departureStation.toLowerCase()).getLinePosition()>line.get(arrivalStation.toLowerCase()).getLinePosition())
            throw new InvalidStationException();
        Station station = line.get(departureStation.toLowerCase());
        return station.getSuitableJourney(arrivalStation,time);
    }
    /**
     * Converts String to desired LocalTime format
     * @param time time
     * @return LocalTime object
     */
    private LocalTime getLocalTime(String time){
        try{
            return LocalTime.parse(time,TIME_FORMAT);
        }catch(DateTimeParseException e) {
            return null;
        }
    }
}
