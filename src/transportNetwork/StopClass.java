package transportNetwork;

public class StopClass implements Stops {
    private Station station;
    private String arrivalTime;
    private String departureTime;

    public StopClass(Station station, String arrivalTime,String departureTime){
        this.station = station;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
