import java.util.*;

public class Route {

    private int head_id = 0;
    private int tail_id = 0;
    private List<Integer> nodes = new ArrayList<>();
    private double demand = 0.0;

    public Route(VRPNode node){
        this.demand = node.getDemand();
        nodes.add(node.getID());
        this.head_id = node.getID();
        this.tail_id = node.getID();
    }

    public Route(){
        this.demand = 0;
        this.head_id = 0;
        this.tail_id = 0;
    }

    public void addToRoute(VRPNode node){
        //System.out.println(nodes.toString());
        if(nodes.size() == 0){
            nodes.add(node.getID());
            this.head_id = node.getID();
        }

        else {
            nodes.add(node.getID());
        }

        this.tail_id = node.getID();
        this.demand = this.demand + node.getDemand();
    }

    public int getHeadId(){
        return head_id;
    }

    public int getTailId(){
        return tail_id;
    }

    public double getDemand(){
        return demand;
    }

    public void setDemand(double demand){
        this.demand = demand;
    }

    // this method gets all the ids of nodes that are on this route.
    public List<Integer> getNodes(){
        return nodes;
    }

    public String toString(){
        String node_sequence = " 0,";

        for(int i: nodes){
            node_sequence = node_sequence + i + ",";
        }

        node_sequence = node_sequence + "0";

        return node_sequence;
    }

}
