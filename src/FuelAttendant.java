
import java.util.Random;

public class FuelAttendant extends Worker{
    private String ID;
    private Truck truck;
    private Manager manager;
    private BoundedBuffer<InComeBus> fuelLine;
    private UnboundedBuffer<InComeBus> techLine;
    private UnboundedBuffer<BusDetails> managerLine;
    private static Random rnd=new Random();
    public FuelAttendant(String ID,Truck truck,BoundedBuffer<InComeBus> fuelLine, UnboundedBuffer<InComeBus> techLine,UnboundedBuffer<BusDetails> managerLine,Manager manager)
    {
        this.manager=manager;
        this.ID=ID;
        this.truck=truck;
        this.fuelLine=fuelLine;
        this.techLine=techLine;
        this.managerLine=managerLine;
    }
    public void run() {
        while (!endDay) {
            boolean fueledBefore=false;
            if(!(truck.canFuel())) {
                goFillTruck();
                fueledBefore=true;
            }
                InComeBus b = fuelLine.extract();
                if (b == null)
                    return;
                fuelBus(b);
                if(fueledBefore)
                    b.updateTimeInFacility(5000);
                sendNext(b);
        }

    }
    private void goFillTruck()
    {
        try
        {
            Thread.sleep(5000);
            truck.fillTruck();
        }
        catch (InterruptedException e)
        {
        }
    }
    private void fuelBus(InComeBus b)
    {
        try
        {
            int sleepTime=1000*rnd.nextInt(3,4);
            Thread.sleep(sleepTime);
            b.updateTimeInFacility(sleepTime);
            truck.setAfterFuel();
            b.setFueled();
        }
        catch (InterruptedException e)
        {

        }
    }
    private void sendNext(InComeBus b)
    {
        if(Math.random()<=0.3)
            sendToTech(b);
        else
            sendToManger(b);
    }
    private void sendToTech(InComeBus b)
    {
        techLine.insert(b);
    }
    private void sendToManger(InComeBus b)
    {
        BusDetails report=new IncomeBusDetails(b);
        managerLine.insert(report);
        manager.numOfReports++;
    }
}
