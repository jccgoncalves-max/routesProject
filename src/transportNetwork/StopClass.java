package transportNetwork;

public class StopClass implements Stops {
    private Station stopStation;
    private String arrivalTime;
    private String departureTime;

    public StopClass(String arrivalTime,String departureTime){
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
