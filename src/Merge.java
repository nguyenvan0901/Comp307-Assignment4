public class Merge implements Comparable<Merge>{

    private Route route1;
    private Route route2;
    private double saving;

    public Merge(Route r1, Route r2, double saving){
        this.route1 = r1;
        this.route2 = r2;
        this.saving = saving;
    }

    public String toString(){
        return route1.toString() + " -" + route2.toString() + ": saving " + saving;
    }

    @Override
    public int compareTo(Merge other) {
        if(this.saving > other.saving){
            return -1;
        }
        else if(this.saving < other.saving){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Route getRoute1(){
        return route1;
    }

    public Route getRoute2(){
        return route2;
    }

    public double getSaving(){
        return saving;
    }
}
