import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        BusStation busStation=new BusStation("src/buses",3,5);
        busStation.startDay();
    }
}