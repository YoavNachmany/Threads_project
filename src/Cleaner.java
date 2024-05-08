public class Cleaner extends Worker{
    private int seniority;
    private double cleanTime;
    private UnboundedBuffer<InComeBus> cleanersLine;
    private BoundedBuffer<InComeBus> fuelLine;
    private UnboundedBuffer<InComeBus> techLine;

    public Cleaner(double cleanTime,int seniority,UnboundedBuffer<InComeBus> cleanersLine,BoundedBuffer<InComeBus> fuelLine, UnboundedBuffer<InComeBus> techLine)//constructor
    {
        this.cleanTime=cleanTime;
        this.seniority=seniority;
        this.cleanersLine=cleanersLine;
        this.fuelLine=fuelLine;
        this.techLine=techLine;
    }
    public void run() {
        while (!endDay) {
            InComeBus b = this.cleanersLine.extract();
            if (b == null)
                return;
            cleanBus(b);
            sendNext(b);
        }
    }
    private void cleanBus(InComeBus b)
    {
        try {
            int sleepTime=(int)(1000*cleanTime);
            Thread.sleep(sleepTime);
            b.updateTimeInFacility(sleepTime);
        } catch (InterruptedException e) {
        }
    }
    private void sendNext(InComeBus b)
    {
        if(Math.random()<=0.05) {
            try {
                int sleepTime=1000;
                Thread.sleep(sleepTime);
                b.setSecurityIssueFound();
                b.updateTimeInFacility(sleepTime);
            } catch (InterruptedException e) {
            }
        }
        if (Math.random()<=0.25)
            sendToTech(b);
        else
            sendToFuel(b);
    }
    private void sendToTech(InComeBus b)
    {
        this.techLine.insert(b);
    }
    private void sendToFuel(InComeBus b)
    {
        this.fuelLine.insert(b);
    }


}
