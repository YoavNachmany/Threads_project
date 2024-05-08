public class InComeBus extends Bus
{
    private int Cargo;
    private boolean fueled;
    private boolean cleaned;
    private boolean SecurityIssue;
    private int fixCost;

    public boolean isFueled() {
        return this.fueled;
    }
    public void setFueled()
    {
        this.fueled=true;
    }
    public boolean isCleaned() {
        return this.cleaned;
    }
    public void setCleaned()
    {
        this.cleaned=true;
    }
    public boolean isSecurityIssue()
    {
        return SecurityIssue;
    }
    public void setSecurityIssueFound()
    {
        SecurityIssue=true;
    }
    public int getFixCost()
    {
        return fixCost;
    }
    public void updateFixCost(int fixCost)
    {
        this.fixCost=this.fixCost+fixCost;
    }
    public InComeBus (String travelCode,int sumOfPassenger,int arrivalTime, int Cargo , UnboundedBuffer<Bus> gateLine)
    {
        super(travelCode,sumOfPassenger,arrivalTime,gateLine);
        this.Cargo=Cargo;
        fueled=false;
        cleaned=false;
        SecurityIssue=false;
        fixCost=0;
    }
    public int getCargo()
    {
        return this.Cargo;
    }
}
