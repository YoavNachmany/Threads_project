public class OutComeBusDetails extends BusDetails
{
    public OutComeBusDetails(Bus bus)
    {
        super(bus);
    }
    public String getDestination()
    {
        return ((OutComeBus)bus).getDestination();
    }


}
