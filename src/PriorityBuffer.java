import java.util.Collections;


public class PriorityBuffer<T extends Comparable> extends UnboundedBuffer<T>
{
    public synchronized void insert(T bus)//add an object to the collection
    {
        queue.add(bus);
        Collections.sort(queue);
        this.notifyAll();
    }

}
