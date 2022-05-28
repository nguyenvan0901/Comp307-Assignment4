public class Merge {

    private Route route1;
    private Route route2;
    private double saving;

    public Merge(Route r1, Route r2, double saving){
        this.route1 = r1;
        this.route2 = r2;
        this.saving = saving;
    }

    public int comparedTo(Merge other){
        if(this.saving > other.saving){
            return 1;
        }
        else if(this.saving < other.saving){
            return -1;
        }
        else{
            return 0;
        }
    }

}
