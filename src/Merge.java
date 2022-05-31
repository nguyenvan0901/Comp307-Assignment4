public class Merge implements Comparable<Merge>{

    private int id1;
    private int id2;
    private double saving;

    public Merge(int id1, int id2, double saving){
        this.id1 = id1;
        this.id2 = id2;
        this.saving = saving;
    }

    public String toString(){
        return id1 + " - " + id2 + ": saving " + saving;
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

    public int getID1(){
        return id1;
    }

    public int getID2(){
        return id2;
    }

    public double getSaving(){
        return saving;
    }
}
