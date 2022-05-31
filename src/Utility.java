import java.util.*;
import java.util.Map;

public class Utility {

    /**
     * Calculate the Euclidean distance between two VRP nodes
     * @param node1 the first VRP node
     * @param node2 the second VRP node
     * @return the Euclidean distance between node1 and node2
     */
    public static double calculateEuclideanDistance(VRPNode node1, VRPNode node2) {
        // TODO: Implement the function to calculate the Euclidean distance.
        double x_dif_squared = Math.pow(node1.getX() - node2.getX(),2);
        double y_dif_squared = Math.pow(node1.getY() - node2.getY(),2);

        return Math.sqrt(x_dif_squared + y_dif_squared);
    }

    /**
     * Calculate the total cost of a VRP solution under a VRP instance.
     * The total cost is the sum of all the Euclidean distance between adjacent nodes in the routes.
     * @param solution the VRP solution.
     * @param instance the VRP instance.
     * @return the total cost of the solution.
     */
    public static double calculateTotalCost(VRPSolution solution, VRPInstance instance) {
        // TODO: Implement the function to calculate the total cost of the solution.

        double total_cost = 0.0;

        Map<Integer, VRPNode> nodes = instance.getNodes();
        List<List<Integer>> routes = solution.getRoutes();

        for(List<Integer> route: routes){

            // distance from depot to the first node
            VRPNode depot = instance.getDepot();
            VRPNode first_node = nodes.get(route.get(0));
            total_cost = total_cost + calculateEuclideanDistance(depot, first_node);

            for(int i=0; i<route.size()-1; i++){

                VRPNode node1 = nodes.get(route.get(i));
                VRPNode node2 = nodes.get(route.get(i+1));
                total_cost = total_cost + calculateEuclideanDistance(node1, node2);

            }

            // distance from last node to the depot
            VRPNode last_node = nodes.get(route.get(route.size()-1));
            total_cost = total_cost + calculateEuclideanDistance(depot, last_node);

        }

        return total_cost;
    }

    /**
     * Generate a VRP solution for a VRP instance using the nearest neighbour heuristic.
     * @param instance the VRP instance.
     * @return the VRP solution generated by the nearest neighbour heuristic.
     */
    public static VRPSolution nearestNeighbourHeuristic(VRPInstance instance) {

        int total_nodes = instance.getNodes().size();
        List<List<Integer>> routes = new ArrayList<>();
        Map<Integer, VRPNode> nodes = new HashMap<>();

        // changing the id of all the nodes to start from 0.
        for(Map.Entry<Integer, VRPNode> entry: instance.getNodes().entrySet()){
            nodes.put(entry.getKey()-1, entry.getValue());
        }

        // ----------------------Calculate the all distances between all pair of nodes.---------------------------------

        double[][] distance_matrix = calculateDistanceMatrix(nodes);
        // -------------------------------------------------------------------------------------------------------------


        // ----------------------Start finding routes-------------------------------------------------------------------

        HashSet<Integer> visited_id = new HashSet<>();
        visited_id.add(0);

        while(visited_id.size() < total_nodes){

            List<Integer> route = new ArrayList<>();
            double capacity = instance.getCapacity();

            // -----------------------find the node that is closest to the depot----------------------------------------
            double min_distance = 1000;
            int depot_row = 0;
            int current_id = -1;

            // skipping col=0 as it will be depot to depot, from 1 means from the first neighbour node.
            for(int col=1; col<total_nodes; col++){

                if(!visited_id.contains(col) && distance_matrix[depot_row][col] < min_distance){
                    min_distance = distance_matrix[depot_row][col];
                    current_id = col;
                }

            }

            // add the node closest to depot into the route
            route.add(current_id);
            visited_id.add(current_id);
            capacity = capacity - nodes.get(current_id).getDemand();

            // ---------------------------------------------------------------------------------------------------------

            // ------------------keep expanding the nodes (find the closest to this node)-------------------------------
            while(true){

                int next_id = -1;
                double min_dist = 1000;

                // finding the closest neighbour to the current neighbour, skipping 0 as we don't want to count the
                // path back to depot.
                for(int col=1; col<total_nodes; col++) {

                    // if the neighbour is not visited and distance is smaller than current min
                    if(!visited_id.contains(col) && distance_matrix[current_id][col] < min_dist && col != current_id){

                        double node_demand = nodes.get(col).getDemand();

                        // if the demand of the node doesn't go over the capacity
                        if(capacity - node_demand >= 0){

                            min_dist = distance_matrix[current_id][col];
                            next_id = col;

                        }

                    }

                }

                // if next_id == -1 means all other nodes exceed capacity, return to depot
                if(next_id == -1){
                    break;
                }
                else{
                    // found the next node, add to the route and mark it as visited.
                    route.add(next_id);
                    visited_id.add(next_id);
                    capacity = capacity - nodes.get(next_id).getDemand();
                    current_id = next_id;
                }

                if(capacity == 0){ // route can't fit any more node, exit loop and create a new route.
                    break;
                }

            }

            routes.add(route);

        }
        // -------------------------------------------------------------------------------------------------------------

        for(List<Integer> route: routes){
            for(int i=0; i<route.size(); i++){
                route.set(i, route.get(i)+1);
            }
            System.out.println(route);
        }

        return new VRPSolution(routes);
    }

    /**
     * Generate a VRP solution for a VRP instance using the savings heuristic.
     * @param instance the VRP instance.
     * @return the VRP solution generated by the savings heuristic.
     */
    public static VRPSolution savingsHeuristic(VRPInstance instance) {
        // TODO: Implement the savings heuristic.

        int total_nodes = instance.getNodes().size();
        List<List<Integer>> routes = new ArrayList<>();
        Map<Integer, VRPNode> nodes_map = new HashMap<>();
        double capacity = instance.getCapacity();

        // changing the id of all the nodes to start from 0.
        for(Map.Entry<Integer, VRPNode> entry: instance.getNodes().entrySet()){
            nodes_map.put(entry.getKey()-1, entry.getValue());
            nodes_map.get(entry.getKey()-1).setID();
        }


        // ----------------------Calculate the all distances between all pair of nodes.---------------------------------
        double[][] distance_matrix = calculateDistanceMatrix(nodes_map);
        // -------------------------------------------------------------------------------------------------------------

        // ----------------------Creating initial routes----------------------------------------------------------------
        List<Route> initial_routes = new ArrayList<>();

        for(int i=1; i<total_nodes; i++){
            Route route = new Route(nodes_map.get(i));
            // assign each node to a specific route.
            nodes_map.get(i).setRoute(route);
            initial_routes.add(route);
        }

        // -------------------------------------------------------------------------------------------------------------

        // ----------------------Creating merges between 2 nodes.-------------------------------------------------------

        List<Merge> feasible_merge_list = new ArrayList<>(); //using array list here because it's easier to manipulate

        for(int i=1; i<total_nodes; i++){

            for(int j=i; j<total_nodes; j++){

                if(j != i) {

                    // saving = l(v1,depot) + l(v2,depot) - l(v1,v2)
                    double saving = distance_matrix[0][i] + distance_matrix[0][j]
                                  - distance_matrix[i][j];

                    Merge merge = new Merge(i, j, saving);
                    feasible_merge_list.add(merge);
                }

            }

        }

        Collections.sort(feasible_merge_list);

        // -------------------------------------------------------------------------------------------------------------

        // ----------------------Continue merging until no merge can be made.-------------------------------------------

        while(feasible_merge_list.size() != 0){

            // this will be the merge with the highest saving.
            Merge feasible_merge = feasible_merge_list.get(0);

            int node_id1 = feasible_merge.getID1();
            int node_id2 = feasible_merge.getID2();

            Route route1 = nodes_map.get(feasible_merge.getID1()).getRoute();
            Route route2 = nodes_map.get(feasible_merge.getID2()).getRoute();

            List<Integer> route_1_node = route1.getNodes();
            List<Integer> route_2_node = route2.getNodes();

            if(route1 == route2){
                // if 2 nodes are already in the same route then skip this feasible merge.
                feasible_merge_list.remove(0);
            }

            // check if the 2 routes exceed the instance capacity.
            else if(route1.getDemand() + route2.getDemand() <= capacity){

                // checking join condition of the 2 routes.
                if(route1.getTailId() == node_id1 && route2.getHeadId() == node_id2){

                    Route new_route = new Route();
                    for(int node_id: route_1_node){
                        new_route.addToRoute(nodes_map.get(node_id));
                    }
                    for(int node_id: route_2_node){
                        new_route.addToRoute(nodes_map.get(node_id));
                    }

                    //System.out.println("new route: " + new_route.toString());

                    // set this new route to be the current route for every node in this current route
                    // (in route1 and route2).
                    for(int node_id: route_1_node){
                        nodes_map.get(node_id).setRoute(new_route);
                    }

                    for(int node_id: route_2_node){
                        nodes_map.get(node_id).setRoute(new_route);
                    }

                    // after using the feasible merge, remove it from the list.
                    feasible_merge_list.remove(0);

                }


                else if(route1.getHeadId() == node_id1 && route2.getTailId() == node_id2){

                    Route new_route = new Route();
                    for(int node_id: route_2_node){
                        new_route.addToRoute(nodes_map.get(node_id));
                    }

                    for(int node_id: route_1_node){
                        new_route.addToRoute(nodes_map.get(node_id));
                    }

                    for(int node_id: route_1_node){
                        nodes_map.get(node_id).setRoute(new_route);
                    }

                    for(int node_id: route_2_node){
                        nodes_map.get(node_id).setRoute(new_route);
                    }

                    feasible_merge_list.remove(0);

                }

                // the nodes can't be connected because tail-head rule is not satisfied, remove the feasible merge.
                else{

                    feasible_merge_list.remove(0);

                }

            }
            else{
                feasible_merge_list.remove(0);
            }

        }
        // -------------------------------------------------------------------------------------------------------------

        HashSet<List<Integer>> routess = new HashSet<>();

        for(int i=1; i<total_nodes; i++){
            VRPNode node = nodes_map.get(i);
            Route route = node.getRoute();
            routess.add(route.getNodes());
        }

        for(List<Integer> route: routess){
            for(int i=0; i<route.size(); i++){
                route.set(i, route.get(i)+1);
            }
            routes.add(route);
            System.out.println(route.toString());
        }

        return new VRPSolution(routes);
    }

    public static double[][] calculateDistanceMatrix(Map<Integer, VRPNode> nodes){

        int total_nodes = nodes.size();
        double[][] distance_matrix = new double[total_nodes][total_nodes];

        for(int row=0; row<total_nodes; row++){

            VRPNode from_node = nodes.get(row);

            for(int col=0; col<total_nodes; col++){

                VRPNode to_node = nodes.get(col);
                distance_matrix[row][col] = calculateEuclideanDistance(from_node, to_node);

            }

        }

        return distance_matrix;
    }

}
