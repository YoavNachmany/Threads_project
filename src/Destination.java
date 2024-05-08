
public class Destination implements Comparable<Destination>
{
    private int numOfBuses;
    private String City;
    public Destination(String City)
    {
        this.City=City;
        this.numOfBuses=0;
    }
    public int getNumOfBuses()
    {
        return numOfBuses;
    }
    public String getCity()
    {
        return City;
    }
    public void updateNumOfBuses()
    {
        numOfBuses++;
    }
    public int compareTo(Destination other)
    {
        return (this.getNumOfBuses()-other.getNumOfBuses());
    }
}
