import java.util.*;

public class test {

    public static void main(String[] args) {
        TreeMap<Double, String> tm = new TreeMap<>();

        tm.put(15.5, "a");
        tm.put(10.2, "b");

        for(Map.Entry<Double, String> entry: tm.entrySet()){
            System.out.println(entry.toString());
        }
    }
}
