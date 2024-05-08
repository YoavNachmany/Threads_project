
import java.util.Vector;
public class UnboundedBuffer<T>
{
    protected Vector<T> queue;
    protected boolean finish;

    public UnboundedBuffer() { //Constructor

        this.queue= new Vector<>();
        this.finish=false;

    }

    public synchronized void insert(T bus)//add an object to the collection
    {
        queue.add(bus);
        this.notifyAll();
    }

    public synchronized T extract() { //remove an object from the collection

        while(queue.isEmpty()&&!finish) {
            try {
                this.wait();
                if (finish)
                    return null;
            }
            catch (InterruptedException e) {
            }
            if(finish&&queue.isEmpty())
                return null;
        }
        T object=queue.remove(0);
        this.notifyAll();
        return object;
    }

    public synchronized void Finish() {
        this.finish=true;
        notifyAll();
    }
    public int size() { //return Queues size
        return queue.size();
    }

}


