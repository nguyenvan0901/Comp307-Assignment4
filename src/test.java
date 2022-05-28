import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(1);
        a.add(2);
        a.add(3);

        for(Integer i: a){
            System.out.println(i);
        }
        System.out.println(a.toString());
    }
}
