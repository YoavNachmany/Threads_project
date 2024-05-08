public abstract class Worker implements Runnable
{
    protected boolean endDay;

    public Worker()
    {
       endDay=false;
    }

    public void setEndDay() { //update that the day is over
        this.endDay= true;
    }



}
