public class OutComeBus extends Bus
{
    private String Destination;
    public OutComeBus(String travelCode,int sumOfPassenger,int arrivalTime,String Destination,UnboundedBuffer<Bus> gateLine)
    {
        super(travelCode,sumOfPassenger,arrivalTime,gateLine);
        this.Destination=Destination;
        priorityLevel=1;
    }
    public String getDestination()
    {
        return Destination;
    }
}
