import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Vector;

public class BusStation
{
    private static Random rnd=new Random();
    private Vector<Bus> buses;
    private Vector<Worker> workers;
    private int techNum;
    private double cleanTime;
    private Vector<Thread> busThreads;
    private Vector<Thread> workersThreads;
    private Manager manager;
    private Thread managerThread;
    private PriorityBuffer<Bus> gateLine;
    //Buffers contains only incomebuses in order to make sure no outcomebus going into those lines
    private UnboundedBuffer<InComeBus> logisticsLine;
    private UnboundedBuffer<InComeBus> cleanLine;
    private UnboundedBuffer<InComeBus> techLine;
    private BoundedBuffer<InComeBus> fuelLine;
    //buffers contain busdetails for manager
    private UnboundedBuffer<BusDetails> managerLine;
    private int workersID;

    public BusStation(String file, int techNum, double cleanTime) throws FileNotFoundException
    {
            setQueues();
            this.techNum=techNum;
            this.cleanTime=cleanTime;
            buses=ReadBusesFile(file);
            workers=new Vector<>();
            busThreads=new Vector<>();
            workersThreads=new Vector<>();
            workersID=200000001;
    }
    private void setQueues()
    {
        this.gateLine=new PriorityBuffer<Bus>();
        this.logisticsLine=new UnboundedBuffer<InComeBus>();
        this.cleanLine=new UnboundedBuffer<InComeBus>();
        this.techLine=new UnboundedBuffer<InComeBus>();
        this.fuelLine=new BoundedBuffer<InComeBus>(8);
        this.managerLine=new UnboundedBuffer<BusDetails>();
    }
    private Vector<Bus> ReadBusesFile(String buses) throws FileNotFoundException
    {
        Vector<Bus> Buses = new Vector<>();
        Path pathToFile = Paths.get(buses);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine(); //read the first line in the file
            line = br.readLine();
            while (line != null) //file not finished
            {
                String[] Data = line.split("\t+"); //divide line to array of fields
                //System.out.println(Data[0]+" "+Data[1]+" "+Data[2]+ " "+Data[3]);
                Bus b = createBus(Data);// create Bus
                //System.out.println(b.getNumOfPassengers());
                Buses.add(b);
                line = br.readLine(); //next line
            }
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return Buses;
    }
    private Bus createBus(String[]Data)
    {
        String travelCode=Data[0];
        int numOfPassengers=Integer.parseInt(Data[1]);
        int arrivalTime=Integer.parseInt(Data[2]);
        if((Data[3].matches("[0-9]+")))
        {
            int cargo=Integer.parseInt(Data[3]);
            return new InComeBus(travelCode,numOfPassengers,arrivalTime,cargo,this.gateLine);
        }
        else
        {
            String Destination=Data[3];
            return new OutComeBus(travelCode,numOfPassengers,arrivalTime,Destination,this.gateLine);
        }
    }
    private void setBusThreads()
    {
        for (int i=0;i<buses.size();i++)
        {
            Thread t =new Thread(buses.get(i));
            busThreads.add(t);
        }
    }
    private void setWorkersThreads()
    {
        for (int i=0;i<workers.size();i++)
        {
            Thread t =new Thread(workers.get(i));
            workersThreads.add(t);
        }
    }
    private void setWorkers()
    {
        createFuel();
        createCleaners();
        createTech();
        createLogistics();
        createGateKeepers();
    }
    private void createCleaners()
    {
        for(int i=0;i<2;i++)
            this.workers.add(new Cleaner(cleanTime,rnd.nextInt(0,10),cleanLine,fuelLine,techLine));
    }
    private void createTech()
    {
        for (int i=0;i<techNum;i++) {
            workersID++;
            workers.add(new TechnicalAttendant(Integer.toString(workersID), techLine, cleanLine, fuelLine, managerLine, manager));
        }
    }
    private void createFuel()
    {
        Vector<Truck> trucks= createTrucks();
        for (int i=0;i<2;i++) {
            workersID++;
            workers.add(new FuelAttendant(Integer.toString(workersID), trucks.get(i), fuelLine, techLine, managerLine, manager));
        }
    }
    private Vector<Truck> createTrucks()
    {
        Vector<Truck> trucks= new Vector<Truck>();
        trucks.add(new Truck(1000));
        trucks.add(new Truck(2000));
        return trucks;
    }

    private void createLogistics()
    {
        double pace=0.03;
        for(int i=0;i<3;i++) {
            workersID++;
            this.workers.add(new LogisticsAttendant(Integer.toString(workersID), pace, this.logisticsLine, this.techLine, this.cleanLine));
            pace = pace + 0.02;
        }
    }
    private void createGateKeepers()
    {
        for(int i=0;i<2;i++)
            this.workers.add(new GateKeeper(this.gateLine,this.logisticsLine,this.managerLine,manager));
    }

    public void startDay()
    {
        setStart();
        managerThread.start();
        startWorkersThreads();
        startBusThreads();
    }
    public void finishBuffers()
    {
        gateLine.Finish();
        managerLine.Finish();
        logisticsLine.Finish();
        techLine.Finish();
        cleanLine.Finish();
        fuelLine.Finish();
    }

    private void startBusThreads()
    {
        for (Thread b:busThreads) {
            b.start();
        }
    }
    private void startWorkersThreads()
    {
        for (Thread w:workersThreads) {
            w.start();
        }
    }

    private void updateWorkersForManager()
    {
       manager.setWorkers();
    }
    private void setStart()
    {
        manager=new Manager(Integer.toString(workersID),this);
        managerThread=new Thread(manager);
        setWorkers();
        setBusThreads();
        setWorkersThreads();
        updateWorkersForManager();
    }
    public Vector<Worker> getWorkers()
    {
        return workers;
    }
    public int getNumOfBuses()
    {
        return buses.size();
    }
    public UnboundedBuffer<BusDetails> getManagerLine()
    {
        return managerLine;
    }




}
