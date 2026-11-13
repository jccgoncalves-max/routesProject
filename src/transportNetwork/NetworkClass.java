package transportNetwork;

import dataStructures.exceptions.*;
import dataStructures.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.time.*;

import java.io.*;
/**
 * Represents the train transportation network, managing stations, journeys,
 * route searches, and persistence.
 * Space Complexity:
 * O(n) - Stores n stations in the line hash table and J journeys in the journeys
 * hash table
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
     * Time Complexity:
     * Best Case: O(1) - Initializes both hash tables with a fixed initial capacity of 100 in constant time.
     * Worst Case: O(1) - Fixed initial capacity requires a constant number of bucket allocations without depending on input size.
     * Train Network constructor.
     */
    public NetworkClass(){
     journeys = new SepChainHashTable<>(100);
     line = new SepChainHashTable<>(100);

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Hash lookup finds the journey at the head of its bucket (or an empty bucket) in constant time with no collisions.
     * Worst Case: O(n) - All n journeys collide into the same hash bucket, requiring a linear traversal of that bucket's list.
     * Checks whether a journey with the given identifier exists in the network.
     *
     * @param id unique identifier of the journey
     * @return true if the journey exists in the network, false otherwise
     */
    @Override
    public boolean hasJourney(String id){
        return journeys.get(id.toLowerCase()) != null;
    }

    /**
     * Time Complexity:
     * Best Case: O(K) - Loops through all K stops of the journey, performing O(1) hash lookups/insertions, O(1) departure insertions at the root of each station's tree, and O(1) stop appends.
     Worst Case: O(n^2) - Iterates n times where each step may incur O(n) hash collisions in line, O(n) traversal in a skewed station departure tree, and O(n) array resizing, alongside O(n^2) if journeys triggers a rehash with collisions.
     * Creates and registers a new journey along with its sequence of stations, arrival times, and departure times.
     * @param journeyId unique identifier of the journey
     * @param stationsName ordered array of station names along the route
     * @param arrivals array of arrival times corresponding to each station
     * @param departures array of departure times corresponding to each station
     */
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

    /**
     * Time Complexity:
     * Best Case: O(1) - Validation fails immediately, or hash lookups resolve in O(1) and the first departure at the root of the station's schedule reaches the arrival station at the first stop checked.
     * Worst Case: O(n^2) - All n stations collide in the hash table (O(n)) and all n departures at the departure station must be checked in a skewed tree while scanning up to n stops per journey.
     * Finds the earliest suitable journey between the departure and arrival stations at or after the passenger's time.
     *
     * @param departureStation name of the departure station
     * @param arrivalStation name of the arrival station
     * @param passengerTime desired earliest departure time in HH:mm format
     * @return ticket for the best matching journey, or null if none is found
     * @throws InvalidStationException if either station does not exist in the network
     * @throws InvalidTimeFormatException if passengerTime does not follow the HH:mm format
     * @throws InvalidStationOrderException if departureStation is not before arrivalStation on the line
     */
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
    /**
     * Time Complexity:
     * Best Case: O(1) - Validation fails early in constant time, or hash lookups resolve in O(1) with no departures falling in the specified time range.
     * Worst Case: O(n^2) - All n stations collide in the hash table (O(n)) and all n departures fall within the time interval, requiring up to n stops per journey to be inspected to find the minimum stop count.
     * Finds the journey with the fewest intermediate stops between two stations within a given departure time window.
     *
     * @param departureStation name of the departure station
     * @param arrivalStation name of the arrival station
     * @param miniumTime earliest acceptable departure time in HH:mm format
     * @param maximumTime latest acceptable departure time in HH:mm format
     * @return ticket for the journey with the minimum number of stops, or null if none is found
     * @throws InvalidStationException if either station does not exist in the network
     * @throws InvalidTimeFormatException if either time string is not in valid HH:mm format
     * @throws InvalidStationOrderException if departureStation is not before arrivalStation on the line
     * @throws InvalideTimeException if miniumTime is after maximumTime
     */
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
    /**
     * Time Complexity:
     * Best Case: O(1) - Journey identifier is not found in the hash table (with no collisions), returning false immediately in constant time.
     * Worst Case: O(n^2) - Hash bucket has n collisions when removing the journey (O(n)), and iterating through all n stops requires O(n) traversal in each station's departure tree to remove the journey.
     * Removes a journey from the network and clears its scheduled departures from all associated stations.
     *
     * @param journeyId unique identifier of the journey to remove
     * @return true if the journey was found and removed, false otherwise
     */
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
    /**
     * Time Complexity:
     * Best Case: O(1) - Hash lookup and insertion into the bucket's list occur at the head in constant time with no collisions and no rehash.
     * Worst Case: O(n^2) - Hash table is full and triggers a rehash where all n stations collide into one bucket (or O(n) without rehash when all n stations collide in a single bucket).
     * Registers a new station in the network if it does not already exist.
     *
     * @param nameStation name of the station to add
     */

    @Override
    public void addStation(String nameStation) {
        String key = nameStation.toLowerCase();
        if (line.get(key) == null) {
            int position = line.size();
            Station station = new StationClass(nameStation, position);
            line.put(key, station);
        }
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Network is empty or opening the output stream fails immediately in constant time.
     * Worst Case: O(n^2) - Traverses and serializes all n stations, n journeys, and their n stops and departures to the file.
     * Serializes and saves the current network state to the specified file.
     *
     * @param fileName path of the destination file
     * @throws OutputWriteException if an I/O error occurs while writing the file
     */
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
     * Time Complexity:
     * Best Case: O(1) - Input string is null or parsed directly against the fixed HH:mm format in constant time.
     * Worst Case: O(1) - Constant time parsing of a fixed-length string without loops over collections.
     * Converts a time string in HH:mm format into a LocalTime object.
     *
     * @param time string representation of the time
     * @return parsed LocalTime instance, or null if the string is null or invalid
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
