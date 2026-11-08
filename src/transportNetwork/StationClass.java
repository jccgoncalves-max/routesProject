package transportNetwork;

import dataStructures.*;

import java.util.List;

public class StationClass implements Station {

    private String name;
    private BSTree<String, List<Journey>> stopsByTime;

public StationClass(String name){

    this.name = name;
    this.stopsByTime = new BSTree<>(); //TODO usar comparador




    }
}
