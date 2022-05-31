import java.util.List;

public class VRPNode {
    private int ID; // the ID of the node
    private double x; // the x-axis position
    private double y; // the y-axis position
    private double demand; // the demand of the node
    private Route route;

    public VRPNode(int ID, double x, double y, double demand) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.demand = demand;
        this.route = new Route(null);
    }

    public VRPNode(int ID, double x, double y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.demand = 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(){
        this.ID = this.ID - 1;
    }

    public void setRoute(Route new_route){
        this.route = new_route;
    }

    public Route getRoute() { return route; }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDemand() {
        return demand;
    }

    public void setDemand(double demand) {
        this.demand = demand;
    }

    public String toString(){
        return "x: " + x + " y: " + y;
    }
}
