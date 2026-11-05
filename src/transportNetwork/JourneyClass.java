package transportNetwork;

import java.util.*;
public class JourneyClass implements Journey{

    private String id;
    private ArrayList<Stops> stopsSequence; // array

    //todo: estrutura ordenada de stops?? sorted???

    public JourneyClass(String id){
        this.id = id;
        stopsSequence = new ArrayList<>();
    }


}
