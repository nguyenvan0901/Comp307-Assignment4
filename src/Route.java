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

    public void addToSequence(int id){
        nodes.add(id);
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

    public String toString(){
        String node_sequence = " 1, ";

        for(Integer i: nodes){
            node_sequence = node_sequence + i + ", ";
        }

        node_sequence = node_sequence + "1";

        return node_sequence;
    }

}
