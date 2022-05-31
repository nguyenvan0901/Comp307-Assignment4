import java.io.File;
import java.util.*;

public class main {

    public static void main(String[] args) {
        String inst = "n32-k5"; // or "n80-k10"

        File instFile = new File(inst + ".vrp");
        VRPInstance instance = VRPIO.loadInstance(instFile);


        VRPSolution nnSol = Utility.nearestNeighbourHeuristic(instance);
        System.out.println("total cost: " + Utility.calculateTotalCost(nnSol, instance));
        nnSol.setTotalCost(Utility.calculateTotalCost(nnSol, instance));

        VRPSolution svSol = Utility.savingsHeuristic(instance);
        System.out.println("total cost: " + Utility.calculateTotalCost(svSol, instance));
        svSol.setTotalCost(Utility.calculateTotalCost(svSol, instance));


        VRPIO.writeSolution(nnSol, inst + "nn.sol2");
        VRPIO.writeSolution(svSol, inst + "sv.sol2");

    }
}
