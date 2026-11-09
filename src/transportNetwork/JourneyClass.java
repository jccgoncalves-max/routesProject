package transportNetwork;

import java.util.*;

/**
 * Represents a Journey taken over the train line.
 */
public class JourneyClass implements Journey{
    /**
     * Journey's unique id.
     */
    private String id;
    /**
     * Journey's sequence of stops.
     */
    private ArrayList<Stops> stopsSequence;

    /**
     * JourneyClass constructor
     * @param id Journey's unique id
     */
    public JourneyClass(String id){
        this.id = id;
        stopsSequence = new ArrayList<>();
    }


    @Override
    public void addStop(Stops newStop) {
        stopsSequence.add(newStop);
    }
}
