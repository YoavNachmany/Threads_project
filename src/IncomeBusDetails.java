public class IncomeBusDetails extends BusDetails
{
    public IncomeBusDetails(Bus bus)
    {
        super(bus);
    }
    public int getCargo()
    {
        return ((InComeBus)bus).getCargo();
    }
    public int getFixCost() {
        return ((InComeBus) bus).getFixCost();
    }
    public boolean isBusFueled()
    {
        return ((InComeBus) bus).isFueled();
    }
    public boolean isSecurityIssueFound()
    {
        return ((InComeBus) bus).isSecurityIssue();
    }
}

