import java.util.*;

public class Route {

    private int head_id;
    private int tail_id;
    private List<Integer> nodes = new ArrayList<>();
    private double demand = 0.0;

    public Route(VRPNode node){
        this.demand = node.getDemand();
        nodes.add(node.getID());
        this.head_id = node.getID();
        this.tail_id = node.getID();
    }

    public void addToRoute(int i){
        nodes.add(i);
        this.tail_id = i;
    }

    public void updateDemand(double new_demand){
        this.demand = this.demand + new_demand;
    }

    public int getHeadId(){
        return head_id;
    }

    public int getTailID(){
        return tail_id;
    }

    public double getDemand(){
        return demand;
    }

    // this method gets all the ids of nodes that are on this route.
    public List<Integer> getNodes(){
        return nodes;
    }

    public String toString(){
        String node_sequence = " 0,";

        for(Integer i: nodes){
            node_sequence = node_sequence + i + ",";
        }

        node_sequence = node_sequence + "0";

        return node_sequence;
    }

}
