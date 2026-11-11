package transportNetwork;

import java.util.*;

/**
 *  Represents trains transportations
 */
public class NetworkClass implements Network{
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
            // Cria a nova Journey
            Journey newJourney = new JourneyClass(journeyId);
            journeys.put(journeyId,newJourney);
    }

    @Override
    public boolean hasJourney(String id){
        return journeys.containsKey(id);
    }


    @Override
    public void addStopToJourney(String journeyId, String stationName, String departureTime, String arrivalTime) {
        // get a journey
        Journey journey = journeys.get(journeyId);
        //Adicionei a estção na linha
        Station station = new StationClass(stationName);
        line.put(stationName,station);
        // Adicionei o stop na journey
        Stops stop = new StopClass(station,journey,departureTime,arrivalTime);
        journey.addStop(stop);
    }
}

// tick - vai ter a linha a estação de partida com a hora e a chegada com a hora