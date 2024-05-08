public class Truck
{
        private int fuel;
        private final int capacity;
        public Truck(int capacity){ //constructor
            this.fuel=capacity;
            this.capacity=capacity;
        }

        //getter
        public int getFuel() {
            return fuel;
        }
        public void setAfterFuel()
        {
            fuel=fuel-200;
        }
        public boolean canFuel()
        {
            if(fuel<200)
                return false;
            return true;

        }
        public void fillTruck()
        {
            fuel=capacity;
        }

}
