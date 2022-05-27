import java.io.File;

public class main {

    public static void main(String[] args) {
        String inst = "n32-k5"; // or "n80-k10"

        File instFile = new File(inst + ".vrp");
        VRPInstance instance = VRPIO.loadInstance(instFile);


        VRPSolution nnSol = Utility.nearestNeighbourHeuristic(instance);
        System.out.println(Utility.calculateTotalCost(nnSol, instance));
        nnSol.setTotalCost(Utility.calculateTotalCost(nnSol, instance));

//        VRPSolution svSol = Utility.savingsHeuristic(instance);
//        svSol.setTotalCost(Utility.calculateTotalCost(svSol, instance));
        double a = 0.0;
        double b = Math.round(a * 100.0) / 100.0;
        System.out.println(b);

        //VRPIO.writeSolution(nnSol, inst + "nn.sol");
        //VRPIO.writeSolution(svSol, inst + "sv.sol");
    }
}
