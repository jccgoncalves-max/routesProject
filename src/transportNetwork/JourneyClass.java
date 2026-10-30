package transportNetwork;

import java.util.*;
public class JourneyClass implements Journey{

    private String id;
    private List<Stops> stopsSequence;

    //todo: estrutura ordenada de stops?? sorted???

    public JourneyClass(String id){
        this.id = id;
    }
}
