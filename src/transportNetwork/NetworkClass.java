package transportNetwork;

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
    public void addStopToJourney(String journeyId, String stationName, String departureTime, String arrivalTime) {
        Journey journey = journeys.get(journeyId.toLowerCase());//todo: confirmar se todo acesso deve ser lowercase??
        Station station = new StationClass(stationName);
        line.put(stationName.toLowerCase(),station);
        Stops stop = new StopClass(station,journey,getLocalTime(departureTime),getLocalTime(arrivalTime));//aqui ja adiciona as referências de station e journy
        journey.addStop(stop);
    }

    @Override
    public boolean isJourneySuitable(String departureStation, String arrivalStation, String passengerTime) {
        Station station = line.get(departureStation);
        station.getSuitableJourney(arrivalStation,getLocalTime(passengerTime));
        return false;
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
