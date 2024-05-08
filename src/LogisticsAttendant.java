import java.util.Random;

public class LogisticsAttendant extends Worker implements Runnable
{
    private String ID;
    private double Pace;
    private UnboundedBuffer<InComeBus> logisticsLine;
    private UnboundedBuffer<InComeBus> techLine;
    private UnboundedBuffer<InComeBus> cleanersLine;


    public LogisticsAttendant(String ID,double Pace,UnboundedBuffer<InComeBus> logisticsLine,UnboundedBuffer<InComeBus> techLine,UnboundedBuffer<InComeBus> cleanersLine)//constructor
    {
        this.ID=ID;
        this.Pace=Pace;
        this.logisticsLine=logisticsLine;
        this.techLine=techLine;
        this.cleanersLine=cleanersLine;
    }
    public void run()
    {
        while (!endDay) {
            InComeBus b = this.logisticsLine.extract();
            if (b == null)
                return;
            unloadBus(b);
            sendNext(b);
        }
    }
    private void unloadBus(InComeBus b)
    {
        try {
            int sleepTime= (int)(b.getCargo()* this.Pace);
            Thread.sleep(sleepTime);
            b.updateTimeInFacility(sleepTime);
        } catch (InterruptedException e) {
        }
    }
    private void sendNext(InComeBus b) {
        if (Math.random() <= 0.1)
            sendToTech(b);
        else
            sendToCleaners(b);
    }

    private void sendToTech(InComeBus b){
        this.techLine.insert(b);
    }

    private void sendToCleaners(InComeBus b){
        this.cleanersLine.insert(b);
    }

}
