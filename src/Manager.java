
import java.util.Collections;
import java.util.Vector;
import java.io.IOException;
import java.io.FileWriter;


public class Manager implements Runnable{
    private String ID;
    private int sumOfCargo;
    private int sumOfPassengers;
    private int sumOfSuspect;
    private int sumOfFuel;
    private int sumOfTechFix;
    private int numOfBuses;
    public static int numOfReports=0;
    public static int busesEntered=0;
    private UnboundedBuffer<BusDetails> managerLine;
    private Vector<Worker> workers;
    private Vector<BusDetails> busDetails;
    private Vector<Destination> destinations;
    private boolean endDay;
    private BusStation agency;


    public Manager(String ID, BusStation agency){//constructor
        this.ID=ID;
        this.managerLine=agency.getManagerLine();
        this.workers=new Vector<>();
        this.endDay=false;
        this.numOfBuses=agency.getNumOfBuses();
        this.busDetails=new Vector<>();
        this.destinations=new Vector<>();
        sumOfFuel=0;
        sumOfCargo=0;
        sumOfTechFix=0;
        sumOfSuspect=0;
        this.agency=agency;
    }

    public void run() {
        while (!endDay) {
            if(numOfBuses==numOfReports) {
                finishDay();
                printDayReport();
                endDay=true;
                return;
            }
                BusDetails report = this.managerLine.extract();
                if (report == null)
                    return;
                busDetails.add(report);
                insertToFile(report);
            }

        }
    private void insertToFile(BusDetails report)
    {
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
        }
        if(report instanceof OutComeBusDetails)
            insertToExiting((OutComeBusDetails)report);
        else
            insertToEntering((IncomeBusDetails)report);
    }
    private void insertToExiting(OutComeBusDetails report) {
        String content = report.getTravelCode() + " " + report.getNumOfPassengers() + " " + report.getDestination() + " " + report.getTimeInFacility()+"\n";
        // Try block to check if exception occurs
        try {
            FileWriter fWriter=new FileWriter("src/Exiting.txt",true);
            fWriter.write(content);
            fWriter.close();
        }
        catch (IOException e) {
        }
    }
    private void insertToEntering(IncomeBusDetails report)
    {
        String content = report.getTravelCode() + " " + report.getNumOfPassengers() + " " + report.getCargo() + " " + report.getFixCost()+" "+report.isSecurityIssueFound()+" "+report.getTimeInFacility()+"\n";
        // Try block to check if exception occurs
        try {
            FileWriter fWriter=new FileWriter("src/Entering.txt",true);
            fWriter.write(content);
            fWriter.close();
        }
        catch (IOException e) {
        }

    }
    private void finishDay()
    {
        for(Worker w:workers)
            w.setEndDay();
        agency.finishBuffers();
        numOfReports=0;
        busesEntered=0;
    }
    public void printDayReport()
    {
        updateDayReportData();
        System.out.println("Total sum of cargo is: "+sumOfCargo);
        System.out.println("Total num of passengers is: "+sumOfPassengers);
        System.out.println("Total sum of fuel is: "+sumOfFuel);
        System.out.println("Total sum of tech fix is: " +sumOfTechFix);
        System.out.println(sumOfSuspect+ " suspect Items has been found");
        System.out.println("the destination with most buses is: "+getMaxDestination());
    }
    private void updateDayReportData() {
        setDataFromBuses();
        setDestinationsData();
    }
    private void setDataFromBuses()
    {
        for (BusDetails b : busDetails) {
            sumOfPassengers = sumOfPassengers + b.getNumOfPassengers();
            if (b instanceof IncomeBusDetails) {
                IncomeBusDetails icb= (IncomeBusDetails) b;
                sumOfCargo = sumOfCargo + icb.getCargo();
                sumOfTechFix = sumOfTechFix +icb.getFixCost();
                if(icb.isBusFueled())
                    sumOfFuel=sumOfFuel+200;
                if(icb.isSecurityIssueFound())
                    sumOfSuspect++;
            }
        }
    }
    private void setDestinationsData()
    {
        setDestinations();
        setNumOfBusesForAllDestinations();
    }
    private void setDestinations() {
        for (BusDetails b : busDetails)
            if (b instanceof OutComeBusDetails)
            {
                OutComeBusDetails ocbd=(OutComeBusDetails)b;
                Destination d=new Destination(ocbd.getDestination());
                if(destinationNotExist(d))
                    destinations.add(d);
            }
    }
    private void setNumOfBusesForDestination(Destination d)
    {
        for (BusDetails b:busDetails) {
            if (b instanceof OutComeBusDetails) {
                OutComeBusDetails ocbd = (OutComeBusDetails) b;
                if (ocbd.getDestination().compareTo(d.getCity()) == 0)
                    d.updateNumOfBuses();
            }
        }
    }
    private void setNumOfBusesForAllDestinations()
    {
        for (Destination d:destinations)
            setNumOfBusesForDestination(d);
    }
    private boolean destinationNotExist(Destination destination)
    {
        if(destinations.size()==0)
            return true;
       for(Destination d:destinations)
           if (d.getCity().compareTo(destination.getCity())==0)
               return false;
       return true;
    }
    private String getMaxDestination()
    {
        Destination d = Collections.max(destinations);
        return d.getCity();
    }
        public void setWorkers()
        {
            this.workers = agency.getWorkers();
        }
        public int getNumOfBuses()
        {
            return numOfBuses;
        }

    }
