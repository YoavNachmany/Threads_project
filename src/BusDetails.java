public abstract class BusDetails
{
    protected Bus bus;
    public static int numOfReports;
    public BusDetails(Bus bus)
    {
        this.bus=bus;
    }
    public int getNumOfPassengers()
    {
        return bus.getNumOfPassengers();
    }
    public String getTravelCode()
    {
        return bus.travelCode;
    }
    public int getTimeInFacility()
    {
        return bus.getTimeInFacility();
    }
}
