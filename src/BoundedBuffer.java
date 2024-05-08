
    import java.util.Vector;
    public class BoundedBuffer<T> extends UnboundedBuffer<T>  {
        private int maxSize;
        public BoundedBuffer (int maxSize)
        {
            super();
            this.maxSize=maxSize;
        }
        public synchronized void insert(T p)  { //add patient to the collection only if there is place

            while(queue.size()>=maxSize&&!finish) {
                try {
                    this.wait();
                }
                catch (InterruptedException e) {
                }
            }
            this.queue.add(p);
            this.notifyAll();
        }
        public int size() { //return Queue size

            return queue.size();
        }
}
