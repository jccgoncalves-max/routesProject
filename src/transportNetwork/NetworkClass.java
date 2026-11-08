package transportNetwork;
import dataStructures.ArrayClass;

import java.util.*;
public class NetworkClass implements Network{
    private Map<String,Station> stationCollection;
    private Map<String,List<Journey>> journeyCollection; // key: estação
    private ArrayList<Station> lineOrder; // usado na serialização
    //todo: estrutura que contem todas as estações do programa
    //todo: mapa que contem todas as journey do programa: mapa que contem lista de journeys
    //todo: Estrutura com linhas que representa sequência ordenada de estações

    //todo: implementar dois comparadores: findJournery e o minimo;

    /**
     * Transport Network constructor
     */
    public NetworkClass(){
        stationCollection = new HashMap<>(20);
        journeyCollection = new HashMap<>(50);
        lineOrder = new ArrayList<>();
    }
}
