public abstract class Bus implements Runnable,Comparable<Bus> {

    protected String travelCode;
    protected int numOfPassengers;
    protected int arrivalTime;
    protected boolean done;
    protected int priorityLevel;
    private int timeInFacility=0;
    private UnboundedBuffer<Bus> gateLine;
    public Bus(String travelCode, int numOfPassengers, int arrivalTime, UnboundedBuffer<Bus> gateLine) { //constructor
        this.travelCode = travelCode;
        this.arrivalTime = arrivalTime;
        this.numOfPassengers = numOfPassengers;
        this.gateLine = gateLine;
        done = false;
        priorityLevel=0;
    }
    public void run () { //run
            waitingBeforeInsert();
            goToGate();
        }
    private void waitingBeforeInsert() {
        try {
            int sleepTime=1000 * arrivalTime;
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e) {
        }
    }
    private void goToGate() { //insert the bus to gate line
        this.gateLine.insert(this);
        }
    //getters
    public int getNumOfPassengers()
    {
        return numOfPassengers;
    }
    public String getTravelCode()
    {
        return travelCode;
    }
    public int getArrivalTime()
    {
        return arrivalTime;
    }
    public int getPriorityLevel()
    {
        return priorityLevel;
    }
    public int compareTo(Bus other)
    {
        return other.getPriorityLevel()-this.priorityLevel;
    }
    public void updateTimeInFacility(int sleepTime)
    {
        timeInFacility=timeInFacility+sleepTime;
    }
    public int getTimeInFacility()
    {
        return timeInFacility;
    }

}
