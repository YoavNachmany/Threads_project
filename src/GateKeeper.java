
import java.util.Random;
import java.util.Vector;

public class GateKeeper extends Worker{
    private UnboundedBuffer<Bus> gateLine;
    private UnboundedBuffer<InComeBus> logisticsLine;
    private UnboundedBuffer<BusDetails> managerLine;
    private Manager manager;

    private static Random rnd=new Random();

    public GateKeeper(UnboundedBuffer<Bus> gateLine,UnboundedBuffer<InComeBus> logisticsLine,UnboundedBuffer<BusDetails> managerLine,Manager manager )   //constructor
    {
        this.manager=manager;
        this.gateLine=gateLine;
        this.logisticsLine=logisticsLine;
        this.managerLine=managerLine;
    }
    public void run()
    {
        while (!endDay)
        {
            if (manager.getNumOfBuses()==manager.busesEntered) {
                setEndDay();
                return;
            }
            Bus b = this.gateLine.extract();
            if (b == null)
                return;
            enterBus(b);
            sendNext(b);
        }
    }

    private void enterBus(Bus b){
        try {
            int sleepTime= 1000*rnd.nextInt(5,10);
            Thread.sleep(sleepTime);
            b.updateTimeInFacility(sleepTime);
        } catch (InterruptedException e) {
        }
    }
    private void sendToLogistics(InComeBus b)
    {
        this.logisticsLine.insert(b);
    }
    private void sendToManager(Bus b)
    {
        BusDetails report = new OutComeBusDetails((OutComeBus)b);
        this.managerLine.insert(report);
        manager.numOfReports++;
    }
    private void sendNext(Bus b)
    {
        if (b instanceof InComeBus)
            sendToLogistics((InComeBus)b);
        else if(b instanceof OutComeBus)
            sendToManager(b);
        manager.busesEntered++;
    }
}
