import java.util.Random;

public class TechnicalAttendant extends Worker
{
    private static Random rnd=new Random();
    private String ID;
    private UnboundedBuffer<InComeBus> techLine;
    private UnboundedBuffer<InComeBus> cleanersLine;
    private BoundedBuffer<InComeBus> fuelLine;
    private UnboundedBuffer<BusDetails> managerLine;
    private Manager manager;
    public TechnicalAttendant(String ID,UnboundedBuffer<InComeBus> techLine,UnboundedBuffer<InComeBus> cleanersLine,BoundedBuffer<InComeBus> fuelLine,UnboundedBuffer<BusDetails> managerLine,Manager manager)
    {
        this.manager=manager;
        this.ID=ID;
        this.techLine=techLine;
        this.cleanersLine=cleanersLine;
        this.fuelLine=fuelLine;
        this.managerLine=managerLine;
    }
    public void run()
    {
        while (!endDay) {
            InComeBus b = techLine.extract();
            if (b == null)
                return;
            fixBus(b);
            sendNext(b);
        }
    }
    private void fixBus(InComeBus b)
    {
        try {
            int sleepTime=(int)(1000*rnd.nextInt(3,5));
            Thread.sleep(sleepTime);
            b.updateTimeInFacility(sleepTime);
            int fixCost=rnd.nextInt(500,1000);
            b.updateFixCost(fixCost);
        } catch (InterruptedException e) {
        };
    }
    private void sendNext(InComeBus b)
    {
        if(b.isFueled()&&b.isCleaned())
            sendToManager(b);
        else if (b.isCleaned())
            sendToFuel(b);
        else
            sendToClean(b);
    }
    private void sendToManager(InComeBus b)
    {
        BusDetails report=new OutComeBusDetails(b);
        managerLine.insert(report);
        manager.numOfReports++;

    }
    private void sendToFuel(InComeBus b)
    {
        fuelLine.insert(b);
    }
    private void sendToClean(InComeBus b)
    {
        cleanersLine.insert(b);
    }

}
