package transportNetwork;

import dataStructures.exceptions.InvalidStationException;
import dataStructures.exceptions.InvalidStationOrderException;
import dataStructures.exceptions.InvalidTimeFormatException;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;
import java.time.*;
/**
 *  Represents trains transportations
 */
public class NetworkClass implements Network{
    private final DateTimeFormatter TIME_FORMAT  = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);//todo: Pesquisar sobre o método
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
    public void addJourney(String journeyId, String[] stationsName, String[] departures, String[] arrivals) {
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

            Stops stop = new StopClass(station, journey, departureTime, arrivalTime);
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
            throw new InvalidStationException();

        Station station = line.get(departureStation.toLowerCase());
        return station.getSuitableJourney(arrivalStation,time);
    }

    private LocalTime getLocalTime(String time){
        if (time == null)
            return null;
        try{
            return LocalTime.parse(time,TIME_FORMAT);
        }catch(DateTimeParseException e) {
            return null;
        }
    }
}

// tick - vai ter a linha a estação de partida com a hora e a chegada com a hora